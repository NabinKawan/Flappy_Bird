import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
      AudioClip clip;
      public static Sound jump=new Sound("/Jump.wav");
      public static Sound hit=new Sound("/Hit.wav");
      public static Sound fall=new Sound("/fall.wav");
      public Sound(String path) {
    	  clip=Applet.newAudioClip(getClass().getResource(path));
      }
      public void play() {
    	  new Thread() {
    		  public void run() {
    			  clip.play();
    		  }
    	  }.start();
    	  
      }
  
}
