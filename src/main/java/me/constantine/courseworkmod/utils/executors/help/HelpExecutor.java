package me.constantine.courseworkmod.utils.executors.help;

import me.constantine.courseworkmod.CourseWorkMod;
import me.constantine.courseworkmod.utils.containers.MessagesContainer;

public class HelpExecutor {
    public static void process() {
        if (CourseWorkMod.PLAYER != null)
            CourseWorkMod.PLAYER.sendRawMessage(MessagesContainer.COMMAND_HELP);
    }
}