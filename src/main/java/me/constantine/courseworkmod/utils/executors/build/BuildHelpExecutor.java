package me.constantine.courseworkmod.utils.executors.build;

import me.constantine.courseworkmod.CourseWorkMod;
import me.constantine.courseworkmod.utils.containers.MessagesContainer;

public class BuildHelpExecutor {
    public static void process() {
        if (CourseWorkMod.PLAYER != null)
            CourseWorkMod.PLAYER.sendRawMessage(MessagesContainer.BUILD_HELP);
    }
}
