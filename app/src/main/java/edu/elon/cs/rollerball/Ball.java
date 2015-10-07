package edu.elon.cs.rollerball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Jack on 10/6/2015.
 */
public class Ball {

    protected float x, y;
    private float width, height;
    private Bitmap bitmap;

    private int screenWidth, screenHeight;

    private final float SCALE = 0.1f;

    public Ball(Context context) {

        //Get the image
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);

        //Scale the size
        width = bitmap.getWidth() * (1/3);
        height = bitmap.getHeight() * (1/3);

        //Figure out the screen width
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        //start in random spot
        x = screenHeight/2;
        y = screenWidth/2;
    }

    public void doDraw(Canvas canvas) {
        //Draw ball
        canvas.drawBitmap(bitmap,
                null,
                new Rect((int) (x - width/2), (int) (y- height/2),
                        (int) (x + width/2), (int) (y + height/2)),
                null);
    }

    public void doUpdate(double elapsed, float ballX, float ballY) {

    }

}
