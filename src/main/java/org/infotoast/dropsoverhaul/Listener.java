package org.infotoast.dropsoverhaul;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Listener implements org.bukkit.event.Listener {
    private Plugin pl;
    public Listener(Plugin pl) {
        this.pl = pl;
    }
    private Random rand = new Random();
    ItemLists lists = new ItemLists();

    private ItemStack getResultItemStack(Player breaker, Material mat) {
        ItemStack item;
        item = new ItemStack(mat);
        switch (mat) {
            case GRASS_BLOCK:
                if (breaker.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH))
                    item = new ItemStack(Material.GRASS_BLOCK);
                else
                    item = new ItemStack(Material.DIRT);
                break;
            case STONE:
                if (breaker.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH))
                    item = new ItemStack(Material.STONE);
                else
                    item = new ItemStack(Material.COBBLESTONE);
                break;
            case GRAVEL:
                if (new Random().nextInt(8) == 0)
                    item = new ItemStack(Material.FLINT);
                else
                    item = new ItemStack(Material.GRAVEL);
                break;
        }
        return item;
    }

    private void processToolDamage(Player breaker) {
        if (lists.isTool(breaker.getInventory().getItemInMainHand().getType()))
            if (breaker.getInventory().getItemInMainHand().getItemMeta() instanceof Damageable) {
                if (!(breaker.getInventory().getItemInMainHand().containsEnchantment(Enchantment.UNBREAKING) && rand.nextInt(100) > (100 / (breaker.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.UNBREAKING) + 1))))
                    breaker.getInventory().getItemInMainHand().setDurability((short)(((Damageable)breaker.getInventory().getItemInMainHand().getItemMeta()).getDamage() + 1));
                if (breaker.getInventory().getItemInMainHand().getDurability() >= breaker.getInventory().getItemInMainHand().getType().getMaxDurability() && breaker.getInventory().getItemInMainHand().getType().getMaxDurability() > 0)
                    breaker.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent evt) {
        Player breaker = evt.getPlayer();
        Block blocc = evt.getBlock();
        Material mat = blocc.getType();
        if (lists.isDeletable(mat)) {
            if (breaker.getGameMode() == GameMode.SURVIVAL) {
                ItemStack item;
                if (lists.requiresPickaxe(mat))
                    if (lists.toolBreaksStone(breaker.getInventory().getItemInMainHand().getType()))
                        item = getResultItemStack(breaker, mat);
                    else { evt.setCancelled(true); blocc.setType(Material.AIR, true); return; }
                else
                    item = getResultItemStack(breaker, mat);

                if (breaker.getInventory().firstEmpty() == -1) {
                    System.out.println("Inventory is full.");
                    Material resultMat = item.getType();
                    if (breaker.getInventory().contains(resultMat)) {
                        System.out.println("Inventory contains valid item.");
                        ItemStack[] storageContents = breaker.getInventory().getStorageContents();
                        for (ItemStack stack : storageContents) {
                            if (stack.getType() == resultMat) {
                                if (stack.getAmount() < resultMat.getMaxStackSize()) {
                                    stack.add();
                                    blocc.setType(Material.AIR, true);
                                    processToolDamage(breaker);
                                    evt.setCancelled(true);
                                    return;
                                }
                            }
                        }
                    }
                    FileConfiguration conf = pl.getCustomConfig();
                    if (!conf.getBoolean("full-inventory-delete")) return;
                    blocc.setType(Material.AIR, true);
                    processToolDamage(breaker);
                    evt.setCancelled(true);
                    return;
                }

                breaker.getInventory().addItem(item);
                blocc.setType(Material.AIR, true);
                processToolDamage(breaker);
                evt.setCancelled(true);
            }
        }
    }
}
