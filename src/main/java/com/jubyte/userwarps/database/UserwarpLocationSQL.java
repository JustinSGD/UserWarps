package com.jubyte.userwarps.database;

import com.jubyte.userwarps.UserWarps;
import com.jubyte.userwarps.database.location.LocationEntry;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Justin_SGD
 * @since 13.07.2021
 */

public class UserwarpLocationSQL {

    public static boolean locationExists(String warpName) {
        String qry = "SELECT * FROM user_warp_location WHERE location_name=?";
        try{
            PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(qry);
            statement.setString(1, warpName);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }catch (SQLException e) {
            return false;
        }
    }

    public static boolean playerLocationExists(String warpName, int playerID) {
        String qry = "SELECT * FROM user_warp_location WHERE location_name=? AND player_id=?";
        try{
            PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(qry);
            statement.setString(1, warpName);
            statement.setInt(2, playerID);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }catch (SQLException e) {
            return false;
        }
    }

    public static void createLocation(int iD, String warpName, String world, double locationX, double locationY, double locationZ, float yaw, float pitch) {
        if(!locationExists(warpName)) {
            String query = "INSERT INTO user_warp_location (player_id, location_name, location_world, location_x, location_y, location_z, location_yaw, location_pitch, uses) " +
                    "VALUES (?,?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
                statement.setInt(1, iD);
                statement.setString(2, warpName);
                statement.setString(3, world);
                statement.setDouble(4, locationX);
                statement.setDouble(5, locationY);
                statement.setDouble(6, locationZ);
                statement.setFloat(7, yaw);
                statement.setFloat(8, pitch);
                statement.setInt(9, 0);
                statement.execute();
                Bukkit.getLogger().info("The UserWarp " + warpName + " was created by ID " + iD + ".");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static LocationEntry getLocation(String warpName) {
        LocationEntry locationEntry = null;
        String query = "SELECT * FROM user_warp_location WHERE location_name=?";
        try {
            PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
            statement.setString(1, warpName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                locationEntry = new LocationEntry(resultSet.getInt("player_id"),
                        resultSet.getString("location_name"),
                        new Location(Bukkit.getWorld(resultSet.getString("location_world")),
                                resultSet.getDouble("location_x"),
                                resultSet.getDouble("location_y"),
                                resultSet.getDouble("location_z"),
                                resultSet.getFloat("location_yaw"),
                                resultSet.getFloat("location_pitch")),
                        resultSet.getInt("uses"));
            }
            return locationEntry;
        } catch (SQLException e) {
            return null;
        }
    }

    public static void updateLocation(int iD, String warpName, String world, double locationX, double locationY, double locationZ, float yaw, float pitch, int uses, long lastUse) {
        if(!locationExists(warpName)) {
            String query = "UPDATE user_warp_location SET location_name=?, location_world=?, location_x=?, location_y=?, location_z=?, " +
                    "location_yaw=?, location_pitch=?, uses=?, last_use=?) WHERE player_id=?";
            try {
                PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
                statement.setString(1, warpName);
                statement.setString(2, world);
                statement.setDouble(3, locationX);
                statement.setDouble(4, locationY);
                statement.setDouble(5, locationZ);
                statement.setFloat(6, yaw);
                statement.setFloat(7, pitch);
                statement.setInt(8, uses);
                statement.setLong(9, lastUse);
                statement.setInt(10, iD);
                statement.execute();
                Bukkit.getLogger().info("The UserWarp " + warpName + " was edited.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<LocationEntry> findWarpsByWorld(String world) {
        List<LocationEntry> usesList = new ArrayList<>();
        String query = "SELECT player_id, location_name, location_world, location_x, location_y, location_z, location_yaw, location_pitch, uses, last_use FROM user_warp_location WHERE location_world";
        try {
            PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
            statement.setObject(1, world);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int playerID = resultSet.getInt("player_id");
                String warpName = resultSet.getString("location_name");
                Location location = new Location(Bukkit.getWorld(resultSet.getString("location_world")),
                        resultSet.getDouble("location_x"),
                        resultSet.getDouble("location_y"),
                        resultSet.getDouble("location_z"),
                        resultSet.getFloat("location_yaw"),
                        resultSet.getFloat("location_pitch"));
                int uses = resultSet.getInt("uses");
                usesList.add(new LocationEntry(playerID, warpName, location, uses));
            }

            return usesList;
        } catch (SQLException e) {
            return null;
        }
    }

    public static void removeLocation(int playerID, String warpName) {
        String query = "DELETE FROM user_warp_location WHERE player_id=? AND location_name=?";
        try {
            PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
            statement.setInt(1, playerID);
            statement.setString(2, warpName);
            statement.executeUpdate();
            Bukkit.getLogger().info("The UserWarp " + warpName + " was deleted by ID " + playerID + ".");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, LocationEntry> loadPlayerWarpLocations(int iD) {
        Map<String, LocationEntry> playerLocations = new HashMap<>();
        String query = "SELECT * FROM user_warp_location WHERE player_id=?";
        try {
            PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
            statement.setString(1, String.valueOf(iD));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String warpName = resultSet.getString("location_name");
                String world = resultSet.getString("location_world");
                double locationX = resultSet.getDouble("location_x");
                double locationY = resultSet.getDouble("location_y");
                double locationZ = resultSet.getDouble("location_z");
                float yaw = resultSet.getFloat("location_yaw");
                float pitch = resultSet.getFloat("location_pitch");
                playerLocations.put(warpName, new LocationEntry(iD,
                        warpName,
                        new Location(Bukkit.getWorld(world), locationX, locationY, locationZ, yaw, pitch),
                        resultSet.getInt("uses")));
            }

            return playerLocations;
        } catch (SQLException e) {
            return null;
        }
    }

    public static List<LocationEntry> getWarps() {
        List<LocationEntry> usesList = new ArrayList<>();
        String query = "SELECT * FROM user_warp_location";
        try {
            PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int playerID = resultSet.getInt("player_id");
                String warpName = resultSet.getString("location_name");
                Location location = new Location(Bukkit.getWorld(resultSet.getString("location_world")),
                        resultSet.getDouble("location_x"),
                        resultSet.getDouble("location_y"),
                        resultSet.getDouble("location_z"),
                        resultSet.getFloat("location_yaw"),
                        resultSet.getFloat("location_pitch"));
                int uses = resultSet.getInt("uses");
                usesList.add(new LocationEntry(playerID, warpName, location, uses));
                System.out.println(usesList.size());
            }
            return usesList;
        } catch (SQLException e) {
            return null;
        }
    }

    public static List<String> getTopWarpsByPlayer(int playerID) {
        List<String> usesList = new ArrayList<>();
        String query = "SELECT location_name FROM user_warp_location WHERE player_id=? ORDER BY uses DESC LIMIT 27";
        try {
            PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
            statement.setInt(1, playerID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                usesList.add(resultSet.getString("location_name"));
            }

            return usesList;
        } catch (SQLException e) {
            return null;
        }
    }

    public static Map<String, Integer> getTopWarps() {
        Map<String, Integer> topWarps = new HashMap<>();
        String query = "SELECT location_name FROM user_warp_location ORDER BY uses DESC";
        int ranking = 0;
        try {
            PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ranking++;
                topWarps.put(resultSet.getString("location_name"), ranking);
            }

            return topWarps;
        } catch (SQLException e) {
            return null;
        }
    }

}