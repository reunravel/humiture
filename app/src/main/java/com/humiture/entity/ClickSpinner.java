package com.humiture.entity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Spinner;

@SuppressLint("AppCompatCustomView")
public class ClickSpinner extends Spinner {

    final Point touchedPoint = new Point();
    private boolean isMoved = false;
    private OnClickMyListener onClickMyListener;

    public ClickSpinner(Context context) {
        super(context);
    }

    public ClickSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ClickSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchedPoint.x = x;
                touchedPoint.y = y;
                break;
            case MotionEvent.ACTION_MOVE:
                isMoved = true;
                break;
            case MotionEvent.ACTION_UP:
                if (isMoved) {
                    if (y - touchedPoint.y < 20 && touchedPoint.y - y < 20) {
                        onClick();
                    }
                    isMoved = false;
                } else {
                    onClick();
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void onClick() {
        if (onClickMyListener != null && isEnabled()) {
            onClickMyListener.onClick();
        }
    }

    public void setOnClickMyListener(OnClickMyListener onClickMyListener) {
        this.onClickMyListener = onClickMyListener;
    }

    public interface OnClickMyListener {
        void onClick();
    }
}
