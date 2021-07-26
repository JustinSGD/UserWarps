package com.jubyte.userwarps.util;

import com.jubyte.userwarps.UserWarps;

/**
 * @author Justin_SGD
 * @since 25.07.2021
 */

public class ConfigData {

    //Messages
    public static String PREFIX = UserWarps.getPlugin().getConfig().getString("Messages.Prefix");
    
    public static String COMMAND_SWARP_HELP = UserWarps.getPlugin().getConfig().getString("Messages.SWarpCommand.Help")
            .replace("[prefix]", PREFIX);
    public static String COMMAND_SWARP_COOLDWON = UserWarps.getPlugin().getConfig().getString("Messages.SWarpCommand.Cooldown")
            .replace("[prefix]", PREFIX);
    public static String COMMAND_SWARP_DEACTIVATED = UserWarps.getPlugin().getConfig().getString("Messages.SWarpCommand.Deactivated")
            .replace("[prefix]", PREFIX);

    public static String SUBCOMMAND_SWARP_CREATE_CREATED = UserWarps.getPlugin().getConfig().getString("Messages.SWarpCreateSubcommand.WarpCreated")
            .replace("[prefix]", PREFIX);
    public static String SUBCOMMAND_SWARP_CREATE_ALREADY_EXISTS = UserWarps.getPlugin().getConfig().getString("Messages.SWarpCreateSubcommand.WarpAlreadyExists")
            .replace("[prefix]", PREFIX);
    public static String SUBCOMMAND_SWARP_CREATE_NAME_TOO_LONG = UserWarps.getPlugin().getConfig().getString("Messages.SWarpCreateSubcommand.WarpNameTooLong")
            .replace("[prefix]", PREFIX);

    public static String SUBCOMMAND_SWARP_DELETE_NOT_EXISTS = UserWarps.getPlugin().getConfig().getString("Messages.SWarpDeleteSubcommand.WarpNotExists")
            .replace("[prefix]", PREFIX);

    public static String SUBCOMMAND_SWARP_LIST_WARPS = UserWarps.getPlugin().getConfig().getString("Messages.SWarpListSubcommand.Warps")
            .replace("[prefix]", PREFIX);
    public static String SUBCOMMAND_SWARP_LIST_APPEND = UserWarps.getPlugin().getConfig().getString("Messages.SWarpListSubcommand.Append")
            .replace("[prefix]", PREFIX);
    public static String SUBCOMMAND_SWARP_LIST_NO_WARPS = UserWarps.getPlugin().getConfig().getString("Messages.SWarpListSubcommand.NoWarps")
            .replace("[prefix]", PREFIX);

    public static String SUBCOMMAND_SWARP_TP_SUCCESSFUL = UserWarps.getPlugin().getConfig().getString("Messages.SWarpTpSubcommand.TpSuccessful")
            .replace("[prefix]", PREFIX);
    public static String SUBCOMMAND_SWARP_TP_WARP_DONT_EXISTS = UserWarps.getPlugin().getConfig().getString("Messages.SWarpTpSubcommand.WarpDontExists")
            .replace("[prefix]", PREFIX);
    public static String SUBCOMMAND_SWARP_TP_WORLD_DONT_EXISTS = UserWarps.getPlugin().getConfig().getString("Messages.SWarpTpSubcommand.WorldDontExists")
            .replace("[prefix]", PREFIX);

    public static String COMMAND_SWARP_ADMIN_PERMISSION = UserWarps.getPlugin().getConfig().getString("Messages.SWarpAdminCommand.Permission")
            .replace("[prefix]", PREFIX);
    public static String COMMAND_SWARP_ADMIN_NO_PERMS = UserWarps.getPlugin().getConfig().getString("Messages.SWarpAdminCommand.NoPerms")
            .replace("[prefix]", PREFIX);

    public static String SUBCOMMAND_SWARP_ADMIN_CREATE_CREATED = UserWarps.getPlugin().getConfig().getString("Messages.SWarpAdminCreateSubcommand.WarpCreated")
            .replace("[prefix]", PREFIX);
    public static String SUBCOMMAND_SWARP_ADMIN_CREATE_ALREADY_EXISTS = UserWarps.getPlugin().getConfig().getString("Messages.SWarpAdminCreateSubcommand.WarpAlreadyExists")
            .replace("[prefix]", PREFIX);
    public static String SUBCOMMAND_SWARP_ADMIN_CREATE_NAME_TOO_LONG = UserWarps.getPlugin().getConfig().getString("Messages.SWarpAdminCreateSubcommand.WarpNameTooLong")
            .replace("[prefix]", PREFIX);

    public static String SUBCOMMAND_SWARP_ADMIN_DELETE_WARP_DONT_EXISTS = UserWarps.getPlugin().getConfig().getString("Messages.SWarpAdminDeleteSubcommand.WarpNotExists")
            .replace("[prefix]", PREFIX);

    public static String SUBCOMMAND_SWARP_ADMIN_LIST_WARPS = UserWarps.getPlugin().getConfig().getString("Messages.SWarpAdminListSubcommand.Warps")
            .replace("[prefix]", PREFIX);
    public static String SUBCOMMAND_SWARP_ADMIN_LIST_APPEND = UserWarps.getPlugin().getConfig().getString("Messages.SWarpAdminListSubcommand.Append")
            .replace("[prefix]", PREFIX);
    public static String SUBCOMMAND_SWARP_ADMIN_LIST_NO_WARPS = UserWarps.getPlugin().getConfig().getString("Messages.SWarpAdminListSubcommand.NoWarps")
            .replace("[prefix]", PREFIX);

