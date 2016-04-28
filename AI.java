package singleplayer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class AI {

	int x, y;
	boolean goingUp=false;
	boolean goingDown=false;
    private boolean d = PingPong.diff_level;
    private int width,height;
	Rectangle boundingbox;
	double speed;
   
    public AI(int x,int y) {
    	this.x = x;
    	this.y = y;
    	if(d == true){
        	width = 15;
        	height = 40;
        	speed=2.75;
        }
        else{
        	width = 15;
        	height = 60;
        	speed=2.5;
        }
    	boundingbox=new Rectangle(x,y,width,height);
    	boundingbox.setBounds(x,y,width,height);

    }
    
    public void tick(Game game,double delta){
    	boundingbox.setBounds(x,y,width,height);
    	if(game.ball.y<y && y>=0){
    		
    		y-=speed*delta;
    	}
    	else if(game.ball.y>y && y+height <= game.getHeight()){
    		y+=speed*delta;
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