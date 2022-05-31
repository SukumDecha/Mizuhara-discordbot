package me.louderdev.discordAPI.command.subcommand_legacy;

import me.louderdev.discordAPI.command.Command_legacy;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class BanAppealCommand extends Command_legacy {
    @Override
    public String getName() {
        return "appeal";
    }

    @Override
    public String getDescription() {
        return "Appeal format";
    }

    @Override
    public List<String> getAliases() {
        return (Arrays.asList(
                "bappeal",
                "banappeal"
        ));
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        if (args.length != 1) {
            return;
        }
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Ban Appeal format");
        eb.setDescription("Information");
        eb.addField("IGN (In game name)", "Your minecraft username", true);
        eb.addField("Reason (Ban reason): ", "Banned reason", true);
        eb.addField("Admit to cheat: ", "Yes or no", true);
        eb.addField("What you want to tell to us: ", "Write it", true);
        eb.setFooter("Requested by " + event.getMember().getUser().getName(), event.getMember().getUser().getAvatarUrl());
        eb.setColor(Color.GREEN);
        event.getChannel().sendMessage(eb.build()).queue();;

    }
}
