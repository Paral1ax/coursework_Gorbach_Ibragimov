package me.constantine.courseworkmod;

import me.constantine.courseworkmod.commands.BuildCommand;
import me.constantine.courseworkmod.commands.MobCommand;
import me.constantine.courseworkmod.commands.WandCommand;
import me.constantine.courseworkmod.entity.Mob;
import me.constantine.courseworkmod.events.EventContainer;
import me.constantine.courseworkmod.items.ItemManager;
import me.constantine.courseworkmod.utils.claimer.LandClaimer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CourseWorkMod extends JavaPlugin {
    public static Player PLAYER;
    public static Mob MOB;
    public static LandClaimer landClaimer;

    @Override
    public void onEnable() {
        ItemManager.init();
        landClaimer = new LandClaimer();
        getServer().getPluginManager().registerEvents(new EventContainer(), this);
        Objects.requireNonNull(getCommand("mob")).setExecutor(new MobCommand());
        Objects.requireNonNull(getCommand("wand")).setExecutor(new WandCommand());
        Objects.requireNonNull(getCommand("build")).setExecutor(new BuildCommand());
    }
}
