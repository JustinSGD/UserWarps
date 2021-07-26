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

    public LocationEntry(int playerID, String warpName, Location location, int uses) {
        this.playerID = playerID;
        this.warpName = warpName;
        this.location = location;
        this.uses = uses;
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

    public int getUses() {
        return uses;
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
}