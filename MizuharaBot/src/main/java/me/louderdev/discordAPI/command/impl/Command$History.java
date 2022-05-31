package me.louderdev.discordAPI.command.impl;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.TimeUtil;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static me.louderdev.discordAPI.utils.SlashCommandUtil.deferReplyComplete;

public class Command$History extends ListenerAdapter {
    public static final String COMMAND_NAME = "history";
    public static final String COMMAND_DESCRIPTION = "Get the punishment history of a player";

    public static final String ARGS_1 = "player";
    public static final String ARGS_1_DESCRIPTION = "The player to get the history of";

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (!event.getName().equals(COMMAND_NAME)) {
            return;
        }

        Tasks.runAsync(() -> {
            deferReplyComplete(event);

            String player = event.getOption(ARGS_1).getAsString();

            PlayerData playerData = PlayerData.getByName(target);

            if (!playerData.isLoaded()) {
                playerData.load(false);
            }

            // Player not found
            if (playerData == null && playerData.getPlaytime() <= 1) {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Errors on fetching");
                embedBuilder.setDescription("There was an error while fetching data from **" + target + "**");
                embedBuilder.setImage("https://www.schoolofchangemakers.com/wp-content/uploads/2017/07/error-code-18.jpeg");
                embedBuilder.setFooter("Requested by " + requestBy.getUser().getName(), requestBy.getUser().getAvatarUrl());
                embedBuilder.setColor(Color.RED);
                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();

                return;
            }

            // No punishment
            if (playerData.getActivePunishmentByType(PunishmentType.IP_BAN) == null && playerData.getActivePunishmentByType(PunishmentType.BAN) == null) {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle(target + "'s history");
                embedBuilder.setDescription("Information");
                embedBuilder.addField("No ban active", " - " + target + " is not banned", false);
                embedBuilder.setImage("https://crafatar.com/renders/head/" + playerData.getUuid().toString());
                embedBuilder.setFooter("Requested by " + requestBy.getUser().getName(), requestBy.getUser().getAvatarUrl());
                embedBuilder.setColor(Color.GREEN);
                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();

                return;
            }

            // Ip ban
            if (playerData.getActivePunishmentByType(PunishmentType.IP_BAN) != null && playerData.getActivePunishmentByType(PunishmentType.BAN) == null) {
                Punishment punishment = playerData.getActivePunishmentByType(PunishmentType.IP_BAN);
                String punishedBy = punishment.getAddedBy() == null ? CC.BD_RED + "CONSOLE" : PlayerData.getByUuid(punishment.getAddedBy()).getRankName();

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle(target + "'s history");
                embedBuilder.setDescription("Information");
                embedBuilder.addField(target, " is current banned", true);
                embedBuilder.addField("Reason: ", punishment.getAddedReason(), true);
                embedBuilder.addField("Added by: ", addedBy, true);
                embedBuilder.addField("Added at: ", TimeUtil.formatDate(punishment.getAddedAt()), true);
                embedBuilder.addField("Duration: ", punishment.getNiceDuration(), true);
                embedBuilder.setImage("https://crafatar.com/renders/head/" + playerData.getUuid().toString());
                embedBuilder.setFooter("Requested by " + requestBy.getUser().getName(), requestBy.getUser().getAvatarUrl());
                embedBuilder.setColor(Color.GREEN);
                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue((message) -> {
                    message.addReaction(event.getGuild().getEmoteById("844085823771115561")).queue();
                    message.addReaction(event.getGuild().getEmoteById("844085848135303169")).queue();
                });

                return;
            }

            // Regular ban
            if (playerData.getActivePunishmentByType(PunishmentType.IP_BAN) == null && playerData.getActivePunishmentByType(PunishmentType.BAN) != null) {
                Punishment punishment = playerData.getActivePunishmentByType(PunishmentType.BAN);
                String punishedBy = punishment.getAddedBy() == null ? CC.BD_RED + "CONSOLE" : PlayerData.getByUuid(punishment.getAddedBy()).getRankName();

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle(target + "'s history");
                embedBuilder.setDescription("Information");
                embedBuilder.addField(target, " is current banned", true);
                embedBuilder.addField("Reason: ", punishment.getAddedReason(), true);
                embedBuilder.addField("Added by: ", addedBy, true);
                embedBuilder.addField("Added at: ", TimeUtil.formatDate(punishment.getAddedAt()), true);
                embedBuilder.addField("Duration: ", punishment.getNiceDuration(), true);
                embedBuilder.setImage("https://crafatar.com/renders/head/" + playerData.getUuid().toString());
                embedBuilder.setFooter("Requested by " + requestBy.getUser().getName(), requestBy.getUser().getAvatarUrl());
                embedBuilder.setColor(Color.GREEN);
                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();

                return;
            }
        });
    }
}
