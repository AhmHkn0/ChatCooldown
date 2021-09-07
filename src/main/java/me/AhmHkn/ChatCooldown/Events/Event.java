package me.AhmHkn.ChatCooldown.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static me.AhmHkn.ChatCooldown.Main.*;

public class Event implements Listener {

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

}
