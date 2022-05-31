package me.louderdev.discordAPI.command;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public abstract class Command_legacy extends ListenerAdapter {

    public abstract String getName();
    public abstract String getDescription();
    public abstract List<String> getAliases();

    public abstract void onCommand(MessageReceivedEvent event, String[] args);

    public static final String PREFIX = ".";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (isMessageFromCommand(args[0]))
            onCommand(event, args);
    }


    public Role findRole(Member member, long id) {
        List<Role> roles = member.getRoles();
        return roles.stream()
                .filter(role -> role.getIdLong() == id) // filter by role name
                .findFirst() // take first result
                .orElse(null); // else return null
    }
    private boolean isMessageFromCommand(String keyword) {
        if (keyword.startsWith(PREFIX)) {
            keyword = keyword.substring(PREFIX.length());
        }
        else {
            return (false);
        }
        return keyword.equals(getName()) || getAliases().contains(keyword);
    }
}
