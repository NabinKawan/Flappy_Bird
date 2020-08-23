import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Flappy extends Canvas implements Runnable,KeyListener{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public static final int WIDTH=640,HEIGHT=480;
private boolean running=false;
Thread thread;
public static int finalscore;
public static float score=0;
public static Room room;
private BufferedImage sheet;
public Bird bird;
public Flappy(){
	Dimension d=new Dimension(WIDTH,HEIGHT);
	setPreferredSize(d);
	addKeyListener(this);
	setFocusable(true);
	room=new Room(80);
	bird=new Bird(20,HEIGHT/2,room.tubes);
	try {
		sheet=ImageIO.read(getClass().getResource("/background.png"));
	}
	catch(IOException e) {
		
	}
}
public synchronized void start() {
	if(running) return;
	running=true;
	thread=new Thread(this);
	thread.start();
}
public synchronized void stop() {
	if(!running) return;
	running=false;
	try {
		thread.join();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	
	}
}
	public static void main(String[] args) {
		JFrame frame=new JFrame("Flappy Bird");
		Flappy flappy=new Flappy();
		frame.add(flappy);
		frame.setResizable(false);
		frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        flappy.start();
}
	@Override
	public void run() {
		int fps=0;
		double timer=System.currentTimeMillis();
		double lastTime=System.nanoTime();
		double ns=1000000000/60;
		double delta=0;
		while(running) {
			double now=System.nanoTime();
			delta+=(now-lastTime)/ns;
			lastTime=now;
		
			while(delta>=1) {
				update();
				render();
			fps++;
			delta--;
		}
			if(System.currentTimeMillis()-timer >=1000) {
			//	System.out.println("FPS: "+ fps);
				fps=0;
				timer+=1000;
			}
		}

		stop();
	}
	private void render() {
		BufferStrategy bs=getBufferStrategy();
		if(bs==null) {
		//	System.out.println("null");
			createBufferStrategy(3);
			return;
		}
	//System.out.println("buffer created");
      Graphics g=bs.getDrawGraphics();
      if(g==null) {
    	 // System.out.println("grpahics_obj is null");
    	  return;
      }
//  g.setColor(Color.BLACK);
//   g.fillRect(0,0,WIDTH,HEIGHT);
      g.drawImage(sheet,0,0,WIDTH,HEIGHT,null);
   room.render(g);
   bird.render(g);
   g.setColor(Color.WHITE);
   g.setFont(new Font(Font.DIALOG,Font.BOLD,16));
   g.drawString("Score: "+ (int)score, 10, 20);
	  g.dispose();
	  bs.show();
	}
	private void update() {
		room.update();
        bird.update();
		
	}
	public void pause() {
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_UP) {
			bird.isPressed=true;
			bird.isUpPressed=true;
		}
		System.out.println("Pressed");
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_UP) {
			bird.isPressed=false;
			System.out.println("Unpressed");
		}
		
	}
	
}