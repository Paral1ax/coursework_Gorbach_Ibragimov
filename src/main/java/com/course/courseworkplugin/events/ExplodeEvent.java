package com.course.courseworkplugin.events;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class ExplodeEvent implements Listener {
    @EventHandler
    public void onExplode(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getCustomName() == null)
            return;
        if (event.getRightClicked().getCustomName().contains(event.getPlayer().getName() + "'s Pet")) {
            Location location = event.getPlayer().getLocation();
            event.getRightClicked().remove();
            event.getPlayer().getWorld().spawnParticle(Particle.EXPLOSION_HUGE, location.getX(), location.getY(),
                    location.getZ(), 0);
        }
    }
}