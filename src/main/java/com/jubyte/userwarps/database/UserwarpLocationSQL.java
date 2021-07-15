package com.jubyte.userwarps.database;

import com.jubyte.userwarps.UserWarps;
import com.jubyte.userwarps.database.location.LocationEntry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Justin_SGD
 * @since 13.07.2021
 */

public class UserwarpLocationSQL {

    public static boolean locationExists(int iD, String warpName) {
        String qry = "SELECT * FROM user_warp_location WHERE player_id=? AND location_name=?";
        try{
            PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(qry);
            statement.setString(1, String.valueOf(iD));
            statement.setString(2, warpName);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }catch (SQLException e) {
            return false;
        }
    }

    public static void createLocation(int iD, String warpName, String world, double x, double y, double z, float yaw, float pitch) {
        if(!locationExists(iD, warpName)) {
            String query = "INSERT INTO user_warp_location (player_id, location_name, location_world, location_x, location_y, location_z, location_yaw, location_pitch, uses) " +
                    "VALUES (?,?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
                statement.setInt(1, iD);
                statement.setString(2, warpName);
                statement.setString(3, world);
                statement.setDouble(4, x);
                statement.setDouble(5, y);
                statement.setDouble(6, z);
                statement.setFloat(7, yaw);
                statement.setFloat(8, pitch);
                statement.setInt(9, 0);
                statement.execute();
                Bukkit.getLogger().info("Der UserWarp " + warpName + " wurde von der ID " + iD + " erstellt.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<Integer, LocationEntry> loadPlayerWarpLocations(int iD) {
        Map<Integer, LocationEntry> playerLocations = new HashMap<>();
        String query = "SELECT * FROM user_warp_location WHERE player_id=?";
        try {
            PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
            statement.setString(1, String.valueOf(iD));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                iD++;
                String world = resultSet.getString("location_world");
                double locationX = resultSet.getDouble("location_x");
                double locationY = resultSet.getDouble("location_x");
                double locationZ = resultSet.getDouble("location_x");
                float yaw = resultSet.getFloat("location_yaw");
                float pitch = resultSet.getFloat("location_pitch");
                playerLocations.put(iD, new LocationEntry(resultSet.getString("location_name"),
                        new Location(Bukkit.getWorld(world), locationX, locationY, locationZ, yaw, pitch),
                        resultSet.getInt("uses")));
            }
            return playerLocations;
        } catch (SQLException e) {
            return null;
        }
    }

}