package org.infotoast.dropsoverhaul.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.infotoast.dropsoverhaul.DropsOverhaul;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

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
                sender.sendMessage(colorize(plugin.getConfig().getString("Messages.No-Permission", "&cAccess denied.")));
                return true;
            }

            plugin.reloadConfig();
            sender.sendMessage(colorize(plugin.getConfig().getString("Messages.Command.Reloaded", "&aSuccessfully reloaded configuration.")));
            return true;
        }

        if (args[0].equalsIgnoreCase("killdrops")) {
            if (!sender.hasPermission("dropsoverhaul.killdrops")) {
                sender.sendMessage(colorize(plugin.getConfig().getString("Messages.No-Permission", "&cAccess denied.")));
                return true;
            }

            if (!(sender instanceof Player player)) {
                sender.sendMessage(colorize(plugin.getConfig().getString("Messages.Players-Only", "&cOnly players can use this command.")));
                return true;
            }

            int radius = plugin.getConfig().getInt("Settings.Kill-Drops-Radius");

            Collection<Item> nearbyItems = player.getWorld().getNearbyEntitiesByType(Item.class, player.getLocation(), radius);
            int itemAmount = nearbyItems.size();
            nearbyItems.forEach(Item::remove);

            player.sendMessage(colorize(plugin.getConfig().getString("Messages.Item-Delete", "&aSuccessfully removed %amount% items."))
                    .replace("%amount%", itemAmount + ""));
            return true;
        }

        sendHelp(sender);
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(colorize(plugin.getConfig().getString("Messages.Command.Help-Header", "&aDrops Overhaul Commands")));

        if (sender.hasPermission("dropsoverhaul.reload")) {
            sender.sendMessage(colorize(plugin.getConfig().getString("Messages.Command.Reload-Command", "&7/do reload &8- &eReloads the configuration.")));
        }

        if (sender.hasPermission("dropsoverhaul.killdrops")) {
            sender.sendMessage(colorize(plugin.getConfig().getString("Messages.Command.Kill-Drops-Command", "&7/do killdrops &8- &eKills nearby items.")));
        }
    }

    private String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
