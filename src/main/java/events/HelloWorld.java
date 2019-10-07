package events;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelloWorld extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        // Ignore message if the user is a bot.
        if (event.getAuthor().isBot()) {
            return;
        }

        // Say hello!
        event.getChannel().sendMessage("Hello!").queue();
    }
}
