package com.jubyte.userwarps;

import com.jubyte.userwarps.commands.SwarpCommand;
import com.jubyte.userwarps.database.MySQL;
import com.jubyte.userwarps.listener.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * @author Justin_SGD
 * @since 13.07.2021
 */

public class UserWarps extends JavaPlugin {

    private static UserWarps plugin;

    private MySQL mySQL;

    @Override
    public void onEnable() {
        plugin = this;

        sendMessage("§aEnabled");

        loadConfig();
        loadMySQL();

        loadListener();
        loadCommands();

    }

    public void onDisable() {
        mySQL.closeConnection();

        sendMessage("§cDisable");

    }

    private void loadCommands() {
        getCommand("test").setExecutor(new SwarpCommand());
    }

    private void loadListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
    }

    private void sendMessage(String status) {
        Bukkit.getConsoleSender().sendMessage("§7================================================");
        Bukkit.getConsoleSender().sendMessage("§eUserWarps §7| §eStatus: " + status);
        Bukkit.getConsoleSender().sendMessage("§eUserWarps §7| §eVersion: §5" + getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("§eUserWarps §7| §eAuthors: §6" + getDescription().getAuthors());
        Bukkit.getConsoleSender().sendMessage("§7================================================");
    }

    private void loadConfig() {
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
    }

    private void loadMySQL() {
        mySQL = new MySQL();
        mySQL.createConnection();
        if(mySQL.isConnected()) {
            mySQL.update("CREATE TABLE IF NOT EXISTS `user_warp_player` (`player_id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY, `player_uuid` varchar(36) NOT NULL)");
            mySQL.update("CREATE TABLE IF NOT EXISTS `user_warp_location` (`player_id` int(11) NOT NULL, " +
                    "`location_name` varchar(25), " +
                    "`location_world` varchar(50), " +
                    "`location_x` double(50, 14) PRIMARY KEY, " +
                    "`location_y` double(25, 14), " +
                    "`location_z` double(50, 14), " +
                    "`location_yaw` float(50), " +
                    "`location_pitch` float(50), " +
                    "`uses` int(11))");
        }
    }

    public static UserWarps getPlugin() {
        return plugin;
    }

    public MySQL getMySQL() {
        return mySQL;
    }
}