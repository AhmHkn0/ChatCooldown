package me.AhmHkn.ChatCooldown.Commands;

import me.AhmHkn.ChatCooldown.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import static me.AhmHkn.ChatCooldown.Main.*;

public class Command implements Listener {

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String s, String[] args) {
        if (s.equalsIgnoreCase("cdreload")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                {
                    if (p.getPlayer().isOp()) {
                        Main.plugin.reloadConfig();
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Prefix")) + "§aReloaded Config!");
                    }else{
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Prefix")) + "§cYou don't have permission!");
                    }
                }
            }else{
                Main.plugin.reloadConfig();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Prefix")) + "§aReloaded Config!");
            }
        }
        return false;
    }
}
