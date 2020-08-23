import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Bird extends Rectangle{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float spd=4;
	public boolean isPressed=false;
	public boolean isFalling=false;
	public boolean isUpPressed=false;
	private int imageindex;
	public int frames=0;
	private BufferedImage[] sheet=new BufferedImage[3];
	private float gravity=0.3f;
	ArrayList<Rectangle> tubes;
	public Bird(int x,int y,ArrayList<Rectangle> tubes)  {
		setBounds(x,y,40,40);
		this.tubes=tubes;
		try {
		sheet[0]=ImageIO.read(getClass().getResource("/f1.png"));
		sheet[1]=ImageIO.read(getClass().getResource("/f2.png"));
		sheet[2]=ImageIO.read(getClass().getResource("/f3.png"));
		}
		catch(IOException e) {
			
		}
	}
	
	public void update() {
		if(isPressed) {
			spd=4;
			y-=spd;
			imageindex=1;
			frames=0;
		}
		
		else if(!isPressed && isUpPressed){
			isFalling=true;
			y+=spd;
			frames++;
			if(frames>20) frames=20;
			//imageindex=2;
			//spd+=gravity;
		}
		if(isFalling) 
			{
			spd+=gravity;
			if(spd>=8) spd=8;
			if(frames>=20) imageindex=2;
			else imageindex=0;
			isFalling=false;
			}
		for(int i=0;i<tubes.size();i++) {
			if(intersects(tubes.get(i))) {
				Flappy.room=new Room(80);
				tubes=Flappy.room.tubes;
				y=Flappy.HEIGHT/2;
				Flappy.score=0;
				isUpPressed=false;
				Sound.hit.play(); 
				break;
			}
		}
		if(y<0 || y>480) {
			Flappy.room=new Room(80);
			tubes=Flappy.room.tubes;
			y=Flappy.HEIGHT/2;
			Flappy.score=0;
			isUpPressed=false;
			Sound.hit.play();
			
		}
	
	}
	public void render(Graphics g) {
	
		g.drawImage(sheet[imageindex],x,y,40,40,null);
		imageindex=0;	
	}
}
