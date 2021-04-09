package com.course.courseworkplugin.npc;

import com.course.courseworkplugin.npc.pathfinder.PathfinderGoalPet;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.Objects;

public class CustomNPC extends EntityZombie {
    public CustomNPC(Location location) {
        super(EntityTypes.ZOMBIE, ((CraftWorld) Objects.requireNonNull(location.getWorld())).getHandle());
        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.setInvulnerable(true);
    }

    @Override
    protected void initPathfinder() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalPet(this, 1.0, 25));
        this.goalSelector.a(2, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(3, new PathfinderGoalRandomLookaround(this));
    }

    public void setOwner(Player player){
        this.setGoalTarget(((CraftPlayer)player).getHandle(),
                EntityTargetEvent.TargetReason.CUSTOM, false);
    }
}
