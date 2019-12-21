package embeds;

import Film.ID.Film;
import Contributor.ID.ContributionType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Iterator;

public class FilmEmbedBuilder extends EmbedBuilder {

    public FilmEmbedBuilder(String name) throws InvalidKeyException, NoSuchAlgorithmException {
        super();

        // Get the film from Letterboxd.
        Film film = Film.getFilm(name);

        // Get the names of each director.
        HashSet<String> directorNames = new HashSet<>();
        for (int i = 0; i < film.getContributions().length; i++) {
            for (int j = 0; j < film.getContributions()[i].getContributors().length; j++) {
                if (film.getContributions()[i].getType().equals(ContributionType.Director))
                    directorNames.add(film.getContributions()[i].getContributors()[j].getName());
            }
        }

        Iterator<String> iterator = directorNames.iterator();
        String directors = "";
        for (int i = 0; i < directorNames.size(); i++) {
            directors += iterator.next();

            if (i != directorNames.size() - 1)
                directors += ", ";
        }

        // Construct an embed.
        this.setTitle(film.getName(), film.getLinks()[0].getUrl());
        this.setThumbnail("https://a.ltrbxd.com/logos/letterboxd-logo-alt-v-neg-rgb-1000px.png");
        this.setColor(new Color(21, 24, 29, 1));

        // Add tag line to embed.
        String description = "";
        if (film.getTagline() != null)
            description += "**" + film.getTagline().toUpperCase() + "**\n\n";

        // Add description to embed.
        if (film.getDescription() != null) {
            description += film.getDescription();
            this.setDescription(description);
        }

        // Add film poster to embed.
        if (film.getPoster() != null)
            this.setImage(film.getPoster().getImageSize(2).getUrl());

        // Add release year to embed.
        if (film.getReleaseYear() != 0)
            this.addField(new MessageEmbed.Field("Year", "" + film.getReleaseYear(), true));

        // Add list of directors to embed.
        if (!directors.equals(""))
            this.addField(new MessageEmbed.Field("Director(s)", directors, true));

    }

}
