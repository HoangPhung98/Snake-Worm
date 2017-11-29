import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JOptionPane;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import javafx.scene.layout.Pane;


public class TheSnake {
	
	Point point;
	public static int velocity;
	Score score;
	
	int currentImage=0;
	Image imageHead, imageTail;
	
	int [] x;
	int [] y;
	int length =3;
	long t1 =0;
	public static int GO_UP = 1;
	public static int GO_DOWN = -1;
	public static int GO_RIGHT = 2;
	public static int GO_LEFT = -2;
	int vector = TheSnake.GO_DOWN;
	
	public static int [][] tailDirection;
	
	boolean allowToHightLightV;
	public TheSnake(){
		velocity = 250;
		score = new Score();
		
		x = new int [400];
		y = new int [400];
		
		x[0]=5; y[0]=5;
		x[1]=5; y[1]=4;
		x[2]=5; y[2]=3;
		
		tailDirection = new int[20][20];
		for(int i=0; i<20; i++){
			for(int j=0; j<20; j++) {
				tailDirection[i][j] = GO_DOWN;
			}
		}
		allowToHightLightV = false;
	}
	public void setVector(int v){
		if(vector != -v) vector = v;
	}
	public boolean checkPositionInTheSnake(int x1, int y1){
		for(int i=0; i< length; i++){
			if(x1==x[i] && y1 == y[i]) return true;
		}
		return false;
	}
	public Point getBallPosition(){
		int x ;
		int y ;
		do{
			x =(int)(Math.random()*20);
			y = (int)(Math.random()*20);
		}while(checkPositionInTheSnake(x,y));
		return new Point(x,y);

	}
	public  void reUpdateAfterOverGame(){
		this.length = 3;
		x = new int [400];
		y = new int [400];
		
		x[0]=7;y[0]=7;
		x[1]=7;y[1]=6;
		x[2]=7;y[2]=5;
		
		Panel.isGameOver=false;
		score.reupdateScore();
		velocity=250;
		Panel.enableToOverGame=false;
		
	}
	
	public void CheckIncreaseVelocity(){
		if(score.score%5==0 && score.score!=0) {
			Data.playSound(Data.ADPowerUp);
			this.velocity-=45-score.score;
			allowToHightLightV =true;
		}else allowToHightLightV =false;
	}
	public void update(){
		
		for(int i=1; i<length; i++) 
			if(x[0]==x[i]&&y[0]==y[i]){
				Data.playSound(Data.ADGameOver);
	
				String name = JOptionPane.showInputDialog("Type Your Name :");

				Panel.user.add(new User(name, String.valueOf(score.score)));
				Panel.updateData();
				
				
				
				Panel.isPlaying=false;
				Panel.isGameOver=true;
				Panel.checkPressSpaceAfterOver=false;
			}
		if(Panel.isPlaying && !Panel.isGameOver){
		if(System.currentTimeMillis()-t1>velocity){
			Data.playSound(Data.ADMonving);

			Panel.waitForTheMove=true;
			currentImage++;
			if(currentImage==2)currentImage=0;
		if(Panel.bg[x[0]][y[0]]==2) {
			Data.playSound(Data.ADEat);
			score.updateScore();
			length++;
			
			Panel.bg[x[0]][y[0]]=0;
			point = getBallPosition();
			Panel.bg[point.x][point.y]=2;
			
			CheckIncreaseVelocity();

		}
		for(int i=length-1; i>0; i--){
				x[i]=x[i-1];
				y[i]=y[i-1];
			}
		if(vector==GO_UP){
			tailDirection[x[0]][y[0]]=GO_UP;
			y[0]--; 
			imageHead = Data.imageHead_Forward;
			}
		
		if(vector==GO_DOWN){
			tailDirection[x[0]][y[0]]=GO_DOWN;
			y[0]++;
			imageHead = Data.imageHead_Godown;

			}
		if(vector==GO_RIGHT){
			tailDirection[x[0]][y[0]]=GO_RIGHT;
			x[0]++; 
			imageHead = Data.imageHead_GoRight;

			}
		if(vector==GO_LEFT){
			tailDirection[x[0]][y[0]]=GO_LEFT;
			x[0]--; 
			imageHead = Data.imageHead_GoLeft;

			}
		
		if(x[0]<0) x[0]=19;
		if(x[0]>19) x[0]=0;
		if(y[0]<0) y[0]=19;
		if(y[0]>19) y[0]=0; 
		
		t1 = System.currentTimeMillis();
		}
		}
	}
	public void paintSnake(Graphics g){
		Image body = null;
		for(int i=1; i<length-1; i++){
			if(tailDirection[x[i]][y[i]]==GO_UP){
				if(tailDirection[x[i+1]][y[i+1]]==GO_LEFT) body= Data.bodyTurnRightDownIm;
				else if(tailDirection[x[i+1]][y[i+1]]==GO_RIGHT) body = Data.bodyTurnLeftDownIm;
				else body = Data.verticalImageBody;
			}
			if(tailDirection[x[i]][y[i]]==GO_DOWN){
				if(tailDirection[x[i+1]][y[i+1]]==GO_LEFT) body= Data.bodyTurnRightUpIm;
				else if(tailDirection[x[i+1]][y[i+1]]==GO_RIGHT) body = Data.bodyTurnLeftUpIm;
				else body = Data.verticalImageBody;
			}
			if(tailDirection[x[i]][y[i]]==GO_RIGHT){
				if(tailDirection[x[i+1]][y[i+1]]==GO_UP) body= Data.bodyTurnRightUpIm;
				else if(tailDirection[x[i+1]][y[i+1]]==GO_DOWN) body = Data.bodyTurnRightDownIm;
				else body = Data.horizontalImageBody;
			}
			if(tailDirection[x[i]][y[i]]==GO_LEFT){
				if(tailDirection[x[i+1]][y[i+1]]==GO_UP) body= Data.bodyTurnLeftUpIm;
				else if(tailDirection[x[i+1]][y[i+1]]==GO_DOWN) body = Data.bodyTurnLeftDownIm;
				else body = Data.horizontalImageBody;
			}
 
			
			g.drawImage(body,x[i]*20+Panel.padding, y[i]*20+1+Panel.padding, 20, 20,null);

			
		}


		if(tailDirection[x[length-1]][y[length-1]]==GO_UP) imageTail = Data.imageTail_Forward;
		if(tailDirection[x[length-1]][y[length-1]]==GO_DOWN) imageTail = Data.imageTail_Godown;
		if(tailDirection[x[length-1]][y[length-1]]==GO_RIGHT) imageTail = Data.imageTail_GoRight;
		if(tailDirection[x[length-1]][y[length-1]]==GO_LEFT) imageTail = Data.imageTail_GoLeft;
		g.drawImage(imageTail,x[length-1]*20+Panel.padding, y[length-1]*20+Panel.padding+1, 20, 20,null);
		
		g.drawImage(imageHead, x[0]*20+Panel.padding, y[0]*20+Panel.padding, null);

	}
}
