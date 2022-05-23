package me.louderdev.discordAPI.command.subcommand;

import me.louderdev.discordAPI.command.Command;
import me.louderdev.netflix.util.Tasks;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

public class ClearChatCommand extends Command {
    @Override
    public String getName() {
        return "clearchat";
    }

    @Override
    public String getDescription() {
        return "Clear chat in the channel";
    }

    @Override
    public List<String> getAliases() {
        return (Arrays.asList(
                "clear",
                "chatclear"
        ));
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        Tasks.runAsync(() -> {
            if (args.length < 2) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Mizuhara - Command");
                eb.setDescription("Please use /clearchat <amount> to send the message");
                eb.setFooter("Created by Sukum_Decha", event.getJDA().getUserById("335690413425819652").getAvatarUrl());

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(eb.build()).queue();
                return;
            }
            int amount = Integer.parseInt(args[1]);
            event.getTextChannel().getHistoryBefore(event.getMessage(), amount).queue(messageHistory -> {
                messageHistory.getRetrievedHistory().forEach(message -> {
                    message.delete().queue();
                });
            });
            event.getMessage().delete().queue();
            event.getTextChannel().sendMessage("Successfully cleared " + amount + " messages.").queue();
        });
    }
}
