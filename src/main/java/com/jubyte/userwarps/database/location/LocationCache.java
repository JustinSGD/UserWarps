package com.jubyte.userwarps.database.location;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Justin_SGD
 * @since 23.07.2021
 */

public class LocationCache {

    public static Map<String, LocationEntry> PLAYER_WARP_LOCATION_MAP = new LinkedHashMap<>();

    public static void addLocation(String warpName, LocationEntry locationEntry) {
        if (PLAYER_WARP_LOCATION_MAP.size() == 750) {
            String firstKey = PLAYER_WARP_LOCATION_MAP.keySet().iterator().next();
            PLAYER_WARP_LOCATION_MAP.remove(firstKey);
        }
        PLAYER_WARP_LOCATION_MAP.put(warpName, locationEntry);
    }

    public static boolean existsLocation(String warpName) {
        return PLAYER_WARP_LOCATION_MAP.get(warpName) != null;
    }

}