package com.course.courseworkplugin.npc;

import com.course.courseworkplugin.npc.pathfinder.PathFinderGoalPet;
import com.google.common.collect.Sets;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomMob extends EntityZombieHusk {
    public List<PathfinderGoal> goals = new ArrayList<>();
    public List<PathfinderGoal> targets = new ArrayList<>();
    public int goalsNumber;
    public int targetsNumber;
    Plugin plugin;
    CustomMob pet;

    public CustomMob(Location location, Player player, Plugin plugin) {
        super(EntityTypes.HUSK, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.setSilent(true);
        this.setInvisible(true);
        removeAI();
        addPaths();
        addTargets();
        goThrowGoals();
        goThrowTargets();
        goalsNumber = goals.size();
        targetsNumber = targets.size();
        this.plugin = plugin;
        checkLists();
        pet = this;
    }

    public void setOwner(Player player) {
        this.setGoalTarget(((CraftPlayer) player).getHandle(),
                EntityTargetEvent.TargetReason.CUSTOM, false);
    }

    public void addPaths() {
        goals.add(new PathfinderGoalFloat(this));
        goals.add(new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        goals.add(new PathfinderGoalRandomLookaround(this));
        goals.add(new PathFinderGoalPet(this, 1.5, 10));
    }

    private void addTargets() {
    }

    public void addGoal(PathfinderGoal pathfinderGoal) {
        goals.add(pathfinderGoal);
    }

    public void addTarget(PathfinderGoal pathfinderGoal) {
        targets.add(pathfinderGoal);
    }

    public void removeGoal(PathfinderGoal pathfinderGoal) {
        goals.remove(pathfinderGoal);
    }

    public void removeTarget(PathfinderGoal pathfinderGoal) {
        targets.remove(pathfinderGoal);
    }

    public void removeAttackGoals() {
        goals.remove(goals.size() - 1);
        goals.remove(goals.size() - 1);
    }

    public void removeAttackTargets() {
        targets.remove(targets.size() - 1);
    }

    public void removeAllGoals() {
        goals.clear();
    }

    public void removeAllTargets() {
        targets.clear();
    }

    public void removeAI() {
        try {
            Field goalsField = PathfinderGoalSelector.class.getDeclaredField("d");
            goalsField.setAccessible(true);
            goalsField.set(this.goalSelector, Sets.newLinkedHashSet());
            goalsField.set(this.targetSelector, Sets.newLinkedHashSet());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void checkLists() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (pet.dead) {
                    removeAllGoals();
                    removeAllTargets();
                    cancel();
                }
                if (goals.size() != goalsNumber || targets.size() != targetsNumber) {
                    plugin.getServer().broadcastMessage("Goals " + goals.size());
                    plugin.getServer().broadcastMessage("Goals " + goalsNumber);
                    plugin.getServer().broadcastMessage("Targets " + targets.size());
                    plugin.getServer().broadcastMessage("Targets " + targetsNumber);
                    goThrowGoals();
                    goThrowTargets();
                    goalsNumber = goals.size();
                    targetsNumber = targets.size();
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0L, 20L);
    }

    public void goThrowGoals() {
        goals.forEach(p -> this.goalSelector.a(goals.indexOf(p), p));
    }

    public void goThrowTargets() {
        targets.forEach(t -> this.targetSelector.a(targets.indexOf(t), t));
    }

    @Override
    public void setOnFire(int i) { }

    @Override
    public void setOnFire(int i, boolean callEvent) { }
}
