package main;

import java.awt.Color;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

public class WindowGame extends BasicGame {

	private GameContainer container=null;
	private TiledMap map;
	private float x = 400, y = 400, widthA=30,heightA=68;
	private int direction = 0;
	private int lr=0;
	private float vY=0;
	private float vX=0;
	private float jump=2;
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
        Music background = new Music("src/ressource/sound/1.ogg");
        background.loop();
    }
   private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
        Animation animation = new Animation();
        for (int x = startX; x < endX; x++) {
            animation.addFrame(spriteSheet.getSprite(x, y), 80);
        }
        return animation;
    }
    
    @Override
    public void keyReleased(int key, char c) {
        if (Input.KEY_ESCAPE == key) {
        	container.exit();
        }
        this.vX=0;
        switch(key){
        case Input.KEY_UP:
        	if(direction == 1) {this.vX=-0.5f;break;}
            if(direction==3) { this.vX=0.5f;break;}
        case Input.KEY_LEFT: 
          if(direction == 1) {this.moving = false;break;}
          if(direction==3) {this.vX=0.5f;}
            break;
        case Input.KEY_DOWN: 
          if(direction == 2) {this.moving = false;break;}
      	  if(direction == 1) {this.vX=-0.5f;break;}
          if(direction==3) { this.vX=0.5f;break;}
        case Input.KEY_RIGHT: 
          if(direction == 3) { this.moving = false;break;}
          if(direction==1) { this.vX=-0.5f;break;}
          }
        
     
 
      }

  
    
    @Override
    public void keyPressed(int key, char c) {
    	
        switch (key) {
        
            case Input.KEY_UP:    this.direction = 0; this.moving = true;if(jump>0) {this.vY=1f;}jump-=1; break;
            case Input.KEY_LEFT:  this.direction = 1; this.moving = true; this.lr=1;this.vX=-0.5f; break;
            case Input.KEY_DOWN:  this.direction = 2; this.moving = true; break;
            case Input.KEY_RIGHT: this.direction = 3; this.moving = true; this.lr=0; this.vX=0.5f;break;
        }
        
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
    	this.map.render(0, 0, 0);
        if(lr==0) { g.drawAnimation(animationsR[direction + (moving ? 4 : 0)], x-35, y-71);}
    	else if(lr==1) {g.drawAnimation(animationsL[direction + (moving ? 4 : 0)], x-35, y-71);}

    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
    	 boolean collision=false;
    	 float futurX = getFuturX(delta);
	     float futurY = getFuturY(delta);
	     collision=isCollision(futurX+10,futurY);
	     if (collision) {jump=2;}
	     collision=false;
	     
	     for (int i=0;i<widthA;i++) {
	        	for (int j=0;j<heightA;j++) {
	        		collision = isCollision(this.x-i,futurY-j);
	        		if(collision==true) {break;}
	        		}
	        	if(collision==true) {break;}
	        	}
	    if (collision==false) {this.y=futurY;}
 	 	
	   
	    
    	if ((this.moving)){	
	        for (int i=0;i<widthA;i++) {
	        	for (int j=0;j<heightA;j++) {
	        		collision = isCollision(futurX-i, futurY-j);
	        		if(collision==true) {break;}
	        		}
	        	if(collision==true) {break;}
	        	}
	        
	        if (collision) {
	            this.moving = false;
	            //vY=1;
	        } 
	        else {
	            this.x = futurX;
	            this.y = futurY;
	        }
	    this.moving=true;
	    
        }
    	 if ((this.moving)){
     		
 	        for (int i=0;i<widthA;i++) {
 	        	for (int j=0;j<heightA;j++) {
 	        		collision = isCollision(futurX-i,this.y-j);
 	        		if(collision==true) {break;}
 	        		}
 	        	if(collision==true) {break;}
 	        	}
 	        
 	        if (collision) {
 	            this.moving = false;
 	        } 
 	        else {
 	            this.x = futurX;
 	        }
         }
    }
    private boolean isCollision(float x, float y) {
    	boolean collision=false;
        int tileW = this.map.getTileWidth();
        int tileH = this.map.getTileHeight();
        int logicLayer = this.map.getLayerIndex("Logic");
        Image tile = this.map.getTileImage((int) x / tileW, (int) y / tileH, logicLayer);
        collision = tile != null;
        if (collision) {
        	org.newdawn.slick.Color color = tile.getColor((int) x % tileW, (int) y % tileH);
            collision = color.getAlpha() > 0;
        }
        return collision;
    }

    private float getFuturX(int delta) {
        float futurX = this.x;
        switch (this.direction) {
        case 1: futurX = this.x -0.1f*delta; break;
        case 3: futurX = this.x +0.1f*delta; break;
        }
        return futurX+(vX*delta);
    }

    private float getFuturY(int delta) {
        float futurY = this.y;
        switch (this.direction) {
        	case 0: futurY =this.y -vY*delta; break;
        	case 2:futurY =this.y +0.4f*delta; break;
        }
        futurY+=0.5f*delta-(vY*delta);
        if (this.vY>0) {this.vY-=0.0045;}
        return futurY;
    }
    

    public static void main(String[] args) throws SlickException {
        new AppGameContainer(new WindowGame(), 1024, 768, false).start();
    }
    

}

