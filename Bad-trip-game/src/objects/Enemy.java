package objects;

import framework.*;
import framework.Handler;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Кирилл on 02.06.2016.
 */
public class Enemy extends GameObject {

    private int health;

    private int boundX;
    private int boundXNegative;

    private float gravity = 0.6f;
    private final float MAX_SPEED = 100;

    Texture tex = Handler.tex;
    private Animation animation;

    private boolean lastFallState;


    public Enemy(float x, float y, ObjectId id, int num) {
        super(x, y, id);
        width = 64;
        height = 64;
        health = 1;
        this.num = num;
        velX = 1;
        animation = new Animation(10, tex.slime[0], tex.slime[1], tex.slime[2]);

    }

    @Override
    public void tick(LinkedList<GameObject> object) {
        if(dead) {
            setY(getInity());
            Handler.killed.add(this);
            object.remove(this);
            dead = false;
        }else {
            x += velX;
            y += velY;
            if (falling) {
                velY += gravity;
                if (velY > MAX_SPEED) {
                    velY = MAX_SPEED;
                }
            }
            collide(object);
            animation.runAnimation();
        }
    }

    private void collide(LinkedList<GameObject> object){
        for (int i = 0; i < object.size(); i++) {
            GameObject tmpObj = object.get(i);

            if (tmpObj.getId() == ObjectId.Block || tmpObj.getId() == ObjectId.Spikes ) {
                if (getBounds().intersects(tmpObj.getBounds())) {
                    //if (onFloor) {
                    y = tmpObj.getY() - height / 2 + 3;
                    velY = 0;
                    falling = false;
                    //onFloor = true;
                } else {
                    falling = true;
                }


                if (getBoundsRight().intersects(tmpObj.getBounds())) {
                    x = tmpObj.getX() - width;
                    velX = -velX;
                }

                if (getBoundsLeft().intersects(tmpObj.getBounds())) {
                    x = tmpObj.getX() + width/2;
                    velX = -velX;

                }
            } else if (tmpObj.getId() == ObjectId.Water) {
                if (getBounds().intersects(tmpObj.getBounds())) {
                    if (!dead)
                        Sound.playSound("res/short_heavy_splash.wav");
                    dead = true;
                }
            }

        }
    }

    @Override
    public void render(Graphics g) {
        if(id ==ObjectId.Slime){
            if (velX > 0 && velY == 0) {
                animation.drawAnimation(g, (int) x, (int) (y - height/2), 64, 64);
            }

            if (velX < 0 && velY == 0) {
                animation.drawAnimation(g, (int) (x + width/2 + width/4), (int) (y - height/2), 64, 64, true);
            }
            if(velY != 0){
                g.drawImage(tex.slime[0], (int) x, (int) (y - height/2), 64, 64, null);
            }
            //g.drawImage(tex.slime[0], (int)x, (int)(y - height / 2), 64, 64, null);
           // g.drawRect((int) x + ((int) width / 2) - (((int) width / 2) / 2) - 8, (int) y - 64 + ((int) height / 2), (int) width / 2 + 13, (int) height / 4);
            /*g.drawRect((int) x + ((int) width / 2) - (((int) width / 2) / 2), (int) y - 2, (int) width / 2, (int) height / 2);
            g.drawRect((int) x + (int) width - 5, (int) y + 6 - 32, (int) 5, (int) height - 15);
            g.drawRect((int) x, (int) y + 6 - 32, (int) 5, (int) height - 15);*/
        }
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int) x + ((int) width / 2) - (((int) width / 2) / 2) - 8, (int) y - 64 + ((int) height / 2), (int) width / 2 + 13, (int) height / 4);
        //return new Rectangle((int)x, (int)y, (int)width, (int)height);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x + ((int) width / 2) - (((int) width / 2) / 2), (int) y - 2, (int) width / 2, (int) height / 2);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) x + (int) width - 5, (int) y + 6 - 32, (int) 5, (int) height - 15);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) x, (int) y + 6 - 32, (int) 5, (int) height - 15);
    }
}
