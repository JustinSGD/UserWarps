package com.jubyte.userwarps.command.subcommand.swarp;

import com.jubyte.userwarps.database.UserwarpLocationSQL;
import com.jubyte.userwarps.database.location.LocationCache;
import com.jubyte.userwarps.database.location.LocationEntry;
import com.jubyte.userwarps.listener.PlayerJoinListener;
import com.jubyte.userwarps.util.ConfigData;
import com.jubyte.userwarps.util.SubCommand;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Justin_SGD
 * @since 17.07.2021
 */

public class SwarpCreateCommand implements SubCommand {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(strings.length == 2 && strings[0].equalsIgnoreCase(getName())) {
            Player player = (Player) commandSender;
            String warpName = strings[1];
            if(warpName.length() <= 25) {
                int iD = PlayerJoinListener.PLAYER_INFORMATION_MAP.get(player.getUniqueId());
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
                    player.sendMessage(ConfigData.SUBCOMMAND_SWARP_CREATE_CREATED.replace("[warpName]", warpName));
                } else {
                    player.sendMessage(ConfigData.SUBCOMMAND_SWARP_CREATE_ALREADY_EXISTS.replace("[warpName]", warpName));
                }
            } else {
                player.sendMessage(ConfigData.SUBCOMMAND_SWARP_CREATE_NAME_TOO_LONG);
            }
        }
    }
}