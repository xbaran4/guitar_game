import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{
	private static final Dimension gameDimension = new Dimension(720,500);
	private static final String gameName = "GuitarMan";
	private static JFrame frame;
	private static Game game;
	private static boolean isInGame = false;
	private boolean running = false;
	private Thread thread;
	private BufferedImage bg;
	private BufferStrategy bs;
	private int numPlayingFields = 4;
	private int playingFieldsWidth = 50;
	private ArrayList<BoxLine> boxLines;
	private int spawnCount;
	private int difficulty;
	private int difficultyMultiplier;
	private Player player = new Player();
	private Player bestPlayer;
	private ArrayList<HitBox> hitBoxes;
	private ArrayList<PlayingBox> playingBoxes;
	private boolean isPaused = false;
	
	
	public static void main(String[] args) {
		game = new Game(); 
		game.setPreferredSize(gameDimension);
		game.setBackground(Color.black);
		frame = new JFrame(gameName);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Login login = new Login(game);
        frame.add(login);
        frame.setResizable(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        boolean temp = true;
        while(temp){
        	System.out.println();
        	 if(isInGame){
        		frame.remove(login);
        		frame.add(game);
	         	frame.requestFocus();
	         	frame.pack();
	         	frame.setLocationRelativeTo(null);
	         	game.start();
	         	temp = false;
        	 }
        }
	}
	
	public void run() 
	{
		init();
		requestFocus();
		long firstStart = System.nanoTime();
		double refreshRate = 60.0;
		double nanosec = 1000000000 / refreshRate;
		double delta = 0.0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		
		while(running)
		{
			long secondStart = System.nanoTime();
			delta += (secondStart - firstStart) / nanosec;
			firstStart = secondStart;
			
			if(delta >= 1)
			{
				refresh();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer+= 1000;
				System.out.println("refresh rate: " + updates + ", fps:" + frames );
				frames = 0;
				updates = 0;
			}
		}
		stop();
	}
	
	private synchronized void start()
	{
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop()
	{
		if(!running)
			return;
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	public void initIO() throws IOException {
		BufferedImageLoader loader = new BufferedImageLoader();     
	    bg = loader.loadImage("Img/bg.jpg");
	    
	    BufferedReader in = new BufferedReader(new FileReader("config/best.cfg"));
	    
	    bestPlayer = new Player();
	    try {
	    	bestPlayer.setName(in.readLine());
			bestPlayer.setScore(Integer.parseInt(in.readLine()));
		} catch (Exception e) {
			bestPlayer.setScore(0);
			bestPlayer.setName("");
		}
	    in.close();
	}
	
	public void init() {
		try {
			initIO();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		createBufferStrategy(2);
		bs = this.getBufferStrategy();
		
		addKeyListener(new Keyboard(this));
		
		boxLines = new ArrayList();
		hitBoxes = new ArrayList();
		playingBoxes = new ArrayList();
		spawnCount = 0;
		difficulty = 4;
		difficultyMultiplier = 2000;
		
		for(int x = 0;x < numPlayingFields; x++)
			boxLines.add(new BoxLine(new Dimension(playingFieldsWidth, gameDimension.height), (x+1) * 100, 0, Color.DARK_GRAY));
		for(int x = 0;x < numPlayingFields; x++)
			hitBoxes.add(new HitBox(new Dimension(playingFieldsWidth, playingFieldsWidth), boxLines.get(x).getPositionX(), gameDimension.height - 100,Color.gray));
    }
	
	private void refresh(){
		if(player.getHealth() < 1)
				endGame();
		
		for(int x = 0; x < playingBoxes.size(); x++)
			playingBoxes.get(x).refresh(game);
		
		spawnCount++;
		Random random = new Random();
		
		if(spawnCount % (difficulty * 12) == 0)
		{
			int randomInt = random.nextInt(numPlayingFields);
			playingBoxes.add(new PlayingBox(new Dimension(playingFieldsWidth,playingFieldsWidth), (randomInt + 1) * 100, 0 - playingFieldsWidth, Color.LIGHT_GRAY, randomInt));
			spawnCount = 0;
		}	
	}
	
	private void render(){
		Graphics g = bs.getDrawGraphics();
		g.drawImage(bg, 0, 0, getWidth(),getHeight(),this);
		for(int x = 0;x < numPlayingFields; x++){
			g.setColor(boxLines.get(x).getColor());
			g.fillRect(boxLines.get(x).getPositionX(), boxLines.get(x).getPositionY(), boxLines.get(x).getDimensions().width, boxLines.get(x).getDimensions().height);
		
			g.setColor(hitBoxes.get(x).getColor());
			g.fillRect(hitBoxes.get(x).getPositionX(), hitBoxes.get(x).getPositionY(), hitBoxes.get(x).getDimensions().width, hitBoxes.get(x).getDimensions().height);
		}
		g.setColor(Color.lightGray);
		g.setFont(new Font("arial",1,50));
		g.drawString("A", 108, 50);
		g.drawString("S", 208, 50);
		g.drawString("D", 308, 50);
		g.drawString("F", 408, 50);
		
		for(int x = 0;x < playingBoxes.size(); x++){	
			g.setColor(playingBoxes.get(x).getColor());
			g.fillRect(playingBoxes.get(x).getPositionX(), playingBoxes.get(x).getPositionY(), playingBoxes.get(x).getDimensions().width, playingBoxes.get(x).getDimensions().height);
		}
		g.setFont(new Font("arial",1,20));
		g.drawString(player.getName(), 550, 100);
		g.drawString("Score:  " + player.getScore(), 550, 150);
		g.drawString("Health:  " + player.getHealth(), 550, 200);
		
		if(bestPlayer.getScore() != 0) {
			g.drawString("Best player : " , 550, 300);
			g.drawString(bestPlayer.getName(), 550, 350);
			g.drawString("Score:  " + bestPlayer.getScore(), 550, 400);
		}
		g.dispose();
		bs.show();	
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_A)
			if(hit(0))
				player.setScore(player.getScore() + 100);
			else
				player.setHealth(player.getHealth() - 1);
		
		else if(key == KeyEvent.VK_S)

			if(hit(1))
				player.setScore(player.getScore() + 100);
			else
				player.setHealth(player.getHealth() - 1);
		
		else if(key == KeyEvent.VK_D)
			if(hit(2))
				player.setScore(player.getScore() + 100);
			else
				player.setHealth(player.getHealth() - 1);
		
		else if(key == KeyEvent.VK_F)
			if(hit(3))
				player.setScore(player.getScore() + 100);
			else
				player.setHealth(player.getHealth() - 1);
		
	}
	
	public boolean hit(int index){

		for(int y = 0; y < playingBoxes.size(); y++)
			if(playingBoxes.get(y).getRectangle().intersects(hitBoxes.get(index).getRectangle()))
			{
				playingBoxes.remove(y);
				
				if((player.getScore() % difficultyMultiplier  == 0 && player.getScore() != 0) && difficulty > 1)
					difficulty--;
				return true;
			}
		return false;
	}
	
	public void endGame() {
		
		if(player.getScore() > bestPlayer.getScore())
		{
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter("config/best.cfg"));
				out.write(player.getName());
				out.newLine();
				out.write(Integer.toString(player.getScore()));
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		EndScreen endScreen = new EndScreen(game);
		game.setVisible(false);
		frame.add(endScreen);
		frame.requestFocus();
     	frame.pack();
     	frame.setLocationRelativeTo(null);
     	stop();
	}
	
	public void setIsInGame(boolean bool) {
		isInGame = bool;
	}
	public Player getPlayer() {
		return player;
	}
	public ArrayList<PlayingBox> getPlayingBoxes() {
		return playingBoxes;
	}
	public int getGameHeight() {
		return gameDimension.height;
	}
	
}
