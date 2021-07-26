package com.jubyte.userwarps.database.location;

import com.jubyte.userwarps.UserWarps;
import org.bukkit.Location;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Justin_SGD
 * @since 14.07.2021
 */

public class LocationEntry {

    private final int playerID;
    private final String warpName;
    private final Location location;
    private int uses;
    private long lastUse;

    public LocationEntry(int playerID, String warpName, Location location, int uses, long lastUse) {
        this.playerID = playerID;
        this.warpName = warpName;
        this.location = location;
        this.uses = uses;
        this.lastUse = lastUse;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getWarpName() {
        return warpName;
    }

    public Location getLocation() {
        return location;
    }

    public long getLastUse() {
        return lastUse;
    }

    public void setUses() {
        String query = "UPDATE user_warp_location SET uses=? WHERE player_id=? AND location_name=?";
        try {
            PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
            statement.setInt(1, uses + 1);
            statement.setInt(2, playerID);
            statement.setString(3, warpName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.uses = uses + 1;
    }

    public void setLastUse() {
        String query = "UPDATE user_warp_location SET last_use=? WHERE player_id=? AND location_name=?";
        try {
            PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
            statement.setLong(1, System.currentTimeMillis());
            statement.setInt(2, playerID);
            statement.setString(3, warpName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.lastUse = System.currentTimeMillis();
    }
}