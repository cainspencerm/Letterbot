package embeds;

import List.ID.List;
import Film.FilmSummary;
import net.dv8tion.jda.api.EmbedBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;

public class ListEmbedBuilder {

    private EmbedBuilder embed;
    private File image;

    public ListEmbedBuilder(String listName) {

        // Get the film from Letterboxd.
        List list = List.getList(listName);

        // Get the names of each director.
        HashSet<FilmSummary> films = new HashSet<>();
        for (int i = 0; i < list.getPreviewEntries().length; i++) {
            films.add(list.getPreviewEntries()[i].getFilm());
        }

        Iterator<FilmSummary> iterator = films.iterator();


        // Construct an embed.
        embed = new EmbedBuilder();
        embed.setTitle(list.getName(), list.getLinks()[0].getUrl());
        embed.setThumbnail("https://a.ltrbxd.com/logos/letterboxd-logo-alt-v-neg-rgb-1000px.png");
        embed.setColor(new Color(21, 24, 29, 1));

        // Add description to embed.
        if (list.getDescription() != null) {
            embed.setDescription(LBMLtoDiscordMarkdown(list.getDescriptionLbml()));
        }

        // Add films.
        HashSet<URL> filmPosters = new HashSet<>();
        for (int i = 0; i < list.getPreviewEntries().length; i++) {
            try {
                filmPosters.add(new URL(list.getPreviewEntries()[i].getFilm().getPoster().getImageSize(1).getUrl()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        // Add preview image.
        try {
            String imageUrl = getConcatenatedImage(filmPosters);
            assert imageUrl != null;

            image = new File(imageUrl);
            embed.setImage("attachment://" + imageUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    public static String LBMLtoDiscordMarkdown(String lbml) {
        String discordMarkdown = "";

        for (int i = 0; i < lbml.length(); i++) {

            // Bold Text.
            if (i < lbml.length() - 4 &&
                    (lbml.substring(i, i+3).equalsIgnoreCase("<b>") ||
                            lbml.substring(i, i+4).equalsIgnoreCase("</b>"))) {
                discordMarkdown += "**";
                i += 2;
                i += lbml.substring(i - 2, i + 2).equalsIgnoreCase("</b>") ? 1 : 0;
            }

            // Italicized Text.
            else if (i < lbml.length() - 4 &&
                    (lbml.substring(i, i+3).equalsIgnoreCase("<i>") ||
                            lbml.substring(i, i+4).equalsIgnoreCase("</i>"))) {
                discordMarkdown += "*";
                i += 2;
                i += lbml.substring(i - 2, i + 2).equalsIgnoreCase("</i>") ? 1 : 0;
            }

            // Strong Text.
            else if (i < lbml.length() - 9 &&
                    (lbml.substring(i, i+8).equalsIgnoreCase("<strong>") ||
                            lbml.substring(i, i+9).equalsIgnoreCase("</strong>"))) {
                discordMarkdown += "**";
                i += 7;
                i += lbml.substring(i-7, i+2).equalsIgnoreCase("</strong>") ? 1 : 0;
            }

            // Line break.
            else if (i < lbml.length() - 6 &&
                    (lbml.substring(i, i + 4).equalsIgnoreCase("<br>") ||
                            lbml.substring(i, i + 6).equalsIgnoreCase("<br />"))) {
                discordMarkdown += "\n";
                i += 3;
                i += lbml.substring(i - 3, i + 3).equalsIgnoreCase("<br />") ? 2 : 0;
            }

            // Emphasized text.
            else if (i < lbml.length() - 5 &&
                    (lbml.substring(i, i + 4).equalsIgnoreCase("<em>") ||
                            lbml.substring(i, i + 5).equalsIgnoreCase("</em>"))) {
                discordMarkdown += "*";
                i += 3;
                i += lbml.substring(i - 3, i + 2).equalsIgnoreCase("</em>") ? 1 : 0;
            }

            // Block quote.
            else if (i < lbml.length() - 13 &&
                    (lbml.substring(i, i + 12).equalsIgnoreCase("<blockquote>") ||
                            lbml.substring(i, i + 13).equalsIgnoreCase("</blockquote>"))) {
                discordMarkdown += lbml.substring(i, i + 12).equalsIgnoreCase("<blockquote>") ? "> " : "";
                i += 11;
                i += lbml.substring(i - 11, i + 2).equalsIgnoreCase("</blockquote>") ? 1 : 0;
            }

            // Link text.
            else if (i < lbml.length() - 13 &&
                    lbml.substring(i, i + 9).equalsIgnoreCase("<a href=\"")) {
                String link = "";
                for (int j = i + 9; !lbml.substring(j, j + 2).equalsIgnoreCase("\">"); j++) {
                    link += lbml.charAt(j);
                    i = j + 3;
                }

                String title = "";
                for (int j = i; !lbml.substring(j, j + 4).equalsIgnoreCase("</a>"); j++) {
                    title += lbml.charAt(j);
                    i = j + 4;
                }
                discordMarkdown += title + " (" + link + ")";
            }

            else {
                discordMarkdown += lbml.charAt(i);
            }
        }

        return discordMarkdown;
    }

    private String getConcatenatedImage(HashSet<URL> filmPosters) {
        BufferedImage[] images = new BufferedImage[filmPosters.size()];
        Iterator<URL> iterator = filmPosters.iterator();
        int heightTotal = 0;
        for (int i = 0; i < filmPosters.size(); i++) {
            try {
                images[i] = ImageIO.read(iterator.next());
            } catch (IOException e) {
                e.printStackTrace();
            }
            heightTotal += images[i].getHeight();
        }

        int currentHeight = 0;
        BufferedImage concatImage = new BufferedImage(100, heightTotal, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = concatImage.createGraphics();
        for (BufferedImage bufferedImage : images) {
            g2d.drawImage(bufferedImage, 0, currentHeight, null);
            currentHeight += bufferedImage.getHeight();
        }
        g2d.dispose();

        String url = "tempImage.png";








        AffineTransform affineTransform = new AffineTransform();



















        try {
            ImageIO.write(concatImage, "png", new File(url));
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }



    }

    public EmbedBuilder getEmbed() {
        return embed;
    }

    public File getImage() {
        return image;
    }
}
