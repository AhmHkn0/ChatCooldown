package me.AhmHkn.ChatCooldown;

import java.util.HashSet;

import me.AhmHkn.ChatCooldown.Events.Event;
import me.AhmHkn.ChatCooldown.bStats.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
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
        getServer().getPluginManager().registerEvents(new Event(), plugin);
        int pluginID = 12728;
        metrics = new Metrics(plugin, pluginID);
    }


    public static void msg(Player p, String cfg) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Prefix")) + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString(cfg)));
    }
}