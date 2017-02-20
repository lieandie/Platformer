package objects;

import framework.Animation;
import framework.ObjectId;
import framework.Texture;
import window.Game;
import framework.Handler;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Кирилл on 14.06.2016.
 */
public class Boss extends GameObject {

    private boolean ending;

    Texture tex = Handler.tex;
    Handler handler;
    private Animation animation;
    Player player;

    public static int health;

    private boolean damaged;
    private boolean collidy;
    private int collidyTimer;

    private int bossBodyInitX;

    private int bossBodyInitY;
    private int bossHeadInitX;
    private int bossHeadInitY;
    private boolean headShutter;
    private int shutterTimer;

    private int leftShooterX;
    private int leftShooterY;
    private int rightShooterX;
    private int rightShooterY;

    private boolean boneDraw;
    private int boneCount;
    private int boneTrigger;
    private boolean headFall;

    private boolean firePhase;
    private int fireTimer;
    private int passiveTimer;
    private int endingTimer;

    private int headX;
    private int headY;

    private float alpha;
    private boolean vive;

    public Boss(float x, float y, ObjectId id, Handler handler, Player player) {
        super(x, y, id);

        this.player = player;

        boneDraw = false;
        boneCount = 0;
        boneTrigger = 0;
        collidyTimer = 0;
        endingTimer = 0;

        health = 4;
        ending = false;

        this.handler = handler;
        bossBodyInitX = (int) x - 382;
        bossBodyInitY = (int) y;
        bossHeadInitX = headX = (int) x - 32;
        bossHeadInitY = headY = (int) y + 170;

        headShutter = true;
        firePhase = true;
        shutterTimer = 0;

        headFall = false;

        damaged = false;
        collidy = true;

        alpha = 0;
        vive = false;

        leftShooterX = 85;
        rightShooterX = 731;
        leftShooterY = rightShooterY = 225;

    }

    public void dealDamage() {
        health--;
        collidy = false;
        headFall = false;
        firePhase = false;
    }

    @Override
    public void tick(LinkedList<GameObject> object) {
        if (!ending) {
            if (Game.allowGameTimer) {
                if (health == 0) {
                    ending = true;
                }

                if (!Player.allowMove) {
                    restart();
                }
                if (!collidy) {
                    collidyTimer++;
                    if (collidyTimer > 80) {
                        collidy = true;
                        collidyTimer = 0;
                    }
                }
                if (!vive) {
                    if (alpha < .8f) {
                        alpha += .01f;
                    } else {
                        vive = true;
                    }
                } else {
                    if (alpha >= 0.06f) {
                        alpha -= .01f;
                    } else {
                        vive = false;
                    }
                }
                if (firePhase) {
                    if (fireTimer % 20 == 0) {
                        shot();
                    }
                    fireTimer++;
                    if (fireTimer > 700) {
                        firePhase = false;
                        headFall = true;
                    }
                } else {
                    headFall();
                }

                if (firePhase) {
                    shutterTimer++;
                    if (shutterTimer > 115) {
                        headMove();
                        shutterTimer = 0;
                    }
                }
            }
        } else {
            endingTimer++;
        }
    }

    public void restart() {
        boneDraw = false;
        boneCount = 0;
        boneTrigger = 0;

        headX = bossHeadInitX;
        headY = bossHeadInitY;

        headShutter = true;
        shutterTimer = 0;

        headFall = false;

        damaged = false;
        collidy = true;
        health = 4;

        alpha = 0;
        vive = false;
        handler.deleteProjectiles();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(tex.boss[0], bossBodyInitX, bossBodyInitY, 800, 300, null);
        if (!firePhase) {
            for (int i = 0; i < boneCount; i++) {
                g.drawImage(tex.boss[2], bossHeadInitX + 21, bossHeadInitY + 15 + 16 * i, 60, 60, null);
            }
        }
        if (!collidy) {
            if (collidyTimer % 20 == 0)
                g.drawImage(tex.boss[4], headX, headY, 100, 120, null);
            else g.drawImage(tex.boss[1], headX, headY, 100, 120, null);
        } else
            g.drawImage(tex.boss[1], headX, headY, 100, 120, null);

        if (firePhase) {
            g.drawImage(tex.boss[3], leftShooterX, leftShooterY, 52, 52, null);
            g.drawImage(tex.boss[3], rightShooterX, rightShooterY, 52, 52, null);
            Graphics2D g2d = (Graphics2D) g;
            Color tmp = g2d.getColor();
            g2d.setColor(Color.orange);
            g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));
            g.fillOval(82, 223, 57, 56);
            g.fillOval(729, 223, 57, 56);
            g2d.setColor(tmp);
            g2d.setComposite(AlphaComposite.SrcOver.derive(1f));
        }

        if (ending) {
            blowUp(g);
            Game.allowGameTimer = false;
            if (endingTimer > 350) {
                Game.LEVEL = 1;
                Game.state = Game.STATE.Results;
            }
        }

    }

    public void blowUp(Graphics g) {
        if (endingTimer % 22 == 0) {
            Handler.object.add(new Flag((float) (Math.random() * 800), (float) (Math.random() * 600), ObjectId.Blow));
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(headX + 20, headY + 25, 55, 85);
    }

    public Rectangle getTopBounds() {
        return new Rectangle(headX + 5, headY, 90, 25);
    }

    private void headMove() {
        if (headShutter) {
            headY += 4;
            headShutter = false;
        } else {
            headY -= 4;
            headShutter = true;
        }
    }

    private void headFall() {
        if (headFall) {
            if (bossHeadInitY + 15 + 16 * boneCount < 430) {
                boneTrigger += 2;
                if (boneTrigger > 15) {
                    boneCount++;
                    boneTrigger = 0;
                }
            } else {
                passiveTimer++;
                if (passiveTimer > 150) {
                    headFall = false;
                }
            }

            if (headY <= 450) {
                headY += 2;
            }
        } else {
            headFall = false;
            if (bossHeadInitY + 15 + 16 * boneCount > bossHeadInitY + 10) {
                boneTrigger += 2;
                if (boneTrigger > 16) {
                    boneCount--;
                    boneTrigger = 0;
                }
            }

            if (headY >= bossHeadInitY) {
                headY -= 2;
            } else {
                firePhase = true;
                fireTimer = 0;
                passiveTimer = 0;
            }
        }
    }

    private void shot() {
        switch ((int) (Math.random() * 3)) {
            case 1: {
                Handler.object.add(new Projectile(leftShooterX, leftShooterY, ObjectId.Projectile, (float) (Math.random() * 24) / 10, 650));
                break;
            }
            case 2: {
                Handler.object.add(new Projectile(rightShooterX, rightShooterY, ObjectId.Projectile, -(float) ((Math.random() * 24) / 10), 650));
                break;
            }
            case 3: {
                Handler.object.add(new Projectile(rightShooterX, rightShooterY, ObjectId.Projectile, -(float) ((Math.random() * 24) / 10), 650));
                Handler.object.add(new Projectile(leftShooterX, leftShooterY, ObjectId.Projectile, (float) (Math.random() * 24) / 10, 650));
                break;
            }
        }
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public boolean isCollidy() {
        return collidy;
    }

    public void setCollidy(boolean collidy) {
        this.collidy = collidy;
    }
}
