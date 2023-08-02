import java.awt.*;


public abstract class Figure {
    private static final float MOVE_DISTANCE = 3;
    private static final int DEFAULT_RADIUS_VALUE = 20;

    protected Vector posVector;
    protected Vector gearVector;
    protected double r;

    public static double getDistance(Vector vect1,Vector vect2){
        double dx = vect1.getX() - vect2.getX();
        double dy = vect1.getY() - vect2.getY();

        return Math.sqrt( dx*dx + dy*dy );
    }

    public static void swapGearVectors(Figure fig1, Figure fig2){
        Vector tmp = fig1.gearVector;
        fig1.gearVector = fig2.gearVector;
        fig2.gearVector = tmp;
    }

    public Vector getPosVector() {
        return posVector;
    }

    public Vector getGearVector() {
        return gearVector;
    }

    public double getR() {
        return r;
    }

    public Figure(Vector posVector, Vector gearVector){
        this(posVector,gearVector,20);
    }

    public Figure(Vector posVector, Vector gearVector, double r){
        this.gearVector = gearVector.getOrt();
        this.posVector = posVector;
        this.r = r;
    }

    public boolean collidesWithWall(int screenWidth,int screenHeight){
        boolean retVal = false;
        if(posVector.getX() - r <= 0 || posVector.getX() + r >= screenWidth) { gearVector.invertX(); retVal = true; }
        if(posVector.getY() - r <= 0 || posVector.getY() + 35 + r >= screenHeight) { gearVector.invertY(); retVal = true; }
        return retVal;
    }

    public boolean vectorOverlaps(Vector vector){
        return this.r > Math.abs(getDistance(this.posVector,vector));
    }

    public boolean collidesWith(Figure figure){
        return this.r + figure.r >= Math.abs(getDistance(this.posVector,figure.posVector));
    }

    public void move(){
        posVector.setX(posVector.getX() + gearVector.getX() * MOVE_DISTANCE);
        posVector.setY(posVector.getY() + gearVector.getY() * MOVE_DISTANCE);
    }

    public abstract Color getColor();

    public abstract void drawFigure(Graphics g);
}
