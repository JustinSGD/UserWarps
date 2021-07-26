package com.jubyte.userwarps.command;

import com.jubyte.userwarps.command.subcommand.swarpadmin.SwarpAdminCreateCommand;
import com.jubyte.userwarps.command.subcommand.swarpadmin.SwarpAdminDeleteCommand;
import com.jubyte.userwarps.command.subcommand.swarpadmin.SwarpAdminListCommand;
import com.jubyte.userwarps.command.subcommand.swarpadmin.SwarpAdminTeleportCommand;
import com.jubyte.userwarps.database.UserwarpPlayerSQL;
import com.jubyte.userwarps.listener.PlayerJoinListener;
import com.jubyte.userwarps.util.ConfigData;
import com.jubyte.userwarps.util.SubCommand;
import com.jubyte.userwarps.util.inventory.AdminGUI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author Justin_SGD
 * @since 26.07.2021
 */

public class SwarpAdminCommand implements CommandExecutor, TabCompleter {

    private final Map<String, SubCommand> subCommandMap = new LinkedHashMap<>();

    public SwarpAdminCommand() {
        this.subCommandMap.put("delete", new SwarpAdminDeleteCommand());
        this.subCommandMap.put("tp", new SwarpAdminTeleportCommand());
        this.subCommandMap.put("list", new SwarpAdminListCommand());
        this.subCommandMap.put("create", new SwarpAdminCreateCommand());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.hasPermission(ConfigData.COMMAND_SWARP_ADMIN_PERMISSION)) {
                if (strings.length == 1) {
                    OfflinePlayer targetPlayer;
                    if (strings[0].length() == 36) {
                        UUID uuid = UUID.fromString(strings[0]);
                        targetPlayer = Bukkit.getOfflinePlayer(uuid);
                    } else {
                        String playerName = strings[0];
                        targetPlayer = Bukkit.getOfflinePlayer(playerName);
                    }
                    int iD;
                    if (PlayerJoinListener.PLAYER_INFORMATION_MAP.containsKey(targetPlayer.getUniqueId())) {
                        iD = PlayerJoinListener.PLAYER_INFORMATION_MAP.get(targetPlayer.getUniqueId());
                    } else {
                        iD = UserwarpPlayerSQL.getIdByPlayer(targetPlayer.getUniqueId());
                    }
                    AdminGUI adminGUI = new AdminGUI();
                    adminGUI.setPlayerID(iD);
                    adminGUI.setPlayerName(targetPlayer.getName());
                    adminGUI.buildGUI();
                    player.openInventory(adminGUI.getInventory());
                } else if (strings.length == 2) {
                    SubCommand subCommand = this.subCommandMap.get(strings[1].toLowerCase());
                    if (subCommand != null) {
                        subCommand.execute(player, strings);
                    }
                } else if (strings.length == 3) {
                    SubCommand subCommand = this.subCommandMap.get(strings[1].toLowerCase());
                    if (subCommand != null) {
                        subCommand.execute(player, strings);
                    }
                }
            } else {
                player.sendMessage(ConfigData.COMMAND_SWARP_ADMIN_NO_PERMS);
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 2) {
            return new ArrayList<>(subCommandMap.keySet());
        }
        return null;
    }
}