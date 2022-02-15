import acm.graphics.GRect;

public class Paddle extends GRect {
    public Paddle(double x, double y, double width, double high){
        super(x,y,width,high);
        setFilled(true);
    }
}
