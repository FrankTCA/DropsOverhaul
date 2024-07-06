package org.infotoast.dropsoverhaul;

import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.infotoast.dropsoverhaul.command.DropsOverhaulCommand;
import org.infotoast.dropsoverhaul.command.KillDropsCommand;

import java.io.File;
import java.util.HashMap;

public class DropsOverhaul extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // Handle older configurations
        if (!this.getConfig().isInt("Config-Version")) {
            saveOldConfigAndMakeNew();
        }
        if (this.getConfig().getInt("Config-Version") != 1) {
            saveOldConfigAndMakeNew();
        }

        getCommand("dropsoverhaul").setExecutor(new DropsOverhaulCommand(this));
        getCommand("killdrops").setExecutor(new KillDropsCommand(this));
        getServer().getPluginManager().registerEvents(this, this);

        getLogger().info("DropsOverhaul has been enabled!");
    }

    private void saveOldConfigAndMakeNew() {
        File oldConfigFile = new File(this.getDataFolder(), "config.yml");
        oldConfigFile.renameTo(new File(getDataFolder(), "config-old.yml"));
        this.saveResource("config.yml", true);
        getLogger().warning("NOTICE: DropsOverhaul Config has been changed.");
        getLogger().warning("PREVIOUS UPDATE INCLUDES BREAKING CONFIG CHANGES.");
        getLogger().warning("Your old configuration has been saved to config-old.yml");
        getLogger().warning("If you have made changes, they will need to be remade in new config format.");
        getLogger().warning("The server will be started with default configuration values.");
    }

    @EventHandler
    public void onBlockDrop(BlockDropItemEvent event) {
        if (!getConfig().getStringList("Settings.Drops-To-Remove").contains(event.getBlockState().getType().name())) {
            return;
        }

        event.setCancelled(true);

        ItemStack[] items = event.getItems().stream().map(Item::getItemStack).toArray(ItemStack[]::new);
        HashMap<Integer, ItemStack> leftOver = event.getPlayer().getInventory().addItem(items);

        if (leftOver.isEmpty() || getConfig().getBoolean("Settings.Full-Inventory-Delete")) {
            return;
        }

        leftOver.values().forEach(item -> event.getPlayer().getWorld().dropItemNaturally(event.getPlayer().getLocation(), item));
    }

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
