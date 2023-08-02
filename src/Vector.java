public class Vector {
    private double x;
    private double y;

    public Vector(double posX, double posY) {
        x = posX;
        y = posY;
    }

    public Vector getOrt() {
        double intensity = Math.sqrt(x * x + y * y);
        return new Vector(x/intensity,y/intensity);
    }

    public static Vector getRandomVector(){
        double newX;
        double newY;

        do {
            newX = Math.random() * 2 - 1;
            newY = Math.random() * 2 - 1;
        } while(newX == 0  && newY == 0);

        return new Vector(newX,newY);
    }

    public void invertX(){ x = -x; }

    public  void invertY(){ y = -y; }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

}
