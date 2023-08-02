import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Scene extends Canvas implements Runnable{
    private static Font pauseTextFont = new Font("Souvenir", Font.BOLD, 30);


    private boolean running = false;
    ArrayList<Figure> figureList = new ArrayList<Figure>();
    private Simulation owner;
    Thread thread = new Thread(this);



    public Scene(Simulation simulation){
        owner = simulation;
        setBackground(Color.gray);
        setActions();
        repaint();
        thread.start();
    }

    private void setActions(){

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(isPaused() && e.getButton() == MouseEvent.BUTTON1) tryToAddFigure();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    if(isPaused()) resume();
                    else pause();
                }
                else if( e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    finishAll();
                    owner.dispose();
                }
            }
        });
    }

    public void finishAll(){
        thread.interrupt();
        try {
            thread.join();
        }
        catch (InterruptedException e){}
    }

    public synchronized void pause(){
        running = false;
        repaint();
    }

    public synchronized void resume(){
        running = true;
        notifyAll();
    }

    public boolean isPaused(){ return !running;}


    private void addFigure(Figure newFigure){
        figureList.add(newFigure);
    }

    private void tryToAddFigure(){
        Point p = getMousePosition();
        Figure newFigure = new Disc(new Vector(p.x,p.y), Vector.getRandomVector(), 20);

        if(newFigure.collidesWithWall(owner.getWidth(), owner.getHeight())){
            return;
        }

        for(Figure figure : figureList){
            if(newFigure.collidesWith(figure)) {
                return;
            }
        }

        addFigure(newFigure);
        repaint();
    }

    private void moveFigures(){
        for (Figure figure : figureList) {
            figure.move();
        }
    }

    @Override
    public void run() {
        try {

            while (!thread.isInterrupted()) {
                synchronized (this) {
                    while (running == false) wait();

                    moveFigures();
                    repaint();

                }
                checkCollisions();
                Thread.sleep(100);
            }

        }
        catch (InterruptedException e){}
    }

    @Override
    public synchronized void paint(Graphics g){
        for (Figure figure : figureList ) {
            figure.drawFigure(g);
        }
        if(isPaused()) drawPausedText(g);
    }

    private void drawPausedText(Graphics g){
        Color oldColor = g.getColor();
        g.setColor(Color.BLACK);
        g.setFont(pauseTextFont);

        int textWidth = g.getFontMetrics(pauseTextFont) .stringWidth("PAUZA");

        g.drawString("PAUZA",(getWidth() - textWidth) / 2 , getHeight() / 2);
        g.setColor(oldColor);
    }

    private void checkCollisions(){
        for(int i=0 ; i < figureList.size() -1 ; i++){
            for(int j = i+1; j < figureList.size() ; j++){
                Figure fig1 = figureList.get(i);
                Figure fig2 = figureList.get(j);

                if(fig1.collidesWith(fig2)) Figure.swapGearVectors(fig1,fig2);
            }
        }

        for(Figure figure : figureList){
            figure.collidesWithWall( owner.getWidth(), owner.getHeight());
        }
    }
}
