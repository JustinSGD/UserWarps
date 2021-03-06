package com.jubyte.userwarps.database;

import com.google.gson.Gson;
import com.jubyte.userwarps.UserWarps;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Justin_SGD
 * @since 13.07.2021
 */

public class UserwarpPlayerSQL {

    public static boolean playerExists(UUID uuid) {
        String qry = "SELECT * FROM user_warp_player WHERE player_uuid=?";
        try{
            PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(qry);
            statement.setString(1, String.valueOf(uuid));
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }catch (SQLException e) {
            return false;
        }
    }

    public static void createPlayer(UUID uuid) {
        if(!playerExists(uuid)){
            String query = "INSERT INTO user_warp_player (player_id, player_uuid, last_use_warps) VALUES (?,?,?)";
            try{
                PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
                statement.setString(1, null);
                statement.setString(2, String.valueOf(uuid));
                statement.setString(3, new Gson().toJson(new ArrayList<>()));
                statement.execute();
                Bukkit.getLogger().info("The with the UUID " + uuid + " was created.");
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getIdByPlayer(UUID uuid) {
        String query = "SELECT * FROM user_warp_player WHERE player_uuid=?";
        int result = 0;
        try {
            PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
            statement.setString(1, String.valueOf(uuid));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("player_id");
            }
            return result;
        } catch (SQLException e) {
            return 0;
        }
    }

    public static String getPlayerById(int iD) {
        String query = "SELECT * FROM user_warp_player WHERE player_id=?";
        String result = "";
        try {
            PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
            statement.setString(1, String.valueOf(iD));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getString("player_uuid");
            }
            return result;
        } catch (SQLException e) {
            return null;
        }
    }

    public static List<Integer> getPlayerIds() {
        List<Integer> playerIds = new ArrayList<>();
        String query = "SELECT player_id FROM user_warp_player";
        try {
            PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int playerID = resultSet.getInt("player_id");
                playerIds.add(playerID);
            }

            return playerIds;
        } catch (SQLException e) {
            return null;
        }
    }

    public static List<String> getLastUseWarps(UUID uuid) {
        String query = "SELECT last_use_warps FROM user_warp_player WHERE player_uuid=?";
        List<String> lastUseWarps = new ArrayList<>();
        try {
            PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
            statement.setString(1, String.valueOf(uuid));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String warpName = resultSet.getString("last_use_warps");
                lastUseWarps = new Gson().fromJson(warpName, List.class);
            }
            return lastUseWarps;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addLastUseWarp(UUID uuid, String warpName) {
        List<String> lastUseWarps = new ArrayList<>(getLastUseWarps(uuid));
        lastUseWarps.remove(warpName);
        if(lastUseWarps.size() >= 27) {
            lastUseWarps.remove(0);
        }
        lastUseWarps.add(warpName);
        String query = "UPDATE user_warp_player SET last_use_warps=? WHERE player_uuid=?";
        try {
            PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
            statement.setString(1, new Gson().toJson(lastUseWarps));
            statement.setString(2, String.valueOf(uuid));
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}