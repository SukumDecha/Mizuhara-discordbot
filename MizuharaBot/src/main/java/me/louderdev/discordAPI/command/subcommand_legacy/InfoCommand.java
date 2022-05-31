package me.louderdev.discordAPI.command.subcommand_legacy;

import me.louderdev.discordAPI.command.Command_legacy;
import me.louderdev.netflix.util.Tasks;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class InfoCommand extends Command_legacy {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Louder info";
    }

    @Override
    public List<String> getAliases() {
        return (Arrays.asList(
                "information",
                "louderhelp",
                "louderwebsite",
                "web", "discord", "ip"
        ));
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        if (args.length != 1) {
            return;
        }
        Tasks.runAsync(() -> {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Louder Network Info");
            eb.setDescription("Information");
            eb.addField("Discord: ", "https://discord.gg/MM6r6k2a3X", true);
            eb.addField("Facebook: ", "Louderpvp Fanpage", true);
            eb.addField("Website: ", "louderpvp.cf, mc-louderpvp.cf", true);
            eb.addField("Server IP: ", "mc-louderpvp.cf, dev-louderpvp.cf, sg-louderpvp.cf", true);
            eb.setFooter("Requested by " + event.getMember().getUser().getName(), event.getMember().getUser().getAvatarUrl());
            eb.setColor(Color.GREEN);
            event.getChannel().sendMessage(eb.build()).queue();;
        });

    }
}
