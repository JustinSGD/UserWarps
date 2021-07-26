package com.jubyte.userwarps.command.subcommand.swarpadmin;

import com.jubyte.userwarps.database.UserwarpLocationSQL;
import com.jubyte.userwarps.database.UserwarpPlayerSQL;
import com.jubyte.userwarps.database.location.LocationCache;
import com.jubyte.userwarps.database.location.LocationEntry;
import com.jubyte.userwarps.listener.PlayerJoinListener;
import com.jubyte.userwarps.util.ConfigData;
import com.jubyte.userwarps.util.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Justin_SGD
 * @since 26.07.2021
 */

public class SwarpAdminCreateCommand implements SubCommand {

    @Override
    public String getName() {
        return "create";
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
            if(warpName.length() <= 25) {
                Location location = player.getLocation();
                if(!UserwarpLocationSQL.locationExists(warpName)) {
                    UserwarpLocationSQL.createLocation(iD,
                            warpName,
                            location.getWorld().getName(),
                            location.getX(),
                            location.getY(),
                            location.getZ(),
                            location.getYaw(),
                            location.getPitch());
                    if (!LocationCache.existsLocation(warpName)) {
                        LocationCache.addLocation(warpName, new LocationEntry(iD, warpName, location, 0, 0));
                    }
                    player.sendMessage(ConfigData.SUBCOMMAND_SWARP_ADMIN_CREATE_CREATED.replace("[warpName]", warpName));
                } else {
                    player.sendMessage(ConfigData.SUBCOMMAND_SWARP_ADMIN_CREATE_ALREADY_EXISTS.replace("[warpName]", warpName));
                }
            } else {
                player.sendMessage(ConfigData.SUBCOMMAND_SWARP_ADMIN_CREATE_NAME_TOO_LONG);
            }
        }
    }
}