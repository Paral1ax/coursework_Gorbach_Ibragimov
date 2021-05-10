package me.constantine.courseworkmod;

import me.constantine.courseworkmod.commands.*;
import me.constantine.courseworkmod.commands.mob.MobNotStandByCommand;
import me.constantine.courseworkmod.commands.mob.MobSpawnCommand;
import me.constantine.courseworkmod.commands.mob.MobStandByCommand;
import me.constantine.courseworkmod.commands.mob.MobTeleportCommand;
import me.constantine.courseworkmod.entity.Mob;
import me.constantine.courseworkmod.events.EventContainer;
import me.constantine.courseworkmod.items.ItemManager;
import me.constantine.courseworkmod.utils.claimer.LandClaimer;
import net.minecraft.server.v1_16_R3.ChatBaseComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CourseWorkMod extends JavaPlugin {
    public static Player PLAYER;
    public static Mob MOB;
    public static Inventory mobInventory;
    public static LandClaimer landClaimer;

    @Override
    public void onEnable() {
        ItemManager.init();
        getServer().getPluginManager().registerEvents(new EventContainer(), this);
        Objects.requireNonNull(getCommand("mob")).setExecutor(new MobSpawnCommand());
        Objects.requireNonNull(getCommand("wand")).setExecutor(new WandCommand());
        Objects.requireNonNull(getCommand("build")).setExecutor(new BuildCommand());
        Objects.requireNonNull(getCommand("standby")).setExecutor(new MobStandByCommand());
        Objects.requireNonNull(getCommand("notstandby")).setExecutor(new MobNotStandByCommand());
        Objects.requireNonNull(getCommand("teleport")).setExecutor(new MobTeleportCommand());
    }
}
