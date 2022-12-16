package org.infotoast.dropsoverhaul;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ItemLists {
    private List<Material> deletables = new ArrayList<>();
    private List<Material> tools = new ArrayList<>();

    private List<Material> canBreakStone = new ArrayList<>();

    private List<Material> requiresPickaxe = new ArrayList<>();
    public ItemLists() {
        deletables.add(Material.DIRT);
        deletables.add(Material.STONE);
        deletables.add(Material.SAND);
        deletables.add(Material.SANDSTONE);
        deletables.add(Material.DIORITE);
        deletables.add(Material.GRANITE);
        deletables.add(Material.ANDESITE);
        deletables.add(Material.GRAVEL);
        deletables.add(Material.NETHERRACK);
        deletables.add(Material.BLACKSTONE);
        deletables.add(Material.BASALT);
        deletables.add(Material.GRASS_BLOCK);
        deletables.add(Material.TUFF);
        deletables.add(Material.CALCITE);
        deletables.add(Material.COBBLED_DEEPSLATE);
        deletables.add(Material.DEEPSLATE);
        deletables.add(Material.SMOOTH_BASALT);
        tools.add(Material.WOODEN_AXE);
        tools.add(Material.WOODEN_HOE);
        tools.add(Material.WOODEN_PICKAXE);
        tools.add(Material.WOODEN_SHOVEL);
        tools.add(Material.WOODEN_SWORD);
        tools.add(Material.STONE_AXE);
        tools.add(Material.STONE_HOE);
        tools.add(Material.STONE_PICKAXE);
        tools.add(Material.STONE_SHOVEL);
        tools.add(Material.STONE_SWORD);
        tools.add(Material.GOLDEN_AXE);
        tools.add(Material.GOLDEN_HOE);
        tools.add(Material.GOLDEN_PICKAXE);
        tools.add(Material.GOLDEN_SHOVEL);
        tools.add(Material.GOLDEN_SWORD);
        tools.add(Material.IRON_AXE);
        tools.add(Material.IRON_HOE);
        tools.add(Material.IRON_PICKAXE);
        tools.add(Material.IRON_SHOVEL);
        tools.add(Material.IRON_SWORD);
        tools.add(Material.DIAMOND_AXE);
        tools.add(Material.DIAMOND_HOE);
        tools.add(Material.DIAMOND_PICKAXE);
        tools.add(Material.DIAMOND_SHOVEL);
        tools.add(Material.DIAMOND_SWORD);
        tools.add(Material.NETHERITE_AXE);
        tools.add(Material.NETHERITE_HOE);
        tools.add(Material.NETHERITE_PICKAXE);
        tools.add(Material.NETHERITE_SHOVEL);
        tools.add(Material.NETHERITE_SWORD);
        tools.add(Material.TRIDENT);
        canBreakStone.add(Material.WOODEN_PICKAXE);
        canBreakStone.add(Material.STONE_PICKAXE);
        canBreakStone.add(Material.GOLDEN_PICKAXE);
        canBreakStone.add(Material.IRON_PICKAXE);
        canBreakStone.add(Material.DIAMOND_PICKAXE);
        canBreakStone.add(Material.NETHERITE_PICKAXE);
        requiresPickaxe.add(Material.STONE);
        requiresPickaxe.add(Material.SANDSTONE);
        requiresPickaxe.add(Material.ANDESITE);
        requiresPickaxe.add(Material.GRANITE);
        requiresPickaxe.add(Material.DIORITE);
        requiresPickaxe.add(Material.NETHERRACK);
        requiresPickaxe.add(Material.BLACKSTONE);
        requiresPickaxe.add(Material.BASALT);
        requiresPickaxe.add(Material.TUFF);
        requiresPickaxe.add(Material.CALCITE);
        requiresPickaxe.add(Material.COBBLED_DEEPSLATE);
        requiresPickaxe.add(Material.DEEPSLATE);
        requiresPickaxe.add(Material.SMOOTH_BASALT);
    }

    public boolean isDeletable(Material mat) {
        return deletables.contains(mat);
    }

    public boolean isTool(Material mat) {
        return tools.contains(mat);
    }

    public boolean toolBreaksStone(Material mat) {
        return canBreakStone.contains(mat);
    }

    public boolean requiresPickaxe(Material mat) {
        return requiresPickaxe.contains(mat);
    }
}
