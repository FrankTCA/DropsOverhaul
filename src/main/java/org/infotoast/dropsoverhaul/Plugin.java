package org.infotoast.dropsoverhaul;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

public class Plugin extends JavaPlugin {
    private File customConfigFile;
    private FileConfiguration customConfig;
    public static Logger logger;
    @Override
    public void onEnable() {
        logger = getLogger();
        createCustomConfig();
        getServer().getPluginManager().registerEvents(new Listener(this), this);
        logger.info("DropsOverhaul has been enabled!");
    }

    public FileConfiguration getCustomConfig() {
        return customConfig;
    }

    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "config.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
        customConfig.load(customConfigFile);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("killdrops")) {
            boolean permDefault = customConfig.getBoolean("default-everyone-killdrops");
            if (permDefault || sender.hasPermission("dropsoverhaul.killdrops")) {
                int radius = customConfig.getInt("killdrops-radius");
                String cmd = "execute as " + sender.getName() + " at " + sender.getName() + " run kill @e[type=item,distance=.." + radius + "]";
                getServer().dispatchCommand(getServer().getConsoleSender(), cmd);
                return true;
            }
            sender.sendMessage("§cAccess denied.§r");
            return false;
        }
        return false;
    }
}