    public static String SUBCOMMAND_SWARP_ADMIN_TP_SUCCESSFUL = UserWarps.getPlugin().getConfig().getString("Messages.SWarpAdminTpSubcommand.TpSuccessful")
            .replace("[prefix]", PREFIX);
    public static String SUBCOMMAND_SWARP_ADMIN_TP_WARP_DONT_EXISTS = UserWarps.getPlugin().getConfig().getString("Messages.SWarpAdminTpSubcommand.WarpDontExists")
            .replace("[prefix]", PREFIX);
    public static String SUBCOMMAND_SWARP_ADMIN_TP_WORLD_DONT_EXISTS = UserWarps.getPlugin().getConfig().getString("Messages.SWarpAdminTpSubcommand.WorldDontExists")
            .replace("[prefix]", PREFIX);

    public static String DELETE_CONFIRM_GUI_DELETE_DELETED = UserWarps.getPlugin().getConfig().getString("Messages.DeleteConfirmGUI.WarpDeleted")
            .replace("[prefix]", PREFIX);
    public static String DELETE_CONFIRM_GUI_DELETE_WARP_DONT_EXISTS = UserWarps.getPlugin().getConfig().getString("Messages.DeleteConfirmGUI.WarpNotExists")
            .replace("[prefix]", PREFIX);

    //Items
    public static String FILL_ITEM_NAME = UserWarps.getPlugin().getConfig().getString("Inventory.Items.FillItem.Name");
    public static String PREVIOUS_GUI_ITEM = UserWarps.getPlugin().getConfig().getString("Inventory.Items.PreviousGUIItem.Name");
    public static String PREVIOUS_PAGE_ITEM = UserWarps.getPlugin().getConfig().getString("Inventory.Items.PreviousPageItem.Name");
    public static String NEXT_PAGE_ITEM = UserWarps.getPlugin().getConfig().getString("Inventory.Items.NextPageItem.Name");

    public static String ALPHABETICAL_GUI_ITEM = UserWarps.getPlugin().getConfig().getString("Inventory.MainGUI.Items.AlphabeticalGUI.Name");
    public static String LAST_USE_GUI_ITEM = UserWarps.getPlugin().getConfig().getString("Inventory.MainGUI.Items.LastUseGUI.Name");
    public static String TOP_USE_GUI_ITEM = UserWarps.getPlugin().getConfig().getString("Inventory.MainGUI.Items.TopUseGUI.Name");
    public static String EDIT_WARP_GUI_ITEM = UserWarps.getPlugin().getConfig().getString("Inventory.MainGUI.Items.EditWarpGUI.Name");

    public static String ALPHABETICAL_GUI_ITEMS_WARP = UserWarps.getPlugin().getConfig().getString("Inventory.AlphabeticalGUI.Items.Warps.Name");
    public static String LAST_USE_GUI_ITEMS_WARP = UserWarps.getPlugin().getConfig().getString("Inventory.LastUseGUI.Items.Warps.Name");
    public static String TOP_USE_GUI_ITEMS_WARP = UserWarps.getPlugin().getConfig().getString("Inventory.TopUseGUI.Items.Warps.Name");
    public static String EDIT_WARP_GUI_ITEMS_WARP = UserWarps.getPlugin().getConfig().getString("Inventory.EditWarpGUI.Items.Warps.Name");
    public static String ADMIN_GUI_ITEMS_WARP = UserWarps.getPlugin().getConfig().getString("Inventory.AdminGUI.Items.Warps.Name");

    public static String DELETE_CONFIRM_ITEMS_YES = UserWarps.getPlugin().getConfig().getString("Inventory.DeleteConfirmGUI.Items.YesItem.Name");
    public static String DELETE_CONFIRM_ITEMS_NO = UserWarps.getPlugin().getConfig().getString("Inventory.DeleteConfirmGUI.Items.NoItem.Name");

    //Inventories
    public static String MAIN_GUI_TITLE = UserWarps.getPlugin().getConfig().getString("Inventory.MainGUI.Title");

    public static String ALPHABETICAL_GUI_TITLE = UserWarps.getPlugin().getConfig().getString("Inventory.AlphabeticalGUI.Title");
    public static String LAST_USE_GUI_TITLE = UserWarps.getPlugin().getConfig().getString("Inventory.LastUseGUI.Title");
    public static String TOP_USE_GUI_TITLE = UserWarps.getPlugin().getConfig().getString("Inventory.TopUseGUI.Title");
    public static String EDIT_WARP_GUI_TITLE = UserWarps.getPlugin().getConfig().getString("Inventory.EditWarpGUI.Title");
    public static String DELETE_CONFIRM_GUI_TITLE = UserWarps.getPlugin().getConfig().getString("Inventory.DeleteConfirmGUI.Title");
    public static String ADMIN_GUI_TITLE = UserWarps.getPlugin().getConfig().getString("Inventory.AdminGUI.Title");

}