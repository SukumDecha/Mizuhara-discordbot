package me.louderdev.discordAPI;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.louderdev.discordAPI.listener.DiscordListener;
import me.louderdev.discordAPI.webhook.MizuharaBot;
import me.louderdev.netflix.util.ConfigFile;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.requests.ErrorResponse;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Getter
@Setter
public class DiscordBot extends JavaPlugin {
    @Getter
    private static DiscordBot instance;

    private String serverName;
    private ConfigFile configFile;

    public void onEnable() {
        instance = this;

        this.registerBots();
        //this.registerListener();

        this.configFile = new ConfigFile(this, "config.yml");

        this.serverName = configFile.getString("MAIN.SERVER_NAME");
    }

    private void registerBots() {
        new MizuharaBot();
    }
}
