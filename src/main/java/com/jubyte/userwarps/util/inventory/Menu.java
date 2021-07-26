package com.jubyte.userwarps.util.inventory;

import com.jubyte.userwarps.util.ConfigData;
import com.jubyte.userwarps.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

/**
 * @author Justin_SGD
 * @since 19.07.2021
 */

public abstract class Menu implements InventoryHolder {
    protected Inventory inventory;

    public abstract String getTitle();

    public abstract int getSlots();

    public abstract void handleEvent(InventoryClickEvent event);

    public void fill() {
        ItemStack fill = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(ConfigData.FILL_ITEM_NAME).build();
        fill(this.inventory, fill);
    }

    private void fill(Inventory inv, ItemStack item) {
        for (int i = 0; i != inv.getSize(); i++) {
            if (i > getSlots() - 10 && i < getSlots()) {
                inv.setItem(i, item);
            }
        }
    }

    public void build() {
        this.inventory = Bukkit.createInventory(this, getSlots(), getTitle());
        fill();
    }
}