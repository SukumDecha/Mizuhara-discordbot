package me.louderdev.discordAPI.command.impl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static me.louderdev.discordAPI.utils.SlashCommandUtil.deferReplyComplete;

public class Command$BanAppeal extends ListenerAdapter {
    public static final String COMMAND_NAME = "banappeal";
    public static final String COMMAND_DESCRIPTION = "Appeal format";

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals(COMMAND_NAME)) {
            return;
        }

        deferReplyComplete(event);

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Ban Appeal format");
        embedBuilder.setDescription("Information");
        embedBuilder.addField("IGN (In game name)", "Your minecraft username", true);
        embedBuilder.addField("Reason (Ban reason): ", "Banned reason", true);
        embedBuilder.addField("Admit to cheat: ", "Yes or no", true);
        embedBuilder.addField("What you want to tell to us: ", "Write it", true);
        embedBuilder.setFooter("Requested by " + event.getMember().getUser().getName(), event.getMember().getUser().getEffectiveAvatarUrl());
        embedBuilder.setColor(Color.GREEN);

        event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
    }
}
