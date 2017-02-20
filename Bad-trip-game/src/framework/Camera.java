package framework;

import objects.GameObject;
import window.Game;

import java.awt.*;

/**
 * Created by Кирилл on 30.05.2016.
 */
public class Camera {

    private float x;
    private float y;

    private int imgX;
    private int imgY;

    private float playerx;

    private float playery;

    private float targetX;


    private float targetY;
    private int boundX;

    private int boundY;
    static Rectangle zone;


    private final float ALPHA = 0.08f;


    public Camera(float x, float y){
        this.x = x;
        this.y = y;
        zone = new Rectangle();
        zone.setBounds((int)x, (int)y, Game.WIDTH, Game.HEIGHT);
    }

    public void tick(GameObject player){
        targetX = (Game.WIDTH / 2 - player.getX());     //псевдо - интерполяция камеры
        if(x != targetX){
            if(targetX + Game.WIDTH / 12 > 0){
               targetX = -32;
            } else if(Math.abs(targetX)  > imgX - Game.WIDTH  - 32 ){
                targetX = -(imgX - Game.WIDTH - 32);
            }
            x += (targetX - x) * ALPHA;
        }

        targetY = (Game.HEIGHT / 2 - player.getY());
        if(y != targetY){
            if(targetY + Game.HEIGHT / 12 > 0) {
                targetY = -32;
            } else if(Math.abs(targetY)  > imgY - Game.HEIGHT  - 32 ){
                targetY = -(imgY - Game.HEIGHT - 32);
            }

            y += (targetY - y) * ALPHA;
        }

        zone.setLocation(-(int)x , -(int)y);

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

    public float getTargetX() {
        return targetX;
    }

    public void setTargetX(float targetX) {
        this.targetX = targetX;
    }

    public float getTargetY() {
        return targetY;
    }

    public void setTargetY(float targetY) {
        this.targetY = targetY;
    }

    public void setImgCoord(int x, int y){
        imgX = x * 32;
        imgY = y * 32;
    }
    public float getPlayery() {
        return playery;
    }

    public void setPlayery(float playery) {
        this.playery = playery;
    }

    public float getPlayerx() {
        return playerx;
    }

    public void setPlayerx(float playerx) {
        this.playerx = playerx;
    }


}
