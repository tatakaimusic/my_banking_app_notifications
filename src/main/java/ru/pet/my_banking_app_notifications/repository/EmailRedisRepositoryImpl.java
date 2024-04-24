package ru.pet.my_banking_app_notifications.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Repository
public class EmailRedisRepositoryImpl implements EmailRedisRepository {

    private final JedisPool jedisPool;

    @Value("${spring.data.redis.email-key-lifetime}")
    private long emailKeyLifeTime;

    @Autowired
    public EmailRedisRepositoryImpl(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public void save(String email, String code) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(email, emailKeyLifeTime, code);
        }
    }

}
