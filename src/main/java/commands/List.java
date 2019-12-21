package commands;

import embeds.ListEmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class List extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        // Avoid bot messages.
        if (event.getAuthor().isBot()) {
            return;
        }

        // Avoid messages with inadequate length.
        if (event.getMessage().getContentRaw().length() < "-list ?".length()) {
            return;
        }

        // Avoid messages with wrong command.
        if (!event.getMessage().getContentRaw().substring(0, 5).equalsIgnoreCase("-list")) {
            return;
        }

        // Clean up the message.
        String listName = event.getMessage().getContentRaw().substring(6);

        // Create an list embed.
        ListEmbedBuilder embed;
        try{
            embed = new ListEmbedBuilder(listName);
        } catch (Exception e) {
            event.getChannel().sendMessage("<@" + event.getAuthor().getId() + ">, I couldn't find that list... " +
                    "Did you spell it correctly?").queue();
            e.printStackTrace();
            return;
        }

        // Send the embed to the channel.
        event.getChannel().sendFile(embed.getImage()).embed(embed.getEmbed().build()).queue();

        // Remove temporarily created image.
        boolean deleted = embed.getImage().delete();
        if (!deleted) {
            System.err.println("tempImage.png failed to delete");
        }
    }
}
