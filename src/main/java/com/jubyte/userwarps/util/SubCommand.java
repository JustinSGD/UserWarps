package com.jubyte.userwarps.util;

import org.bukkit.command.CommandSender;

/**
 * @author Justin_SGD
 * @since 15.07.2021
 */

public interface SubCommand {

    /**
     *
     * @return
     */
    String getName();

    /**
     *
     * @param commandSender
     * @param strings
     */
    void execute(CommandSender commandSender, String[] strings);

}
