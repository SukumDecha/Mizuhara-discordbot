package me.louderdev.discordAPI.command.subcommand;

import me.louderdev.discordAPI.command.Command;
import me.louderdev.netflix.util.Tasks;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class ChatCommand extends Command {
    @Override
    public String getName() {
        return "chat";
    }

    @Override
    public String getDescription() {
        return "Force player typing";
    }

    @Override
    public List<String> getAliases() {
        return (Arrays.asList(
                "chats",
                "forcechat"
        ));
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        Tasks.runAsync(() -> {
            if (args.length < 2) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Mizuhara - Command");
                eb.setDescription("Please use /chat <message> to send the message");
                eb.setFooter("Created by Sukum_Decha", event.getJDA().getUserById("335690413425819652").getAvatarUrl());

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(eb.build()).queue();
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int x = 1; x < args.length; x++) {
                stringBuilder.append(args[x]);
                stringBuilder.append(" ");
            }
            Message toSend = new MessageBuilder().append(stringBuilder.toString()).build();
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(toSend).queue();
        });

    }
}
