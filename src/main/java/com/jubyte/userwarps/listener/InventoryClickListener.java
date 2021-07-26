package com.jubyte.userwarps.listener;

import com.jubyte.userwarps.util.ConfigData;
import com.jubyte.userwarps.util.inventory.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @author Justin_SGD
 * @since 18.07.2021
 */

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.isLeftClick() || event.isRightClick()) {
            if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null) {
                if (event.getView().getTitle().equalsIgnoreCase(ConfigData.MAIN_GUI_TITLE)) {
                    event.setCancelled(true);
                    if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ConfigData.ALPHABETICAL_GUI_ITEM)) {
                        AlphabeticalGUI alphabeticalGUI = new AlphabeticalGUI();
                        alphabeticalGUI.buildGUI();
                        player.openInventory(alphabeticalGUI.getInventory());
                    } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ConfigData.LAST_USE_GUI_ITEM)) {
                        LastUseGUI lastUseGUI = new LastUseGUI();
                        lastUseGUI.buildGUI(player);
                        player.openInventory(lastUseGUI.getInventory());
                    } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ConfigData.TOP_USE_GUI_ITEM)) {
                        TopUseGUI topUseGUI = new TopUseGUI();
                        topUseGUI.buildGUI();
                        player.openInventory(topUseGUI.getInventory());
                    } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ConfigData.EDIT_WARP_GUI_ITEM)) {
                        EditWarpGUI editWarpGUI = new EditWarpGUI();
                        editWarpGUI.buildGUI(player);
                        player.openInventory(editWarpGUI.getInventory());
                    }
                } else if(event.getView().getTopInventory().getHolder() instanceof AlphabeticalGUI) {
                    event.setCancelled(true);
                    if(event.getClickedInventory() != null) {
                        if(event.getClickedInventory().getItem(event.getSlot()) != null) {
                            AlphabeticalGUI alphabeticalGUI = (AlphabeticalGUI) event.getView().getTopInventory().getHolder();
                            alphabeticalGUI.handleEvent(event);
                        }
                    }
                } else if(event.getView().getTopInventory().getHolder() instanceof LastUseGUI) {
                    event.setCancelled(true);
                    if(event.getClickedInventory() != null) {
                        if(event.getClickedInventory().getItem(event.getSlot()) != null) {
                            LastUseGUI lastUseGUI = (LastUseGUI) event.getView().getTopInventory().getHolder();
                            lastUseGUI.handleEvent(event);
                        }
                    }
                } else if(event.getView().getTopInventory().getHolder() instanceof TopUseGUI) {
                    event.setCancelled(true);
                    if (event.getClickedInventory() != null) {
                        if (event.getClickedInventory().getItem(event.getSlot()) != null) {
                            TopUseGUI topUseGUI = (TopUseGUI) event.getView().getTopInventory().getHolder();
                            topUseGUI.handleEvent(event);
                        }
                    }
                } else if(event.getView().getTopInventory().getHolder() instanceof EditWarpGUI) {
                    event.setCancelled(true);
                    if (event.getClickedInventory() != null) {
                        if (event.getClickedInventory().getItem(event.getSlot()) != null) {
                            EditWarpGUI editWarpGUI = (EditWarpGUI) event.getView().getTopInventory().getHolder();
                            editWarpGUI.handleEvent(event);
                        }
                    }
                } else if(event.getView().getTopInventory().getHolder() instanceof DeleteConfirmGUI) {
                    event.setCancelled(true);
                    if (event.getClickedInventory() != null) {
                        if (event.getClickedInventory().getItem(event.getSlot()) != null) {
                            DeleteConfirmGUI deleteConfirmGUI = (DeleteConfirmGUI) event.getView().getTopInventory().getHolder();
                            deleteConfirmGUI.handleEvent(event);
                        }
                    }
                } else if(event.getView().getTopInventory().getHolder() instanceof AdminGUI) {
                    event.setCancelled(true);
                    if (event.getClickedInventory() != null) {
                        if (event.getClickedInventory().getItem(event.getSlot()) != null) {
                            AdminGUI adminGUI = (AdminGUI) event.getView().getTopInventory().getHolder();
                            adminGUI.handleEvent(event);
                        }
                    }
                }
            }
        }
    }
}