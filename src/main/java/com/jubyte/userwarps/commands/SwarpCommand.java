package com.jubyte.userwarps.commands;

import com.jubyte.userwarps.UserWarps;
import com.jubyte.userwarps.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * @author Justin_SGD
 * @since 13.07.2021
 */

public class SwarpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            String prefix = UserWarps.getPlugin().getConfig().getString("Messages.Prefix");
            String helpMessage = UserWarps.getPlugin().getConfig().getString("Messages.SWarpCommand.Help").replace("[prefix]", prefix);
            if(strings.length == 0) {
                createInventory(player);
            } else {
                player.sendMessage(helpMessage);
            }
        }
        return false;
    }

    public void createInventory(Player player) {
        String inventoryTitle = UserWarps.getPlugin().getConfig().getString("Inventory.Main.Title");
        String itemBookshelf = UserWarps.getPlugin().getConfig().getString("Inventory.Main.Items.Bookshelf.Name");
        String itemClock = UserWarps.getPlugin().getConfig().getString("Inventory.Main.Items.Clock.Name");
        String itemBook = UserWarps.getPlugin().getConfig().getString("Inventory.Main.Items.Book.Name");
        String itemComparator = UserWarps.getPlugin().getConfig().getString("Inventory.Main.Items.Comparator.Name");

        Inventory inventory = Bukkit.createInventory(null, 9*3, inventoryTitle);
        inventory.setItem(10, new ItemBuilder(Material.BOOKSHELF).setDisplayName(itemBookshelf).build());
        inventory.setItem(12, new ItemBuilder(Material.CLOCK).setDisplayName(itemClock).build());
        inventory.setItem(14, new ItemBuilder(Material.BOOK).setDisplayName(itemBook).build());
        inventory.setItem(16, new ItemBuilder(Material.COMPARATOR).setDisplayName(itemComparator).build());

        player.openInventory(inventory);
    }
}