package framework;

import objects.GameObject;
import objects.Player;
import window.Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Кирилл on 30.05.2016.
 */
public class KeyInput extends KeyAdapter {

    Handler handler;
    Game game;
    Menu menu;

    private int velX = 6;
    private int velY = -12;

    public KeyInput(Handler handler, Game game, Menu menu) {
        this.handler = handler;
        this.game = game;
        this.menu = menu;
    }


    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
            if(Game.state == Game.STATE.Menu){
                switch (key){
                    case KeyEvent.VK_UP:{
                        menu.setSelected(menu.getSelected() - 1);
                        break;
                    }
                    case KeyEvent.VK_DOWN:{
                        menu.setSelected(menu.getSelected() + 1);
                        break;
                    }
                    case KeyEvent.VK_ENTER:{
                        switch (menu.getSelected()){
                            case 0:{
                                Game.state = Game.STATE.Game;
                                handler.switchLevel();
                                break;
                            }
                            case 1:{
                                Game.state = Game.STATE.Help;
                                break;
                            }
                            case 2:{
                                System.exit(1);
                                break;
                            }
                        }
                        break;
                    }
                }
            } else if (Game.state == Game.STATE.Help) {
                    if(key == KeyEvent.VK_SPACE){
                        Game.state = Game.STATE.Menu;
                    }
            }else if (Game.state == Game.STATE.Results) {
                if(key == KeyEvent.VK_SPACE){
                    Game.state = Game.STATE.Menu;
                }
            } else {
                    GameObject tmpObj = Handler.player;
                    if (Player.allowMove)
                        if (tmpObj.getId() == ObjectId.Player) {
                            switch (key) {
                                case KeyEvent.VK_RIGHT: {
                                    tmpObj.setWalking(true);
                                    tmpObj.setVelX(velX);
                                    break;
                                }
                                case KeyEvent.VK_LEFT: {
                                    tmpObj.setWalking(true);
                                    tmpObj.setVelX(-velX);
                                    break;
                                }
                                case (KeyEvent.VK_SPACE): {
                                    if (!tmpObj.isJumping()) {
                                        tmpObj.setJumping(true);
                                        tmpObj.setVelY(velY);
                                        Sound.playSound("/jump_07.wav");
                                    } else if (!tmpObj.isDoubleJumping()) {
                                        tmpObj.setDoubleJumping(true);
                                        tmpObj.setVelY(velY - velY / 4);
                                        Sound.playSound("/jump_01.wav");
                                    }
                                    break;
                                }

                            }
                        }
                }



        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        }

    }


    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (Game.state != Game.STATE.Menu && Game.state != Game.STATE.Help && Game.state != Game.STATE.Results) {
            GameObject tmpObj = Handler.player;
            if (tmpObj.getId() == ObjectId.Player) {
                switch (key) {
                    case KeyEvent.VK_SPACE: {
                        break;
                    }
                    case KeyEvent.VK_RIGHT: {
                        tmpObj.setWalking(false);
                        tmpObj.setVelX(0);
                        break;
                    }
                    case KeyEvent.VK_LEFT: {
                        tmpObj.setWalking(false);
                        tmpObj.setVelX(0);
                        break;
                    }

                }
            }

        }
    }

}
