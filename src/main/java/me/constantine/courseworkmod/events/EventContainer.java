package me.constantine.courseworkmod.events;

import me.constantine.courseworkmod.CourseWorkMod;
import me.constantine.courseworkmod.entity.Mob;
import me.constantine.courseworkmod.items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class EventContainer implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        CourseWorkMod.PLAYER = event.getPlayer();
        Bukkit.broadcastMessage("Welcome " + ChatColor.GOLD +
                event.getPlayer().getDisplayName() +
                ChatColor.WHITE + "!");
    }

    @EventHandler
    public void onRightClickWand(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null) {
                if (Objects.equals(event.getItem().getItemMeta(), ItemManager.wand.getItemMeta())) {
                    if (CourseWorkMod.landClaimer.getFirst() == null) {
                        CourseWorkMod.landClaimer.setFirst(event.getClickedBlock());
                    } else if (CourseWorkMod.landClaimer.getSecond() == null)
                        CourseWorkMod.landClaimer.setSecond(event.getClickedBlock());
                    CourseWorkMod.landClaimer.process();
                }
            }
        }
    }

    @EventHandler
    public void onRightClickMob(PlayerInteractEntityEvent event){
        if(Objects.requireNonNull(event.getRightClicked().getCustomName()).contains("'s Mob")){
            CourseWorkMod.PLAYER.openInventory(CourseWorkMod.mobInventory);
        }
    }
}
