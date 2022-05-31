package me.louderdev.discordAPI.command.impl;

import me.louderdev.netflix.backend.RedisUtil;
import me.louderdev.netflix.player.PlayerData;
import me.louderdev.netflix.player.punishment.Punishment;
import me.louderdev.netflix.player.punishment.PunishmentType;
import me.louderdev.netflix.rank.Rank;
import me.louderdev.netflix.util.CC;
import me.louderdev.netflix.util.ServerUtil;
import me.louderdev.netflix.util.Tasks;
import me.louderdev.netflix.util.TimeUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

import static me.louderdev.discordAPI.utils.SlashCommandUtil.deferReplyComplete;
import static me.louderdev.discordAPI.utils.SlashCommandUtil.findRole;

public class Command$MoveRank extends ListenerAdapter {
    public static final String COMMAND_NAME = "changerank";
    public static final String COMMAND_DESCRIPTION = "Move a rank";

    public static final String ARGS_1 = "name";
    public static final String ARGS_1_DESCRIPTION = "The name of the player to move the rank of";
    public static final String ARGS_2 = "new-name";
    public static final String ARGS_2_DESCRIPTION = "The name of the player to move the rank to";
    public static final String ARGS_3 = "rank";
    public static final String ARGS_3_DESCRIPTION = "The rank to move";

    private String EMOTE_1 = "✔";
    private String EMOTE_2 = "❌";

    private PlayerData oldData;
    private PlayerData newData;
    private String oldName;
    private String newName;
    private Rank rank;
    private long duration;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        Tasks.runAsync(() -> {
            Member member = event.getMember();

            if (findRole(member,  547266636420218880L) == null) {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("No permission!");
                embedBuilder.setDescription("You do not have permission to execute this command.");
                embedBuilder.setImage("https://www.schoolofchangemakers.com/wp-content/uploads/2017/07/error-code-18.jpeg");
                embedBuilder.setFooter("Requested by " + member.getUser().getName(), member.getUser().getEffectiveAvatarUrl());
                embedBuilder.setColor(Color.RED);

                event.deferReply(true).queue((c) -> {
                    c.sendMessageEmbeds(embedBuilder.build()).queue();
                });

                return;
            }

            oldName = event.getOption(ARGS_1).getAsString();
            newName = event.getOption(ARGS_2).getAsString();
            rank = Rank.getByName(event.getOption(ARGS_3).getAsString());
            oldData = PlayerData.getByName(oldName);

            if (!oldData.isLoaded()) {
                oldData.load(true);
            }

            // Never join this server before
            if (oldData.getIp() == null) {
                deferReplyComplete(event);

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Errors detect!");
                embedBuilder.setDescription(oldName + " never played on here!");
                embedBuilder.setImage("https://www.schoolofchangemakers.com/wp-content/uploads/2017/07/error-code-18.jpeg");
                embedBuilder.setFooter("Requested by " + member.getUser().getName(), member.getUser().getEffectiveAvatarUrl());
                embedBuilder.setColor(Color.RED);

                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();

                return;
            }

            newData = PlayerData.getByName(newName);

            if(!newData.isLoaded()) {
                newData.load(true);
            }

            // Never join this server before
            if (newData.getIp() == null) {
                deferReplyComplete(event);

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Errors detect!");
                embedBuilder.setDescription(newName + " never played on here!");
                embedBuilder.setImage("https://www.schoolofchangemakers.com/wp-content/uploads/2017/07/error-code-18.jpeg");
                embedBuilder.setFooter("Requested by " + member.getUser().getName(), member.getUser().getEffectiveAvatarUrl());
                embedBuilder.setColor(Color.RED);

                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();

                return;
            }

            // Old account has a default rank
            if (oldData.getRank() == Rank.DEFAULT) {
                deferReplyComplete(event);

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Why are you trying to move rank?");
                embedBuilder.setDescription("Default rank cann't be moved!");
                embedBuilder.setImage("https://www.schoolofchangemakers.com/wp-content/uploads/2017/07/error-code-18.jpeg");
                embedBuilder.setFooter("Requested by " + member.getUser().getName(), member.getUser().getEffectiveAvatarUrl());
                embedBuilder.setColor(Color.RED);

                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();

                return;
            }

            // Old account doesn't have the specified rank
            if (oldData.getRank() != rank) {
                deferReplyComplete(event);

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("The rank doesn't matchup");
                embedBuilder.setDescription("oldData rank and the specify one doesn't matchup!");
                embedBuilder.setImage("https://www.schoolofchangemakers.com/wp-content/uploads/2017/07/error-code-18.jpeg");
                embedBuilder.setFooter("Requested by " + member.getUser().getName(), member.getUser().getEffectiveAvatarUrl());
                embedBuilder.setColor(Color.RED);

                event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();

                return;
            }

            // Passed all the checks, move the rank
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(newName + "'s moving rank format");
            embedBuilder.setDescription("Information");
            embedBuilder.addField("Old IGN: ", oldName, true);
            embedBuilder.addField("New IGN: ", newName, true);
            embedBuilder.addField("Moving Rank: ", rank.getName(), true);
            embedBuilder.addField("State:", "Need to be confirm", true);
            embedBuilder.addBlankField(false);
            embedBuilder.addField("Confirm the format ", "react to the emote below", true);
            embedBuilder.setImage("https://crafatar.com/renders/head/" + newData.getUuid().toString());
            embedBuilder.setFooter("Created by " + member.getUser().getName(), member.getUser().getAvatarUrl());
            embedBuilder.setColor(Color.GREEN);

            event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue((message) -> {
                message.addReaction(EMOTE_1).queue();
                message.addReaction(EMOTE_2).queue();
            });
        });
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        Member member = event.getMember();

