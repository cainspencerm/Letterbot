package commands;

import embeds.FilmEmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Film extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        // Avoid bot messages.
        if (event.getAuthor().isBot()) {
            return;
        }

        // Avoid messages with inadequate length.
        if (event.getMessage().getContentRaw().length() < "-film ?".length()) {
            return;
        }

        // Avoid messages with wrong command.
        if (!event.getMessage().getContentRaw().substring(0, 5).equalsIgnoreCase("-film")) {
            return;
        }

        // Clean up the message.
        String filmName = event.getMessage().getContentRaw().substring(6);

        // Create an film embed.
        MessageEmbed embed;
        try{
            embed = new FilmEmbedBuilder(filmName).build();
        } catch (Exception e) {
            event.getChannel().sendMessage("<@" + event.getAuthor().getId() + ">, I couldn't find that film... " +
                    "Did you spell it correctly?").queue();
            e.printStackTrace();
            return;
        }

        // Send the embed to the channel.
        event.getChannel().sendMessage(embed).queue();
    }
}
