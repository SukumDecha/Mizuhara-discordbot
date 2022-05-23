package me.louderdev.discordAPI.listener;

import me.louderdev.discordAPI.DiscordBot;
import me.louderdev.discordAPI.api.DiscordAPI;
import me.louderdev.discordAPI.webhook.MizuharaBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import net.dv8tion.jda.internal.handle.GuildMemberUpdateHandler;
import org.bukkit.Bukkit;

public class DiscordListener extends ListenerAdapter {

    public JDA jda = MizuharaBot.getJDA();

    @SubscribeEvent
    public void onReady(ReadyEvent event) {
        MizuharaBot.setReady(true);
        for(int x = 0; x< 5; x++) {
            Bukkit.getConsoleSender().sendMessage("Discord bot is now ready!");
        }
    }
    /*
    @SubscribeEvent
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.getAuthor().isFake() || event.isWebhookMessage()) return;
        String[] args = event.getMessage().getContentRaw().split(" ");
        TextChannel channel = MizuharaBot.getJDA().getTextChannelById("437201071417327619");

        if(args[0].toLowerCase().equalsIgnoreCase("/help")) {
            DiscordAPI.sendHelpMessage(event.getChannel(), event.getMember());
            return;
        }
        if(args[0].toLowerCase().equalsIgnoreCase("/chat")) {

            if(args.length < 2) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Mizuhara - Command");
                eb.setDescription("Please use /chat <message> to send the message");
                eb.setFooter("Created by Sukum_Decha", jda.getUserById("335690413425819652").getAvatarUrl());

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(eb.build()).queue();
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for(int x = 1; x < args.length; x++) {
                stringBuilder.append(args[x]);
                stringBuilder.append(" ");
            }
            Message toSend = new MessageBuilder().append(stringBuilder.toString()).build();
            channel.sendTyping().queue();
            channel.sendMessage(toSend).queue();
        }
        if(args[0].toLowerCase().equalsIgnoreCase("/chat")) {

            if(args.length < 2) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("Mizuhara - Command");
                eb.setDescription("Please use /chat <message> to send the message");
                eb.setFooter("Created by Sukum_Decha", jda.getUserById("335690413425819652").getAvatarUrl());

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(eb.build()).queue();
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for(int x = 1; x < args.length; x++) {
                stringBuilder.append(args[x]);
                stringBuilder.append(" ");
            }
            Message toSend = new MessageBuilder().append(stringBuilder.toString()).build();
            channel.sendTyping().queue();
            channel.sendMessage(toSend).queue();
        }
    }

     */
}
