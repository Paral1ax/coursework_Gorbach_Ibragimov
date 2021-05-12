package me.constantine.courseworkmod.commands;

import me.constantine.courseworkmod.CourseWorkMod;
import me.constantine.courseworkmod.utils.containers.MaterialContainer;
import me.constantine.courseworkmod.utils.containers.MessagesContainer;
import me.constantine.courseworkmod.utils.executors.build.farm.FarmExecutor;
import me.constantine.courseworkmod.utils.executors.build.houses.HouseFirstExecutor;
import me.constantine.courseworkmod.utils.executors.build.houses.HouseSecondExecutor;
import me.constantine.courseworkmod.utils.executors.help.HelpExecutor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.AbstractMap;

public class BuildCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args[0].equals("house1")) {
                if (!check()) return false;
                HouseFirstExecutor.process();
            } else if (args[0].equals("house2")) {
                if (!check()) return false;
                HouseSecondExecutor.process();
            } else if (args[0].equals("farm")) {
                if (!check()) return false;
                FarmExecutor.process();
            } else if (args[0].equals("help")) {
                HelpExecutor.process();
            }
        } else {
            Bukkit.broadcastMessage("Only a player can use this command");
        }
        return false;
    }

    private boolean checkWand() {
        if (CourseWorkMod.landClaimer == null)
            return false;
        if (CourseWorkMod.landClaimer.getList().size() == 0) {
            Bukkit.broadcastMessage(MessagesContainer.WAND_NULL);
            return false;
        }
        return true;
    }

    private boolean checkMaterials() {              // пример проверки на нужные материалы для первого дома
        if (CourseWorkMod.MOB == null) return false;
        for (AbstractMap.SimpleEntry<Material, Integer> entry : MaterialContainer.house1) {
            if (!CourseWorkMod.MOB.getInventory().contains(entry.getKey(), entry.getValue()))
                return false;
        }
        return true;
    }

    private boolean check() {
        return checkWand() & checkMaterials();
    }

}
