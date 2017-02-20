package framework;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by Кирилл on 14.06.2016.
 */
public class Menu
{
    Texture tex;
    Handler controller;

    private float radius = 800;
    private boolean changing = true;

    private int x = 100, y = 100;
    private float velX = 1f;
    private float velY = 1f;

    private int w = 810;
    private int h = 610;

    private int selected = 0;

    public Menu(Texture tex, Handler controller)
    {
        this.tex = tex;
        this.controller = controller;
    }

    public void tick()
    {
        x += velX;
        y += velY;

        if(x <= 0)
            velX *= -1;
        if(y <= 0)
            velY *= -1;
        if(x >= 640)
            velX *= -1;
        if(y >= 480)
            velY *= -1;


        if(selected < 0)
            selected = 2;
        else if(selected > 2)
            selected = 0;

        if(changing)
        {
            if(radius > 700)
                radius -= 0.2f;
            else
                changing = false;
        }
        else
        {
            if(radius < 800)
                radius += 0.2f;
            else
                changing = true;
        }
    }

    public void render(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g.drawImage(Handler.background, 0, 0, 810, (32 * 20), null);

        g.setColor(Color.red);
        g.setFont(new Font("Press start 2P", 0, 80));
        g.drawString("Bad Trip", 100, 150);


        g.setColor(Color.red);

        if(selected == 0){
            g.setFont(new Font("Press start 2P", 0, 45));
            g.drawString("Play", 330, 250);
        }
        else{
            g.setFont(new Font("Press start 2P", 0, 30));
            g.drawString("Play", 330, 250);
        }

        if(selected == 1){
            g.setFont(new Font("Press start 2P", 0, 45));
            g.drawString("Help", 330, 350);
        }
        else{
            g.setFont(new Font("Press start 2P", 0, 30));
            g.drawString("Help", 330, 350);
        }

        if(selected == 2){
            g.setFont(new Font("Press start 2P", 0, 45));
            g.drawString("Quit", 330, 450);
        }
        else{
            g.setFont(new Font("Press start 2P", 0, 30));
            g.drawString("Quit", 330, 450);
        }


        g.setColor(Color.red);
        g.setFont(new Font("Press start 2P", 0, 20));

        Graphics2D g2d2 = (Graphics2D) g;

        Point2D center = new Point2D.Float(x, y);
        float[] dist = {0.0f, 1.0f};
        Color[] colors = {new Color(0.0f, 0.0f, 0.0f, 0.0f), Color.black};
        RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);
        g2d.setPaint(p);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .95f));
        g2d.fillRect(0, 0, 810, 610);
        g2d.dispose();
    }

    public int getSelected(){
        return selected;
    }

    public void setSelected(int selected){
        this.selected = selected;
    }


}

