package com.course.courseworkplugin;

import com.course.courseworkplugin.commands.createNPC.CreateNPC;
import com.course.courseworkplugin.commands.movement.BackCommand;
import com.course.courseworkplugin.events.ExplodeEvent;
import com.course.courseworkplugin.events.FightEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CourseworkPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("npc")).setExecutor(new CreateNPC(this));
        Objects.requireNonNull(getCommand("back")).setExecutor(new BackCommand(this));
        getServer().getPluginManager().registerEvents(new ExplodeEvent(), this);
        getServer().getPluginManager().registerEvents(new FightEvent(this), this);
    }
}
