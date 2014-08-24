/**
 * 创建时间：2014-8-10 下午8:48:58
 * @author kuxinwei
 * @since 1.0
 * @version 1.0<br>
 */
package com.kuxinwei.customview.drawable;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.Property;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * 一种不断重复的圆饼，每次转都是在上次的底色上完成
 */
public class RecurColorProgressDrawable extends Drawable implements Animatable {

	/**
	 * linear interpolator
	 */
	private static final Interpolator SWEEP_INTERPOLATOR = new AccelerateDecelerateInterpolator();
	/**
	 * duration
	 */
	private static final int SWEEP_ANIMATION_DURATION = 1000;

	private final RectF rBounds = new RectF();

	private ObjectAnimator mSweepObjectAnimator;

	private static final int[] COLOR_ARRAY = new int[] { Color.BLACK,
			Color.GREEN, Color.LTGRAY, Color.MAGENTA, Color.RED };
	private static final int COLOR_COUNT = COLOR_ARRAY.length;

	private int mColorIndex = 0;
	private float mCurrentSweepAngle = 0;
	private float mBorderWidth;
	private Paint mPaint;
	private Paint mBasePaint;

	public RecurColorProgressDrawable(float boardWidth) {
		mPaint = new Paint();
		mBasePaint = new Paint();
		mPaint.setAntiAlias(true);
		mBasePaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mBasePaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(Color.BLACK);
		mBasePaint.setColor(Color.BLACK);
		mPaint.setStrokeWidth(boardWidth);
		mBasePaint.setStrokeWidth(boardWidth - 7);
		mPaint.setAlpha(100);
		mBasePaint.setAlpha(200);
		mBorderWidth = boardWidth;
		setupAnimation();
	}

	private float getCurrentSweepAngle() {
		return mCurrentSweepAngle;
	}

	private void setCurrentSweepAngle(float arc) {
		this.mCurrentSweepAngle = arc;
		invalidateSelf();
	}

	private Property<RecurColorProgressDrawable, Float> mSweepProperty = new Property<RecurColorProgressDrawable, Float>(
			Float.class, "arc") {

		@Override
		public Float get(RecurColorProgressDrawable object) {
			return object.getCurrentSweepAngle();
		}

		public void set(RecurColorProgressDrawable object, Float value) {
			object.setCurrentSweepAngle(value);
		};
	};

	private void setupAnimation() {
		mSweepObjectAnimator = ObjectAnimator.ofFloat(this, mSweepProperty,
				360f);
		mSweepObjectAnimator.setInterpolator(SWEEP_INTERPOLATOR);
		mSweepObjectAnimator.setDuration(SWEEP_ANIMATION_DURATION);
		mSweepObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);
		mSweepObjectAnimator.setRepeatMode(ValueAnimator.RESTART);
		mSweepObjectAnimator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				changePaintColorIndex();
			}

			@Override
			public void onAnimationEnd(Animator animation) {
			}

			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
	}

	protected void changePaintColorIndex() {
//		mBasePaint.setColor(COLOR_ARRAY[mColorIndex]);
		mColorIndex = (++mColorIndex) % COLOR_COUNT;
		mPaint.setColor(COLOR_ARRAY[mColorIndex]);
	}

	@Override
	public void draw(Canvas canvas) {
		float startAngle = 0;
		float sweepAngle = mCurrentSweepAngle;
		// 直接使用画笔画出圆的底色
		canvas.drawArc(rBounds, 0, 360, false, mBasePaint);
		canvas.drawArc(rBounds, startAngle, sweepAngle, false, mPaint);
	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		super.onBoundsChange(bounds);
		rBounds.left = bounds.left + mBorderWidth / 2f + 0.5f;
		rBounds.right = bounds.right - mBorderWidth / 2f - 0.5f;
		rBounds.top = bounds.top + mBorderWidth / 2f + 0.5f;
		rBounds.bottom = bounds.bottom - mBorderWidth / 2f - 0.5f;
	}

	@Override
	public void setAlpha(int alpha) {
		mPaint.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		mPaint.setColorFilter(cf);
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSPARENT;
	}

	private boolean running = false;

	@Override
	public void start() {
		if (running)
			return;
		running = true;
		mSweepObjectAnimator.start();
		invalidateSelf();
	}

	@Override
	public void stop() {
		if (!isRunning())
			return;
		mSweepObjectAnimator.cancel();
		invalidateSelf();
	}

	@Override
	public boolean isRunning() {
		return running;
	}

}
