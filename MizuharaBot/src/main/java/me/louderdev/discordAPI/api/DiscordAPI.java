package me.louderdev.discordAPI.api;


import me.louderdev.discordAPI.DiscordBot;
import me.louderdev.discordAPI.utils.DateUtil;
import me.louderdev.discordAPI.webhook.MizuharaBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.awt.*;
import java.util.List;
import java.util.*;

public class DiscordAPI {

    public static void sendHelpMessage(TextChannel textChannel, Member member) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Mizuhara - Command");
        eb.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
        eb.setDescription("You can view the correct command down here!");
        eb.addField("/chat <message>", " - Excuted this cmd will force bot to chat the message", false);
        eb.addField("/list , /onlines", " - View the playercount on the server", false);
        eb.setFooter("Requested by " + member.getUser().getName(), member.getUser().getAvatarUrl());

        textChannel.sendMessage(eb.build()).queue();

    }
    public static void sendFFAWinUHC(String winner, List<String> kill) {
        TextChannel textChannel = MizuharaBot.getJDA().getTextChannelById("630030751227707402");
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**UHC Announce**");
        eb.setColor(Color.YELLOW);
        eb.setDescription("");
        eb.addField("**──────────────────────────────────**", "", false);
        eb.addField("**Champion found! The winner is**", "", false);
        eb.addField("**" + winner + "**",   "* Kills: " + kill, false);
        eb.addField("", "**Thanks for playing**", false);
        eb.addField("**──────────────────────────────────**", "", true);

        eb.setAuthor("Alicization");

        textChannel.sendMessage(eb.build()).queue();;
    }

    public static void sendWinUHC(String size, String host, Calendar time, List<String> scenarios) {
        TextChannel textChannel = MizuharaBot.getJDA().getTextChannelById("630030751227707402");
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.orange);
        eb.setTitle("Louder UHC | " + size, null);

        eb.addField("Open Time »", DateUtil.readableTime(time.getTimeInMillis()), false);
        eb.addBlankField(true);
        eb.addField("Host »", host, true);
        eb.addField("IP »", "mc-louderpvp.cf", true);
        eb.addBlankField(true);
        eb.addField("Scenario(s) »", scenarios.toString().replace("[", "").replace("]", ""), true);

        eb.setFooter("Louder Network | 1.7 - 1.15", "http://mc-louderpvp.cf/icon.png");
        textChannel.sendMessage(eb.build()).queue();
    }
        
    public static void sendLeaderboard(Map<String, Integer> map, String ladder) {
        TextChannel real = MizuharaBot.getJDA().getTextChannelById("630035300969742352");
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Leaderboard - " + ladder+ "**");
        eb.setColor(Color.PINK);
        eb.setDescription("List top 5 players.");
        eb.setAuthor("");
        int added = 1;
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        for (String kitLeaderboards: sortedMap.keySet()) {
            if(added <= 5) {
                eb.addField( added + ". " + kitLeaderboards , "* " + sortedMap.get(kitLeaderboards), false);
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(kitLeaderboards);
                String url = "https://crafatar.com/renders/body/" + offlinePlayer.getUniqueId()  + "?overlay";
            }
            added++;

        }
        eb.setFooter("@here", "http://mc-louderpvp.cf/assets/img/logo.png");
        eb.setThumbnail("http://mc-louderpvp.cf/assets/img/logo.png");
        real.sendMessage(eb.build()).queue();
    }

    public static void sendAliveKit() {
        TextChannel real = MizuharaBot.getJDA().getTextChannelById("630035300969742352");
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**There is no leaderboard kit with that name.**");
        eb.setColor(Color.YELLOW);
        eb.setDescription("Alive Kits: NoDebuff, Sumo, BuildUHC");
        real.sendMessage(eb.build()).queue();
    }

    public static void sendWrongUsuage() {
        TextChannel textChannel = MizuharaBot.getJDA().getTextChannelById("630035300969742352");
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**You need to specify kits name**");
        eb.setColor(Color.YELLOW);
        eb.setDescription("Alive Kits: NoDebuff, Sumo, BuildUHC");
        textChannel.sendMessage(eb.build()).queue();
    }

    public static void sendOnline(TextChannel textChannel, int online) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("**Louder Network | Online**");
        eb.setColor(Color.GREEN);
        eb.setDescription("Players: " + online);
        textChannel.sendMessage(eb.build()).queue();
    }

}
