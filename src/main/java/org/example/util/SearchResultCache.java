package org.example.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SearchResultCache {
    private static final Map<String, String> cache = new ConcurrentHashMap<>();

    public static void setResult(String query, String result) {
        cache.put(query, result);
    }

    public static String getResult(String query) {
        return cache.get(query);
    }
}
