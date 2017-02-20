package window;

import framework.*;
import framework.Menu;
import objects.Boss;
import objects.Player;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;

/**
 * Created by Кирилл on 29.05.2016.
 */
public class Game extends Canvas implements Runnable {


    private boolean running = false;
    private Thread thread;
    private final int FPS = 60;
    private int curFps;

    public static float opacity;

    public static int WIDTH;

    public static int HEIGHT;
    private Sound music;
    public static boolean showHelp = false;
    public static boolean allowGameTimer = false;

    private int splashtime = 0;
    public static float gameTime;

    private Handler handler;
    private Splash splash;
    private Menu menu;

    static Camera cam;
    public static boolean levelStart = true;

    public static int LEVEL = 1;

    public static enum STATE{
        Menu,
        Help,
        Game,
        Boss,
        Results;
    }

    public static STATE state = STATE.Menu;


    private void init(){
        WIDTH = getWidth();
        HEIGHT = getHeight();
        gameTime = 0;

        cam = new Camera(0, 0);
        handler = new Handler(cam);

        //handler.switchLevel();

        opacity = 1f;
        splash = new Splash(Handler.splash);
        menu = new Menu(Handler.tex, handler);

        music = new Sound("/Oniku Loop.wav");
        this.addKeyListener(new KeyInput(handler,this, menu));
    }

    public synchronized void start(){
        if(!running){
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        init();
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                curFps = frames;
                frames = 0;
            }
        }
    }

    private void tick(){

        if(state == STATE.Game || state == STATE.Boss ) {
            if (allowGameTimer) {
                gameTime++;
            }
            handler.tick();
            for (int i = 0; i < handler.object.size(); i++) {
                if (handler.object.get(i).getId() == ObjectId.Player) {
                    cam.tick(handler.object.get(i));
                }
            }
            if (!music.isPlaying()) {
                music.play();
            }
        } else if(state == STATE.Menu){
            menu.tick();
        }
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        Graphics2D g2d = (Graphics2D) g;

        if(state == STATE.Help){
            g.drawImage(Handler.background, 0, 0, 810, (32 * 20), null);
            g2d.drawImage(Handler.help, 0, 0, getWidth(), getHeight(), this);
        } else if(state == STATE.Menu){
            menu.render(g);
        }else if (state == STATE.Game || state == STATE.Boss || state == STATE.Results) {
            g.drawImage(Handler.background, 0, 0, getWidth(), getHeight(), this);

            g2d.setFont(Font.getFont("ARIAL"));

            g2d.translate(cam.getX(), cam.getY());

            handler.render(g);

            g2d.translate(-cam.getX(), -cam.getY());

            splash.splashController(g2d, levelStart, LEVEL, 150);

            g.setColor(Color.WHITE);

            if(state == STATE.Boss) {
                g.setFont(new Font("Press start 2P", Font.PLAIN, 20));
                g2d.drawString("Boss       " + Boss.health, 505, 584);
                switch (Boss.health) {
                    case 0: {
                        //g.drawImage(tex.health[0], 405, 5, null);
                        break;
                    }
                    case 1: {
                        g.drawImage(Handler.tex.health[3], 500, 480, 306, 125, null);
                        break;
                    }
                    case 2: {
                        g.drawImage(Handler.tex.health[2], 500, 480, 306, 125, null);
                        break;
                    }
                    case 3: {
                        g.drawImage(Handler.tex.health[1], 500, 480, 306, 125, null);
                        break;
                    }
                    case 4: {
                        g.drawImage(Handler.tex.health[0], 500, 480, 306, 125, null);
                        break;
                    }
                }
            }

            g.setFont(new Font("Press start 2P", Font.PLAIN, 20));
            g2d.drawString("Deaths " + Player.deaths, getWidth() - 200, 20);
            g2d.drawString("Time " + gameTime / 100, getWidth() - 200, 40);
        }
        if (state == STATE.Results){
            splash.splashControllerFinal(g2d, Player.deaths, gameTime, 0);
        }

        g.dispose();
        bs.show();
    }


    public static void main(String[] args) {
        new Window(800, 600, "Bad Trip", new Game());
    }

}
