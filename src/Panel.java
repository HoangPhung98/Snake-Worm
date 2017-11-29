import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable {
	private static int [] scoreArr;
	public static ArrayList<User> user;
	
	Thread thread;
	static int [][] bg = new int [20][20];
	
	TheSnake snake ;
	
	Score score;
	static int padding=10;
	static int wight=400;
	static int hight=400;
	
	static boolean isPlaying = true;
	static boolean isGameOver =false;
	static boolean enableToStartGame = true;
	static boolean enableToOverGame = false;
	
	static boolean checkPressSpaceAfterOver=false;
	
	static long timeWorm;
	
	static boolean waitForTheMove = true;
	public Panel() {
		scoreArr = new int [20];
		user = new ArrayList<User>();
		readData(); 
		
		score = new Score();
		
		Data.loadImage();
		Data.loadFont();
		Data.loadAudio(); 		
		
//		Data.playBackgroungMusic();

		
		bg[(int)(Math.random()*20)][(int)(Math.random()*20)]=2;
		
		snake = new TheSnake();
				
		thread = new Thread(this);
		thread.start();
		timeWorm=0;
	}
	public static  void updateData(){
		FileWriter fw;
		try {
			fw = new FileWriter("Data/data.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			
			
			for(User u:user){
				bw.write(u.getName()+" "+ u.getScore());
				bw.newLine();
			}
			
			bw.close();
		} catch (IOException e) {}
		sortUser();
		
	}
	
	public void readData(){
		try {
			FileReader fr = new FileReader("Data/data.txt");
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			
			while((line = br.readLine()) != null){
				String str[] = line.split(" ");
				user.add(new User(str[0], str[1]));
			}
			
			br.close();
		} catch (IOException e) {}
		sortUser();

 		
	}
	public static void sortUser(){

		Comparator<User> comp = new Comparator<User>() {
			
			@Override
			public int compare(User o1, User o2) {
				
				int score1 = Integer.parseInt(o1.getScore());
				int score2 = Integer.parseInt(o2.getScore());
				
				if(score1<score2)return 1; 
				else return -1;
			}
		};
		Collections.sort(user, comp);

	}
	public void painBg(Graphics g) {
		
		g.setColor(new Color(23, 23, 23));
		g.fillRect(0,0, 650, 500);

		paintFrame(g);
		g.drawImage(Data.backgoundCut, padding-1, padding - 1, this);


		
		paintNameScore(g);
		paintValocity(g);
		
		for(int i=0; i<20; i++){
			for(int j=0; j<20; j++){
				if(bg[i][j]==2) {
					g.drawImage(Data.imageBall[Data.numberBall], i*20+padding, j*20+padding, 18, 18,null);
					if(System.currentTimeMillis()-timeWorm>100){
						Data.numberBall++;
						if(Data.numberBall==3) Data.numberBall=0;
						timeWorm = System.currentTimeMillis();
					}
				}
			}
		}
		

	}
	public  void paintValocity(Graphics g){
		if(snake.allowToHightLightV){
			g.setColor(Color.YELLOW);
			g.drawLine(430, 35, 620, 35);
			g.drawLine(430, 36, 620, 36);
		} else g.setColor(new Color(91,158,14));
		g.drawString("Valocity: "+(1000/TheSnake.velocity)+","+(1000%TheSnake.velocity)+" Km/h", 430, 25);
	}
	public static void paintNameScore(Graphics g){
		g.setFont(g.getFont().deriveFont(20f));
		g.setColor(new Color(207, 215, 219));
		for(int i=0; i<user.size(); i++){
			g.drawString(user.get(i).toString(), 450, 60+(i*30));;
		}

	}
	private void paintScore(Graphics g){
		g.setColor(Color.WHITE);
		g.draw3DRect(360, 20, 45, 35, true);
		
		g.setColor(Color.GREEN);
		g.setFont(g.getFont().deriveFont(20f));
		g.drawString(score.score+"", 370, 45);
		
	}
	private void paintFrame(Graphics g){
		g.setColor(new Color(166, 166, 166));
		g.fillRect(0, 0, wight+padding*2+3, hight+padding*2+3);
	}
	public void paint(Graphics g) {

		painBg(g);
		paintScore(g);
		snake.paintSnake(g);
		
		
		if(!isPlaying) {
			if(enableToOverGame){
				g.setColor(Color.RED);
				g.setFont(g.getFont().deriveFont(30f));
				g.drawString("GAME OVER!", 120, 200);

			}
			
			if(enableToStartGame ){
			g.setColor(Color.WHITE);
			g.setFont(g.getFont().deriveFont(20f));
			g.drawString("PRESS SPACE TO CONTINUE!", 60, 50);
			}
		}
		
		
	}

	public void run() {
		long t=0;
		while (true) {
			
			if(isPlaying){
				snake.update();
			}else {
				if(isGameOver){
					enableToOverGame=true;
				}
				if(System.currentTimeMillis()-t>500 ){
				enableToStartGame=!enableToStartGame;
				t=System.currentTimeMillis();
				}
			}
			repaint();

			try {
				thread.sleep(30);

			} catch (Exception e) {
			}
		}
	}
}
