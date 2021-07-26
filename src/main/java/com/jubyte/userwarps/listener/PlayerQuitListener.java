package com.jubyte.userwarps.listener;

import com.jubyte.userwarps.util.inventory.DeleteConfirmGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Justin_SGD
 * @since 25.07.2021
 */

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        DeleteConfirmGUI deleteConfirmGUI = new DeleteConfirmGUI();
        deleteConfirmGUI.deleteConfirmList.remove(player);
    }
}