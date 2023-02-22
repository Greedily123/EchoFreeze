package me.idiots.echofreeze.listeners;

import me.idiots.echofreeze.ConfigManager;
import me.idiots.echofreeze.EchoFreeze;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class FrozenListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if(event.getPlayer().hasMetadata("Frozen")) {
            Location from = event.getFrom();
            Location to = event.getTo();
            if (from.getBlockX() == to.getBlockX() && from.getBlockZ() == to.getBlockZ()) {
                return;
            }
            event.setTo(event.getFrom());
        }
    }
    @EventHandler
    public void takeDamage(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            if(event.getEntity().hasMetadata("Frozen")) {
                event.getDamager().sendMessage(ChatColor.RED + "This player is currently frozen, you may not attack.");
                event.setCancelled(true);
            }
        }
        if(event.getDamager() instanceof Player) {
            if(event.getDamager().hasMetadata("Frozen")) {
                if(event.getEntity() instanceof Player)
                event.getDamager().sendMessage(ChatColor.RED + "You may not attack players whilst frozen.");
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void takeDamageByOtherSources(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            if(event.getEntity().hasMetadata("Frozen")) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getPlayer().hasMetadata("Frozen")) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerPickup(PlayerPickupItemEvent event) {
        if(event.getPlayer().hasMetadata("Frozen")) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        if(event.getPlayer().hasMetadata("Frozen")) {
            event.setCancelled(true);
        }
    }
}
