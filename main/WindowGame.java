package main;

import java.awt.Color;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

public class WindowGame extends BasicGame {

	private GameContainer container=null;
	private TiledMap map;
	private float x = 400, y = 400;
	private int direction = 0;
	private int lr=0;
	private boolean moving = false;
	private Animation[] animationsR = new Animation[8];
	private Animation[] animationsL=new Animation[8];

	public WindowGame() {
        super("StickMan:: WindowGame");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
    	this.container = container;
    	this.map = new TiledMap("src/ressource/map/map.tmx");
    	SpriteSheet spriteSheet = new SpriteSheet("src/ressource/sprites/ninja.png", 50, 78);
    	this.animationsR[0] = loadAnimation(spriteSheet, 0, 1, 2); //-D
    	this.animationsR[1] = loadAnimation(spriteSheet, 0, 1, 1); //-G
    	this.animationsR[2] = loadAnimation(spriteSheet, 0, 1, 2); //-D
    	this.animationsR[3] = loadAnimation(spriteSheet, 0, 1, 2); //-D
    	this.animationsR[4] = loadAnimation(spriteSheet, 6, 7, 4); //-D
        this.animationsR[5] = loadAnimation(spriteSheet, 0, 7, 1); //Avancer gauche
        this.animationsR[6] = loadAnimation(spriteSheet, 5, 6, 4); //bas
        this.animationsR[7] = loadAnimation(spriteSheet, 0, 7, 2); // Avancer droite
        
        this.animationsL[0] = loadAnimation(spriteSheet, 0, 1, 1); //-G
    	this.animationsL[1] = loadAnimation(spriteSheet, 0, 1, 1); //-G
    	this.animationsL[2] = loadAnimation(spriteSheet, 0, 1, 1); //-G
    	this.animationsL[3] = loadAnimation(spriteSheet, 0, 1, 2); //-D
    	this.animationsL[4] = loadAnimation(spriteSheet, 2, 3, 4); //-D
        this.animationsL[5] = loadAnimation(spriteSheet, 0, 7, 1); //Avancer gauche
        this.animationsL[6] = loadAnimation(spriteSheet, 1, 2, 4); //bas
        this.animationsL[7] = loadAnimation(spriteSheet, 0, 7, 2); // Avancer droite
    }
   private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 100);
        }
        return animation;
    }
    
    @Override
    public void keyReleased(int key, char c) {
        if (Input.KEY_ESCAPE == key) {
        	container.exit();
        }
        this.moving = false;
  
    }
    
    @Override
    public void keyPressed(int key, char c) {
    	
        switch (key) {
        
            case Input.KEY_UP:    this.direction = 0; this.moving = true; break;
            case Input.KEY_LEFT:  this.direction = 1; this.moving = true; this.lr=1; break;
            case Input.KEY_DOWN:  this.direction = 2; this.moving = true; break;
            case Input.KEY_RIGHT: this.direction = 3; this.moving = true; this.lr=0; break;
        }
        
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
    	this.map.render(0, 0);
        if(lr==0) { g.drawAnimation(animationsR[direction + (moving ? 4 : 0)], x-50, y-78);}
    	else if(lr==1) {g.drawAnimation(animationsL[direction + (moving ? 4 : 0)], x-50, y-78);}
      
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
    	 if (this.moving) {
    		 float futurX = this.x;
	         float futurY = this.y;
    		 Image tile = this.map.getTileImage(
	                (int) futurX / this.map.getTileWidth(), 
	                (int) futurY / this.map.getTileHeight(), 
	                this.map.getLayerIndex("Logic"));
    	        boolean collision = tile != null;
    	        if (collision) {
    	            this.moving = false;
    	        } else {
    	            this.x = futurX;
    	            this.y = futurY;
    	        }
		 	switch (this.direction) {
	            case 0: this.y -= .2f * delta; break;
	            case 1: this.x -= .2f * delta; break;
	            case 2: this.y += .2f * delta; break;
	            case 3: this.x += .2f * delta; break;
	        }
        }
    }

    public static void main(String[] args) throws SlickException {
        new AppGameContainer(new WindowGame(), 1024, 768, false).start();
    }
    

}