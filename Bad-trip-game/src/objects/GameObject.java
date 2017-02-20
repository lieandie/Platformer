package objects;

import framework.ObjectId;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Кирилл on 29.05.2016.
 */
public abstract class GameObject {

    protected ObjectId id;

    protected static int num;

    protected float x;
    protected float y;

    protected float initx;

    protected float inity;
    protected float velX = 0;

    protected float velY = 0;
    protected boolean falling = true;
    protected boolean jumping = false;
    protected boolean walking = false;
    protected boolean dead = false;
    protected float width;
    protected float height;

    protected boolean doubleJumping = false;

    public GameObject(float x, float y, ObjectId id){
        initx = x;
        inity = y;
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void tick(LinkedList<GameObject> object);

    public abstract void render(Graphics g);

    public abstract Rectangle getBounds();

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    public float getVelX() {
        return velX;
    }
    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isDoubleJumping() {
        return doubleJumping;
    }

    public void setDoubleJumping(boolean doubleJumping) {
        this.doubleJumping = doubleJumping;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public boolean isWalking() {
        return walking;
    }

    public void setWalking(boolean walking) {
        this.walking = walking;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public float getInitx() {
        return initx;
    }

    public void setInitx(float initx) {
        this.initx = initx;
    }

    public float getInity() {
        return inity;
    }

    public void setInity(float inity) {
        this.inity = inity;
    }
}
