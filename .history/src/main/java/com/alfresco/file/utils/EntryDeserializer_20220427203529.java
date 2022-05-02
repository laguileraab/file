package com.alfresco.file.utils;

import java.io.IOException;

import com.alfresco.file.models.Entry;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class EntryDeserializer extends StdDeserializer<Entry> {

    public EntryDeserializer() {
        this(null);
    }

    public EntryDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Entry deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        int id = (Integer) ((IntNode) node.get("id")).numberValue();
        String itemName = node.get("itemName").asText();
        int userId = (Integer) ((IntNode) node.get("createdBy")).numberValue();

        return new Entry(id, itemName, new User(userId, null));
    }
}