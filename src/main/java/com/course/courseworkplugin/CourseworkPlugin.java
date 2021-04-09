package com.course.courseworkplugin;

import com.course.courseworkplugin.commands.createNPC.CreateNPC;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class CourseworkPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
       Objects.requireNonNull(getCommand("npc")).setExecutor(new CreateNPC());
    }
}
