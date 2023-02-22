package me.idiots.echofreeze;

import me.idiots.echofreeze.commands.FreezeCommand;
import me.idiots.echofreeze.listeners.FrozenListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class EchoFreeze extends JavaPlugin {

    public static HashMap<Player, String> echoCode = new HashMap<>();

    @Override
    public void onEnable() {
        FrozenCheck();
        ConfigManager.ConfigManager(this);
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new FrozenListener(), this);
        getCommand("freeze").setExecutor(new FreezeCommand());


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    private void FrozenCheck() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) this, (Runnable) () -> {
            for (final Player player : Bukkit.getOnlinePlayers()) {
                if(player.hasMetadata("Frozen")) {
                    for(String frozenMessage : ConfigManager.getConfig().getStringList("frozen-message")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', frozenMessage.replaceAll("%echocode%", EchoFreeze.echoCode.get(player))));
                    }
                }
            }
        }, 0L, 9 * 20);
    }
}
