package events;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelloWorld extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("Hello!").queue();
    }
}
