package objects;

import framework.ObjectId;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Кирилл on 01.06.2016.
 */
public class Bound extends GameObject {



    public Bound(float x, float y, ObjectId id) {
        super(x, y, id);
        width = 32;
        height = 32;
    }

    @Override
    public void tick(LinkedList<GameObject> object) {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, (int)width, (int)height);
    }

}
