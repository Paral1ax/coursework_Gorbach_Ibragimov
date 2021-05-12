package me.constantine.courseworkmod.entity;

import me.constantine.courseworkmod.CourseWorkMod;
import me.constantine.courseworkmod.ai.PetGoal;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class Mob extends EntityZombie implements InventoryHolder {
    public Inventory inventory;
    public boolean stand = true;

    public Mob(Player player) {
        super(EntityTypes.HUSK, ((CraftWorld) player.getWorld()).getHandle());
        Location loc = player.getLocation();
        this.setSilent(true);
        this.setBaby(false);
        this.setCanPickupLoot(true);
        this.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        this.setCustomName(new ChatComponentText(
                player.getName() + "'s Mob"));
        this.setCustomNameVisible(true);
        this.setGoalTarget(((CraftPlayer) player).getHandle(),
                EntityTargetEvent.TargetReason.CUSTOM, false);
        CourseWorkMod.MOB = this;
        CourseWorkMod.mobInventory = Bukkit.getServer().createInventory(CourseWorkMod.MOB, 18, "Mob Inventory");
    }

    @Override
    protected void initPathfinder() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0f));
        this.goalSelector.a(2, new PetGoal(this, 1.5, 25.0f));
        //this.goalSelector.a(3, new PathfinderGoalMeleeAttack(this, 0.5d, false));
        //this.goalSelector.a(4, new PathfinderGoalNearestAttackableTarget<>(this, EntityMonster.class, false));
        //this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget<>(this, EntityMonster.class, false));
    }

    @Override
    public void setOnFire(int i) {

    }

    @Override
    public void setOnFire(int i, boolean callEvent) {
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
