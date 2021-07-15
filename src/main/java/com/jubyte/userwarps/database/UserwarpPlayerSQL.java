package com.jubyte.userwarps.database;

import com.jubyte.userwarps.UserWarps;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            String query = "INSERT INTO user_warp_player (player_id, player_uuid) VALUES (?,?)";
            try{
                PreparedStatement statement = UserWarps.getPlugin().getMySQL().getConnection().prepareStatement(query);
                statement.setString(1, null);
                statement.setString(2, String.valueOf(uuid));
                statement.execute();
                Bukkit.getLogger().info("Der Spieler mit der UUID " + uuid + " wurde erstellt.");
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
            System.out.println("Result: " + result);
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
            System.out.println("Result: " + result);
            return result;
        } catch (SQLException e) {
            return null;
        }
    }

}