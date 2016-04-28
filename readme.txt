

Dependencies : 
	Java version : 1.5 and above 
	Libraries : swing , awt
	Packages : java.net , java.io
Key combinations :
	Move up : Up Arrow key 
	Move Down : Down Arrow Key 
	Move Left : Left Arrow key 
	Move Right : Right Arrow key
	Exit : esc button

	For the host and first player to connect ,Key combinations would be Up and Down Arrow keys as their paddles would be vertical.
	For the third player and fourth player , Left and Right Arrow keys will allow them to move their paddles left and right respectively.

Instructions for a new player :
	When the PingPong.java is run , game window opens with following options:
	1. Number of players : JButton with options 'Two' ,'Three' and 'Four' allows
	the player to choose the number of players he wants to play with.

	2. Difficulty level : Player can choose the difficulty level(either Amateur or Professional) by clicking on the corresponding JRadio button.
	In Amateur mode, The paddle height and width are higher while speed of the ball is lower in comparison to Professional mode. Also the chnaces of AI to win increases as its sync with the ball increases in professional mode.

	3. Play Game Button : Allows the player to play the game against computer player(s).

	4. Host Button : Allows the player to host the game , after he/she provides
	a valid port number.

	5. Connect Button : Allows the second player to connect to a hosted game after he provides the port number and IP address of machine hosting the game.

	6. Connect2 Button : Allows the third player to connect to a hosted game after he provides the port number and IP address of machine hosting the game.

	7. Connect3 Button : Allows the fourth(and last) player to connect to a hosted game after he provides the port number and IP address of machine hosting the game.

	8. Quit button : Closes the game window.

	SCORING : Everytime the ball touches a player's wall , his score is incremented by one. Once his score reaches 3 , he is deemed as dead and his paddle is removed with other players still connected and playing the game .
	Last player remaining wins the game.

	Any player can leave the game at any time by pressing the Close button on the top of game window.

	Game won't start till the selected numebr of players by the host join the game.
	



	
