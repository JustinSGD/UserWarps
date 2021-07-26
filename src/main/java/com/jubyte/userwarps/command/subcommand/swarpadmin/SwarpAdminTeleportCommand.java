package com.jubyte.userwarps.command.subcommand.swarpadmin;

import com.jubyte.userwarps.database.UserwarpLocationSQL;
import com.jubyte.userwarps.database.location.LocationCache;
import com.jubyte.userwarps.util.ConfigData;
import com.jubyte.userwarps.util.SubCommand;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Justin_SGD
 * @since 26.07.2021
 */

public class SwarpAdminTeleportCommand implements SubCommand {

    @Override
    public String getName() {
        return "tp";
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(strings.length == 3 && strings[1].equalsIgnoreCase(getName())) {
            Player player = (Player) commandSender;
            String warpName = strings[2];
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
                    player.sendMessage(ConfigData.SUBCOMMAND_SWARP_ADMIN_TP_SUCCESSFUL.replace("[warpname]", warpName));
                } else {
                    player.sendMessage(ConfigData.SUBCOMMAND_SWARP_ADMIN_TP_WORLD_DONT_EXISTS);
                }
            } else {
                player.sendMessage(ConfigData.SUBCOMMAND_SWARP_ADMIN_TP_WARP_DONT_EXISTS.replace("[warpname]", warpName));
            }
        }
    }
}