package me.louderdev.discordAPI.command.subcommand;

import me.louderdev.discordAPI.command.Command;
import me.louderdev.netflix.util.Tasks;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class TrueWalletCommand extends Command {
    @Override
    public String getName() {
        return "truewallet";
    }

    @Override
    public String getDescription() {
        return "Louder info";
    }

    @Override
    public List<String> getAliases() {
        return (Arrays.asList(
                "tw",
                "twnumber"
        ));
    }

    @Override
    public void onCommand(MessageReceivedEvent event, String[] args) {
        if (args.length != 1) {
            return;
        }
        Tasks.runAsync(() -> {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("True Wallet");
            eb.setDescription("Information");
            eb.addField("Name: ", "เทพประวิณ พฤกษชาติ", true);
            eb.addField("True Wallet: ", "0993589886", true);
            eb.setColor(Color.GREEN);
            event.getChannel().sendMessage(eb.build()).queue();;
        });
    }
}
