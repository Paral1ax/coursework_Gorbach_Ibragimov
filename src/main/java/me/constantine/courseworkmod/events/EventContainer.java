package me.constantine.courseworkmod.events;

import me.constantine.courseworkmod.CourseWorkMod;
import me.constantine.courseworkmod.items.ItemManager;
import me.constantine.courseworkmod.utils.claimer.LandClaimer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;

import java.util.Objects;

public class EventContainer implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        CourseWorkMod.PLAYER = event.getPlayer();
        Bukkit.broadcastMessage("Welcome " + ChatColor.GOLD +
                event.getPlayer().getDisplayName() +
                ChatColor.WHITE + "!");
        CourseWorkMod.PLAYER.getWorld().setTime(1000);
        if(CourseWorkMod.PLAYER.getInventory().contains(ItemManager.wand))
            CourseWorkMod.landClaimer = new LandClaimer();
    }

    @EventHandler
    public void onRightClickWand(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null) {
                if (Objects.equals(event.getItem().getItemMeta(), ItemManager.wand.getItemMeta())) {
                    if (CourseWorkMod.landClaimer.getFirst() == null) {
                        Block picked = event.getClickedBlock();
                        CourseWorkMod.landClaimer.setFirst(picked);
                        Bukkit.broadcastMessage("You claimed first block at (" + picked.getX() +
                                ", " + picked.getY() + ", " + picked.getZ() + ")");
                    } else if (CourseWorkMod.landClaimer.getSecond() == null) {
                        Block picked = event.getClickedBlock();
                        CourseWorkMod.landClaimer.setSecond(picked);
                        Bukkit.broadcastMessage("You claimed second block at (" + picked.getX() +
                                ", " + picked.getY() + ", " + picked.getZ() + ")");
                    }
                    CourseWorkMod.landClaimer.process();
                }
            }
        }
    }

    @EventHandler
    public void onRightClickMob(PlayerInteractEntityEvent event) {
        if (CourseWorkMod.MOB == null) return;
        if (Objects.requireNonNull(event.getRightClicked().getCustomName()).contains("'s Mob")) {
            CourseWorkMod.PLAYER.openInventory(CourseWorkMod.mobInventory);
        }
    }

    /*public static void onPlayerLeave(Plugin plugin) {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (Bukkit.getServer().getOnlinePlayers().size() == 0) {
                    if (CourseWorkMod.PLAYER != null) {
                        CourseWorkMod.PLAYER.getInventory().remove(ItemManager.wand);
                    }
                    if (CourseWorkMod.MOB != null) {
                        for (ItemStack item : CourseWorkMod.MOB.getInventory().getContents())
                            CourseWorkMod.PLAYER.getInventory().addItem(item);
                        CourseWorkMod.MOB.setHealth(0);
                    }
                }
            }
        }, 0, 60L);
    }*/
}
