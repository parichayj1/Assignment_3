package singleplayer;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;


public class Game extends Canvas implements Runnable,KeyListener {
	private static final long serialVersionUID = 1L;

	public static Player player;
	public static AI ai;
	public static AI_ ai1;
	public static AI_ ai2;
	public static ball ball;
	public final int width = PingPong.WINDOW_WIDTH;
	public final int height = PingPong.WINDOW_HEIGHT;
	public final Dimension gameSize = new Dimension(width,height);
	BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	public int p1score=0;
	public int p2score=0;
	public int p3score=0;
	public int p4score=0;
	public int lost=0;
	public long p1time, p2time, p3time, p4time;
	
	static boolean gameRunning = false;
	
	double delta;
	boolean shouldRender;
	
	public void run(){
		
			 long lastTime = System.nanoTime();
			 double nsPerTick = 1000000000D / 60D;

			 	long lastTimer = System.currentTimeMillis();
				delta = 0D;
				
				while (gameRunning) {
					long now = System.nanoTime();
					delta += (now - lastTime) / nsPerTick;
					lastTime = now;

					// If you want to limit frame rate, shouldRender = false
					shouldRender = false;

					// If the time between ticks = 1, then various things (shouldRender = true, keeps FPS locked at UPS)
					while (delta >= 1) {
						tick();
						delta -= 1;
						shouldRender = true;
					}

					// If you should render, render!
					if (shouldRender) {
						render();
					}

					// Reset stuff every second for the new "FPS" and "UPS"
					if (System.currentTimeMillis() - lastTimer >= 1) {
						lastTimer += 1000;
					}
				}
			}

	public Game(){
		this.addKeyListener(this);
        this.setFocusable(true);
        
		player = new Player(1,60);
		ai = new AI(width-20,60);
		ball=new ball((width-6)/2,(height-28)/2);
		if(PingPong.players==3){
			ai1 = new AI_(height-26,1);
		}
		else if(PingPong.players==4){
			ai1 = new AI_(height-26,1);
			ai2 = new AI_(height-26,457);
		}
		start();
	}
	 
	 public void tick(){
		 player.tick(this,delta);
		 ai.tick(this,delta);
		 ball.tick(this,delta);
		 if(PingPong.players==3){
				ai1.tick(this,delta);
			}
		 else if(PingPong.players==4){
				ai1.tick(this,delta);
				ai2.tick(this,delta);
			}
	 }

	 public synchronized void start(){
		 gameRunning = true;
		 new Thread(this).start();
	 }
	 
	 public static synchronized void stop(){
		 gameRunning = false;
		 System.exit(0);
	 }
	 
		
	 public void render(){
			 BufferStrategy bs = getBufferStrategy();;
			 if(bs==null){
				 createBufferStrategy(3);
				 return;
			 }
			 Graphics g= bs.getDrawGraphics();
			 g.drawImage(image,0,0,getWidth(),getHeight(),null);
			 g.setColor(Color.WHITE);
			 if(p1score<3){
			 g.drawString("Player1 : "+p1score,5,10);
			 }
			 else{
				 g.drawString("Player1 : Lost",5,10);
			 }
			 if(p2score<3){
			 g.drawString("Player2 : "+p2score,width-69,10);
			 }
			 else{
				 g.drawString("Player2 :Lost",width-78,10);
			 }
			 if(PingPong.players==2 && lost==1){
				   if(p1time<p2time){
				   g.drawString("Player1 wins",200,200);
				   ball.size=0;
				   player.remove();
				   }
				   else{
					   g.drawString("Player2 wins",200,200);
					   ball.size=0;
					   ai.remove();
					}
			   }
			 else if(PingPong.players==3 && lost==2){
				   if(p1time<p2time && p1time<p3time){
				   g.drawString("Player1 wins",200,200);
				   ball.size=0;
				   player.remove();
				   }
				   else if(p2time<p1time && p2time<p3time){
					   g.drawString("Player2 wins",200,200);
					   ball.size=0;
					   ai.remove();
				   }
				   else{
					   g.drawString("Player3 wins",200,200);
					   ball.size=0;
					   ai1.remove();
					}
			   }
			 else if(PingPong.players==4 && lost==3){
				   if(p1time<p2time && p1time<p3time && p1time<p4time){
				   g.drawString("Player1 wins",200,200);
				   ball.size=0;
				   player.remove();
				   }
				   else if(p2time<p1time && p2time<p3time && p2time<p4time){
					   g.drawString("Player2 wins",200,200);
					   ball.size=0;
					   ai.remove();
					   }
				   else if(p3time<p1time && p3time<p2time && p3time<p4time){
					   g.drawString("Player3 wins",200,200);
					   ball.size=0;
					   ai1.remove();
					   }
				   else{
					   g.drawString("Player4 wins",200,200);
					   ball.size=0;
					   ai2.remove();
					}
			   }
			 player.render(g);
			 ai.render(g);
			 ball.render(g);
			 if(PingPong.players==3){
				 g.setColor(Color.WHITE);
				 if(p3score<3){
				 g.drawString("Player3 : "+p3score,(width-69)/2,10);
				 }
				 else{
					 g.drawString("Player3 : Lost",(width-69)/2,10); 
				 }
				 ai1.render(g);
				}
			 else if(PingPong.players==4){
				 g.setColor(Color.WHITE);
				 if(p3score<3){
				 g.drawString("Player3 : "+p3score,5,468);
				 }
				 else{
					 g.drawString("Player3 : Lost",5,468); 
				 }
				 if(p4score<3){
				 g.drawString("Player4 : "+p4score,width-69,468);
				 }
				 else{
					 g.drawString("Player4 :Lost",width-78,468);
				 }
				 ai1.render(g);
				 ai2.render(g);
				}
			   
			 g.dispose();
			 bs.show();
			
		}
		
	 
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP){
			Game.player.goingUp=true;
			Game.player.goingDown=false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN){
			Game.player.goingDown=true;
			Game.player.goingUp=false;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP){
			Game.player.goingUp=false;
			//Game.player.goingDown=false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN){
			Game.player.goingDown=false;
			//Game.player.goingUp=false;
		}
	}


	public void keyTyped(KeyEvent arg0) {
		
	}
	
}
