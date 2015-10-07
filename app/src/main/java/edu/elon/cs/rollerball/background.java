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
 * Created by dpetroni on 10/6/2015.
 */
public class Background {

    private Bitmap bitmap;
    private float width, height;
    private int screenWidth, screenHeight;
    public Background(Context context) {

        //Get the image
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.board);

        //Scale the size
        width = bitmap.getWidth();
        height = bitmap.getHeight();

        //Figure out the screen width
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

    }


    public void doDraw(Canvas canvas) {
        //Draw ball
        canvas.drawBitmap(bitmap,
                null,
                new Rect(0, 0,
                        screenWidth,screenHeight),
                null);
    }


}
