package org.infotoast.dropsoverhaul.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.infotoast.dropsoverhaul.DropsOverhaul;
import org.jetbrains.annotations.NotNull;

import java.util.List;

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

        if (args[0].equalsIgnoreCase("addBlock")) {
            if (!sender.hasPermission("dropsoverhaul.addblock")) {
                sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.No-Permission", "&cAccess denied.")));
                return true;
            }

            if (args.length == 2) {
                try {
                    Material.valueOf(args[1].toUpperCase());
                } catch (IllegalArgumentException e) {
                    doWhenBlockInvalid(sender, args[1]);
                    return true;
                } catch (NullPointerException e) {
                    doWhenBlockInvalid(sender, args[1]);
                    return true;
                }
                List<String> dropsList = plugin.getConfig().getStringList("Settings.Drops-To-Remove");
                dropsList.addLast(args[1].toUpperCase());
                plugin.getConfig().set("Settings.Drops-To-Remove", dropsList);
                plugin.saveConfig();
                sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.Command.Block-Added", "&aSuccessfully added block to list!")).replace("%block%", args[1].toUpperCase()));
                return true;
            } else {
                sendHelp(sender);
                sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.AddBlock-Usage", "&cPlease use /do addblock <block>.")));
            }
        }

        if (args[0].equalsIgnoreCase("removeBlock")) {
            if (!sender.hasPermission("dropsoverhaul.removeblock")) {
                sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.No-Permission", "&cAccess denied.")));
                return true;
            }
            List<String> dropsList = plugin.getConfig().getStringList("Settings.Drops-To-Remove");
            if (!dropsList.contains(args[1].toUpperCase())) {
                sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.Invalid-Block-Specified-RemoveBlock", "&cBlock is not on the list.")).replace("%block%", args[1].toUpperCase()));
                return true;
            }
            dropsList.remove(args[1].toUpperCase());
            plugin.getConfig().set("Settings.Drops-To-Remove", dropsList);
            plugin.saveConfig();
            sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.Command.Block-Removed", "&aBlock has been removed from the block list.")).replace("%block%", args[1].toUpperCase()));
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            if (!sender.hasPermission("dropsoverhaul.list")) {
                sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.No-Permission", "&cAccess denied.")));
                return true;
            }
            List<String> dropsList = plugin.getConfig().getStringList("Settings.Drops-To-Remove");
            sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messasges.Command.List-Header", "&aDrops Overhaul-affected blocks list")));
            for (String drop : dropsList) {
                sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.Command.List-Item", "&7-  &a%block%")).replace("%block%", drop));
            }
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
        if (sender.hasPermission("dropsoverhaul.addblock")) {
            sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.Command.AddBlock-Command", "&7/do addblock <block> &8- &eAdds block to list of blocks for dropsoverhaul to affect.")));
        }
        if (sender.hasPermission("dropsoverhaul.removeblock")) {
            sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.Command.RemoveBlock-Command", "&7/do removeblock <block> &8- &eRemoves block from the list.")));
        }
        if (sender.hasPermission("dropsoverhaul.list")) {
            sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.Command.List-Command")));
        }
    }

    private void doWhenBlockInvalid(CommandSender sender, String block) {
        sendHelp(sender);
        sender.sendMessage(DropsOverhaul.colorize(plugin.getConfig().getString("Messages.Invalid-Block-Specified-AddBlock", "&cThat is not a valid block!")).replace("%block%", block));
    }
}
