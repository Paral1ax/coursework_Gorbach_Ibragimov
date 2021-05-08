package me.constantine.courseworkmod;

import me.constantine.courseworkmod.commands.MobCommand;
import me.constantine.courseworkmod.entity.Mob;
import me.constantine.courseworkmod.events.EventContainer;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Courseworkmod extends JavaPlugin {
    public static Player PLAYER;
    public static Mob MOB;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EventContainer(), this);
        Objects.requireNonNull(getCommand("mob")).setExecutor(new MobCommand());
    }
}
