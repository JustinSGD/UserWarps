package com.jubyte.userwarps.command.subcommand.swarpadmin;

import com.jubyte.userwarps.database.UserwarpLocationSQL;
import com.jubyte.userwarps.database.UserwarpPlayerSQL;
import com.jubyte.userwarps.database.location.LocationEntry;
import com.jubyte.userwarps.listener.PlayerJoinListener;
import com.jubyte.userwarps.util.ConfigData;
import com.jubyte.userwarps.util.SubCommand;
import com.jubyte.userwarps.util.inventory.DeleteConfirmGUI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Justin_SGD
 * @since 26.07.2021
 */

public class SwarpAdminDeleteCommand implements SubCommand {

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(strings.length == 3 && strings[1].equalsIgnoreCase(getName())) {
            Player player = (Player) commandSender;
            OfflinePlayer targetPlayer;
            if(strings[0].length() == 36) {
                UUID uuid = UUID.fromString(strings[0]);
                targetPlayer = Bukkit.getOfflinePlayer(uuid);
            } else {
                String playerName = strings[0];
                targetPlayer = Bukkit.getOfflinePlayer(playerName);
            }
            String warpName = strings[2];
            int iD;
            if(PlayerJoinListener.PLAYER_INFORMATION_MAP.containsKey(targetPlayer.getUniqueId())) {
                iD = PlayerJoinListener.PLAYER_INFORMATION_MAP.get(targetPlayer.getUniqueId());
            } else {
                iD = UserwarpPlayerSQL.getIdByPlayer(targetPlayer.getUniqueId());
            }
            if(UserwarpLocationSQL.playerLocationExists(warpName, iD)) {
                DeleteConfirmGUI deleteConfirmGUI = new DeleteConfirmGUI();
                deleteConfirmGUI.setWarpName(warpName);
                deleteConfirmGUI.buildGUI();
                deleteConfirmGUI.deleteConfirmList.put(player, new LocationEntry(iD, warpName, null, 0, 0));
                player.openInventory(deleteConfirmGUI.getInventory());
            } else {
                player.sendMessage(ConfigData.SUBCOMMAND_SWARP_ADMIN_DELETE_WARP_DONT_EXISTS.replace("[warpName]", warpName));
            }
        }
    }
}