        if (event.getReactionEmote().getName().equals(EMOTE_1)) {
            for (Message message : event.getTextChannel().getHistory().retrievePast(1).complete()) {
                message.delete().queue();
            }

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(newName + "'s moving rank format");
            embedBuilder.setDescription("Information");
            embedBuilder.addField("Old IGN: ", oldName, true);
            embedBuilder.addField("New IGN: ", newName, true);
            embedBuilder.addField("Moving Rank: ", rank.getName(), true);
            embedBuilder.addField("State:", "Complete", true);
            embedBuilder.addBlankField(false);
            embedBuilder.setImage("https://crafatar.com/renders/head/" + newData.getUuid().toString());
            embedBuilder.setFooter("Created by " + member.getUser().getName(), member.getUser().getEffectiveAvatarUrl());
            embedBuilder.setColor(Color.GREEN);

            RedisUtil.sendGrant(rank, null, newData.getName(), duration, "Moved by " + member.getUser().getName() + " on discord");

            while (oldData.getRank() != Rank.DEFAULT) {
                ServerUtil.runCommand("demote " + oldData.getName() + " moved rank");
            }

//            event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();

            return;
        }

        if (event.getReactionEmote().getName().equals(EMOTE_2)) {
            for (Message message : event.getTextChannel().getHistory().retrievePast(1).complete()) {
                message.delete().queue();
            }

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle(newName + "'s moving rank format");
            embedBuilder.setDescription("Information");
            embedBuilder.addField("Old IGN: ", oldName, true);
            embedBuilder.addField("New IGN: ", newName, true);
            embedBuilder.addField("Moving Rank: ", rank.getName(), true);
            embedBuilder.addField("State:", "Canceled", true);
            embedBuilder.addBlankField(false);
            embedBuilder.setImage("https://crafatar.com/renders/head/" + newData.getUuid().toString());
            embedBuilder.setFooter("Created by " + member.getUser().getName(), member.getUser().getEffectiveAvatarUrl());
            embedBuilder.setColor(Color.RED);

//            event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();

            return;
        }
    }
}
