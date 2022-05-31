package me.louderdev.discordAPI.command.impl;

import me.louderdev.netflix.util.Tasks;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static me.louderdev.discordAPI.utils.SlashCommandUtil.deferReplyComplete;

public class Command$Info extends ListenerAdapter {
    public static final String COMMAND_NAME = "info";
    public static final String COMMAND_DESCRIPTION = "Louder info";

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals(COMMAND_NAME)) {
            return;
        }

        Tasks.runAsync(() -> {
            deferReplyComplete(event);

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Louder Network Info");
            embedBuilder.setDescription("Information");
            embedBuilder.addField("Discord: ", "https://discord.gg/MM6r6k2a3X", true);
            embedBuilder.addField("Facebook: ", "Louderpvp Fanpage", true);
            embedBuilder.addField("Website: ", "louderpvp.cf, mc-louderpvp.cf", true);
            embedBuilder.addField("Server IP: ", "mc-louderpvp.cf, dev-louderpvp.cf, sg-louderpvp.cf", true);
            embedBuilder.setFooter("Requested by " + event.getMember().getUser().getName(), event.getMember().getUser().getAvatarUrl());
            embedBuilder.setColor(Color.GREEN);

            event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
        });
    }
}
