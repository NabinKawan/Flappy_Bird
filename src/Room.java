import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Room {
	
	public ArrayList<Rectangle> tubes;
	private int time;
	private int currentTime=0;
	Random random;
	private int spd=4;
	private final int SPACE_TUBES=150;
	private final int WIDTH_TUBES=60;
	private BufferedImage[] sheet=new BufferedImage[2];
	public Room(int time) {
		tubes=new ArrayList<>();
		random=new Random();
	    this.time=time;
	    try {
	    	  sheet[0]=ImageIO.read(getClass().getResource("/pipeTop.png"));
	    	  sheet[1]=ImageIO.read(getClass().getResource("/pipeBtm.png"));
	    }
	 catch(IOException e) {
		 
	 }
	}

	public void update() {
		currentTime++;
		if(currentTime==time) {
			currentTime=0;
			int height1=random.nextInt(Flappy.HEIGHT/2);
			int y1=height1 + SPACE_TUBES;
			int height2=Flappy.HEIGHT-y1;
			tubes.add(new Rectangle(Flappy.WIDTH,0,WIDTH_TUBES,height1));
			tubes.add(new Rectangle(Flappy.WIDTH,y1,WIDTH_TUBES,height2));
			return;
		}
		boolean s=false;
		for(int i=0;i<tubes.size();i++) {
			Rectangle rect=tubes.get(i);
			rect.x-=spd;
			if(rect.x+rect.width<=0) {
				tubes.remove(i--);
				Flappy.score+=0.5;
				s=true;	
			}
			
		}
		if(s) Sound.jump.play(); 
	
}
	public void render(Graphics g1) {
		//g1.setColor(Color.CYAN);
		for(int i=0;i<tubes.size();i++) {
			Rectangle rect=tubes.get(i);
			if(i%2==0) {
			g1.drawImage(sheet[0],rect.x,rect.y,rect.width,rect.height,null);
			}
			else {
				g1.drawImage(sheet[1],rect.x,rect.y,rect.width,rect.height,null);
			}
		//g1.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
 }
}

