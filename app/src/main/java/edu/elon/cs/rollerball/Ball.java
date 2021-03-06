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
    private int width, height;
    private Bitmap bitmap;

    private int screenWidth, screenHeight;

    private final float SCALE = 0.1f;

    public Ball(Context context) {

        //Get the image
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);

        //Scale the size
        width =  (bitmap.getWidth()/3);
        height = (bitmap.getHeight()/3);

        //Figure out the screen width
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        //start in random spot
        x = screenWidth/2;
        y = screenHeight/2;
    }

    public void doDraw(Canvas canvas) {
        //Draw ball
        canvas.drawBitmap(bitmap,
                null,
                new Rect((int)x, (int)y,
                        (int) (x+width),(int) (y+height)),
                null);
    }




}
