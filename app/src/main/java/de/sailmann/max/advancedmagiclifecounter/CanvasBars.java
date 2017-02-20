package de.sailmann.max.advancedmagiclifecounter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Admin on 11.01.2017.
 */

public class CanvasBars extends View {


    private Paint paint;
    private int barnumber;

    private double height;

    public CanvasBars(Context context, int barnumber, double height, boolean log) {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);
        this.barnumber = barnumber;
        if (log) {
            this.height = getLog(height);
        } else {
            this.height = height;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRect(canvas);
        invalidate();
    }

    private void drawRect(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        switch (barnumber) {

            case 0:
                paint.setColor(Color.GRAY);
                canvas.drawRect(0, (float) (canvas.getHeight() - canvas.getHeight() * height), canvas.getWidth(), canvas.getHeight(), paint);
                break;
            case 1:
                paint.setColor(Color.YELLOW);
                canvas.drawRect(0, (float) (canvas.getHeight() - canvas.getHeight() * height), canvas.getWidth(), canvas.getHeight(), paint);
                break;
            case 2:
                paint.setColor(Color.BLUE);
                canvas.drawRect(0, (float) (canvas.getHeight() - canvas.getHeight() * height), canvas.getWidth(), canvas.getHeight(), paint);
                break;
            case 3:
                paint.setColor(Color.BLACK);
                canvas.drawRect(0, (float) (canvas.getHeight() - canvas.getHeight() * height), canvas.getWidth(), canvas.getHeight(), paint);
                break;
            case 4:
                paint.setColor(Color.RED);
                canvas.drawRect(0, (float) (canvas.getHeight() - canvas.getHeight() * height), canvas.getWidth(), canvas.getHeight(), paint);
                break;
            case 5:
                paint.setColor(Color.GREEN);
                canvas.drawRect(0, (float) (canvas.getHeight() - canvas.getHeight() * height), canvas.getWidth(), canvas.getHeight(), paint);
                break;
            default:
                break;
        }
    }

    private double getLog(double number) {
        number *= 10;

        if (number > 1) {
            return Math.log10(number);

        } else {
            return 0;
        }
    }


}
