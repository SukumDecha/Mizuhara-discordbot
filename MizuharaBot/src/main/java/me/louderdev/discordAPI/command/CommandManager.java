package me.louderdev.discordAPI.command;

import me.louderdev.discordAPI.webhook.MizuharaBot;
import net.dv8tion.jda.api.JDA;

public class CommandManager {
    private static CommandManager INSTANCE = new CommandManager();

    public static CommandManager getInstance() {
        return INSTANCE;
    }

    private CommandManager() {
    }

    public void init() {
        JDA jda = MizuharaBot.getJDA();
    }
}
