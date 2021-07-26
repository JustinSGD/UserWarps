package com.jubyte.userwarps.util.inventory;

import com.jubyte.userwarps.database.UserwarpLocationSQL;
import com.jubyte.userwarps.database.UserwarpPlayerSQL;
import com.jubyte.userwarps.listener.PlayerJoinListener;
import com.jubyte.userwarps.util.ConfigData;
import com.jubyte.userwarps.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Justin_SGD
 * @since 20.07.2021
 */

public class LastUseGUI extends Menu {

    @Override
    public String getTitle() {
        return ConfigData.LAST_USE_GUI_TITLE;
    }

    @Override
    public int getSlots() {
        return 9*4;
    }

    @Override
    public void handleEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equalsIgnoreCase(getTitle())) {
            if(event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null) {
                event.setCancelled(true);
                if (event.getCurrentItem().getType() == Material.BARRIER && event.getSlot() == getSlots() -5 ) {
                    MainGUI.createInventory(player);
                } else if(event.getSlot() < 27) {
                    int iD = PlayerJoinListener.PLAYER_INFORMATION_MAP.get(player.getUniqueId());
                    List<String> warpNames = new ArrayList<>(UserwarpLocationSQL.getTopWarpsByPlayer(iD));
                    for(String warpName : warpNames) {
                        String warpNameItem = event.getCurrentItem().getItemMeta().getDisplayName().substring(6);
                        if(warpName.equalsIgnoreCase(warpNameItem)) {
                            player.closeInventory();
                            Bukkit.dispatchCommand(player, "swarp tp " + warpName);
                        }
                    }
                }
            }
        }
    }

    public void buildGUI(Player player) {
        build();
        createInventory(player);
    }

    public void createInventory(Player player) {
        if(getTitle() == null) return;
        List<String> warpNames = UserwarpPlayerSQL.getLastUseWarps(player.getUniqueId());
        Collections.reverse(warpNames);
        String warpItem = ConfigData.LAST_USE_GUI_ITEMS_WARP;
        for (int i = 0; i < warpNames.size(); i++) {
            for(String warpName : warpNames)
                inventory.setItem(i++, new ItemBuilder(Material.BOOK).setDisplayName(warpItem.replace("[warpName]", warpName)).build());
        }

        inventory.setItem(inventory.getSize() - 5, new ItemBuilder(Material.BARRIER).setDisplayName(ConfigData.PREVIOUS_GUI_ITEM).build());
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}