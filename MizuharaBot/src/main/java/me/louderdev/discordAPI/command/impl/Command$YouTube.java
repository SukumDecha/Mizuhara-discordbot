package me.louderdev.discordAPI.command.impl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static me.louderdev.discordAPI.utils.SlashCommandUtil.deferReplyComplete;

public class Command$YouTube extends ListenerAdapter {
    public static final String COMMAND_NAME = "youtubeapply";
    public static final String COMMAND_DESCRIPTION = "Apply for the YouTube rank";

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals(COMMAND_NAME)) {
            return;
        }

        deferReplyComplete(event);

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Youtuber requirement!");
        embedBuilder.setDescription("Information");
        embedBuilder.addField("Subscriber count: ", " 150+", true);
        embedBuilder.addField("View on the video count: ", "150+", true);
        embedBuilder.addField("Upload  the 1 video / 1-2 weeks ", "", true);
        embedBuilder.setFooter("Requested by " + event.getMember().getUser().getName(), event.getMember().getUser().getAvatarUrl());
        embedBuilder.setColor(Color.GREEN);

        event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
    }
}
