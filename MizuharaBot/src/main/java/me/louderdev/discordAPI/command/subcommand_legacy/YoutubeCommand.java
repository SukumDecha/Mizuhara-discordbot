package me.louderdev.discordAPI.command.subcommand_legacy;

import me.louderdev.discordAPI.command.Command_legacy;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class YoutubeCommand extends Command_legacy {
    @Override
    public String getName() {
        return "youtubeappeal";
    }

    @Override
    public String getDescription() {
        return "Youtube format";
    }

    @Override
    public List<String> getAliases() {
        return (Arrays.asList(
                "ytrequire",
                "ytrequires",
                "ytapply",
                "youtubeapply",
                "applyyt",
                "applyyoutube"
        ));
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        if (args.length != 1) {
            return;
        }
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Youtuber requirement!");
        eb.setDescription("Information");
        eb.addField("Subscriber count: ", " 150+", true);
        eb.addField("View on the video count: ", "150+", true);
        eb.addField("Upload  the 1 video / 1-2 weeks ", "", true);
        eb.setFooter("Requested by " + event.getMember().getUser().getName(), event.getMember().getUser().getAvatarUrl());
        eb.setColor(Color.GREEN);
        event.getChannel().sendMessage(eb.build());
    }
}
