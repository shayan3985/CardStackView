package com.shynline.android.cardstackview.internal;

import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import com.shynline.android.cardstackview.R;
import com.shynline.android.cardstackview.SwipeDirection;

public class CardContainerView extends FrameLayout {

    private CardStackOption option;

    private float viewOriginX = 0f;
    private float viewOriginY = 0f;
    private float motionOriginX = 0f;
    private float motionOriginY = 0f;
    private boolean isDragging = false;
    private boolean isDraggable = true;
    private final int THRESHOLD = 5;

    private ViewGroup contentContainer = null;
    private ViewGroup overlayContainer = null;
    private View leftOverlayView = null;
    private View rightOverlayView = null;
    private View bottomOverlayView = null;
    private View topOverlayView = null;

    private ContainerEventListener containerEventListener = null;
    private GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (containerEventListener != null) {
                containerEventListener.onContainerClicked();
            }
            return true;
        }
    };
    private GestureDetector gestureDetector = new GestureDetector(getContext(), gestureListener);

    public CardContainerView(Context context) {
        this(context, null);
    }

    public CardContainerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
//        THRESHOLD = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        inflate(getContext(), R.layout.card_frame, this);
        contentContainer = findViewById(R.id.card_frame_content_container);
        overlayContainer = findViewById(R.id.card_frame_overlay_container);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        handleTouchEvent(ev);

        return true;
    }

    private boolean intercepted = false;
    private float lastTouchPointX, lastTouchPointY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        final int action = event.getAction();
        if ((action == MotionEvent.ACTION_MOVE) && (intercepted)) {
            return true;
        }


        if (super.onInterceptTouchEvent(event))
            return true;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchPointX = event.getX();
                lastTouchPointY = event.getY();
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(lastTouchPointX - event.getX()) > THRESHOLD || Math.abs(lastTouchPointY - event.getY()) > THRESHOLD) {
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:

                break;
        }
        return intercepted;
    }

    private boolean handleTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        if (!option.isSwipeEnabled || !isDraggable) {
            return true;
        }

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                handleActionDown(event);
                try {
                    getParent().getParent().requestDisallowInterceptTouchEvent(true);
                }catch (Exception e){

                }
                break;
            case MotionEvent.ACTION_UP:
                handleActionUp(event);
                try {
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                }catch (Exception e){

                }
                break;
            case MotionEvent.ACTION_CANCEL:
                try {
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                }catch (Exception e){

                }
                break;
            case MotionEvent.ACTION_MOVE:
                handleActionMove(event);
                break;
        }

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    private void handleActionDown(MotionEvent event) {
        motionOriginX = event.getRawX();
        motionOriginY = event.getRawY();
    }

    private void handleActionUp(MotionEvent event) {
        if (isDragging) {
            isDragging = false;

            float motionCurrentX = event.getRawX();
            float motionCurrentY = event.getRawY();

            Point point = Util.getTargetPoint(motionOriginX, motionOriginY, motionCurrentX, motionCurrentY);
            Quadrant quadrant = Util.getQuadrant(motionOriginX, motionOriginY, motionCurrentX, motionCurrentY);
            double radian = Util.getRadian(motionOriginX, motionOriginY, motionCurrentX, motionCurrentY);
            double degree = 0f;
            SwipeDirection direction = null;
            switch (quadrant) {
                case TopLeft:
                    degree = Math.toDegrees(radian);
                    degree = 180 - degree;
                    radian = Math.toRadians(degree);
                    if (Math.cos(radian) < -0.5) {
                        direction = SwipeDirection.Left;
                    } else {
                        direction = SwipeDirection.Top;
                    }
                    break;
                case TopRight:
                    degree = Math.toDegrees(radian);
                    radian = Math.toRadians(degree);
                    if (Math.cos(radian) < 0.5) {
                        direction = SwipeDirection.Top;
                    } else {
                        direction = SwipeDirection.Right;
                    }
                    break;
                case BottomLeft:
                    degree = Math.toDegrees(radian);
                    degree = 180 + degree;
                    radian = Math.toRadians(degree);
                    if (Math.cos(radian) < -0.5) {
                        direction = SwipeDirection.Left;
                    } else {
                        direction = SwipeDirection.Bottom;
                    }
                    break;
                case BottomRight:
                    degree = Math.toDegrees(radian);
                    degree = 360 - degree;
                    radian = Math.toRadians(degree);
                    if (Math.cos(radian) < 0.5) {
                        direction = SwipeDirection.Bottom;
                    } else {
                        direction = SwipeDirection.Right;
                    }
                    break;
            }

            float percent = 0f;
            if (direction == SwipeDirection.Left || direction == SwipeDirection.Right) {
                percent = getPercentX();
            } else {
                percent = getPercentY();
            }

            if (Math.abs(percent) > option.swipeThreshold) {
                if (option.swipeDirection.contains(direction)) {
                    if (containerEventListener != null) {
                        containerEventListener.onContainerSwiped(point, direction);
                    }
                } else {
                    moveToOrigin();
                    if (containerEventListener != null) {
                        containerEventListener.onContainerMovedToOrigin();
                    }
                }
            } else {
                moveToOrigin();
                if (containerEventListener != null) {
                    containerEventListener.onContainerMovedToOrigin();
                }
            }
        }

        motionOriginX = event.getRawX();
        motionOriginY = event.getRawY();
    }

    private void handleActionMove(MotionEvent event) {
        isDragging = true;

        updateTranslation(event);
        updateRotation();
        updateAlpha();

        if (containerEventListener != null) {
            containerEventListener.onContainerDragging(getPercentX(), getPercentY());
        }
    }

    private void updateTranslation(MotionEvent event) {
        setTranslationX(viewOriginX + event.getRawX() - motionOriginX);
        setTranslationY(viewOriginY + event.getRawY() - motionOriginY);

    }

    private void updateRotation() {
        setRotation(getPercentX() * 20);
    }

    private void updateAlpha() {
        float percentX = getPercentX();
        float percentY = getPercentY();

        if (option.swipeDirection == SwipeDirection.HORIZONTAL) {
            showHorizontalOverlay(percentX);
        } else if (option.swipeDirection == SwipeDirection.VERTICAL) {
            showVerticalOverlay(percentY);
        } else if (option.swipeDirection == SwipeDirection.FREEDOM_NO_BOTTOM) {
            if (Math.abs(percentX) < Math.abs(percentY) && percentY < 0) {
                showTopOverlay();
                setOverlayAlpha(Math.abs(percentY));
            } else {
                showHorizontalOverlay(percentX);
            }
        } else if (option.swipeDirection == SwipeDirection.FREEDOM) {
            if (Math.abs(percentX) > Math.abs(percentY)) {
                showHorizontalOverlay(percentX);
            } else {
                showVerticalOverlay(percentY);
            }
        } else {
            if (Math.abs(percentX) > Math.abs(percentY)) {
                if (percentX < 0) {
                    showLeftOverlay();
                } else {
                    showRightOverlay();
                }
                setOverlayAlpha(Math.abs(percentX));
            } else {
                if (percentY < 0) {
                    showTopOverlay();
                } else {
                    showBottomOverlay();
                }
                setOverlayAlpha(Math.abs(percentY));
            }
        }
    }

    private void showHorizontalOverlay(float percentX) {
        if (percentX < 0) {
            showLeftOverlay();
        } else {
            showRightOverlay();
        }
        setOverlayAlpha(Math.abs(percentX));
    }

    private void showVerticalOverlay(float percentY) {
        if (percentY < 0) {
            showTopOverlay();
        } else {
            showBottomOverlay();
        }
        setOverlayAlpha(Math.abs(percentY));
    }

    private void moveToOrigin() {
        animate().translationX(viewOriginX)
                .translationY(viewOriginY)
                .setDuration(300L)
                .setInterpolator(new OvershootInterpolator(1.0f))
                .setListener(null)
                .start();
    }

    public void setContainerEventListener(ContainerEventListener listener) {
        this.containerEventListener = listener;
        viewOriginX = getTranslationX();
        viewOriginY = getTranslationY();
    }

    public void setCardStackOption(CardStackOption option) {
        this.option = option;
    }

    public void setDraggable(boolean isDraggable) {
        this.isDraggable = isDraggable;
    }

    public void reset() {
        contentContainer.setAlpha(1f);
        overlayContainer.setAlpha(0f);
    }

    public ViewGroup getContentContainer() {
        return contentContainer;
    }

    public ViewGroup getOverlayContainer() {
        return overlayContainer;
    }

    public void setOverlay(int left, int right, int bottom, int top) {
        if (leftOverlayView != null) {
            overlayContainer.removeView(leftOverlayView);
        }
        if (left != 0) {
            leftOverlayView = LayoutInflater.from(getContext()).inflate(left, overlayContainer, false);
            overlayContainer.addView(leftOverlayView);
            leftOverlayView.setAlpha(0f);
        }

        if (rightOverlayView != null) {
            overlayContainer.removeView(rightOverlayView);
        }
        if (right != 0) {
            rightOverlayView = LayoutInflater.from(getContext()).inflate(right, overlayContainer, false);
            overlayContainer.addView(rightOverlayView);
            rightOverlayView.setAlpha(0f);
        }

        if (bottomOverlayView != null) {
            overlayContainer.removeView(bottomOverlayView);
        }
        if (bottom != 0) {
            bottomOverlayView = LayoutInflater.from(getContext()).inflate(bottom, overlayContainer, false);
            overlayContainer.addView(bottomOverlayView);
            bottomOverlayView.setAlpha(0f);
        }

        if (topOverlayView != null) {
            overlayContainer.removeView(topOverlayView);
        }
        if (top != 0) {
            topOverlayView = LayoutInflater.from(getContext()).inflate(top, overlayContainer, false);
            overlayContainer.addView(topOverlayView);
            topOverlayView.setAlpha(0f);
        }
    }

    public void setOverlayAlpha(AnimatorSet overlayAnimatorSet) {
        if (overlayAnimatorSet != null) {
            overlayAnimatorSet.start();
        }
    }

    public void setOverlayAlpha(float alpha) {
        ViewCompat.setAlpha(overlayContainer, alpha);
    }

    public void showLeftOverlay() {
        if (leftOverlayView != null) {
            leftOverlayView.setAlpha(1f);
        }
        if (rightOverlayView != null) {
            rightOverlayView.setAlpha(0f);
        }
        if (bottomOverlayView != null) {
            bottomOverlayView.setAlpha(0f);
        }
        if (topOverlayView != null) {
            topOverlayView.setAlpha(0f);
        }
    }

    public void showRightOverlay() {
        if (leftOverlayView != null) {
            leftOverlayView.setAlpha(0f);
        }

        if (bottomOverlayView != null) {
            bottomOverlayView.setAlpha(0f);
        }

        if (topOverlayView != null) {
            topOverlayView.setAlpha(0f);
        }

        if (rightOverlayView != null) {
            rightOverlayView.setAlpha(1f);
        }
    }

    public void showBottomOverlay() {
        if (leftOverlayView != null) {
            leftOverlayView.setAlpha(0f);
        }

        if (bottomOverlayView != null) {
            bottomOverlayView.setAlpha(1f);
        }

        if (topOverlayView != null) {
            topOverlayView.setAlpha(0f);
        }

        if (rightOverlayView != null) {
            rightOverlayView.setAlpha(0f);
        }
    }

    public void showTopOverlay() {
        if (leftOverlayView != null) {
            leftOverlayView.setAlpha(0f);
        }

        if (bottomOverlayView != null) {
            bottomOverlayView.setAlpha(0f);
        }

        if (topOverlayView != null) {
            topOverlayView.setAlpha(1f);
        }

        if (rightOverlayView != null) {
            rightOverlayView.setAlpha(0f);
        }
    }

    public float getViewOriginX() {
        return viewOriginX;
    }

    public float getViewOriginY() {
        return viewOriginY;
    }

    public float getPercentX() {
        float percent = 2f * (getTranslationX() - viewOriginX) / getWidth();
        if (percent > 1) {
            percent = 1;
        }
        if (percent < -1) {
            percent = -1;
        }
        return percent;
    }

    public float getPercentY() {
        float percent = 2f * (getTranslationY() - viewOriginY) / getHeight();
        if (percent > 1) {
            percent = 1;
        }
        if (percent < -1) {
            percent = -1;
        }
        return percent;
    }

    public interface ContainerEventListener {
        void onContainerDragging(float percentX, float percentY);

        void onContainerSwiped(Point point, SwipeDirection direction);

        void onContainerMovedToOrigin();

        void onContainerClicked();
    }

}
