package com.jubyte.userwarps.util.inventory;

import com.jubyte.userwarps.util.ConfigData;
import com.jubyte.userwarps.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @author Justin_SGD
 * @since 23.07.2021
 */

public class MainGUI {

    public static void createInventory(Player player) {

        Inventory inventory = Bukkit.createInventory(null, 9*3, ConfigData.MAIN_GUI_TITLE);
        inventory.setItem(10, new ItemBuilder(Material.BOOKSHELF).setDisplayName(ConfigData.ALPHABETICAL_GUI_ITEM).build());
        inventory.setItem(12, new ItemBuilder(Material.CLOCK).setDisplayName(ConfigData.LAST_USE_GUI_ITEM).build());
        inventory.setItem(14, new ItemBuilder(Material.BOOK).setDisplayName(ConfigData.TOP_USE_GUI_ITEM).build());
        inventory.setItem(16, new ItemBuilder(Material.COMPARATOR).setDisplayName(ConfigData.EDIT_WARP_GUI_ITEM).build());

        player.openInventory(inventory);
    }

}