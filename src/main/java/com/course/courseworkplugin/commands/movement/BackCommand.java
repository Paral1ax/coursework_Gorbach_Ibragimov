package com.course.courseworkplugin.commands.movement;

import com.course.courseworkplugin.commands.createNPC.CreateNPC;
import com.course.courseworkplugin.events.FightEvent;
import com.course.courseworkplugin.npc.CustomNPC;
import com.course.courseworkplugin.utils.Container;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class BackCommand implements CommandExecutor {
    private Plugin plugin;

    public BackCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            CreateNPC.npc.setHealth(0);
            CustomNPC pet = new CustomNPC(new Location(player.getWorld(), CreateNPC.npc.locX(),
                    CreateNPC.npc.locY(), CreateNPC.npc.locZ()), player, plugin);
            CreateNPC.npc.teleportAndSync(0, 0, 0);
            pet.setCustomName(new ChatComponentText(ChatColor.LIGHT_PURPLE + player.getName() + "'s Pet"));
            pet.setOwner(player);
            WorldServer server = ((CraftWorld) player.getWorld()).getHandle();
            server.addEntity(pet);
            CreateNPC.npc = pet;
            Bukkit.getScheduler().cancelTask(FightEvent.id);
            Container.pig = null;
            Container.flag = true;
        } else {
            System.out.println("Console can not perform this command");
        }
        return false;
    }
}