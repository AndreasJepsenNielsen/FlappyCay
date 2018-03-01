import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Bird extends Rectangle{

    private static final long serialVersionUID = 1L;

    private float speed = 6; // bird speed
    public boolean isPressed = false;
    private BufferedImage Sheet;
    private BufferedImage[] textures;
    private ArrayList<Rectangle> tubes;
    private BufferedImage defaultTexture;
    private int imageIndex = 0;
    private boolean isFalling = false;

    private float gravity = 0.3f; // bird gravity


    public Bird(int x, int y, ArrayList<Rectangle> tubes)
    {
        setBounds(x,y,62,62);
        this.tubes = tubes;
        textures = new BufferedImage[3];
        try {
            Sheet = ImageIO.read(this.getClass().getResourceAsStream("Cay222.png"));
            textures[0] = Sheet.getSubimage(0,0,400,348);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update()
    {
        isFalling = false;
        if(isPressed)
        {   y-=(speed/1.4);

        }else{
            isFalling = true;
            y+=speed;
        }

        if(isFalling)
        {
            if(speed >= 8 ) speed = 8;
        }

        for (int i = 0; i < tubes.size() ; i++) {
            if(this.intersects(tubes.get(i))|| y <= 0)
            {
                if (Flappy.score > Flappy.highScore)
                {
                    Flappy.createHighScore(Flappy.score);
                    Flappy.highScore = Flappy.readHighscore();
                }
                Splat splat = new Splat();
                splat.play();
                Flappy.room = new Room(80);
                tubes = Flappy.room.tubes;
                y = Flappy.HEIGHT/2;
                Flappy.score = 0;
                Room.setSpeed(4);
                speed = 6;
                break;
            }
        }
        if(y >= Flappy.HEIGHT)
        {
            if (Flappy.score > Flappy.highScore)
            {
                Flappy.createHighScore(Flappy.score);
                Flappy.highScore = Flappy.readHighscore();
            }
            Splat splat = new Splat();
            splat.play();
            Flappy.room = new Room(80);
            tubes = Flappy.room.tubes;
            y = Flappy.HEIGHT/2;
            Flappy.score = 0;
            Room.setSpeed(4);
            speed = 6;
        }

    }

    public void render(Graphics g)
    {
        g.drawImage(textures[imageIndex],x,y,width,height,null);
        //g.setColor(Color.green);
       // g.fillOval(x,y,width,height);
    }


}
