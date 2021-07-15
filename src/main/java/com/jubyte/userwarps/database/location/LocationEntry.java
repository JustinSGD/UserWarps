package com.jubyte.userwarps.database.location;

import org.bukkit.Location;

/**
 * @author Justin_SGD
 * @since 14.07.2021
 */

public class LocationEntry {

    private String warpName;
    private Location location;
    private int uses;

    public LocationEntry(String warpName, Location location, int uses) {
        this.warpName = warpName;
        this.location = location;
        this.uses = uses;

    }

    public String getWarpName() {
        return warpName;
    }

    public Location getLocation() {
        return location;
    }
}