package me.louderdev.discordAPI.webhook;

import lombok.Getter;
import lombok.Setter;
import me.louderdev.discordAPI.command.impl.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;

public class MizuharaBot {
    private static JDA jda;

    @Getter
    @Setter
    public static boolean isReady;

    public MizuharaBot() {
        handleStart();
    }

    private void handleStart() {
        try {
            jda = JDABuilder
                    .createDefault("") //put bot token here
                    .setStatus(OnlineStatus.ONLINE)
                    .setActivity(Activity.watching("Brand New Music bot"))
                    .setAutoReconnect(true)
                    .build()
                    .awaitReady();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerCommands() {
        Guild guild = jda.getGuildById(""); //put guild id here
        if (guild == null) {
            throw new IllegalStateException("Guild not found");
        }

        // register commands
        guild
                .upsertCommand(Command$BanAppeal.COMMAND_NAME, Command$BanAppeal.COMMAND_DESCRIPTION)
                .queue();
        guild
                .upsertCommand(Command$Chat.COMMAND_NAME, Command$Chat.COMMAND_DESCRIPTION)
                .addOption(OptionType.STRING, Command$Chat.ARGS_1, Command$Chat.ARGS_1_DESCRIPTION, true)
                .queue();
        guild
                .upsertCommand(Command$ClearChat.COMMAND_NAME, Command$ClearChat.COMMAND_DESCRIPTION)
                .addOption(OptionType.INTEGER, Command$ClearChat.ARGS_1, Command$ClearChat.ARGS_1_DESCRIPTION, true)
                .queue();
        guild
                .upsertCommand(Command$History.COMMAND_NAME, Command$History.COMMAND_DESCRIPTION)
                .addOption(OptionType.STRING, Command$History.ARGS_1, Command$History.ARGS_1_DESCRIPTION, true)
                .queue();
        guild
                .upsertCommand(Command$Info.COMMAND_NAME, Command$Info.COMMAND_DESCRIPTION)
                .queue();
        guild
                .upsertCommand(Command$MoveRank.COMMAND_NAME, Command$MoveRank.COMMAND_DESCRIPTION)
                .addOption(OptionType.STRING, Command$MoveRank.ARGS_1, Command$MoveRank.ARGS_1_DESCRIPTION, true)
                .addOption(OptionType.STRING, Command$MoveRank.ARGS_2, Command$MoveRank.ARGS_2_DESCRIPTION, true)
                .addOption(OptionType.STRING, Command$MoveRank.ARGS_3, Command$MoveRank.ARGS_3_DESCRIPTION, true)
                .queue();
        guild
                .upsertCommand(Command$TrueWallet.COMMAND_NAME, Command$TrueWallet.COMMAND_DESCRIPTION)
                .queue();
        guild
                .upsertCommand(Command$YouTube.COMMAND_NAME, Command$YouTube.COMMAND_DESCRIPTION)
                .queue();

        // register event listeners
        jda.addEventListener(
                new Command$BanAppeal(),
                new Command$Chat(),
                new Command$ClearChat(),
                new Command$History(),
                new Command$Info(),
                new Command$MoveRank(),
                new Command$TrueWallet(),
                new Command$YouTube()
        );
    }

    public static JDA getJDA() {
            return jda;
    }
}
