import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Room {

    public ArrayList<Rectangle> tubes;
    private int time;
    private int currentTime = 0;

    private int speed = 4;

    private Random random;
    private final int SPACE_TUBES = 150 ;
    private final int WIDTH_TUBES = 32;

    public Room(int time)
    {
        tubes = new ArrayList<>();
        this.time = time;

        random = new Random();
    }

public void update()
{
    currentTime++;
    if(currentTime == time)
    {
        currentTime = 0;






         int height1 = random.nextInt(Flappy.HEIGHT/2);

         int y2 = height1 + SPACE_TUBES;
         int height2 = Flappy.HEIGHT -y2;

         tubes.add(new Rectangle(Flappy.WIDTH,0,WIDTH_TUBES,height1));
         tubes.add(new Rectangle(Flappy.WIDTH,y2,WIDTH_TUBES,height2));

    }

    for (int i = 0; i < tubes.size(); i++) {
        Rectangle rect = tubes.get(i);
        rect.x-=speed;

        if(rect.x+rect.width <= 0)
        {
            tubes.remove(i--); // fjerner tubes som er udefra vinduet
            Ding ding = new Ding();
            ding.play();
            Flappy.score += 0.5;
            continue;
        }
    }



}

public void render(Graphics g)
{
    g.setColor(Color.BLUE);

    for (int i = 0; i < tubes.size(); i++) {
        Rectangle rect = tubes.get(i);
        g.fillRect(rect.x,rect.y,rect.width,rect.height);
    }
}

}
