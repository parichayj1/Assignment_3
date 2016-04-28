package singleplayer;
import multiplayer.ClientPlayer;
import multiplayer.CreateServer;
import multiplayer.Ball;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class AI_ {
	int x, y;
	boolean goingLeft=false;
	boolean goingRight=false;
    private boolean d = PingPong.diff_level;
    private int width,height;
	public Rectangle boundingbox;
	double speed;
   
    public AI_(int x,int y) {
    	this.x = x;
    	this.y = y;
    	if(d == true){
        	width = 40;
        	height = 15;
        	speed=2.75;
        }
        else{
        	width = 60;
        	height = 15;
        	speed=2.5;
        }
    	boundingbox=new Rectangle(x,y,width,height);
    	boundingbox.setBounds(x,y,width,height);

    }
    
    public void tick(Game game,double delta){
    	boundingbox.setBounds(x,y,width,height);
    	if(game.ball.x<x && x>=0){
    		
    		x-=speed*delta;
    	}
    	else if(game.ball.x>x && x+width <= game.getWidth()){
    		x+=speed*delta;
    	}
    }
    
    public void tick_(CreateServer cs,double delta){
    	boundingbox.setBounds(x,y,width,height);
    	if(cs.b.x < x && x>=0){
    		
    		x-=speed*delta;
    	}
    	else if(cs.b.x > x && x+width <= cs.getWidth()){
    		x+=speed*delta;
    	}
    }
    
    public void tick_1(ClientPlayer cp,double delta){
    	boundingbox.setBounds(x,y,width,height);
    	if(cp.bX < x && x>=0){
    		
    		x-=speed*delta;
    	}
    	else if(cp.bX > x && x+width <= cp.getWidth()){
    		x+=speed*delta;
    	}
    }
    
    public void render(Graphics g){
    	g.setColor(Color.RED);
    	g.fillRect(x,y,width,height);
    }
    
    public void remove(){
    	this.height=0;
    	this.width=0;
    	this.speed=0;
    }

}
