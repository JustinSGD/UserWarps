package com.jubyte.userwarps.listener;

import com.jubyte.userwarps.database.UserwarpPlayerSQL;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author Justin_SGD
 * @since 13.07.2021
 */

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UserwarpPlayerSQL.createPlayer(player.getUniqueId());
    }

}