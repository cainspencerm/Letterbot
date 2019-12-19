package embeds;

import Contributor.ID.Contributions.FilmContribution;
import Contributor.ID.Contributor;
import Contributor.ID.Contributor.ContributionType;
import Search.Search;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;

public class ActorEmbedBuilder extends EmbedBuilder {

    public ActorEmbedBuilder(String name) {
        super();

        // Get the actor from Letterboxd.
        Contributor actor = Search.getActor(name);
        FilmContribution[] contributions = actor.getContributions(ContributionType.Actor);

        // Get the names of each film.
        HashSet<String> filmNames = new HashSet<>();
        for (int i = 0; i < contributions.length; i++) {
            filmNames.add(contributions[i].getFilm().getName());
        }

        Iterator<String> iterator = filmNames.iterator();
        String directors = "";
        for (int i = 0; i < filmNames.size(); i++) {
            directors += iterator.next();

            if (i != filmNames.size() - 1)
                directors += ", ";
        }

        // Construct an embed.
        this.setTitle(actor.getName(), actor.getLinks()[0].getUrl());
        this.setThumbnail("https://a.ltrbxd.com/logos/letterboxd-logo-alt-v-neg-rgb-1000px.png");
        this.setColor(new Color(21, 24, 29, 1));

        // TODO Find a way to get a contributor's member id.
        //this.setImage(actor.getAsMember(ContributionType.Actor).getAvatar().getImageSize(2).getUrl());
    }
}
