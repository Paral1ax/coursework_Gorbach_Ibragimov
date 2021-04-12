package com.course.courseworkplugin.commands.createNPC;

import com.course.courseworkplugin.npc.CustomMob;
import com.course.courseworkplugin.npc.NPCCreator;
import net.minecraft.server.v1_16_R3.ChatComponentText;
import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CreateNPCCommand implements CommandExecutor {
    Plugin plugin;

    public CreateNPCCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            NPCCreator creator = new NPCCreator();
            creator.createNPC(plugin, player);
        } else {
            System.out.println("You should be a player to perform this command");
        }
        return false;
    }
}
