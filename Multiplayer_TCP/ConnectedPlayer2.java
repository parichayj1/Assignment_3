package Multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ConnectedPlayer2 implements Runnable{
	DataOutputStream out;
	DataInputStream in;

	public ConnectedPlayer2(DataOutputStream out, DataInputStream in) {
		this.out = out;
		this.in = in;
	} // End constructor

	public void run() {
		while (true) {
			try {
				CreateServer.c2XPos = in.readInt(); // Set the client yPos to whatever the client has sent (update)
			} catch (IOException e) {
				this.out = null;
				this.in = null;
				System.out.println("Disconnected from Client");
			}
		} // End while loop
	} // End run
	

}
