package objects;

import framework.ObjectId;
import framework.Texture;
import framework.Handler;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Кирилл on 29.05.2016.
 */
public class Block extends GameObject {

    Texture tex = Handler.tex;

    private int type;

    public Block(float x, float y, int type, ObjectId id, int num) {
        super(x, y, id);
        this.type = type;
        width = 32;
        height = 32;
        this.num = num;
    }

    @Override
    public void tick(LinkedList<GameObject> object) {

    }

    @Override
    public void render(Graphics g) {
        if(type == 0){
            g.drawImage(tex.block[0],(int)x, (int)y,(int) width, (int) height, null); //вода верх
        }
        if(type == 4){
            g.drawImage(tex.block[3],(int)x, (int)y,(int) width, (int) height, null); //вода тело
        }
        if(type == 1){
            g.drawImage(tex.block[1],(int)x, (int)y, (int) width, (int) height, null); //блок с травой
        }
        if(type == 2){
            g.drawImage(tex.block[2],(int)x, (int)y, (int) width, (int) height, null); // грязь
        }
       //g.drawString(""+num,(int) (x + width / 4),(int) (y + height / 2));
       /* g.setColor(Color.YELLOW);
        Graphics2D g2d = (Graphics2D)g;
        g2d.draw(getBounds());*/
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }

    public int getType() {
        return type;
    }

}
