import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Flappy extends Canvas implements Runnable,KeyListener {


    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 1200, HEIGHT = 800; // height and width of canvas
    private  boolean running = false; // boolean to check if game is running
    private Thread thread;

    public static double score = 0;

    public static Room room;
    public Bird bird;


    public Flappy()
    {

        Dimension d = new Dimension(Flappy.WIDTH,Flappy.HEIGHT);
        setPreferredSize(d);
        addKeyListener(this);
        room = new Room(80);
        bird = new Bird(20,Flappy.HEIGHT/2,room.tubes);
    }

    public synchronized void start()
    {
        if(running)return;
        running = true; // sets running to true
        thread = new Thread(this); // creates new thread which has this class as its target
        thread.start(); // starts the thread
    }

    public synchronized void stop()
    {
        if (!running) return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Flappy Cay"); // title
        Flappy flappy = new Flappy(); // Flappy indeholder canvas så laver en ny instance af flappy
        frame.add(flappy); // tilføjer flappy til frame

        /*
        try { // try block sets background image of Jframe
            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("FlappyBirdBackground.jpg")))));

        }catch (IOException ex)
        {

        }
        */



        frame.setResizable(false); // cant resize window
        frame.pack(); // sizes the frame so all its contents are at or above their preffered size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit on window close
        frame.setLocationRelativeTo(null); // center window
        frame.setVisible(true);

        flappy.start();


    }



    @Override
    public void run() {
        int fps = 0; // variable fps
        double timer = System.currentTimeMillis();
        long lastTime = System.nanoTime(); // uses nanotime to be precise
        double ns = 1000000000 / 60; // refresh rate
        double delta = 0;
        while(running)
        {
            long now = System.nanoTime();
            delta+= (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                update();
                render();
                fps++;
                delta--;
            }
            if(System.currentTimeMillis() - timer >= 1000)
            {
                System.out.println("FPS: " +fps);
                fps = 0;
                timer+=1000;
            }
        }

        stop();
    }

    private void render()
    {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null)
        {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.BLACK);


        g.fillRect(0,0,Flappy.WIDTH, Flappy.HEIGHT);
        room.render(g);
        bird.render(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.DIALOG,Font.BOLD,25));
        g.drawString("Score: " + ((int) score), 12,20);
        g.dispose();
        bs.show();

    }

    private void update()
    {
        room.update();
        bird.update();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {   bird.y++;
            bird.isPressed = true;

        }else if(e.getKeyCode() == KeyEvent.VK_UP)
        {   bird.y++;
            bird.isPressed = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {

            bird.isPressed = false;
        }else if(e.getKeyCode() == KeyEvent.VK_UP)
        {

            bird.isPressed = false;
        }
    }

}
