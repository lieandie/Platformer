package objects;

import framework.*;
import framework.Camera;
import framework.Handler;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Кирилл on 30.05.2016.
 */
public class Player extends GameObject {
    private float width = 48f;

    private float height = 64f;
    private float spawn_x;
    private float spawn_y;

    private float gravity = 0.6f;

    private float ALPHA;

    public static int deaths = Handler.deaths;
    public static boolean allowMove = true;

    private final float MAX_SPEED = 100;

    private Handler handler;
    private Camera cam;
    Texture tex = Handler.tex;

    private Animation walkAnimation;

    public Player(float x, float y, Handler handler, Camera cam, ObjectId id) {
        super(x, y, id);
        ALPHA = 1f;
        this.cam = cam;
        this.handler = handler;
        spawn_x = x;
        spawn_y = y;
        walkAnimation = new Animation(6, tex.player[1], tex.player[2]);
    }

    @Override
    public void tick(LinkedList<GameObject> object) {
        if (dead) {
            if(ALPHA > 0.10f){
                ALPHA -= 0.06f;
                velX = 0;
                velY = 0;
                allowMove = false;
            } else {
                spawn();
                ALPHA = 1f;
                allowMove = true;
                deaths++;
            }
        } else {

            x += velX;
            y += velY;

            if (falling || jumping) {
                velY += gravity;

                if (velY > MAX_SPEED) {
                    velY = MAX_SPEED;
                }
            }

            collision(object);
            cam.setPlayerx(x);
            cam.setPlayery(y);

            walkAnimation.runAnimation();
        }
    }

    private void collision(LinkedList<GameObject> object) {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tmpObj = handler.object.get(i);

            if (tmpObj.getId() == ObjectId.Block) {

                if (getBoundsTop().intersects(tmpObj.getBounds())) {
                    y = tmpObj.getY() + height / 2;
                    velY = 0;
                }

                if (getBounds().intersects(tmpObj.getBounds())) {
                    y = tmpObj.getY() - height + 1;
                    velY = 0;
                    falling = false;
                    jumping = false;
                    doubleJumping = false;
                } else {
                    falling = true;
                }


                if (getBoundsRight().intersects(tmpObj.getBounds())) {
                    x = tmpObj.getX() - 51;

                }

                if (getBoundsLeft().intersects(tmpObj.getBounds())) {
                    x = tmpObj.getX() + 33;

                }
            } else if (tmpObj.getId() == ObjectId.Water) {
                if(getBounds().intersects(tmpObj.getBounds())) {
                    if(!dead)
                    Sound.playSound("/short_heavy_splash.wav");
                    dead = true;
                }
            } else if (tmpObj.getId() == ObjectId.CameraBound) {        //предел

                if (getBoundsTop().intersects(tmpObj.getBounds())) {
                    y = tmpObj.getY() + height / 2;
                    velY = 0;
                }


                if (getBounds().intersects(tmpObj.getBounds())) {
                    //x = spawn_x;
                    //y = spawn_y;
                    //velY = 0;
                }

                if (getBoundsRight().intersects(tmpObj.getBounds())) {
                    x = tmpObj.getX() - 51;

                }

                if (getBoundsLeft().intersects(tmpObj.getBounds())) {
                    x = tmpObj.getX() + 33;

                }
            } else if (tmpObj.getId() == ObjectId.Spikes) {    //флаг
                if (getBoundsTop().intersects(tmpObj.getBounds())) {
                    dead = true;
                }

                if (getBounds().intersects(tmpObj.getBounds())) {
                    dead = true;
                }

                if (getBoundsRight().intersects(tmpObj.getBounds())) {
                    dead = true;
                }

                if (getBoundsLeft().intersects(tmpObj.getBounds())) {
                    dead = true;
                }
            } else if (tmpObj.getId() == ObjectId.Flag) {    //флаг
                if (getBounds().intersects(tmpObj.getBounds())) {
                    handler.switchLevel();
                }
            }else if (tmpObj.getId() == ObjectId.Slime){
                Enemy tmp = (Enemy) tmpObj;
                if(getBounds().intersects((tmp.getBoundsTop()))){
                    velY = -7;
                    jumping = true;
                    tmpObj.setX(tmpObj.getInitx());
                    tmpObj.setDead(true);
                }
                if(getBounds().intersects(tmp.getBounds()) || getBoundsRight().intersects(tmp.getBoundsLeft()) || getBoundsLeft().intersects(tmp.getBoundsRight())){
                    dead = true;
                }
            } else if (tmpObj.getId() == ObjectId.Boss){
                Boss tmp = (Boss) tmpObj;
                if(tmp.isCollidy()) {
                    if (getBounds().intersects(tmp.getTopBounds())) {
                        velY = -12;
                        jumping = true;
                        tmp.dealDamage();
                    } else if (getBounds().intersects(tmp.getBounds()) || getBoundsLeft().intersects(tmp.getBounds()) || getBoundsRight().intersects(tmp.getBounds()) || getBoundsTop().intersects(tmp.getBounds())) {
                        dead = true;
                    }
                }
            } else if(tmpObj.getId() == ObjectId.Projectile){
                Projectile tmp = (Projectile) tmpObj;
                if(getBounds().intersects(tmp.getBounds()) || getBoundsTop().intersects(tmp.getBounds()) || getBoundsLeft().intersects(tmp.getBounds()) || getBoundsRight().intersects(tmp.getBounds())){
                    dead = true;
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        Graphics2D g2d = (Graphics2D) g;

        if(dead){
                g2d.setComposite(AlphaComposite.SrcOver.derive(ALPHA));
        }

        if (velX > 0 && velY == 0) {
            walkAnimation.drawAnimation(g, (int) x, (int) y, 48, 64);
        }

        if (velX < 0 && velY == 0) {
            walkAnimation.drawAnimation(g, (int) x, (int) y, 48, 64, true);
        }

        if (velX == 0 && velY == 0) {
            g.drawImage(tex.player[0], (int) x, (int) y, 48, 64, null);
        }

        if (velX >= 0 && velY < 0) {
            g.drawImage(tex.player[3], (int) x, (int) y, 48, 64, null);
        }

        if (velX < 0 && velY < 0) {
            g.drawImage(tex.player[3], (int) x + tex.player[3].getWidth() / 2, (int) y, -48, 64, null);
        }

        if (velY > 0 && velX >= 0) {
            g.drawImage(tex.player[4], (int) x, (int) y, 48, 64, null);
        }

        if (velY > 0 && velX < 0) {
            g.drawImage(tex.player[4], (int) x + tex.player[4].getWidth() / 2, (int) y, -48, 64, null);
        }

        g2d.setComposite(AlphaComposite.SrcOver.derive(1f));

       /* g.setColor(Color.RED);
        g2d.draw(getBounds());
        g2d.draw(getBoundsRight());
        g2d.draw(getBoundsLeft());
        g2d.draw(getBoundsTop());*/
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x + ((int) width / 2) - (((int) width / 2) / 2), (int) y + ((int) height / 2), (int) width / 2, (int) height / 2);
        //return new Rectangle((int)x, (int)y, (int)width, (int)height);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int) x + ((int) width / 2) - (((int) width / 2) / 2), (int) y, (int) width / 2, (int) height / 2);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) x + (int) width - 5, (int) y + 6, (int) 5, (int) height - 15);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) x, (int) y + 6, (int) 5, (int) height - 15);
    }

    public void spawn(){
        x = spawn_x;
        y = spawn_y;
        jumping = false;
        walking = false;
        dead = false;
        velY = 0;
        velX = 0;
        handler.spawnEnemies();
        handler.killed.clear();
    }


}
