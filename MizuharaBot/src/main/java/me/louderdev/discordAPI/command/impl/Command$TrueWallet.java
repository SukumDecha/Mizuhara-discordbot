package me.louderdev.discordAPI.command.impl;

import me.louderdev.netflix.util.Tasks;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static me.louderdev.discordAPI.utils.SlashCommandUtil.deferReplyComplete;

public class Command$TrueWallet extends ListenerAdapter {
    public static final String COMMAND_NAME = "truewallet";
    public static final String COMMAND_DESCRIPTION = "Gives you an information about the true wallet.";

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals(COMMAND_NAME)) {
            return;
        }

        Tasks.runAsync(() -> {
            deferReplyComplete(event);

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("True Wallet");
            embedBuilder.setDescription("Information");
            embedBuilder.addField("Name: ", "เทพประวิณ พฤกษชาติ", true);
            embedBuilder.addField("True Wallet: ", "0993589886", true);
            embedBuilder.setColor(Color.GREEN);

            event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
        });
    }
}
