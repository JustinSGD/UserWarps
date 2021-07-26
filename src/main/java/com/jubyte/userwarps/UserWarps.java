package com.jubyte.userwarps;

import com.jubyte.userwarps.command.SwarpAdminCommand;
import com.jubyte.userwarps.command.SwarpCommand;
import com.jubyte.userwarps.database.MySQL;
import com.jubyte.userwarps.listener.InventoryClickListener;
import com.jubyte.userwarps.listener.PlayerJoinListener;
import com.jubyte.userwarps.listener.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Justin_SGD
 * @since 13.07.2021
 */

public class UserWarps extends JavaPlugin {

    private static UserWarps plugin;

    private MySQL mySQL;
    private List<String> deniedWarps = new ArrayList<>();
    private boolean disabledWarpCommand = false;

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
        getCommand("swarp").setExecutor(new SwarpCommand());
        getCommand("swarpadmin").setExecutor(new SwarpAdminCommand());

        getCommand("swarp").setTabCompleter(new SwarpCommand());
        getCommand("swarpadmin").setTabCompleter(new SwarpAdminCommand());
    }

    private void loadListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
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
            mySQL.update("CREATE TABLE IF NOT EXISTS `user_warp_player` (`player_id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY, `player_uuid` varchar(36) NOT NULL, " +
                    "`last_use_warps` varchar(1000))");
            mySQL.update("CREATE TABLE IF NOT EXISTS `user_warp_location` (`player_id` int(11) NOT NULL, " +
                    "`location_name` varchar(25) NOT NULL PRIMARY KEY, " +
                    "`location_world` varchar(50) NOT NULL, " +
                    "`location_x` double(50, 14) NOT NULL, " +
                    "`location_y` double(25, 14) NOT NULL, " +
                    "`location_z` double(50, 14) NOT NULL, " +
                    "`location_yaw` float(50) NOT NULL, " +
                    "`location_pitch` float(50) NOT NULL, " +
                    "`uses` int(11) NOT NULL, " +
                    "`last_use` bigint(50) NOT NULL)");
        }
    }

    public static UserWarps getPlugin() {
        return plugin;
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public List<String> getDeniedWarps() {
        return deniedWarps;
    }

    public boolean isDisabledWarpCommand() {
        return disabledWarpCommand;
    }

    public void setDisabledWarpCommand(boolean status) {
        disabledWarpCommand = status;
    }
}