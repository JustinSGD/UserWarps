package com.jubyte.userwarps.command.subcommand.swarp;

import com.jubyte.userwarps.database.UserwarpLocationSQL;
import com.jubyte.userwarps.database.location.LocationCache;
import com.jubyte.userwarps.util.ConfigData;
import com.jubyte.userwarps.util.SubCommand;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Justin_SGD
 * @since 15.07.2021
 */

public class SwarpTeleportCommand implements SubCommand {

    @Override
    public String getName() {
        return "tp";
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(strings. length == 2 && strings[0].equalsIgnoreCase(getName())) {
            Player player = (Player) commandSender;
            String warpName = strings[1];
            if(UserwarpLocationSQL.locationExists(warpName)) {
                Location location;
                if(LocationCache.existsLocation(warpName)) {
                    location = LocationCache.PLAYER_WARP_LOCATION_MAP.get(warpName).getLocation();
                } else {
                    LocationCache.addLocation(warpName, UserwarpLocationSQL.getLocation(warpName));
                    location = UserwarpLocationSQL.getLocation(warpName).getLocation();
                }
                if(location.getWorld() != null) {
                    player.teleport(location);
                    player.sendMessage(ConfigData.SUBCOMMAND_SWARP_TP_SUCCESSFUL.replace("[warpName]", warpName));
                    LocationCache.PLAYER_WARP_LOCATION_MAP.get(warpName).setUses();
                    LocationCache.PLAYER_WARP_LOCATION_MAP.get(warpName).setLastUse();
                } else {
                    player.sendMessage(ConfigData.SUBCOMMAND_SWARP_TP_WORLD_DONT_EXISTS);
                }
            } else {
                player.sendMessage(ConfigData.SUBCOMMAND_SWARP_TP_WARP_DONT_EXISTS.replace("[warpName]", warpName));
            }
        }
    }
}