package me.louderdev.discordAPI.webhook;

import lombok.Getter;
import lombok.Setter;
import me.louderdev.discordAPI.DiscordBot;
import me.louderdev.discordAPI.command.Command;
import me.louderdev.discordAPI.command.subcommand.*;
import me.louderdev.discordAPI.listener.DiscordListener;

import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.List;


public class MizuharaBot {

    public static JDA jda;

    @Getter @Setter public static boolean isReady;
    public MizuharaBot() {
        handleStart();
    }

    private void handleStart() {
        try {
            JDA api = JDABuilder.createDefault("") //put bot token here
                    .setStatus(OnlineStatus.ONLINE)
                    .setActivity(Activity.watching("Brand New Music bot"))
                    .setAutoReconnect(true)
                    .build();
           
            
            api.addEventListener(new ChatCommand());
            api.addEventListener(new ClearChatCommand());
            api.addEventListener(new HistoryCommand());
            api.addEventListener(new MoveRankCommand());
            api.addEventListener(new BanAppealCommand());
            api.addEventListener(new InfoCommand());
            api.addEventListener(new YoutubeCommand());
            api.addEventListener(new TrueWalletCommand());

             
            this.jda = api;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JDA getJDA() {
            return jda;
    }
}
