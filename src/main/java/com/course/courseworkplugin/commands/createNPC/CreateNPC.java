package com.course.courseworkplugin.commands.createNPC;

import com.course.courseworkplugin.npc.CustomNPC;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;

public class CreateNPC implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            CustomNPC npc = new CustomNPC(player.getLocation());
            npc.setCustomName(new ChatComponentText(ChatColor.LIGHT_PURPLE + player.getName() + "'s NPC"));
            npc.setOwner(player);
            WorldServer server = ((CraftWorld) player.getWorld()).getHandle();
            server.addEntity(npc);
            player.sendMessage(ChatColor.RED + "NPC Spawned");
        } else {
            System.out.println("Console can not perform this command");
        }
        return false;
    }
}
