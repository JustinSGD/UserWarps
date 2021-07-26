package com.jubyte.userwarps.command.subcommand.swarp;

import com.jubyte.userwarps.database.UserwarpLocationSQL;
import com.jubyte.userwarps.listener.PlayerJoinListener;
import com.jubyte.userwarps.util.ConfigData;
import com.jubyte.userwarps.util.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Justin_SGD
 * @since 17.07.2021
 */

public class SwarpListCommand implements SubCommand {

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(strings.length == 1 && strings[0].equalsIgnoreCase(getName())) {
            Player player = (Player) commandSender;
            int iD = PlayerJoinListener.PLAYER_INFORMATION_MAP.get(player.getUniqueId());
            if (UserwarpLocationSQL.loadPlayerWarpLocations(iD).size() != 0) {
                StringBuilder warpName = new StringBuilder();
                boolean first = true;
                for (String worlds : UserwarpLocationSQL.loadPlayerWarpLocations(iD).keySet()) {
                    if (!first) {
                        warpName.append(ConfigData.SUBCOMMAND_SWARP_LIST_APPEND);
                    }
                    warpName.append(worlds);
                    first = false;
                }
                player.sendMessage(ConfigData.SUBCOMMAND_SWARP_LIST_WARPS.replace("[warps]", warpName));
            } else {
                player.sendMessage(ConfigData.SUBCOMMAND_SWARP_LIST_NO_WARPS);
            }
        }
    }
}