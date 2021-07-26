package com.jubyte.userwarps.listener;

import com.jubyte.userwarps.database.UserwarpPlayerSQL;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Justin_SGD
 * @since 13.07.2021
 */

public class PlayerJoinListener implements Listener {

    public static Map<UUID, Integer> PLAYER_INFORMATION_MAP = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UserwarpPlayerSQL.createPlayer(player.getUniqueId());
        int iD = UserwarpPlayerSQL.getIdByPlayer(player.getUniqueId());

        if(!PLAYER_INFORMATION_MAP.containsKey(player.getUniqueId()))
            PLAYER_INFORMATION_MAP.put(player.getUniqueId(), iD);
    }

}