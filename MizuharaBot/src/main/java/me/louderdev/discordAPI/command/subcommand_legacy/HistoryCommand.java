package me.louderdev.discordAPI.command.subcommand_legacy;

import me.louderdev.discordAPI.command.Command_legacy;
import me.louderdev.netflix.player.PlayerData;
import me.louderdev.netflix.player.punishment.Punishment;
import me.louderdev.netflix.player.punishment.PunishmentType;
import me.louderdev.netflix.util.CC;
import me.louderdev.netflix.util.Tasks;
import me.louderdev.netflix.util.TimeUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class HistoryCommand extends Command_legacy {


    @Override
    public String getName() {
        return "history";
    }

    @Override
    public String getDescription() {
        return "Check history the channel";
    }

    @Override
    public List<String> getAliases() {
        return (Arrays.asList(
                "check",
                "c"
        ));
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        if (!event.getTextChannel().canTalk()) return;

        Tasks.runAsync(() -> {
            Member requestBy = event.getMember();

            if (args.length != 2) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Mizuhara - Command");
                eb.setDescription("Please use /history <play> to view player ban");
                eb.setFooter("Created by Sukum_Decha", event.getJDA().getUserById("335690413425819652").getAvatarUrl());

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(eb.build()).queue();
                return;
            }
            String target = args[1];

            PlayerData playerData = PlayerData.getByName(target);
            if(!playerData.isLoaded()) {
                playerData.load(false);
            }
            if(playerData == null || playerData != null && playerData.getPlaytime() <= 1) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Errors on fetching");
                eb.setDescription("There was an error while fetching data from **" + target + "**");
                eb.setImage("https://www.schoolofchangemakers.com/wp-content/uploads/2017/07/error-code-18.jpeg");
                eb.setFooter("Requested by " + requestBy.getUser().getName(), requestBy.getUser().getAvatarUrl());
                eb.setColor(Color.RED);
                event.getChannel().sendMessage(eb.build()).queue();
            } else {
                if (playerData.getActivePunishmentByType(PunishmentType.IP_BAN) == null && playerData.getActivePunishmentByType(PunishmentType.BAN) == null) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle(target + "'s history");
                    eb.setDescription("Information");
                    eb.addField("No ban active", " - " + target + " is not banned", false);
                    eb.setImage("https://crafatar.com/renders/head/" + playerData.getUuid().toString());
                    eb.setFooter("Requested by " + requestBy.getUser().getName(), requestBy.getUser().getAvatarUrl());
                    eb.setColor(Color.GREEN);
                    event.getChannel().sendMessage(eb.build()).queue();
                    return;
                } else if (playerData.getActivePunishmentByType(PunishmentType.IP_BAN) != null && playerData.getActivePunishmentByType(PunishmentType.BAN) == null) {
                    Punishment punishment = playerData.getActivePunishmentByType(PunishmentType.IP_BAN);

                    String addedBy = punishment.getAddedBy() == null ? CC.BD_RED + "CONSOLE" : PlayerData.getByUuid(punishment.getAddedBy()).getRankName();

                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle(target + "'s history");
                    eb.setDescription("Information");
                    eb.addField(target, " is current banned", true);
                    eb.addField("Reason: ", punishment.getAddedReason(), true);
                    eb.addField("Added by: ", addedBy, true);
                    eb.addField("Added at: ", TimeUtil.formatDate(punishment.getAddedAt()), true);
                    eb.addField("Duration: ", punishment.getNiceDuration(), true);

                    eb.setImage("https://crafatar.com/renders/head/" + playerData.getUuid().toString());
                    eb.setFooter("Requested by " + requestBy.getUser().getName(), requestBy.getUser().getAvatarUrl());
                    eb.setColor(Color.GREEN);

                    event.getChannel().sendMessage(eb.build()).queue(message -> {
                        message.addReaction(event.getGuild().getEmoteById("844085823771115561")).queue();
                        message.addReaction(event.getGuild().getEmoteById("844085848135303169")).queue();
                    });

                    return;
                } else if (playerData.getActivePunishmentByType(PunishmentType.IP_BAN) == null && playerData.getActivePunishmentByType(PunishmentType.BAN) != null) {
                    Punishment punishment = playerData.getActivePunishmentByType(PunishmentType.BAN);

                    String addedBy = punishment.getAddedBy() == null ? CC.BD_RED + "CONSOLE" : PlayerData.getByUuid(punishment.getAddedBy()).getRankName();

                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setTitle(target + "'s history");
                    eb.setDescription("Information");
                    eb.addField(target, " is current banned", true);
                    eb.addField("Reason: ", punishment.getAddedReason(), true);
                    eb.addField("Added by: ", addedBy, true);
                    eb.addField("Added at: ", TimeUtil.formatDate(punishment.getAddedAt()), true);
                    eb.addField("Duration: ", punishment.getNiceDuration(), true);

                    eb.setImage("https://crafatar.com/renders/head/" + playerData.getUuid().toString());
                    eb.setFooter("Requested by " + requestBy.getUser().getName(), requestBy.getUser().getAvatarUrl());
                    eb.setColor(Color.GREEN);
                    event.getChannel().sendMessage(eb.build()).queue();
                    return;
                }

            }
        });


    }
}
