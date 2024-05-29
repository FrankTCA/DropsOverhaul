package org.infotoast.dropsoverhaul;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.infotoast.dropsoverhaul.command.DropsOverhaulCommand;

import java.util.HashMap;

public class DropsOverhaul extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getCommand("dropsoverhaul").setExecutor(new DropsOverhaulCommand(this));
        getServer().getPluginManager().registerEvents(this, this);

        getLogger().info("DropsOverhaul has been enabled!");
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

}
