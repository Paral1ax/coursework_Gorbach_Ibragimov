package com.course.courseworkplugin.npc;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class NPCCreator {
    private static List<EntityPlayer> list = new ArrayList<>();
    private double prevX;
    private double prevY;
    private double prevZ;
    private float curHealth;
    public static EntityPlayer notPlayable;
    public static CustomMob mob;

    public void createNPC(Plugin plugin, Player player) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Objects.requireNonNull(Bukkit.getWorld(player.getWorld().getName()))).getHandle();
        GameProfile profile = new GameProfile(UUID.randomUUID(), ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Dude");
        EntityPlayer npc = new EntityPlayer(server, world, profile, new PlayerInteractManager(world));
        npc.setPositionRotation(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(),
                player.getLocation().getYaw(), player.getLocation().getPitch());
        npc.setPosition(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
        addNPCPacket(npc);
        enableRotation(plugin, npc);
        list.add(npc);
        notPlayable = npc;
        addMob(plugin, player, world, npc);
    }

    private void addMob(Plugin plugin, Player player, WorldServer world, EntityPlayer npc) {
        mob = new CustomMob(player.getLocation(), player, plugin);
        mob.setOwner(player);
        world.addEntity(mob);
        mob.setCustomName(new ChatComponentText(ChatColor.AQUA + mob.getGoalTarget().getName() + "'s Mob"));
        mob.setCustomNameVisible(false);
        prevX = mob.locX();
        prevY = mob.locY();
        prevZ = mob.locZ();
        curHealth = mob.getHealth();
        new BukkitRunnable() {
            public void run() {
                mob.setInvisible(true);
            }
        }.runTaskTimer(plugin, 0, 0);
        new BukkitRunnable() {
            public void run() {
                update(npc, mob);
                double x = mob.locX();
                double y = mob.locY();
                double z = mob.locZ();
                if (x != prevX || y != prevY || z != prevZ) {
                    double getX = x - prevX;
                    double getY = y - prevY;
                    double getZ = z - prevZ;
                    npcMoves(npc, getX, getY, getZ);
                    prevX = x;
                    prevY = y;
                    prevZ = z;
                }
                if (mob.getHealth() < curHealth) {
                    npcTakeDamage(npc);
                    curHealth = mob.getHealth();
                }
                if (mob.dead) {
                    removeNPCPacket(npc);
                    list.remove(npc);
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 0);
    }

    public static void addNPCPacket(EntityPlayer npc) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
            connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook());
        }
    }

    public static void addJoinPacket(Player player) {
        for (EntityPlayer npc : list) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
        }
    }

    public void removeNPCPacket(EntityPlayer npc) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            DataWatcher watcher = npc.getDataWatcher();
            watcher.set(new DataWatcherObject<>(16, DataWatcherRegistry.a), (byte) 255);
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo
                    .EnumPlayerInfoAction.REMOVE_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));
        }
    }

    public static void enableRotation(Plugin plugin, EntityPlayer npc) {
        int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (calculateDistance(player, npc) > 20) continue;

                // Get the Player Connection so we can send Packets
                PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

                // Calculate the Yaw for the NPCCreator
                Vector difference = player.getLocation().subtract(npc.getBukkitEntity().getLocation()).toVector().normalize();
                byte yaw = (byte) MathHelper.d((Math.toDegrees(Math.atan2(difference.getZ(), difference.getX()) - Math.PI / 2) * 256.0F) / 360.0F);

                // Calculate the Pitch for the NPCCreator
                Vector height = npc.getBukkitEntity().getLocation().subtract(player.getLocation()).toVector().normalize();
                byte pitch = (byte) MathHelper.d((Math.toDegrees(Math.atan(height.getY())) * 256.0F) / 360.0F);

                // Send the Packets to update the NPCCreator
                connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, yaw));
                connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(npc.getId(), yaw, pitch, true));
            }
        }, 1, 1);
    }

    private static double calculateDistance(Player player, EntityPlayer npc) {
        double diffX = npc.locX() - player.getLocation().getX(), diffZ = npc.locZ() - player.getLocation().getZ();
        double x = diffX < 0 ? (diffX * -1) : diffX, z = diffZ < 0 ? (diffZ * -1) : diffZ;
        return Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
    }

    public static List<EntityPlayer> getList() {
        return list;
    }

    public static void deleteList() {
        list = new ArrayList<>();
    }

    public void npcTakeDamage(EntityPlayer npc) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutAnimation(npc, 1)); // 1 - takes damage
        }
    }

    public void update(EntityPlayer npc, CustomMob mob) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (mob.lastYaw * 256 / 360)));
            connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutRelEntityMove());
            connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutEntityLook(npc.getId(),
                    (byte) (mob.lastYaw * 256 / 360), (byte) (mob.lastPitch * 256 / 360), true));
        }
    }

    public void npcMoves(EntityPlayer npc, double x, double y, double z/*, float yaw, float pitch*/) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook(npc.getId(), (short) (x * 4096),
                    (short) (y * 4096), (short) (z * 4096), (byte) (npc.lastYaw * 256 / 360),
                    (byte) (npc.lastPitch * 256 / 360), true));
        }
    }
}