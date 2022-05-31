package me.louderdev.discordAPI.command.impl;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import static me.louderdev.discordAPI.utils.SlashCommandUtil.deferReplyComplete;

public class Command$ClearChat extends ListenerAdapter {
    public static final String COMMAND_NAME = "clearchat";
    public static final String COMMAND_DESCRIPTION = "Clear chat in the channel";

    public static final String ARGS_1 = "amount";
    public static final String ARGS_1_DESCRIPTION = "Amount of messages to delete";

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals(COMMAND_NAME)) {
            return;
        }

        Tasks.runAsync(() -> {
            deferReplyComplete(event);

            int amount = event.getOption(ARGS_1).getAsInt();
            event.getTextChannel().getHistoryBefore(event.getMessageChannel().getLatestMessageId(), amount).queue((messages) -> {
                messages.getRetrievedHistory().forEach((m) -> {
                    m.delete().queue();
                });
            });

            event.getHook().sendMessage("Successfully cleared " + amount + " messages.").queue();
        });
    }
}
