package images;

import Film.FilmSummary;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.image.WritableRenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

public class ListPreviewImage {

    // The image containing each film.
    private Image image;

    // The location of the file.
    private String url;

    public ListPreviewImage(HashSet<String> filmPosters) {

        BufferedImage[] images = new BufferedImage[filmPosters.size()];
        Iterator<String> iterator = filmPosters.iterator();
        int heightTotal = 0;
        for (int i = 0; i < filmPosters.size(); i++) {
            try {
                images[i] = ImageIO.read(new File(iterator.next()));
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

        url = "/Users/spencer/IdeaProjects/letterbot/src/main/java/images/tempImage.png";

        try {
            ImageIO.write(concatImage, "png", new File(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
