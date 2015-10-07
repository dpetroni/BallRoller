package edu.elon.cs.rollerball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.method.BaseKeyListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Jack on 10/6/2015.
 */
public class GameLoopView extends SurfaceView implements SurfaceHolder.Callback{

    private GameLoopThread thread;
    private SurfaceHolder surfaceHolder;
    private Context context;
    //Location for the ball
    private float ballX, ballY;





    public GameLoopView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // remember the context for finding resources
        this.context = context;

        // want to know when the surface changes
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        // game loop thread
        thread = new GameLoopThread();
    }

    // SurfaceHolder.Callback methods:
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // thread exists, but is in terminated state
        if (thread.getState() == Thread.State.TERMINATED) {
            thread = new GameLoopThread();
        }

        // start the game loop
        thread.setIsRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        thread.setIsRunning(false);

        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }


    public boolean onTouchEvent(MotionEvent event){
        ballX = event.getX();
        ballY = event.getY();
        return true;
    }


    private class GameLoopThread extends Thread {

        private boolean isRunning = false;
        private long lastTime;

        //the ball
        private Ball ball;
        private Background background;

        //the spot
        private Spot spot;

        // frames per second calculation
        private int frames;
        private long nextUpdate;

        public GameLoopThread() {
            background = new Background(context);
            ball = new Ball(context);
            spot = new Spot(context);

            ballX = ball.x;
            ballY = ball.y;

        }

        public void setIsRunning(boolean isRunning) {
            this.isRunning = isRunning;
        }

        // the main loop
        @Override
        public void run() {

            lastTime = System.currentTimeMillis();

            while (isRunning) {

                // grab hold of the canvas
                Canvas canvas = surfaceHolder.lockCanvas();
                if (canvas == null) {
                    // trouble -- exit nicely
                    isRunning = false;
                    continue;
                }

                synchronized (surfaceHolder) {

                    // compute how much time since last time around
                    long now = System.currentTimeMillis();
                    double elapsed = (now - lastTime) / 1000.0;
                    lastTime = now;

                    // update/draw
//                    doUpdate(elapsed);
                    doDraw(canvas);

                    //updateFPS(now);
                }

                // release the canvas
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

        // an approximate frames per second calculation
        private void updateFPS(long now) {
            float fps = 0.0f;
            ++frames;
            float overtime = now - nextUpdate;
            if (overtime > 0) {
                fps = frames / (1 + overtime/1000.0f);
                frames = 0;
                nextUpdate = System.currentTimeMillis() + 1000;
                System.out.println("FPS: " + (int) fps);
            }
        }

        /* THE GAME */

        // move all objects in the game
//        private void doUpdate(double elapsed) {
//
//            ball.doUpdate(elapsed, ballX, ballY);
//        }

        // draw all objects in the game
        private void doDraw(Canvas canvas) {

            // draw the background and ball

            background.doDraw(canvas);
            spot.doDraw(canvas);
            ball.doDraw(canvas);


        }
    }




}
