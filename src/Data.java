import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;


public class Data {
	public Data(){
		
	}
	public static Font fontH, fontVA, fontPress;
	
	public static BufferedImage sprites;
	public static BufferedImage worm;
	public static BufferedImage backgound;
	
	public static Image snakeIcon;
	
	public static Image imageHead_Forward;
	public static Image imageHead_GoLeft;
	public static Image imageHead_GoRight;
	public static Image imageHead_Godown;
	
	public static Image imageTail_Forward;
	public static Image imageTail_GoLeft;
	public static Image imageTail_GoRight;
	public static Image imageTail_Godown;
	
	
	public static Image verticalImageBody, horizontalImageBody;
	public static Image bodyTurnLeftUpIm, bodyTurnRightUpIm, bodyTurnLeftDownIm, bodyTurnRightDownIm;
	
	public static Image backgoundCut;
	
	public static Image [] imageBall= new Image[3];
	public static int numberBall=0;
	
	static AudioPlayer AP = AudioPlayer.player;
	static AudioStream ASEat, ASGameOver, ASPowerUp, ASMoving, ASBackgoundMusic;
	static AudioData ADEat, ADGameOver, ADPowerUp, ADMonving, ADBackgoundMusic;
	static AudioDataStream loop = null;
	static ContinuousAudioDataStream loopCon = null;

	public static void loadImage(){
		try{
			
			backgound = ImageIO.read(new File("image/rszSlitherIOBackground.png"));
			backgoundCut = backgound.getSubimage(0, 0, Panel.wight+Panel.padding-7, Panel.hight+Panel.padding-7);
			
			worm = ImageIO.read(new File("image/wormImages.png"));
			imageBall[0] = worm.getSubimage(4, 39, 29, 34);
			imageBall[1] = worm.getSubimage(34, 43, 25, 29);
			imageBall[2] = worm.getSubimage(61, 46, 25, 25);
			
			sprites = ImageIO.read(new File("image/edittedSnakeImages.png"));
			verticalImageBody = sprites.getSubimage(43, 20, 19, 20);
			horizontalImageBody = sprites.getSubimage(20, 1, 20, 20);
			
			bodyTurnLeftUpIm = sprites.getSubimage(43, 1, 20, 20);
			bodyTurnRightUpIm = sprites.getSubimage(1, 1, 20, 20);
			bodyTurnLeftDownIm = sprites.getSubimage(43, 43, 20, 20);
			bodyTurnRightDownIm = sprites.getSubimage(1, 26, 19, 16);

			imageHead_Forward = sprites.getSubimage(64, 0, 20, 21);
			imageHead_Godown = sprites.getSubimage(85, 21, 20, 20);
			imageHead_GoLeft = sprites.getSubimage(64, 21, 20, 20);
			imageHead_GoRight = sprites.getSubimage(85, 0, 20, 20);
			
			imageTail_Forward = sprites.getSubimage(64, 43, 20, 20);
			imageTail_Godown = sprites.getSubimage(86, 64, 18, 14);
			imageTail_GoLeft = sprites.getSubimage(64, 66, 20, 18);
			imageTail_GoRight = sprites.getSubimage(86, 44	, 18, 19);

		} catch (Exception e) {}
	}
	public static void loadAudio(){
		
		try {
			ASEat = new AudioStream(new FileInputStream("Audio/audioEat.wav"));
			ASGameOver = new AudioStream(new FileInputStream("Audio/audioGameOver.wav"));
			ASPowerUp = new AudioStream(new FileInputStream("Audio/audioPowerUp.wav"));
			ASMoving = new AudioStream(new FileInputStream("Audio/movingBubble.wav"));
			
			ADEat = ASEat.getData();
			ADGameOver = ASGameOver.getData();
			ADPowerUp = ASPowerUp.getData();
			ADMonving =  ASMoving.getData();
			

		} catch (Exception e) {
		}

		
	}
	public static void playSound( AudioData AD){
		
		loop = new AudioDataStream(AD);
		AP.start(loop);

	}

	public static void loadFont(){
		fontH = new Font("H",Font.ITALIC,18);
		fontVA = new Font("H",Font.BOLD,12);
		fontPress = new Font("Press",Font.TRUETYPE_FONT,10);
	}
}
