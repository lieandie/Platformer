package framework;

import java.awt.image.BufferedImage;

/**
 * Created by Кирилл on 30.05.2016.
 */
public class SpriteSheet {

    private BufferedImage image;

    public SpriteSheet(BufferedImage image){
        this.image = image;
    }

    public BufferedImage grabImage(int col, int row, int width, int height){
        BufferedImage img = image.getSubimage((col * width) - width, (row * height) - height, width, height);
        return img;
    }

    public BufferedImage grabNative(int x, int y, int width, int height){
        BufferedImage img = image.getSubimage(x, y, width, height);
        return img;
    }

}
