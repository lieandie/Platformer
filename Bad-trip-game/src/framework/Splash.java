package framework;

import objects.Player;
import window.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Кирилл on 01.06.2016.
 */
public class Splash {

    private BufferedImage splash;
    private int timer;
    private int initx;
    private int y1;
    private int y1f;
    private boolean fstage = false;
    private boolean sstage = false;
    private boolean end = false;

    public Splash(BufferedImage splash){
        this.splash = splash;
        initx = -600;
        timer = 0;
        y1 = 115;
        y1f = 0;
    }

    public void tick(int lenght) {
        if (!fstage) {
            if (y1f < y1) {
                y1f++;
            } else fstage = true;
        }else {
            if(Game.state != Game.STATE.Results) {
                timer++;
                if (timer >= lenght) {
                    sstage = true;
                    if (y1f > 0) {
                        y1f -= 3;
                    } else end = true;
                }
            }
        }

    }

    public void render(Graphics2D g, String caption, int width, int height){
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g.setColor(Color.WHITE);
        //g.fillRect(0, 0, getWidth(), getHeight());
        Font str = new Font("Press start 2P",Font.PLAIN, 50);
        g.setFont(str);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,width, y1f);
        g.fillRect(0,620 - y1f,width,y1f );
        g.setColor(Color.WHITE);
        g.drawImage(splash, -805 + y1f * 7, 0, width, height, null);
        if(sstage){
            g.drawString(caption, -(width / 4 + 200) + y1f, height / 2 );
        } else
        g.drawString(caption, -(width / 4 - 130) + y1f, height / 2 );
    }

    public void splashController(Graphics2D g, boolean levelStart, int LEVEL, int lenght){
        if(levelStart) {
            Player.allowMove = false;
            tick(lenght);
            if(Game.state == Game.STATE.Boss) {
                Game.allowGameTimer = false;
                render(g, "Boss", Game.WIDTH, Game.HEIGHT);
            } else {
                Game.allowGameTimer = false;
                render(g, "Level " + (LEVEL - 1), Game.WIDTH, Game.HEIGHT);
            }
            }
                if(end){
                    Game.levelStart = false;
                    timer = 0;
                    fstage = false;
                    y1f = 0;
                    end = false;
                    sstage = false;
                    Game.allowGameTimer = true;
                    Player.allowMove = true;
                }

    }

    public void splashControllerFinal(Graphics2D g, int deaths, float time, int lenght){
        Game.state = Game.STATE.Results;
        Player.allowMove = false;
        tick(lenght);
        Game.allowGameTimer = false;
        g.setColor(Color.WHITE);
        //g.fillRect(0, 0, getWidth(), getHeight());
        Font str = new Font("Press start 2P",Font.PLAIN, 30);
        g.setFont(str);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,Game.WIDTH, y1f);
        g.fillRect(0,620 - y1f,Game.WIDTH,y1f );
        g.setColor(Color.WHITE);
        g.drawImage(splash, -805 + y1f * 7, 0, Game.WIDTH, Game.HEIGHT, null);
        g.drawString("CONGRATS", -(Game.WIDTH / 4 - 400) + y1f, Game.HEIGHT - 500 );
        g.drawString("RESULTS:", -(Game.WIDTH / 4 - 400) + y1f, Game.HEIGHT - 400 );
        g.drawString("DEATHS = "+deaths, -(Game.WIDTH / 4 - 400) + y1f, Game.HEIGHT - 350 );
        g.drawString("TIME = "+time/100, -(Game.WIDTH / 4 - 400) + y1f, Game.HEIGHT - 300 );
        g.drawString("PRESS SPACE TO CONTINUE", -(Game.WIDTH / 4 - 100) + y1f, Game.HEIGHT - 100 );
    }

}

