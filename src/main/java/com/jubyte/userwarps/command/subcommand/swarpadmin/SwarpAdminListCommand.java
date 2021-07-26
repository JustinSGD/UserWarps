package com.jubyte.userwarps.command.subcommand.swarpadmin;

import com.jubyte.userwarps.database.UserwarpLocationSQL;
import com.jubyte.userwarps.database.UserwarpPlayerSQL;
import com.jubyte.userwarps.listener.PlayerJoinListener;
import com.jubyte.userwarps.util.ConfigData;
import com.jubyte.userwarps.util.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Justin_SGD
 * @since 26.07.2021
 */

public class SwarpAdminListCommand implements SubCommand {

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(strings.length == 2 && strings[1].equalsIgnoreCase(getName())) {
            Player player = (Player) commandSender;
            OfflinePlayer targetPlayer;
            if(strings[0].length() == 36) {
                UUID uuid = UUID.fromString(strings[0]);
                targetPlayer = Bukkit.getOfflinePlayer(uuid);
            } else {
                String playerName = strings[0];
                targetPlayer = Bukkit.getOfflinePlayer(playerName);
            }
            int iD;
            if(PlayerJoinListener.PLAYER_INFORMATION_MAP.containsKey(targetPlayer.getUniqueId())) {
                iD = PlayerJoinListener.PLAYER_INFORMATION_MAP.get(targetPlayer.getUniqueId());
            } else {
                iD = UserwarpPlayerSQL.getIdByPlayer(targetPlayer.getUniqueId());
            }
            if (UserwarpLocationSQL.loadPlayerWarpLocations(iD).size() != 0) {
                StringBuilder warpName = new StringBuilder();
                boolean first = true;
                for (String worlds : UserwarpLocationSQL.loadPlayerWarpLocations(iD).keySet()) {
                    if (!first) {
                        warpName.append(ConfigData.SUBCOMMAND_SWARP_ADMIN_LIST_APPEND);
                    }
                    warpName.append(worlds);
                    first = false;
                }
                player.sendMessage(ConfigData.SUBCOMMAND_SWARP_ADMIN_LIST_WARPS.replace("[warps]", warpName).replace("[player]", targetPlayer.getName()));
            } else {
                player.sendMessage(ConfigData.SUBCOMMAND_SWARP_ADMIN_LIST_NO_WARPS);
            }
        }
    }
}