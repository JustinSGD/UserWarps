package com.jubyte.userwarps.util.inventory;

import com.jubyte.userwarps.util.ConfigData;
import com.jubyte.userwarps.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author Justin_SGD
 * @since 19.07.2021
 */

public abstract class PaginatedMenu extends Menu {
    protected int page = 0;
    protected int maxItemsPerPage = this.getSlots()-9;
    protected int index = 0;

    protected final ItemStack previousPage = new ItemBuilder(Material.ARROW).setDisplayName(ConfigData.PREVIOUS_PAGE_ITEM).build();
    protected final ItemStack nextPage = new ItemBuilder(Material.ARROW).setDisplayName(ConfigData.NEXT_PAGE_ITEM).build();
    protected final ItemStack previousGUI = new ItemBuilder(Material.BARRIER).setDisplayName(ConfigData.PREVIOUS_GUI_ITEM).build();

    public void build() {
        super.build();
        fill();
    }

    public int getMaxItemsPerPage() {
        return this.maxItemsPerPage;
    }
}