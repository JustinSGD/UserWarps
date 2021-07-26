package com.jubyte.userwarps.util.inventory;

import com.jubyte.userwarps.database.UserwarpLocationSQL;
import com.jubyte.userwarps.database.UserwarpPlayerSQL;
import com.jubyte.userwarps.database.location.LocationEntry;
import com.jubyte.userwarps.util.ConfigData;
import com.jubyte.userwarps.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * @author Justin_SGD
 * @since 20.07.2021
 */

public class TopUseGUI extends PaginatedMenu {
    private final List<LocationEntry> warpList = new ArrayList<>(UserwarpLocationSQL.getWarps());

    @Override
    public String getTitle() {
        return ConfigData.TOP_USE_GUI_TITLE;
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(event.getView().getTitle().equalsIgnoreCase(getTitle())) {
            if(event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null) {
                if (event.getSlot() >= 27) {
                    switch (event.getSlot()) {
                        case 27:
                            if (this.page != 0) {
                                this.page--;
                                buildGUI();
                                player.openInventory(this.inventory);
                            }
                            break;
                        case 31:
                            MainGUI.createInventory(player);
                            break;
                        case 35:
                            if (this.index + 1 < warpList.size()) {
                                this.page++;
                                buildGUI();
                                player.openInventory(this.inventory);
                            }
                            break;
                    }
                } else {
                    for(LocationEntry entry : warpList) {
                        String warpName = event.getCurrentItem().getItemMeta().getDisplayName().substring(6);
                        if(entry.getWarpName().equalsIgnoreCase(warpName)) {
                            player.closeInventory();
                            Bukkit.dispatchCommand(player, "swarp tp " + warpName);
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

    public void buildGUI() {
        build();
        warpList.sort((Comparator.comparing(LocationEntry::getLastUse).reversed()));
        addContent(warpList);
        setButtons(warpList);
    }

    public void addContent(List<LocationEntry> warpList) {
        String warpItem = ConfigData.TOP_USE_GUI_ITEMS_WARP;
        for (int i = 0; i < getMaxItemsPerPage(); i++) {
            this.index = getMaxItemsPerPage() * this.page + i;
            if (this.index >= warpList.size()) break;
            if (warpList.get(this.index) != null) {
                UUID uuid = UUID.fromString(UserwarpPlayerSQL.getPlayerById(warpList.get(this.index).getPlayerID()));
                OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(uuid);
                this.inventory.addItem(new ItemBuilder(Material.BOOK).setDisplayName(warpItem.replace("[warpName]", warpList.get(this.index).getWarpName()))
                        .setLore("§7Warp von: §6" + offlinePlayer.getName()).build());
            }
        }
    }

    public void setButtons(List<LocationEntry> warpList) {
        if(page >= 0 && this.index + 1 < warpList.size()) {
            this.inventory.setItem(inventory.getSize() - 1, nextPage);
        }
        if(page >= 1) {
            this.inventory.setItem(inventory.getSize() - 9, previousPage);
        }
        this.inventory.setItem(inventory.getSize() - 5, previousGUI);
    }
}