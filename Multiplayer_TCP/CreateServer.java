package Multiplayer;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.util.HashSet;

public class CreateServer extends Canvas implements Runnable, KeyListener {
		
	
	private static final long serialVersionUID = 1L;

	// Ball
	Ball b;

	// Input and Output
	DataOutputStream out;
	DataInputStream in;
	
	/*DataOutputStream out2;
	DataInputStream in2;*/
	
	//private static HashSet<String> names = new HashSet<String>();


	// Socket information
	ServerSocket serverSocket;
	Socket socket;
	int serverPort;
	
	// Frame
		JFrame frame;
		int width = 500;
		int height = 500;
		public final Dimension gameDim = new Dimension(width, height);
		
		// Screen
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// Game Info
		int pWidth = 15;
		int pHeight = 45;
		// server's coordinates
		int xPos;
		int yPos;
		// client's coordinates
		int c1XPos;
		static int c1YPos;
		
		static int c2XPos , c3XPos;
		int c2YPos , c3YPos;
		
		

		// Booleans for movement
		boolean moveUp = false;
		boolean moveDown = false;

		// Rectangles for ball collision
		Rectangle serverRect;
		Rectangle client1Rect , client2Rect;

		// Scores
		int serverScore = 0;
		int client1Score = 0;
		int client2Score = 0;

		// For run
		private int ticks = 0;
		private int frames = 0;
		private int FPS = 0;
		private int UPS = 0;
		public double delta;

		// Used in the "run" method to limit the frame rate to the UPS
		boolean limitFrameRate = false;
		boolean shouldRender;
		
		private void requestInformation() {
			try {
				serverPort = Integer.parseInt(JOptionPane.showInputDialog("What is the port that you wish to utilize? (Make sure to PortForward, if needed)"));
				JOptionPane.showMessageDialog(null, "You can get your IP at whatismyip.com (For incoming connections)");
				//JOptionPane.showMessageDialog(null, "Creating server...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void run() {
			long lastTime = System.nanoTime();
			double nsPerTick = 1000000000D / 60D;

			long lastTimer = System.currentTimeMillis();
			delta = 0D;

			while (true) {
				long now = System.nanoTime();
				delta += (now - lastTime) / nsPerTick;
				lastTime = now;

				// If you want to limit frame rate, shouldRender = false
				shouldRender = false;

				// If the time between ticks = 1, then various things (shouldRender = true, keeps FPS locked at UPS)
				while (delta >= 1) {
					ticks++;
					tick();
					delta -= 1;
					shouldRender = true;
				}
				if (!limitFrameRate && ticks > 0)
					shouldRender = true;

				// If you should render, render!
				if (shouldRender) {
					frames++;
					render();
				}

				// Reset stuff every second for the new "FPS" and "UPS"
				if (System.currentTimeMillis() - lastTimer >= 1000) {
					lastTimer += 1000;
					FPS = frames;
					UPS = ticks;
					frames = 0;
					ticks = 0;
				}
			}
		} // End run
		private void createFrame() {
			// Frame stuff
			setMinimumSize(gameDim);
			setMaximumSize(gameDim);
			setPreferredSize(gameDim);
			frame = new JFrame("Pong Multiplayer -Server-");

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new BorderLayout());

			frame.add(this, BorderLayout.CENTER);
			frame.pack();

			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);

			// Players initializing
			xPos = 15;
			yPos = frame.getHeight() / 2 - pHeight;
			c1XPos = frame.getWidth() - pWidth - 15;
			c1YPos = frame.getHeight() / 2 - pHeight;
			
			//c2XPos = frame.getWidth()/2 - pWidth;
			//c2YPos = 15;
			
			//c3XPos = ;
			//c3YPos = ;

			serverRect = new Rectangle(xPos, yPos, pWidth, pHeight);
			client1Rect = new Rectangle(c1XPos, c1YPos, pWidth, pHeight);
			//client2Rect = new Rectangle(c2XPos, c2YPos,pHeight,pWidth);
			//client3Rect = new REctangle(c3XPos,c3YPos,pHeight,pWidth);

			b = new Ball(this);

			addKeyListener(this);

			requestFocus();
			
			Thread thread = new Thread(this);
			//Thread thread2 = new Thread(this);
			thread.start();
			//thread2.start();
	
			
		}
		private void handShake() {
			try {
				serverSocket = new ServerSocket(serverPort);
				
				
					socket = serverSocket.accept();
					if (out == null || in == null ) {
						out = new DataOutputStream(socket.getOutputStream());
						in = new DataInputStream(socket.getInputStream());
						
					}
					ConnectedPlayer cp1 = new ConnectedPlayer(out, in);
					Thread userThread1 = new Thread(cp1);
					System.out.println("User1 has connected");
					userThread1.start();
			
					/*ConnectedPlayer2 cp2 = new ConnectedPlayer2(out,in);
					Thread userThread2 = new Thread(cp2);
					System.out.println("User2 has connected");
					userThread2.start();*/
			
		} catch (IOException e) {
			e.printStackTrace();
			}
	}

		public CreateServer() {
			requestInformation();
			handShake();
			createFrame();
		}

		private void movement() {
			if (moveUp && yPos > 0) {
				yPos -= 3;
			}

			if (moveDown && yPos + pHeight < getHeight()) {
				yPos += 3;
			}

		}

		private void tick() {
			movement(); // Check for any movement

			// Re-bind the collision rectangles to new positions after movement
			serverRect.setBounds(xPos, yPos, pWidth, pHeight);
			client1Rect.setBounds(c1XPos, c1YPos, pWidth, pHeight);
			//client2Rect.setBounds(c2XPos,c2YPos,pHeight,pWidth);

			// Move the ball
			b.tick(this);

			// Send coordinates to Client
			try {
				out.writeInt(b.x); // Ball x
				out.writeInt(b.y); // Ball y
				out.writeInt(yPos); // Send new coordinates of player
				out.writeInt(serverScore);
				out.writeInt(client1Score);
				//out.writeInt(client2Score);
				//out.writeInt(c2XPos);
				
				
				
			} catch (Exception e) {
			}
		}
		private void render() {
			BufferStrategy bs = getBufferStrategy();

			if (bs == null) {
				createBufferStrategy(3);
				return;
			}

			Graphics g = bs.getDrawGraphics();

			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

			g.setColor(Color.WHITE);
			g.fillRect(xPos, yPos, pWidth, pHeight);
			g.fillRect(c1XPos, c1YPos, pWidth, pHeight);
			//g.fillRect(c2XPos,c2YPos,pHeight,pWidth);
			b.render(g);

			// Draw scores
			g.drawString("P1 Score: " + serverScore, 40, 10);
			g.drawString("P2 Score: " + client1Score, getWidth() - 80, 10);
			//g.drawString("P3 score: " + client2Score, getWidth()-125, 20);

			g.dispose();
			bs.show();
		}

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				moveUp = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				moveDown = true;
			}
		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_W) {
				moveUp = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_S) {
				moveDown = false;
			}
		}

		public void keyTyped(KeyEvent e) {

		}
		
		
		

		

		

}
