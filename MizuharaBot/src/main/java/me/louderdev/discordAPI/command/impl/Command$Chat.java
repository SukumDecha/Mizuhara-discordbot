package me.louderdev.discordAPI.command.impl;

import me.louderdev.discordAPI.webhook.MizuharaBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import static me.louderdev.discordAPI.utils.SlashCommandUtil.deferReplyComplete;

public class Command$Chat extends ListenerAdapter {
    public static final String COMMAND_NAME = "chat";
    public static final String COMMAND_DESCRIPTION = "Force player typing";

    public static final String ARGS_1 = "message";
    public static final String ARGS_1_DESCRIPTION = "The message to send";

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals(COMMAND_NAME)) {
            return;
        }

        Tasks.runAsync(() -> {
            deferReplyComplete(event);

            String message = event.getOption(ARGS_1).getAsString();

            event.getHook().sendMessage(message).queue();
        });
    }
}
