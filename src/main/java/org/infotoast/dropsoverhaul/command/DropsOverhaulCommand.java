package org.infotoast.dropsoverhaul.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.infotoast.dropsoverhaul.DropsOverhaul;
import org.jetbrains.annotations.NotNull;

public class DropsOverhaulCommand implements CommandExecutor {

    private final DropsOverhaul plugin;

    public DropsOverhaulCommand(DropsOverhaul plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("dropsoverhaul.reload")) {
                sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.No-Permission", "&cAccess denied.")));
                return true;
            }

            plugin.reloadConfig();
            sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.Command.Reloaded", "&aSuccessfully reloaded configuration.")));
            return true;
        }

        sendHelp(sender);
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.Command.Help-Header", "&aDrops Overhaul Commands")));

        if (sender.hasPermission("dropsoverhaul.reload")) {
            sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.Command.Reload-Command", "&7/do reload &8- &eReloads the configuration.")));
        }
    }

}
