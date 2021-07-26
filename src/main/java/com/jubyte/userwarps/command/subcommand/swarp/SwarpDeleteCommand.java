package com.jubyte.userwarps.command.subcommand.swarp;

import com.jubyte.userwarps.database.UserwarpLocationSQL;
import com.jubyte.userwarps.database.location.LocationEntry;
import com.jubyte.userwarps.listener.PlayerJoinListener;
import com.jubyte.userwarps.util.ConfigData;
import com.jubyte.userwarps.util.SubCommand;
import com.jubyte.userwarps.util.inventory.DeleteConfirmGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Justin_SGD
 * @since 17.07.2021
 */

public class SwarpDeleteCommand implements SubCommand {

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(strings.length == 2 && strings[0].equalsIgnoreCase(getName())) {
            Player player = (Player) commandSender;
            String warpName = strings[1];
            int iD = PlayerJoinListener.PLAYER_INFORMATION_MAP.get(player.getUniqueId());
            if(UserwarpLocationSQL.playerLocationExists(warpName, iD)) {
                DeleteConfirmGUI deleteConfirmGUI = new DeleteConfirmGUI();
                deleteConfirmGUI.setWarpName(warpName);
                deleteConfirmGUI.buildGUI();
                deleteConfirmGUI.deleteConfirmList.put(player, new LocationEntry(iD, warpName, null, 0));
                player.openInventory(deleteConfirmGUI.getInventory());
            } else {
                player.sendMessage(ConfigData.SUBCOMMAND_SWARP_DELETE_NOT_EXISTS.replace("[warpName]", warpName));
            }
        }
    }
}