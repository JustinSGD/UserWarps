package com.jubyte.userwarps.util.inventory;

import com.jubyte.userwarps.database.UserwarpLocationSQL;
import com.jubyte.userwarps.database.location.LocationCache;
import com.jubyte.userwarps.database.location.LocationEntry;
import com.jubyte.userwarps.util.ConfigData;
import com.jubyte.userwarps.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Justin_SGD
 * @since 25.07.2021
 */

public class DeleteConfirmGUI extends Menu {
    public Map<Player, LocationEntry> deleteConfirmList = new HashMap<>();

    private String warpName = null;

    @Override
    public String getTitle() {
        return ConfigData.DELETE_CONFIRM_GUI_TITLE.replace("[warp]", getWarp());
    }

    @Override
    public int getSlots() {
        return 9*3;
    }

    @Override
    public void handleEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equalsIgnoreCase(getTitle())) {
            if(event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null) {
                event.setCancelled(true);
                if (event.getCurrentItem().getType() == Material.LIME_DYE && event.getSlot() == 11) {
                    if(deleteConfirmList.containsKey(player)) {
                        LocationEntry locationEntry = deleteConfirmList.get(player);
                        warpName = locationEntry.getWarpName();
                        int iD = locationEntry.getPlayerID();
                        if(UserwarpLocationSQL.playerLocationExists(warpName, iD)) {
                            LocationCache.PLAYER_WARP_LOCATION_MAP.remove(warpName);
                            deleteConfirmList.remove(player);
                            UserwarpLocationSQL.removeLocation(iD, warpName);
                            player.closeInventory();
                            player.sendMessage(ConfigData.DELETE_CONFIRM_GUI_DELETE_DELETED.replace("[warpName]", warpName));
                        } else {
                            player.closeInventory();
                            player.sendMessage(ConfigData.DELETE_CONFIRM_GUI_DELETE_WARP_DONT_EXISTS.replace("[warpName]", warpName));
                        }
                    }
                } else if(event.getCurrentItem().getType() == Material.RED_DYE && event.getSlot() == 15) {
                    deleteConfirmList.remove(player);
                    player.closeInventory();
                }
            }
        }
    }

    public String getWarp() {
        return warpName;
    }

    public void setWarpName(String warp) {
        warpName = warp;
    }

    public void buildGUI() {
        super.build();
        addContent();
    }

    public void addContent() {
        for (int i = 0; i < this.getSlots(); i++) {
            inventory.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(ConfigData.FILL_ITEM_NAME).build());
        }
        inventory.setItem(11, new ItemBuilder(Material.LIME_DYE).setDisplayName(ConfigData.DELETE_CONFIRM_ITEMS_YES).build());
        inventory.setItem(15, new ItemBuilder(Material.RED_DYE).setDisplayName(ConfigData.DELETE_CONFIRM_ITEMS_NO).build());
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}