package com.course.courseworkplugin.events;

import com.course.courseworkplugin.npc.NPCCreator;
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
        if (event.getRightClicked().getCustomName().contains(event.getPlayer().getName() + "'s Mob")) {
            Location location = event.getPlayer().getLocation();
            event.getRightClicked().remove();
            event.getPlayer().getWorld().spawnParticle(Particle.EXPLOSION_HUGE, location.getX(), location.getY(),
                    location.getZ(), 0);
            NPCCreator.notPlayable.setHealth(0);
            NPCCreator.mob.setHealth(0);
        }
    }
}