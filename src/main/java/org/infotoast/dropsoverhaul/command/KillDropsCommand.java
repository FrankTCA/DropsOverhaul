package org.infotoast.dropsoverhaul.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.infotoast.dropsoverhaul.DropsOverhaul;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class KillDropsCommand implements CommandExecutor {
    private final DropsOverhaul plugin;

    public KillDropsCommand(DropsOverhaul plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("dropsoverhaul.killdrops")) {
            sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.No-Permission", "&cAccess denied.")));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.Players-Only", "&cOnly players can use this command.")));
            return true;
        }

        int radius = plugin.getConfig().getInt("Settings.Kill-Drops-Radius");

        if (args.length == 1) {
            radius = Integer.parseInt(args[0]);

            if (!sender.hasPermission("dropsoverhaul.killdrops.radius")) {
                sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.No-Permission-Killdrops-Radius", "&cYou don't have permission to specify a radius.")));
                return true;
            }
        }

        Collection<Item> nearbyItems = player.getWorld().getNearbyEntitiesByType(Item.class, player.getLocation(), radius);
        int itemAmount = nearbyItems.size();
        nearbyItems.forEach(Item::remove);

        player.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.Item-Delete", "&aSuccessfully removed %amount% items."))
                .replace("%amount%", itemAmount + ""));
        return true;
    }

}
