package me.idiots.echofreeze.commands;

import com.google.common.primitives.Ints;
import me.idiots.echofreeze.ConfigManager;
import me.idiots.echofreeze.EchoFreeze;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FreezeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to execute this command.");
            return true;
        }
        if (sender.hasPermission(ConfigManager.getConfig().getString("freeze-permission"))) {
            if (args.length != 2) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        sender.sendMessage(ChatColor.RED + "Player '" + args[0] + "' found.");
                        return true;
                    }
                    if (target.hasMetadata("Frozen")) {
                        EchoFreeze.echoCode.put(target, null);
                        target.setWalkSpeed(1);
                        target.setAllowFlight(true);
                        target.removePotionEffect(PotionEffectType.JUMP);
                        target.removePotionEffect(PotionEffectType.BLINDNESS);
                        target.removeMetadata("Frozen", EchoFreeze.getPlugin(EchoFreeze.class));
                        target.sendMessage(ChatColor.DARK_AQUA + "[Freeze]" + ChatColor.AQUA + " You are no longer frozen.");
                        sender.sendMessage(ChatColor.DARK_AQUA + "[Freeze]" + ChatColor.AQUA + " " + target.getDisplayName() + " is no longer frozen.");
                        return true;
                    }
                }
                sender.sendMessage(ChatColor.RED + "Usage: /freeze <playerName> [pin]");
                return true;
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "Player '" + args[0] + "' found.");
                    return true;
                } else {
                    Integer a1 = Ints.tryParse(args[1]);
                    if (a1 == null) {
                        sender.sendMessage(ChatColor.RED + "Pin must be an integer.");
                        return true;
                    } else {
                        EchoFreeze.echoCode.put(target, args[1]);
                        target.setWalkSpeed(0);
                        target.setAllowFlight(false);
                        target.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, 150, false, false));
                        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 1000000, 0, false, false));
                        target.setMetadata("Frozen", new FixedMetadataValue(EchoFreeze.getPlugin(EchoFreeze.class), true));
                        target.sendMessage(ChatColor.DARK_AQUA + "[Freeze]" + ChatColor.AQUA + " You are now frozen.");
                        sender.sendMessage(ChatColor.DARK_AQUA + "[Freeze]" + ChatColor.AQUA + " " + target.getDisplayName() + " is now frozen.");
                    }
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }
        return true;
    }
}
