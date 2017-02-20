package objects;

import framework.ObjectId;
import framework.Texture;
import framework.Handler;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Кирилл on 15.06.2016.
 */
public class Projectile extends GameObject {

    private float velx, desty;
    Texture tex = Handler.tex;
    int size = 52;

    public Projectile(float x, float y, ObjectId id, float velx, float desty) {
        super(x, y, id);
        this.velx = velx;
        this.desty = desty;
    }

    @Override
    public void tick(LinkedList<GameObject> object) {

        if(y > 610){
            Handler.object.remove(this);
        }


        x += velx;

        y += 1.5f;


    }

    @Override
    public void render(Graphics g) {
        g.drawImage(tex.boss[3], (int)x, (int)y, 52, 52, null);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x + 10, (int)y + 5, 36, 32);
    }
}
