package objects;

import framework.ObjectId;
import framework.Texture;
import framework.Handler;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Кирилл on 01.06.2016.
 */
public class StaticEnemy extends GameObject {

    Texture tex = Handler.tex;

    public StaticEnemy(float x, float y, ObjectId id) {
        super(x, y, id);
        height = 32;
        width = 32;
    }

    @Override
    public void tick(LinkedList<GameObject> object) {

    }

    @Override
    public void render(Graphics g) {
        if(this.id == ObjectId.Spikes){
            g.drawImage(tex.trap[0], (int)x, (int)y, null);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }
}
