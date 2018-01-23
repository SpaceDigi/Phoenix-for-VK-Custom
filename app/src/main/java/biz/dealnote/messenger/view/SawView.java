package biz.dealnote.messenger.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import biz.dealnote.messenger.R;
import biz.dealnote.messenger.util.Utils;

/**
 * Project: MobileBanking
 * Created by ruslan.kolbasa on 22-Apr-16.
 * View like saw ^^^^^^^^^^^^^^^^^^^^
 */
public class SawView extends View {

    private float mToothPrefWidht;
    private int mBackgroundColor;

    public SawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        initializeAttributes(context, attrs);
    }

    private void initializeAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SawView);
        try {
            mToothPrefWidht = ta.getDimensionPixelSize(R.styleable.SawView_sawToothPrefWidht, pixelOf(8));
            mBackgroundColor = ta.getColor(R.styleable.SawView_sawBackground, Color.GREEN);
        } finally {
            ta.recycle();
        }
    }

    private int pixelOf(int dp){
        return (int) Utils.dpToPx(dp, getContext());
    }

    private static final Paint FILL_PAINT = new Paint();

    static {
        FILL_PAINT.setStyle(Paint.Style.FILL);
        FILL_PAINT.setDither(true);
        FILL_PAINT.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count = (int) ((float) canvas.getWidth() / mToothPrefWidht);

        if (count % 2 == 1) {
            count = count + 1;
        }

        int startH = 0;
        int endH = canvas.getHeight();

        FILL_PAINT.setColor(mBackgroundColor);

        float realToothWidth = (float) canvas.getWidth() / (float) count;

        float offset = 0;

        int side = SIDE_DOWN;
        for (int i = 0; i < count; i++) {

            if (side == SIDE_DOWN) {
                PATH.reset();
                PATH.moveTo(offset, startH);
                PATH.lineTo(offset + realToothWidth, startH);
                PATH.lineTo(offset, endH);
                PATH.lineTo(offset, startH);

                canvas.drawPath(PATH, FILL_PAINT);
                //canvas.drawLine(offset, endH, offset + realToothWidth, startH, STROKE_PAINT);
            } else {
                PATH.reset();
                PATH.moveTo(offset, startH);
                PATH.lineTo(offset + realToothWidth, startH);
                PATH.lineTo(offset + realToothWidth, endH);
                PATH.lineTo(offset, startH);
                canvas.drawPath(PATH, FILL_PAINT);
                //canvas.drawLine(offset, startH, offset + realToothWidth, endH, STROKE_PAINT);
            }

            offset = offset + realToothWidth;
            side = side == SIDE_DOWN ? SIDE_UP : SIDE_DOWN;
        }
    }

    private static final Path PATH = new Path();

    private static final int SIDE_DOWN = 1;
    private static final int SIDE_UP = 2;
}
