package commands;

import embeds.ActorEmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Actor extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        // Avoid bot messages.
        if (event.getAuthor().isBot()) {
            return;
        }

        // Avoid messages with inadequate length.
        if (event.getMessage().getContentRaw().length() < "-actor ?".length()) {
            return;
        }

        // Avoid messages with wrong command.
        if (!event.getMessage().getContentRaw().substring(0, 6).equalsIgnoreCase("-actor")) {
            return;
        }

        // Clean up the message.
        String actorName = event.getMessage().getContentRaw().substring(7);

        // Create an film embed.
        MessageEmbed embed;
        try{
            embed = new ActorEmbedBuilder(actorName).build();
        } catch (Exception e) {
            event.getChannel().sendMessage("<@" + event.getAuthor().getId() + ">, I couldn't find that actor... " +
                    "Did you spell it correctly?").queue();
            e.printStackTrace();
            return;
        }

        // Send the embed to the channel.
        event.getChannel().sendMessage(embed).queue();
    }
}
