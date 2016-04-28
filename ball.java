package singleplayer;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.Rectangle;


public class ball {
	int x,y;
	int size=16;
	int speed=3;
	List<Integer> number = Arrays.asList(1,-1);
	int vx,vy;
	Rectangle boundingbox;
	
 public ball(int x, int y){
		this.x=x;
		this.y=y;
		vx=speed*number.get(ThreadLocalRandom.current().nextInt(number.size()));
		vy=speed*number.get(ThreadLocalRandom.current().nextInt(number.size()));
		boundingbox=new Rectangle(x,y,size,size);
    	boundingbox.setBounds(x,y,size,size);
		
	}
	
	public void tick(Game game,double delta){
		boundingbox.setBounds(x,y,size,size);
		if(x<=0){
			game.p1score++;
			vx=speed;
			if(game.p1score==3){
				game.player.remove();
				game.p1time = System.nanoTime(); 
				game.lost++;
			}
		}
		else if(x+size >= game.getWidth()){
			game.p2score++;
			vx = -speed;
			if(game.p2score==3){
				game.ai.remove();
				game.p2time = System.nanoTime(); 
				game.lost++;
			}
		}
		if(y<=0){
			vy=speed;
			game.p3score++;
			if((PingPong.players==3 || PingPong.players==4) && game.p3score==3){
				game.ai1.remove();
				game.p3time = System.nanoTime(); 
				game.lost++;
			}
		}
		else if(y+size >= game.getHeight()){
			vy = -speed;
			game.p4score++;
			if(PingPong.players==4 && game.p4score==3){
				game.ai2.remove();
				game.p4time = System.nanoTime(); 
				game.lost++;
			}
		}
		x+=vx*delta;
		y+=vy*delta;
		paddlecollide(game);
	}
	
	public void render(Graphics g){
		g.setColor(Color.GREEN);
		g.fillOval(x, y, size, size);
	}
	
	private void paddlecollide(Game game){
		if(boundingbox.intersects(game.player.boundingbox)){
			vx = speed;
		}
		else if(boundingbox.intersects(game.ai.boundingbox)){
			vx = -speed;
		}
		else if((PingPong.players==3 || PingPong.players==4) && boundingbox.intersects(game.ai1.boundingbox)){
			vy = speed;
		}
		else if(PingPong.players==4 && boundingbox.intersects(game.ai2.boundingbox)){
			vy = -speed;
		}
	}
}
