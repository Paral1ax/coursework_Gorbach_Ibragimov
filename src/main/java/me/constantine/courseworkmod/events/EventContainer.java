package me.constantine.courseworkmod.events;

import me.constantine.courseworkmod.Courseworkmod;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventContainer implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Courseworkmod.PLAYER = event.getPlayer();
        Bukkit.broadcastMessage("Welcome " + ChatColor.GOLD +
                event.getPlayer().getDisplayName() +
                ChatColor.WHITE + "!");
    }
}
