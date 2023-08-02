import java.awt.*;

public class Disc extends Figure {
    private final static double deltaAngle = 2 * Math.PI / 8;

    public Disc(Vector posVector,Vector gearVector){
        super(posVector,gearVector);
    }

    public Disc(Vector posVector,Vector gearVector,double r){
        super(posVector,gearVector,r);
    }

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    @Override
    public void  drawFigure(Graphics g) {
        double angle = 0;
        double x = posVector.getX();
        double y = posVector.getY();

        Polygon octagon = new Polygon();

        for(int i=0;i<8;i++){
            octagon.addPoint((int) (x + r * Math.cos(angle)),(int) (y + r * Math.sin(angle)) );
            angle += deltaAngle;
        }

        Color oldColor = g.getColor();

        g.setColor(Color.BLUE);
        g.fillPolygon(octagon);

        g.setColor(oldColor);
    }


}
