package com.course.courseworkplugin.events;

import com.course.courseworkplugin.commands.createNPC.CreateNPCCommand;
import com.course.courseworkplugin.npc.CustomMob;
import com.course.courseworkplugin.npc.NPCCreator;
import com.course.courseworkplugin.utils.Container;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;


public class FightEvent implements Listener {
    Plugin plugin;
    public static int id;

    public FightEvent(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFight(EntityDamageByEntityEvent event) {
        if (NPCCreator.mob == null) return;
        if (event.getDamager() instanceof Player & event.getEntity() instanceof CraftPig) {

            PathfinderGoal melee = new PathfinderGoalMeleeAttack(NPCCreator.mob, 2.0, false);
            PathfinderGoal attackable = new PathfinderGoalNearestAttackableTarget<>(NPCCreator.mob,
                    EntityPig.class, false);
            PathfinderGoal attackableTarget = new PathfinderGoalNearestAttackableTarget<>(NPCCreator.mob,
                    EntityPig.class, false);

            if (Container.flag) {
                Container.pig = (CraftPig) event.getEntity();
                NPCCreator.mob.addGoal(melee);
                NPCCreator.mob.addGoal(attackable);
                NPCCreator.mob.addTarget(attackableTarget);
                runTask(event);
                Container.flag = false;
            }
            event.getDamager().sendMessage("Work");
        } else
            event.getDamager().sendMessage("No");
    }

    private void runTask(EntityDamageByEntityEvent event) {
        id = new BukkitRunnable() {
            @Override
            public void run() {
                if (NPCCreator.mob.dead) {
                    Container.pig = null;
                    Container.flag = true;
                    Bukkit.broadcastMessage("DEAD");
                    cancel();
                }
                if (Container.pig == null) cancel();
                if (Container.pig.getHealth() <= 0.1) {
                    NPCCreator.mob.setHealth(0);
                    CustomMob pet = new CustomMob(new Location(event.getDamager().getWorld(), NPCCreator.mob.locX(),
                            NPCCreator.mob.locY(), NPCCreator.mob.locZ()), (Player) event.getDamager(), plugin);
                    NPCCreator.mob.teleportAndSync(0, 0, 0);
                    pet.setCustomName(new ChatComponentText(ChatColor.LIGHT_PURPLE + event.getDamager().getName() + "'s Pet"));
                    pet.setOwner(((Player) event.getDamager()));
                    WorldServer server = ((CraftWorld) event.getDamager().getWorld()).getHandle();
                    server.addEntity(pet);
                    NPCCreator.mob = pet;
                    Container.pig = null;
                    Container.flag = true;
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 0L).getTaskId();
    }
}
