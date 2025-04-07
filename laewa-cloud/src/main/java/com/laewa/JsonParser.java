package com.laewa;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonParser {

    // Entry point
    public static Object parse(String json) {
        json = json.trim();
        if (json.startsWith("{")) {
            return parseObject(json);
        } else if (json.startsWith("[")) {
            return parseArray(json);
        }
        throw new RuntimeException("Invalid JSON input");
    }

    // Parse a JSON object into Map
    private static Map<String, Object> parseObject(String json) {
        Map<String, Object> map = new LinkedHashMap<>();
        json = json.trim().substring(1, json.length() - 1).trim(); // remove { }

        Pattern pattern = Pattern.compile("\"(.*?)\"\\s*:\\s*(\".*?\"|\\d+|\\{.*?}|\\[.*?])");
        Matcher matcher = pattern.matcher(json);

        while (matcher.find()) {
            String key = matcher.group(1);
            String rawValue = matcher.group(2).trim();
            map.put(key, parseValue(rawValue));
        }
        return map;
    }

    // Parse a JSON array into List
    private static List<Object> parseArray(String json) {
        List<Object> list = new ArrayList<>();
        json = json.trim().substring(1, json.length() - 1).trim(); // remove [ ]

        Pattern pattern = Pattern.compile("(\".*?\"|\\d+|\\{.*?}|\\[.*?])");
        Matcher matcher = pattern.matcher(json);

        while (matcher.find()) {
            String rawValue = matcher.group(1).trim();
            list.add(parseValue(rawValue));
        }
        return list;
    }

    // Parse a value (string, number, object, array)
    private static Object parseValue(String raw) {
        if (raw.startsWith("\"")) {
            return raw.substring(1, raw.length() - 1); // remove quotes
        } else if (raw.startsWith("{")) {
            return parseObject(raw);
        } else if (raw.startsWith("[")) {
            return parseArray(raw);
        } else {
            return Integer.parseInt(raw); // assume it's a number
        }
    }

    public static void main(String[] args) {
        String json = "{\"name\":\"Alice\",\"age\":30,\"hobbies\":[\"reading\",\"gaming\"],\"address\":{\"city\":\"Wonderland\",\"zip\":12345}}";

        Object result = parse(json);
        System.out.println(result);

        // Example of casting
        Map<String, Object> object = (Map<String, Object>) result;
        System.out.println("Name: " + object.get("name"));
        System.out.println("Hobbies: " + object.get("hobbies"));
    }
}