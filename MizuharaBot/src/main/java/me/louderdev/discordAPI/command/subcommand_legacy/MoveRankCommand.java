package me.louderdev.discordAPI.command.subcommand_legacy;

import me.louderdev.discordAPI.command.Command_legacy;
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
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import org.bukkit.Bukkit;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MoveRankCommand extends Command_legacy {

    private Rank rank;
    private String oldName;
    private String newName;
    private long duration;

    private PlayerData newData;
    private PlayerData oldData;

    @Override
    public String getName() {
        return "changerank";
    }

    @Override
    public String getDescription() {
        return "Check historyn the channel";
    }

    @Override
    public List<String> getAliases() {
        return (Arrays.asList(
                "moverank",
                "mrank"
        ));
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        if (!event.getTextChannel().canTalk()) return;
        Tasks.runAsync(() -> {
            Member request = event.getMember();
            if(findRole(request,  547266636420218880L) == null) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("No permission!");
                eb.setDescription("You do not have permission to execute this command.");
                eb.setImage("https://www.schoolofchangemakers.com/wp-content/uploads/2017/07/error-code-18.jpeg");
                eb.setFooter("Requested by " + request.getUser().getName(), request.getUser().getAvatarUrl());
                eb.setColor(Color.RED);
                event.getChannel().sendMessage(eb.build()).queue();
                return;
            }
            if (args.length != 4) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Mizuhara - Command");
                eb.setDescription("Please use /moverank <name> <new name> <rank> to move the player rank");
                eb.setFooter("Created by Sukum_Decha", event.getJDA().getUserById("335690413425819652").getAvatarUrl());

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(eb.build()).queue();
                return;
            }
            oldName = args[1];
            newName = args[2];
            String rankName = args[3];

            rank = Rank.getByName(rankName);
            if(rank == null) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Errors detect!");
                eb.setDescription("There is no rank with the name " + rankName);
                eb.setImage("https://www.schoolofchangemakers.com/wp-content/uploads/2017/07/error-code-18.jpeg");
                eb.setFooter("Requested by " + request.getUser().getName(), request.getUser().getAvatarUrl());
                eb.setColor(Color.RED);
                event.getChannel().sendMessage(eb.build()).queue();
                return;
            }
            oldData = PlayerData.getByName(oldName);
            if(!oldData.isLoaded()) {
                oldData.load(true);
            }
            if(oldData.getIp() == null) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Errors detect!");
                eb.setDescription(oldName + " never played on here!");
                eb.setImage("https://www.schoolofchangemakers.com/wp-content/uploads/2017/07/error-code-18.jpeg");
                eb.setFooter("Requested by " + request.getUser().getName(), request.getUser().getAvatarUrl());
                eb.setColor(Color.RED);
                event.getChannel().sendMessage(eb.build()).queue();
                return;
            } else {
                newData = PlayerData.getByName(newName);
                if(!newData.isLoaded()) {
                    newData.load(true);
                }
                if(newData.getIp() == null) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle("Errors detect!");
                    eb.setDescription(newName + " never played on here!");
                    eb.setImage("https://www.schoolofchangemakers.com/wp-content/uploads/2017/07/error-code-18.jpeg");
                    eb.setFooter("Requested by " + request.getUser().getName(), request.getUser().getAvatarUrl());
                    eb.setColor(Color.RED);
                    event.getChannel().sendMessage(eb.build()).queue();
                    return;
                } else {
                    if(oldData.getRank() == Rank.DEFAULT) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("Why are you trying to move rank?");
                        eb.setDescription("Default rank cann't be moved!");
                        eb.setImage("https://www.schoolofchangemakers.com/wp-content/uploads/2017/07/error-code-18.jpeg");
                        eb.setFooter("Requested by " + request.getUser().getName(), request.getUser().getAvatarUrl());
                        eb.setColor(Color.RED);
                        event.getChannel().sendMessage(eb.build()).queue();
                        return;
                    } else if(oldData.getRank() != rank) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setTitle("The rank doesn't matchup");
                        eb.setDescription("oldData rank and the specify one doesn't matchup!");
                        eb.setImage("https://www.schoolofchangemakers.com/wp-content/uploads/2017/07/error-code-18.jpeg");
                        eb.setFooter("Requested by " + request.getUser().getName(), request.getUser().getAvatarUrl());
                        eb.setColor(Color.RED);
                        event.getChannel().sendMessage(eb.build()).queue();
                        return;
                    }


                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle(newName + "'s moving rank format");
                    eb.setDescription("Information");
                    eb.addField("Old IGN: ", oldName, true);
                    eb.addField("New IGN: ", newName, true);
                    eb.addField("Moving Rank: ", rank.getName(), true);
                    eb.addField("State:", "Need to be confirm", true);
                    eb.addBlankField(false);
                    eb.addField("Confirm the format ", "react to the emote below", true);

                    eb.setImage("https://crafatar.com/renders/head/" + newData.getUuid().toString());
                    eb.setFooter("Created by " + request.getUser().getName(), request.getUser().getAvatarUrl());
                    eb.setColor(Color.GREEN);

                    event.getChannel().sendMessage(eb.build()).queue(message -> {
                        message.addReaction("✔").queue();
                        message.addReaction("❌").queue();
                    });

                    duration = oldData.getGrant().getDuration();
                }
            }
        });

    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        Member request = event.getMember();

        if(event.getReactionEmote().getName().equals("✔")) {
            List<Message> history = event.getTextChannel().getHistory().retrievePast(1).complete(); //arbitrary ammount
            for (Message msg : history) {
                msg.delete().queue();
            }
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(newName + "'s moving rank format");
            eb.setDescription("Information");
            eb.addField("Old IGN: ", oldName, true);
            eb.addField("New IGN: ", newName, true);
            eb.addField("Moving Rank: ", rank.getName(), true);
            eb.addField("State:", "Complete", true);
            eb.addBlankField(false);

            eb.setImage("https://crafatar.com/renders/head/" + newData.getUuid().toString());
            eb.setFooter("Created by " + request.getUser().getName(), request.getUser().getAvatarUrl());
            eb.setColor(Color.GREEN);

            RedisUtil.sendGrant(rank, null, newData.getName(), duration, "Moved by " + request.getUser().getName() + " on discord");

            while (oldData.getRank() != Rank.DEFAULT) {
                ServerUtil.runCommand("demote " + oldData.getName() + " moved rank");
            }
        } else if(event.getReactionEmote().getName().equals("❌")) {
            List<Message> history = event.getTextChannel().getHistory().retrievePast(1).complete(); //arbitrary ammount
            for (Message msg : history) {
                msg.delete().queue();
            }
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(newName + "'s moving rank format");
            eb.setDescription("Information");
            eb.addField("Old IGN: ", oldName, true);
            eb.addField("New IGN: ", newName, true);
            eb.addField("Moving Rank: ", rank.getName(), true);
            eb.addField("State:", "Canceled", true);
            eb.addBlankField(false);

            eb.setImage("https://crafatar.com/renders/head/" + newData.getUuid().toString());
            eb.setFooter("Created by " + request.getUser().getName(), request.getUser().getAvatarUrl());
            eb.setColor(Color.RED);



        }


    }
}
