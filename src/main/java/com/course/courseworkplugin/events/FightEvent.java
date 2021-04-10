package com.course.courseworkplugin.events;

import com.course.courseworkplugin.commands.createNPC.CreateNPC;
import com.course.courseworkplugin.npc.CustomNPC;
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
        if (CreateNPC.npc == null) return;
        if (event.getDamager() instanceof Player & event.getEntity() instanceof CraftPig) {

            PathfinderGoal melee = new PathfinderGoalMeleeAttack(CreateNPC.npc, 2.0, false);
            PathfinderGoal attackable = new PathfinderGoalNearestAttackableTarget<>(CreateNPC.npc,
                    EntityPig.class, false);
            PathfinderGoal attackableTarget = new PathfinderGoalNearestAttackableTarget<>(CreateNPC.npc,
                    EntityPig.class, false);

            if (Container.flag) {
                Container.pig = (CraftPig) event.getEntity();
                CreateNPC.npc.addGoal(melee);
                CreateNPC.npc.addGoal(attackable);
                CreateNPC.npc.addTarget(attackableTarget);
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
                if (CreateNPC.npc.dead) {
                    Container.pig = null;
                    Container.flag = true;
                    Bukkit.broadcastMessage("DEAD");
                    cancel();
                }
                if (Container.pig.getHealth() <= 0.1) {
                    CreateNPC.npc.setHealth(0);
                    CustomNPC pet = new CustomNPC(new Location(event.getDamager().getWorld(), CreateNPC.npc.locX(),
                            CreateNPC.npc.locY(), CreateNPC.npc.locZ()), (Player) event.getDamager(), plugin);
                    CreateNPC.npc.teleportAndSync(0, 0, 0);
                    pet.setCustomName(new ChatComponentText(ChatColor.LIGHT_PURPLE + event.getDamager().getName() + "'s Pet"));
                    pet.setOwner(((Player) event.getDamager()));
                    WorldServer server = ((CraftWorld) event.getDamager().getWorld()).getHandle();
                    server.addEntity(pet);
                    CreateNPC.npc = pet;
                    Container.pig = null;
                    Container.flag = true;
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 0L).getTaskId();
    }
}
