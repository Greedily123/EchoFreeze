package me.idiots.echofreeze;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    public static File configFile;
    public static FileConfiguration config;

    public static void ConfigManager(EchoFreeze plugin) {
        if (!EchoFreeze.getPlugin(EchoFreeze.class).getDataFolder().exists()) {
            EchoFreeze.getPlugin(EchoFreeze.class).getDataFolder().mkdirs();
        }
        configFile = new File(EchoFreeze.getPlugin(EchoFreeze.class).getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            EchoFreeze.getPlugin(EchoFreeze.class).saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);

    }

    public static FileConfiguration getConfig() {
        return config;
    }
    public static boolean getBoolean(String s) {
        return config.getBoolean(s);
    }


    public static void reloadConfig(CommandSender sender) throws IOException, InvalidConfigurationException {
        config.load(configFile);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aConfig successfully reloaded."));
    }
}
