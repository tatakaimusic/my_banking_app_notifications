package ru.pet.my_banking_app_notifications.config;

import com.jcabi.xml.XML;

public final class TextXpath {

    private final XML xml;
    private final String node;

    public TextXpath(XML xml, String node) {
        this.xml = xml;
        this.node = node;
    }

    @Override
    public String toString() {
        return this.xml.nodes(this.node)
                .get(0)
                .xpath("text()")
                .get(0);
    }
}
