package com.jubyte.userwarps.util.inventory;

import com.jubyte.userwarps.database.UserwarpLocationSQL;
import com.jubyte.userwarps.database.location.LocationEntry;
import com.jubyte.userwarps.listener.PlayerJoinListener;
import com.jubyte.userwarps.util.ConfigData;
import com.jubyte.userwarps.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Justin_SGD
 * @since 25.07.2021
 */

public class EditWarpGUI extends PaginatedMenu {

    @Override
    public String getTitle() {
        return ConfigData.EDIT_WARP_GUI_TITLE;
    }

    @Override
    public int getSlots() {
        return 9*4;
    }

    @Override
    public void handleEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int iD = PlayerJoinListener.PLAYER_INFORMATION_MAP.get(player.getUniqueId());
        Map<String, LocationEntry> warpList = new HashMap<>(UserwarpLocationSQL.loadPlayerWarpLocations(iD));
        if(event.getView().getTitle().equalsIgnoreCase(getTitle())) {
            if(event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null) {
                if (event.getSlot() >= 27) {
                    switch (event.getSlot()) {
                        case 27:
                            if (this.page != 0) {
                                this.page--;
                                buildGUI(player);
                                player.openInventory(this.inventory);
                            }
                            break;
                        case 31:
                            MainGUI.createInventory(player);
                            break;
                        case 35:
                            if (this.index + 1 < warpList.size()) {
                                this.page++;
                                buildGUI(player);
                                player.openInventory(this.inventory);
                            }
                            break;
                    }
                } else {
                    String warpName = event.getCurrentItem().getItemMeta().getDisplayName().substring(6);
                    if(event.getClick().isLeftClick()) {
                        player.closeInventory();
                        Bukkit.dispatchCommand(player, "swarp tp " + warpName);
                    } else if(event.getClick().isRightClick()) {
                        if (warpList.containsKey(warpName)) {
                            DeleteConfirmGUI deleteConfirmGUI = new DeleteConfirmGUI();
                            deleteConfirmGUI.setWarpName(warpName);
                            deleteConfirmGUI.buildGUI();
                            deleteConfirmGUI.deleteConfirmList.put(player, new LocationEntry(iD, warpName, null, 0, 0));
                            player.openInventory(deleteConfirmGUI.getInventory());
                        }
                    }
                }
            }
        }
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    public void buildGUI(Player player) {
        super.build();
        fill();
        int iD = PlayerJoinListener.PLAYER_INFORMATION_MAP.get(player.getUniqueId());
        Map<String, LocationEntry> warpList = new HashMap<>(UserwarpLocationSQL.loadPlayerWarpLocations(iD));
        List<String> warpNames = new ArrayList<>(warpList.keySet());
        warpNames.sort((o1, o2) -> {
            if (o1 == null && o2 == null) {
                return 0;
            }
            if (o1 == null) {
                return -1;
            }
            return o1.toLowerCase().compareTo(o2.toLowerCase());
        });
        addContent(warpNames);
        setButtons(warpNames);
    }

    public void addContent(List<String> playerWarps) {
        String warpItem = ConfigData.EDIT_WARP_GUI_ITEMS_WARP;
        for (int i = 0; i < getMaxItemsPerPage(); i++) {
            this.index = getMaxItemsPerPage() * this.page + i;
            if (this.index >= playerWarps.size()) break;
            if (playerWarps.get(this.index) != null) {
                this.inventory.addItem(new ItemBuilder(Material.BOOK).setDisplayName(warpItem.replace("[warpName]", playerWarps.get(this.index)))
                        .setLore("§a", "§aZur Location teleportieren §7(Linksklick)", "§cWarp löschen §7(Rechtsklick)").build());
            }
        }
    }

    public void setButtons(List<String> warpList) {
        if(page >= 0 && this.index + 1 < warpList.size()) {
            this.inventory.setItem(inventory.getSize() - 1, nextPage);
        }
        if(page >= 1) {
            this.inventory.setItem(inventory.getSize() - 9, previousPage);
        }
        this.inventory.setItem(inventory.getSize() - 5, previousGUI);
    }
}