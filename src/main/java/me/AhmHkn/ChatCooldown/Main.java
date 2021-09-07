package me.AhmHkn.ChatCooldown;

import java.util.HashSet;

import me.AhmHkn.ChatCooldown.bStats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    public static HashSet<Player> cooldownlist;
    public static Main plugin;
    public static Integer cooldown;
    public static Metrics metrics;


    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        cooldown = getConfig().getInt("Cooldown");
        cooldownlist = new HashSet<>();
        getServer().getPluginManager().registerEvents(plugin, plugin);
        int pluginID = 12728;
        metrics = new Metrics(plugin, pluginID);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("chatcooldown.bypass"))
            return;
        if (cooldownlist.contains(p)) {
            msg(p, "Message");
            e.setCancelled(true);
        } else {
            cooldownlist.add(p);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                cooldownlist.remove(p);
            }, 10L * cooldown);
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