package singleplayer;

import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JButton;

import multiplayer.ClientPlayer;
import multiplayer.ClientPlayer2;
import multiplayer.ClientPlayer3;
import multiplayer.CreateServer;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridLayout;

import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.UIManager;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.DropMode;
import javax.swing.JRadioButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class PingPong extends JFrame{

	public static final int WINDOW_WIDTH = 500;
	public static final int WINDOW_HEIGHT = 500;
	private JFrame frame;
	private JTextField txtNumberOfPlayers;
	private JTextField textField_1;
	private JTextField txtDifficultyLevel;
	private JRadioButton amatuer;
	private JRadioButton pro;
	public static boolean diff_level;
	public static int players=2;
	public final Dimension gameSize = new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT);
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PingPong window = new PingPong();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PingPong() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Ping-Pong");
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setLocationRelativeTo(null);
		frame.setContentPane(new JLabel(new ImageIcon("C:\\Users\\Parichay\\Desktop\\pingpong.jpg")));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[]{1.0, 1.0};
		frame.getContentPane().setLayout(gridBagLayout);
		
		ArrayList<Object> player = new ArrayList<Object>();
		player.add("Two  ");
		player.add("Three");
		player.add("Four ");
		
		txtNumberOfPlayers = new JTextField();
		txtNumberOfPlayers.setBorder(null);
		txtNumberOfPlayers.setBackground(Color.BLACK);
		txtNumberOfPlayers.setForeground(Color.RED);
		txtNumberOfPlayers.setHorizontalAlignment(SwingConstants.CENTER);
		txtNumberOfPlayers.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtNumberOfPlayers.setEditable(false);
		txtNumberOfPlayers.setText("No. of Players :");
		GridBagConstraints gbc_txtNumberOfPlayers = new GridBagConstraints();
		gbc_txtNumberOfPlayers.insets = new Insets(0, 0, 5, 5);
		gbc_txtNumberOfPlayers.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNumberOfPlayers.gridx = 0;
		gbc_txtNumberOfPlayers.gridy = 0;
		frame.getContentPane().add(txtNumberOfPlayers, gbc_txtNumberOfPlayers);
		txtNumberOfPlayers.setColumns(8);
		
		JSpinner spinner = new JSpinner();
		Dimension d = spinner.getPreferredSize();
        d.width = 100;
        spinner.setPreferredSize(d);
		spinner.setModel(new javax.swing.SpinnerListModel(player));
		JSpinner.ListEditor editor = new JSpinner.ListEditor(spinner);
        JTextField tf = editor.getTextField();
        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setFont(new Font("Tahoma", Font.PLAIN, 15));
        tf.setEditable(false);
        spinner.setEditor(editor);
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 1;
		gbc_spinner.gridy = 0;
		frame.getContentPane().add(spinner, gbc_spinner);
		
		spinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
               final String number = (String) ((JSpinner) e.getSource()).getValue();
               if(number.equals("Two  ")){
            	   players = 2;
               }
               else if(number.equals("Three")){
            	   players = 3;
               }
               else if(number.equals("Four ")){
            	   players = 4;
               }
            }
        });
		
		txtDifficultyLevel = new JTextField();
		txtDifficultyLevel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtDifficultyLevel.setBackground(Color.BLACK);
		txtDifficultyLevel.setForeground(Color.RED);
		txtDifficultyLevel.setBorder(null);
		txtDifficultyLevel.setHorizontalAlignment(SwingConstants.CENTER);
		txtDifficultyLevel.setEditable(false);
		txtDifficultyLevel.setText("Difficulty Level :");
		GridBagConstraints gbc_txtDifficultyLevel = new GridBagConstraints();
		gbc_txtDifficultyLevel.insets = new Insets(0, 0, 5, 5);
		gbc_txtDifficultyLevel.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDifficultyLevel.gridx = 0;
		gbc_txtDifficultyLevel.gridy = 1;
		frame.getContentPane().add(txtDifficultyLevel, gbc_txtDifficultyLevel);
		txtDifficultyLevel.setColumns(8);
		
		amatuer = new JRadioButton("Amateur    ");
		amatuer.setHorizontalAlignment(SwingConstants.LEFT);
		amatuer.setBackground(Color.BLACK);
		amatuer.setForeground(Color.WHITE);
		amatuer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_rdbtnNewRadioButton = new GridBagConstraints();
		gbc_rdbtnNewRadioButton.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton.gridx = 1;
		gbc_rdbtnNewRadioButton.gridy = 1;
		frame.getContentPane().add(amatuer, gbc_rdbtnNewRadioButton);
	
		amatuer.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {  
	        	 if (e.getStateChange() == ItemEvent.SELECTED){
	            pro.setEnabled(false);
	            diff_level = false;
	        	 }
	        	 else{
	        		 pro.setEnabled(true); 
	        	 }
	         }  
	         
	      });
		
		pro = new JRadioButton("Professional");
		pro.setBackground(Color.BLACK);
		pro.setForeground(Color.WHITE);
		pro.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pro.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_rdbtnNewRadioButton_1 = new GridBagConstraints();
		gbc_rdbtnNewRadioButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnNewRadioButton_1.gridx = 1;
		gbc_rdbtnNewRadioButton_1.gridy = 2;
		frame.getContentPane().add(pro, gbc_rdbtnNewRadioButton_1);
		pro.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {         
	        	 if (e.getStateChange() == ItemEvent.SELECTED){
	 	            amatuer.setEnabled(false);
	 	            diff_level = true;
	 	        	 }
	 	        	 else{
	 	        		 amatuer.setEnabled(true); 
	 	        	 }
	         }           
	      });
		
		JButton play=new JButton("Play Game");
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(amatuer.isSelected()==true || pro.isSelected()==true){
					 PingPong1();
					 frame.dispose();
				}
				else{
					textField_1.setText("Difficulty level not selected");
				}
			}
		});
		play.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_play = new GridBagConstraints();
		gbc_play.insets = new Insets(0, 0, 5, 5);
		gbc_play.gridx = 0;
		gbc_play.gridy = 5;
		frame.getContentPane().add(play, gbc_play);
		
		JButton quit=new JButton("Quit Game");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				Game.stop();
			}
		});
		quit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_quit = new GridBagConstraints();
		gbc_quit.insets = new Insets(0, 0, 5, 5);
		gbc_quit.gridx = 0;
		gbc_quit.gridy = 6;
		frame.getContentPane().add(quit, gbc_quit);
		
		JButton host=new JButton("    Host    ");
		host.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(amatuer.isSelected()==true || pro.isSelected()==true){
						System.out.println("host the game");
						frame.dispose();
						if (ClientPlayer.connected==1){
						   new CreateServer();
						}
						else {
							//JOptionPane.showMessageDialog(null, "Creating server...");
							new CreateServer();
						}
				}
				else{
					textField_1.setText("Difficulty level not selected");
				}
			
			}
		});
		host.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_host = new GridBagConstraints();
		gbc_host.insets = new Insets(0, 0, 5, 5);
		gbc_host.gridx = 0;
		gbc_host.gridy = 3;
		frame.getContentPane().add(host, gbc_host);
		
		JButton connect=new JButton(" Connect  ");
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

							System.out.println("Connect to the game");
							frame.dispose();
							try {
								new ClientPlayer();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				
			}
		});
		connect.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_connect = new GridBagConstraints();
		gbc_connect.insets = new Insets(0, 0, 5, 5);
		gbc_connect.gridx = 0;
		gbc_connect.gridy = 4;
		frame.getContentPane().add(connect, gbc_connect);
		
		JButton connect2=new JButton(" Connect2 ");
		connect2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

							System.out.println("Connect to the game");
							frame.dispose();
							try {
								new ClientPlayer2();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				
			}
		});
		connect2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_connect2 = new GridBagConstraints();
		gbc_connect2.insets = new Insets(0, 0, 5, 5);
		gbc_connect2.gridx = 1;
		gbc_connect2.gridy = 4;
		frame.getContentPane().add(connect2, gbc_connect2);
		
		JButton connect3=new JButton(" Connect3 ");
		connect3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

							System.out.println("Connect to the game");
							frame.dispose();
							try {
								new ClientPlayer3();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
				
			}
		});
		connect3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GridBagConstraints gbc_connect3 = new GridBagConstraints();
		gbc_connect3.insets = new Insets(0, 0, 5, 5);
		gbc_connect3.gridx = 2;
		gbc_connect3.gridy = 4;
		frame.getContentPane().add(connect3, gbc_connect3);
	
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setEnabled(true);
		textField_1.setBorder(null);
		textField_1.setBackground(Color.BLACK);
		textField_1.setForeground(Color.RED);
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		GridBagConstraints gbc_display1 = new GridBagConstraints();
		gbc_display1.fill = GridBagConstraints.HORIZONTAL;
		gbc_display1.gridx = 1;
		gbc_display1.gridy = 5;
		frame.getContentPane().add(textField_1, gbc_display1);
		textField_1.setColumns(20);
		
	}
	
	 public void PingPong1() {
		 	setTitle("Ping-Pong");
	        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	        setResizable(false);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        add(new Game());
			setLocationRelativeTo(null);
	        setVisible(true);  
	    }
}
