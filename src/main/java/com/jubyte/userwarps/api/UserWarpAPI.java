package com.jubyte.userwarps.api;

import com.jubyte.userwarps.UserWarps;
import com.jubyte.userwarps.database.UserwarpLocationSQL;
import com.jubyte.userwarps.database.UserwarpPlayerSQL;
import com.jubyte.userwarps.database.location.LocationEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Justin_SGD
 * @since 26.07.2021
 */

public class UserWarpAPI {

    public static void createUserWarp(int playerID, String warpName, String world, double locationX, double locationY, double locationZ, float yaw, float pitch) {
        UserwarpLocationSQL.createLocation(playerID, warpName, world, locationX, locationY, locationZ, yaw, pitch);
    }

    public static void deleteUserWarp(int playerID, String warpName) {
        UserwarpLocationSQL.removeLocation(playerID, warpName);
    }

    public static void updateUserWarp(int playerID, String warpName, String world, double locationX, double locationY, double locationZ, float yaw, float pitch, int uses, long lastUse) {
        UserwarpLocationSQL.updateLocation(playerID, warpName, world, locationX, locationY, locationZ, yaw, pitch, uses, lastUse);
    }

    public static Map<String, LocationEntry> getUserWarps(int playerID) {
        return UserwarpLocationSQL.loadPlayerWarpLocations(playerID);
    }

    public static LocationEntry getWarpByName(String warpName) {
        return UserwarpLocationSQL.getLocation(warpName);
    }

    public static List<LocationEntry> getUserWarpsByWorld(String worldName) {
        return UserwarpLocationSQL.findWarpsByWorld(worldName);
    }

    public static int getTotalWarps() {
        return UserwarpLocationSQL.getWarps().size();
    }

    public static int getTotalWarpsFromUser(int playerID) {
        return UserwarpLocationSQL.loadPlayerWarpLocations(playerID).size();
    }

    public static int getRanking(String warpName) {
        return UserwarpLocationSQL.getTopWarps().get(warpName);
    }

    public static Map<String, Integer> getRankingWarps() {
        return UserwarpLocationSQL.getTopWarps();
    }

    public static double getAveragePerPlayer() {
        List<Integer> totalWarps = new ArrayList<>();
        for(int playerID : UserwarpPlayerSQL.getPlayerIds()) {
            totalWarps.add(getTotalWarpsFromUser(playerID));
        }
        double totalAverage = 0;
        for(double totalWarp : totalWarps) {
            totalAverage = totalAverage + totalWarp;
        }
        return totalAverage/totalWarps.size();
    }

    public static void addDeniedWorld(String worldName) {
        UserWarps.getPlugin().getDeniedWarps().add(worldName);
    }

    public static void removeDeniedWorld(String worldName) {
        UserWarps.getPlugin().getDeniedWarps().remove(worldName);
    }

    public static void setWarpCommandStatus(boolean status) {
        UserWarps.getPlugin().setDisabledWarpCommand(status);
    }
}