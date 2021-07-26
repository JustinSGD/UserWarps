package com.jubyte.userwarps.command;

import com.jubyte.userwarps.UserWarps;
import com.jubyte.userwarps.command.subcommand.swarp.SwarpCreateCommand;
import com.jubyte.userwarps.command.subcommand.swarp.SwarpDeleteCommand;
import com.jubyte.userwarps.command.subcommand.swarp.SwarpListCommand;
import com.jubyte.userwarps.command.subcommand.swarp.SwarpTeleportCommand;
import com.jubyte.userwarps.util.ConfigData;
import com.jubyte.userwarps.util.SubCommand;
import com.jubyte.userwarps.util.inventory.MainGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Justin_SGD
 * @since 13.07.2021
 */

public class SwarpCommand implements CommandExecutor, TabCompleter {

    /**
     * @author TheRealDomm
     * For other projects
     */

    private final Map<String, SubCommand> subCommandMap = new LinkedHashMap<>();

    private final List<Player> guiPlayer = new ArrayList<>();

    public SwarpCommand() {
        //this.register(SwarpTeleportCommand.class, SwarpTeleportCommand.class);
        this.subCommandMap.put("tp", new SwarpTeleportCommand());
        this.subCommandMap.put("list", new SwarpListCommand());
        this.subCommandMap.put("create", new SwarpCreateCommand());
        this.subCommandMap.put("delete", new SwarpDeleteCommand());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(!UserWarps.getPlugin().isDisabledWarpCommand()) {
                if (strings.length == 0) {
                    if (!guiPlayer.contains(player)) {
                        MainGUI.createInventory(player);
                        guiPlayer.add(player);
                        Bukkit.getScheduler().runTaskLater(UserWarps.getPlugin(),
                                () -> guiPlayer.remove(player), 15 * 20);
                    } else {
                        player.sendMessage(ConfigData.COMMAND_SWARP_COOLDWON);
                    }
                } else if (strings.length == 1) {
                    SubCommand subCommand = this.subCommandMap.get(strings[0].toLowerCase());
                    if (subCommand != null) {
                        subCommand.execute(commandSender, strings);
                    }
                } else if (strings.length == 2) {
                    SubCommand subCommand = this.subCommandMap.get(strings[0].toLowerCase());
                    if (subCommand != null) {
                        subCommand.execute(commandSender, strings);
                    }
                } else {
                    player.sendMessage(ConfigData.COMMAND_SWARP_HELP);
                }
            } else {
                player.sendMessage(ConfigData.COMMAND_SWARP_DEACTIVATED);
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        if(strings.length == 1) {
            return new ArrayList<>(subCommandMap.keySet());
        }
        return null;
    }

    /**
     * @author TheRealDomm
     * For other projects
     */

    /*private void register(Class<? extends SubCommand>... classes) {
        for (Class<? extends SubCommand> clazz : classes) {
            try {
                if (clazz.getDeclaredConstructors()[0].getParameterCount() != 0) {
                    Bukkit.getLogger().warning("Class " + clazz.getSimpleName()
                            + " does not have a no args constructor");
                    continue;
                }
                SubCommand subCommand = clazz.newInstance();
                this.subCommandMap.put(subCommand.getName().toLowerCase(), subCommand);
            } catch (InstantiationException | IllegalAccessException e) {
                Bukkit.getLogger().warning("Could not register " + clazz.getSimpleName());
            }
        }
    }*/
}