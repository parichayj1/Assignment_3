package Multiplayer;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClientPlayer extends Canvas implements Runnable, KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	//Datastreams
	DataOutputStream out;
	DataInputStream in;
	
	//Connection info
	String serverIP;
	int serverPort;
	Socket socket;
	
	//Frame
	JFrame frame;
	int width = 500;
	int height = 500;
	public final Dimension gameDim = new Dimension(width,height);
	
	//Screen
	BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	
	//Game info
	int pWidth = 15;
	int pHeight = 45;
	int xPos;
	int yPos;
	int sXPos;
	static int sYPos;
	
	static int c2XPos , c3XPos;
	int c2YPos , c3YPos;
	
	
	// Booleans for movement
		boolean moveUp = false;
		boolean moveDown = false;
		

	// Scores
		static int serverScore = 0;
		static int clientScore = 0;
		//static int client2Score = 0;

	// Ball info
		static int bX;
		static int bY;
		int bSize = 8;
		
		// For run
		private int ticks = 0;
		private int frames = 0;
		private int FPS = 0;
		private int UPS = 0;
		public double delta;
		
		// Used in the "run" method to limit the frame rate to the UPS
		boolean limitFrameRate = false;
		boolean shouldRender;

		private String username;
		//private int userid = 1;
		
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

		private void requestInformation() {
			serverIP = JOptionPane.showInputDialog("What is the IP of the server you are connecting to?");
			serverPort = Integer.parseInt(JOptionPane.showInputDialog("What is the port you are connecting through?"));
			username = JOptionPane.showInputDialog("Enter username:");
		}

		private void createFrame() {
			// Frame stuff
			setMinimumSize(gameDim);
			setMaximumSize(gameDim);
			setPreferredSize(gameDim);
			frame = new JFrame("Pong Multiplayer : "+ username );

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new BorderLayout());

			frame.add(this, BorderLayout.CENTER);
			frame.pack();

			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);

			// Players initializing
			xPos = frame.getWidth() - pWidth - 15;
			yPos = frame.getHeight() / 2 - pHeight;
			sXPos = 15;
			sYPos = frame.getHeight() / 2 - pHeight;
			//c2XPos = frame.getWidth()/2 - pWidth;
			//c2YPos = 15;

			addKeyListener(this);

			requestFocus();

			Thread thread = new Thread(this);
			thread.start();
		}

		private void handShake() {
			try {

				socket = new Socket(serverIP, serverPort);
				out = new DataOutputStream(socket.getOutputStream());
				in = new DataInputStream(socket.getInputStream());

				try {
					out.writeUTF("This goes to 'ConnectedPlayer'");
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				Input serverIn = new Input(in);
				Thread inputThread = new Thread(serverIn);
				inputThread.start();
			} catch (IOException e) {
				System.out.println("Could not connect to the Server!"); // If it couldn't utilize the incoming data stream
			}
		}

		public ClientPlayer() {
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
			movement();
			try {
				out.writeInt(yPos); // Send new coordinates
			} catch (IOException e) {
				e.printStackTrace();
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

			g.setColor(Color.WHITE); // Set the color of players to white
			g.fillRect(xPos, yPos, pWidth, pHeight);
			g.fillRect(sXPos, sYPos, pWidth, pHeight);
			//g.fillRect(c2XPos,c2YPos,pHeight,pWidth);
			//g.fillRect(c3XPos,c3YPos,pHeight,pWidth);

			// Render ball
			g.fillOval(bX, bY, bSize, bSize);

			// Draw scores
			g.drawString("P1 Score: " + serverScore, 40, 10);
			g.drawString("P2 Score: " + clientScore, getWidth() - 105, 10);
			//g.drawString("P3 Score: " + client2Score, getWidth() - 105, 20);
			//g.drawString("P4 Score: " + client3Score, getWidth() - 105, 10);

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

	class Input implements Runnable {
		DataInputStream in;

		public Input(DataInputStream in) {
			this.in = in;
		}

		public void run() {
			while (true) {
				try {
					ClientPlayer.bX = in.readInt();
					ClientPlayer.bY = in.readInt();
					ClientPlayer.sYPos = in.readInt();
					ClientPlayer.serverScore = in.readInt();
					ClientPlayer.clientScore = in.readInt();
					//ClientPlayer.client2Score = in.readInt();
					//ClientPlayer.c2XPos = in.readInt();
					/* 
					 * 
					 * ClientPlayer.client3Score = in.readInt();
					 * 
					 * ClientPlayer.c3XPos = in.readInt();*/
					
				} catch (IOException e) {
					System.out.println("Disconnected from Server!"); // If it is not receiving any info from server
					System.exit(0);
				}
			}
		}


	
	

}
