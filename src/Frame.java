import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;



public class Frame extends JFrame {
	Panel panel;
	static int Width, Height;
	public Frame() {
		Width = 650;
		Height = 463;
		
		try {
			setIconImage(ImageIO.read(new File("image/snakeIcon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		setSize(Width, Height);
		setTitle("SNAKE");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int)(dim.getWidth()-Width)/2, (int)(dim.getHeight()-Height)/2);
		setBackground(Color.LIGHT_GRAY);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		panel = new Panel();
		add(panel);
		
		this.addKeyListener(new handler());
		
		setVisible(true);

	}
	
	public static void main(String [] args){
		Frame f= new Frame();
		
	}
	private class handler implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_SPACE){
				Panel.isPlaying=!Panel.isPlaying;
				if(Panel.isGameOver==true) {
					panel.snake.reUpdateAfterOverGame();
				}
			}
			if(panel.waitForTheMove){
			if(e.getKeyCode()==KeyEvent.VK_UP){
				panel.snake.setVector(TheSnake.GO_UP);
			}
			if(e.getKeyCode()==KeyEvent.VK_DOWN){
				panel.snake.setVector(TheSnake.GO_DOWN);
			}
			if(e.getKeyCode()==KeyEvent.VK_RIGHT){
				panel.snake.setVector(TheSnake.GO_RIGHT);
			}
			if(e.getKeyCode()==KeyEvent.VK_LEFT){
				panel.snake.setVector(TheSnake.GO_LEFT);
			}
			panel.waitForTheMove=false;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {}
		
	}
}
