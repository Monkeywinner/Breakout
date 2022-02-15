import acm.graphics.GRect;
import java.awt.*;

public class Brick extends GRect {

    public static final int WIDTH = 44;
    public static final int HEIGHT = 20;
    public int hits;
    private Color[] rowColors = {Color.BLACK,Color.CYAN,Color.GREEN,Color.YELLOW,Color.ORANGE,Color.RED};

    public Brick(double x, double y,int row){
        super(x,y,WIDTH,HEIGHT);
        this.setFilled(true);
        switch (row){
            case 9,8->{
                hits=1;
            }
            case 7,6->{
                hits=2;
            }
            case 5,4->{
                hits=3;
            }
            case 3,2->{
                hits=4;
            }
            case 1,0->{
                hits=5;
            }
        }
        this.setFillColor(rowColors[hits]);
    }
    public void hit(){
        hits -=1;
        this.setFillColor(rowColors[hits]);
    }
}
