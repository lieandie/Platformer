package framework;

import objects.Boss;
import objects.GameObject;
import objects.*;
import window.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Created by Кирилл on 30.05.2016.
 */
public class Handler {

    public static LinkedList<GameObject> object = new LinkedList<>();
    public static LinkedList<GameObject> killed = new LinkedList<>();

    private GameObject tempObject;
    public static Player player;

    private Camera cam;

    public static int deaths;

    public static Texture tex;

    public static BufferedImage background = null;
    public static BufferedImage splash = null;


    public static BufferedImage help = null;
    public static BufferedImage menu = null;
    public static BufferedImage level1 = null;
    public static BufferedImage level2 = null;
    public static BufferedImage level3 = null;
    public static BufferedImage boss = null;


    public Handler(Camera cam){
        tex = new Texture();
        this.cam = cam;

        BufferedImageLoader loader = new BufferedImageLoader();
        background = loader.loadImage("/level1back.png");
        menu = loader.loadImage("/menu.png");
        help = loader.loadImage("/help.png");
        level1 = loader.loadImage("/level.png");
        level2 = loader.loadImage("/level2.png");
        level3 = loader.loadImage("/level3.png");
        splash = loader.loadImage("/levelsplash.png");
        boss = loader.loadImage("/bossfight.png");
    }

    public void tick(){
        for(int i = 0; i < object.size(); i++ ){
            tempObject = object.get(i);
            tempObject.tick(object);
        }
    }

    public void deleteProjectiles(){
        for(int i = 0; i < object.size(); i++ ){
            if (object.get(i).getId() == ObjectId.Projectile){
                object.remove(i);
            }
        }
    }

    public void render(Graphics g){
       // int draw = 0;
        for(int i = 0; i < object.size(); i++ ){
            tempObject = object.get(i);
            if(Camera.zone.getBounds().intersects(tempObject.getBounds())){
                //tempObject.getX() >= Game.cam.getX() || )
                tempObject.render(g);
                //draw++;
                //g.drawRect((int)Camera.zone.getX(),(int)Camera.zone.getY(),(int)Camera.zone.getWidth(),(int)Camera.zone.getHeight());
            }
        }

        //System.out.println("Отрисовано "+draw);
    }

    private void clearLevel(){
        object.clear();
    }

    public void loadImageLevel(BufferedImage image){
        int w = image.getWidth();
        int h = image.getHeight();
        cam.setImgCoord(w, h);

        for(int x = 0; x < w; x++) {
            for(int y = 0; y < h; y++){
                int pixel = image.getRGB(x, y);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if(red == 100 && green == 100 && blue == 100){
                    addObject(new Block(x * 32, y * 32, 0, ObjectId.Water, x+y*x));
                }
                if(red == 255 && green == 255 && blue == 255){
                    addObject(new Block(x * 32, y * 32, 1, ObjectId.Block, x+y*x));
                }
                if(red == 255 && green == 255 && blue == 0){
                    addObject(new Block(x * 32, y * 32, 2, ObjectId.Block, x+y*x));
                }
                if(red == 0 && green == 0 && blue == 255){
                    this.player = new Player(x * 32, y * 32, this, cam, ObjectId.Player);
                    addObject(player);
                }
                if(red == 150 && green == 150 && blue == 150){
                    addObject(new Block(x * 32, y * 32, 4, ObjectId.Water, x+y*x));
                }
                if(red == 255 && green == 0 && blue == 0){
                    addObject(new Bound(x * 32, y * 32, ObjectId.CameraBound));
                }
                if(red == 85 && green == 85 && blue == 150){
                    addObject(new Flag(x * 32, y * 32, ObjectId.Flag));
                }
                if(red == 230 && green == 0 && blue == 230){
                    addObject(new Flag(x * 32, y * 32, ObjectId.Start));
                }
                if(red == 0 && green == 230 && blue == 230){
                    addObject(new Flag(x * 32, y * 32, ObjectId.Exit));
                }
                if(red == 155 && green == 255 && blue == 255){
                    addObject(new StaticEnemy(x * 32, y * 32, ObjectId.Spikes));
                }
                if(red == 123 && green == 123 && blue == 123){
                    addObject(new Enemy(x * 32, y * 32, ObjectId.Slime, x + y*x));
                }
                if(red == 25 && green == 125 && blue == 25){
                    addObject(new Boss(x * 32, y * 32, ObjectId.Boss, this, player));
                }
            }
        }
    }


    public void switchLevel(){
        clearLevel();
        cam.setX(-64);
        cam.setY(-64);
        switch (Game.LEVEL){
            case 1:{
                Game.allowGameTimer = true;
                Game.gameTime = 0;
                Player.deaths = 0;
                loadImageLevel(level1);
                killed.clear();
                break;
            }
            case 2:{
                Game.allowGameTimer = true;
                loadImageLevel(level2);
                killed.clear();
                break;
            }
            case 3:{
                Game.allowGameTimer = true;
                loadImageLevel(level3);
                killed.clear();
                break;
            }
            case 4:{
                Game.allowGameTimer = true;
                loadImageLevel(boss);
                killed.clear();
                Game.state = Game.STATE.Boss;
                break;
            }
        }
        Game.LEVEL++;
        Game.levelStart = true;
        Game.opacity = 1f;
    }

    public void addObject(GameObject object){
        this.object.add(object);
    }

    public void removeObject(GameObject object){
        this.object.remove(object);
    }

    public void spawnEnemies(){
        if(!killed.isEmpty()) {
            for (int i = 0; i < killed.size(); i++){
                object.add(killed.get(i));
            }
        }
    }



}
