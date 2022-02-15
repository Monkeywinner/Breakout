import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.program.GraphicsProgram;
import java.awt.*;
import java.awt.event.MouseEvent;
import svu.csc213.Dialog;

public class Breakout extends GraphicsProgram {



    private Ball ball;
    private Paddle paddle;
    private int numBricksInRow,score,lives,bricksOnScreen;
    private GLabel scoreLabel,livesLabel;

    @Override
    public void init(){

        numBricksInRow = (int) (getWidth() / (Brick.WIDTH + 5.0));
        bricksOnScreen=0;

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < numBricksInRow; col++) {

                double brickX = 10 + col * (Brick.WIDTH + 5);
                double brickY = Brick.HEIGHT + row * (Brick.HEIGHT + 5);

                Brick brick = new Brick(brickX, brickY, row);
                add(brick);
                bricksOnScreen+=1;
            }
        }

        ball = new Ball(getWidth()/2, 350, 10, this.getGCanvas());
        add(ball);
        lives=3;
        paddle = new Paddle(230, 430, 50 ,10);
        add(paddle);
        scoreLabel = new GLabel("Score"+score);
        add(scoreLabel,10,10);
        livesLabel = new GLabel("Lives"+lives);
        add(livesLabel,100,10);

    }

    @Override
    public void run(){
        addMouseListeners();
        waitForClick();
        gameLoop();
    }

    @Override
    public void mouseMoved(MouseEvent me){
        // make sure that the paddle doesn't go offscreen
        if((me.getX() < getWidth() - paddle.getWidth()/2)&&(me.getX() > paddle.getWidth() / 2)){
            paddle.setLocation(me.getX() - paddle.getWidth()/2, paddle.getY());
        }
    }
    //runs the main loop for the game
    private void gameLoop(){
        while(true){
            // move the ball
            ball.handleMove();

            // handle collisions
            handleCollisions();

            // handle losing the ball
            if(ball.lost){
                handleLoss();
            }
            if(bricksOnScreen==0){
                win();
            }

            pause(5);
        }
    }

    private void handleCollisions(){
        // obj can store what we hit
        GObject obj = null;

        // check to see if the ball is about to hit something

        if(obj == null){
            // check the top right corner
            obj = this.getElementAt(ball.getX()+ball.getWidth(), ball.getY());
        }

        if(obj == null){
            // check the top left corner
            obj = this.getElementAt(ball.getX(), ball.getY());
        }

        //check the bottom right corner for collision
        if (obj == null) {
            obj = this.getElementAt(ball.getX() + ball.getWidth(), ball.getY() + ball.getHeight());
        }
        //check the bottom left corner for collision
        if (obj == null) {
            obj = this.getElementAt(ball.getX(), ball.getY() + ball.getHeight());
        }

        // see if we hit something
        if(obj != null){

            // lets see what we hit!
            if(obj instanceof Paddle){

                if(ball.getX() < (paddle.getX() + (paddle.getWidth() * .2))){
                    // did I hit the left side of the paddle?
                    ball.bounceLeft();
                } else if(ball.getX() > (paddle.getX() + (paddle.getWidth() * .8))) {
                    // did I hit the right side of the paddle?
                    ball.bounceRight();
                } else {
                    // did I hit the middle of the paddle?
                    ball.bounce();
                }

            }


            if(obj instanceof Brick){
                // bounce the ball
                ball.bounce();
                // destroy the brick
                ((Brick) obj).hit();
                score+=1;
                scoreLabel.setLabel("Score "+score);
                if(((Brick) obj).hits ==0){
                    this.remove(obj);
                    bricksOnScreen-=1;
                }
            }

        }

        // if by the end of the method obj is still null, we hit nothing
    }

    private void handleLoss(){
        ball.lost = false;
        lives-=1;
        livesLabel.setLabel("Lives "+lives);
        if(lives==0){
            Dialog.showMessage("You lost. Your score is "+score);
            System.exit(0);
        }
        reset();
    }

    private void reset(){
        ball.setLocation(getWidth()/2, 350);
        paddle.setLocation(230, 430);
        waitForClick();
    }
    private void win(){
        Dialog.showMessage("You win. Your score is "+score);
    }

    public static void main(String[] args) {
        new Breakout().start();
    }

}