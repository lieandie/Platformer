package framework;

import java.awt.image.BufferedImage;

/**
 * Created by Кирилл on 30.05.2016.
 */
public class Texture {

    SpriteSheet bs, ps, ss, bss, hs;
    private BufferedImage block_sheet = null;
    private BufferedImage player_sheet = null;
    private BufferedImage slime_sheet = null;
    private BufferedImage boss_sheet = null;
    private BufferedImage hbar = null;

    public  BufferedImage[] block = new BufferedImage[6];
    public  BufferedImage[] trap = new BufferedImage[1];
    public  BufferedImage[] player = new BufferedImage[5];
    public  BufferedImage[] slime = new BufferedImage[3];
    public  BufferedImage[] boss = new BufferedImage[6];
    public BufferedImage[] health = new BufferedImage[4];

    public Texture(){
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            block_sheet = loader.loadImage("/ground.png");
            player_sheet = loader.loadImage("/player.png");
            slime_sheet = loader.loadImage("/Slime.png");
            boss_sheet = loader.loadImage("/boss.png");
            hbar = loader.loadImage("/hp.png");
        }catch (Exception e){
            e.printStackTrace();
        }

        bs = new SpriteSheet(block_sheet);
        ps = new SpriteSheet(player_sheet);
        ss = new SpriteSheet(slime_sheet);
        bss = new SpriteSheet(boss_sheet);
        hs = new SpriteSheet(hbar);


        getTextures();
    }

    private void getTextures(){
        block[0] = bs.grabImage(4, 1, 32, 32);  //вода врех
        block[1] = bs.grabImage(1, 1, 32, 32);  //трава
        block[2] = bs.grabImage(2, 1, 32, 32);  //грязь
        block[3] = bs.grabImage(5, 1, 32, 32);  //вода тело
        block[4] = bs.grabImage(6, 1, 32, 32);
        block[5] = bs.grabImage(7, 1, 32, 32);

        player[0] = ps.grabImage(1, 1, 105, 150); //стойка
        player[1] = ps.grabImage(1, 2, 112, 150);
        player[2] = ps.grabImage(1, 3, 112, 152);
        player[3] = ps.grabImage(1, 4, 105, 153);
        player[4] = ps.grabImage(1, 5, 110, 153);

        slime[0] = ss.grabImage(1, 1, 32, 32);
        slime[1] = ss.grabImage(2, 1, 32, 32);
        slime[2] = ss.grabImage(3, 1, 32, 32);

        trap[0] = bs.grabImage(1 , 2, 32, 32);

        boss[0] = bss.grabNative(0, 0, 272, 127);
        boss[1] = bss.grabNative(0, 134, 50, 75);
        boss[2] = bss.grabNative(239, 140, 21, 20);
        boss[3] = bss.grabNative(0, 236 , 46, 46);
        boss[4] = bss.grabNative(222, 208 , 50, 72);
        boss[5] = bss.grabNative(125, 251 , 44, 30);


        health[0] = hs.grabNative(0, 0, 206, 85);
        health[1] = hs.grabNative(0, 87, 206, 85);
        health[2] = hs.grabNative(0, 174, 206, 85);
        health[3] = hs.grabNative(0, 261, 206, 85);

    }

}
