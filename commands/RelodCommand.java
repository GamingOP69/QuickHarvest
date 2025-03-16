package com.quickharvest.commands;

import com.quickharvest.QuickHarvest;
import com.quickharvest.utils.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    private final QuickHarvest plugin;

    public ReloadCommand(QuickHarvest plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("quickharvest.reload")) {
            sender.sendMessage(plugin.getConfigManager().getNoPermissionMessage());
            return true;
        }

        plugin.getConfigManager().reload();
        sender.sendMessage("§aQuickHarvest config reloaded!");
        return true;
    }
}