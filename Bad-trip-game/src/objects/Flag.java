package objects;

import framework.ObjectId;
import framework.Texture;
import framework.Handler;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.LinkedList;


/**
 * Created by Кирилл on 31.05.2016.
 */
public class Flag extends GameObject{

    Texture tex = Handler.tex;
    private float angle;
    public int live;

    public Flag(float x, float y, ObjectId id) {
        super(x, y, id);
        width = 64;
        live = 0;
        height = 64;
        angle = 0;
    }

    @Override
    public void tick(LinkedList<GameObject> object) {
        if(id == ObjectId.Blow){
            live++;
            if (live > 25){
                object.remove(this);
            }
        }
    }

    private void tick(){
        if(angle <= 360){
            angle += 0.1f;
        } else angle = 0;
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if(this.id == ObjectId.Flag) {
            tick();
            BufferedImage image = tex.block[4];
            double rotationRequired = Math.toRadians (angle);
            double locationX = 16;
            double locationY = 16;
            AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            image = op.filter(image,null);
            g2d.drawImage(image, (int) x, (int) (y - height / 2), (int) width, (int) height, null);
        }
        if(this.id == ObjectId.Exit) {
            Font tmp = g.getFont();
            Color tmpcolor = g.getColor();
            g.setFont(new Font("Press start 2P",Font.PLAIN, 25));
            g.setColor(Color.WHITE);
            g.drawString("Exit", (int) x - 20, (int) y);
            g.setColor(tmpcolor);
            g.setFont(tmp);
        }
        if(this.id == ObjectId.Start){
            tick();
            BufferedImage image = tex.block[4];
            double rotationRequired = Math.toRadians (angle);
            double locationX = 16;
            double locationY = 16;
            AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
            image = op.filter(image,null);
            g.drawImage(image, (int) x, (int) (y - height / 2), (int) width, (int) height, null);
            //g2d.dispose();
            Font tmp = g.getFont();
            Color tmpcolor = g.getColor();
            g.setFont(new Font("Press start 2P",Font.PLAIN, 25));
            g.setColor(Color.WHITE);
            g.drawString("Start", (int) x - 25, (int) y - 35);
            g.setColor(tmpcolor);
            g.setFont(tmp);
        }
        if(this.id == ObjectId.Blow){
            g.drawImage(tex.boss[5], (int) x, (int) (y), 84, 60, null);
        }

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y - 32, 64, 64);
    }
}
