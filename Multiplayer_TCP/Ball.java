package Multiplayer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Ball extends Rectangle {
	
	private static final long serialVersionUID = 1L;

	CreateServer cs;
	int speed = 3;
	int x, y, width, height, vx = speed, vy = speed;

	public Ball(CreateServer cs) {
		this.cs = cs;

		width = 8;
		height = 8;
		x = cs.getWidth() / 2 - width;
		y = cs.getHeight() / 2 - height;

		setBounds(x, y, width, height);
	} // End constructor
	public void tick(CreateServer cs) {
		movement();
		setBounds(x, y, width, height);
	} // End tick
	private void movement() {
		if (y <= 0){
			vy = speed;	
		}
		if (y >= cs.getHeight() - height){
			vy = -speed;}
		if (x <= 0) { // If hits left side (Player)
			vx = speed;
			cs.client1Score++;
		}
		if (x >= cs.getWidth() - width) { // If hits right side (AI)
			vx = -speed;
			cs.serverScore++;
		}

		x += vx;
		y += vy;

		if (this.intersects(cs.serverRect)) {
			vx = speed;
		}
		if (this.intersects(cs.client1Rect)) {
			vx = -speed;
		}
	} // End movement

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(x, y, width, height);
	} // End render
	

	

}
