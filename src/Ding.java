import java.applet.Applet;
import java.applet.AudioClip;

public class Ding {

    private AudioClip clip;



    public Ding()
    {
        clip = Applet.newAudioClip(getClass().getResource("Ding.wav"));
    }


    public void play()
    {
        new Thread()
        {
            public void run()
            {
                clip.play();
            }
        }.start();
    }
}

class Splat {

    private AudioClip clip2;

    public Splat()
    {
        clip2 = Applet.newAudioClip(getClass().getResource("Splat.wav"));
    }


    public void play()
    {
        new Thread()
        {
            public void run()
            {
                clip2.play();
            }
        }.start();
    }


}

