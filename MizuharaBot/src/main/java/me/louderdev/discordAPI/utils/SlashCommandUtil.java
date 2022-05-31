package me.louderdev.discordAPI.utils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;

import java.util.List;

public class SlashCommandUtil {
    public static void deferReplyQueue(SlashCommandInteractionEvent event) {
        deferReply(event).queue();
    }

    public static void deferReplyComplete(SlashCommandInteractionEvent event) {
        deferReply(event).complete();
    }

    public static Role findRole(Member member, long id) {
        List<Role> roles = member.getRoles();
        return roles.stream()
                .filter(role -> role.getIdLong() == id) // filter by role name
                .findFirst() // take first result
                .orElse(null); // else return null
    }

    private static ReplyCallbackAction deferReply(SlashCommandInteractionEvent event) {
        return event.deferReply();
    }
}
