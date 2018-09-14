package com.twigdoo.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class RequestParameterMapper {
    private static PropertyNamingStrategy.SnakeCaseStrategy strategy =
            new PropertyNamingStrategy.SnakeCaseStrategy();

    private final ObjectMapper objectMapper = ObjectMapperFactory.make();

    public <T> Map<String, String> writeToMap(T object) {
        try {
            Map<String, String> values = new LinkedHashMap<>();
            for (Field field : getDeclaredFields(object.getClass())) {
                field.setAccessible(true);
                Object value = field.get(object);
                if (value == null || Modifier.isTransient(field.getModifiers())) {
                    continue;
                }
                if (value instanceof Collection) {
                    Collection collection = (Collection) value;
                    if (!collection.isEmpty()) {
                        values.put(strategy.translate(field.getName()), toString(collection));
                    }
                } else {
                    values.put(strategy.translate(field.getName()), String.valueOf(value));
                }
            }
            return values;
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private String toString(Collection collection) {
        StringBuilder sb = new StringBuilder();
        for (Object item : collection) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(item);
        }
        return sb.toString();
    }

    public <T> String write(T object) {
        try {
            Map<String, String> params = writeToMap(object);
            StringBuilder paramString = new StringBuilder();
            for (Entry<String, String> entry : params.entrySet()) {
                paramString
                        .append(paramString.length() == 0 ? '?' : '&')
                        .append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                        .append('=')
                        .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            return paramString.toString();
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public <T> T read(URL url, Class<T> type) {
        try {
            Map<String, Object> params = splitQuery(url);
            return objectMapper.readValue(objectMapper.writeValueAsString(params), type);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private <T> List<Field> getDeclaredFields(Class<T> type) {
        List<Field> fields = new ArrayList<>();
        Class clazz = type;
        while (!clazz.equals(Object.class)) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private static Map<String, Object> splitQuery(URL url) throws UnsupportedEncodingException {
        Map<String, Object> queryPairs = new LinkedHashMap<>();
        String query = url.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
            String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
            if (key.endsWith("[]")) {
                key = key.substring(0, key.length() - 2);
                Set<String> set = (Set<String>) queryPairs.get(key);
                if (set == null) {
                    set = new TreeSet<>();
                    queryPairs.put(key, set);
                }
                set.add(value);
            } else {
                queryPairs.put(key, value);
            }
        }
        return queryPairs;
    }
}
