package me.AhmHkn.ChatCooldown;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    ArrayList<String> cooldownlist = new ArrayList<>();
    public static Main plugin;

    int Cooldown;

    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        this.Cooldown = getConfig().getInt("Cooldown");
        getServer().getPluginManager().registerEvents(plugin, plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        final Player p = e.getPlayer();
        if (p.isOp() || p.hasPermission("chatcooldown.bypass"))
            return;
        if (plugin.cooldownlist.contains(p.getName())) {
            msg(p, "Message");
            e.setCancelled(true);
        } else {
            plugin.cooldownlist.add(p.getName());
            getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    if (Main.plugin.cooldownlist.contains(p.getPlayer().getName()))
                        Main.plugin.cooldownlist.remove(p.getName());
                }
            }, (20L * plugin.Cooldown));
        }
    }

    public static void msg(Player p, String cfg) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(cfg)));
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
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
