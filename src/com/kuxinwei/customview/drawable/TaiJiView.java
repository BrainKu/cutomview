package com.kuxinwei.customview.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class TaiJiView extends View {

	private Paint mBorderPaint;
	private Paint mPaint;
	private Paint mWhitePaint;
	private Path mPath = new Path();
	private Path mBlackPath = new Path();
	private Path mWhitePath = new Path();
	private Path mBlackPointPath = new Path();
	private RectF mRectF = new RectF();
	private RectF mTopRectF = new RectF();
	private RectF mBottomRectF = new RectF();
	private PointF mCenterPoint = new PointF();

	private int mStrokeWidth = 4;
	private int mBorderWidth = 4;

	private float height = 200;
	private float width = 200;

	public TaiJiView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		height = h;
		width = w;
		float centerX = w / 2;
		float centerY = h / 2;
		mCenterPoint.set(centerX, centerY);
		float halfW = mBorderWidth / 2.0f;
		mRectF.set(0 + halfW, 0 + halfW, width - halfW, height - halfW);
		float forthW = width / 4.0f;
		mTopRectF.set(forthW + halfW, 0 + halfW, centerX + forthW - halfW,
				centerY);
		mBottomRectF.set(forthW + halfW, centerY, centerX + forthW - halfW,
				height - halfW);
		initPath();
	}

	private void init() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStrokeWidth(mStrokeWidth);
		mPaint.setColor(Color.BLACK);
		mPaint.setStyle(Style.FILL);
		mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mBorderPaint.setStrokeWidth(mBorderWidth);
		mBorderPaint.setColor(Color.BLACK);
		mBorderPaint.setStyle(Style.STROKE);
		mWhitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mWhitePaint.setStrokeWidth(mStrokeWidth);
		mWhitePaint.setColor(Color.WHITE);
		mWhitePaint.setStyle(Style.FILL);
		initPath();
	}

	private void initPath() {
		mWhitePath.reset();
		mBlackPointPath.reset();
		mPath.addOval(mRectF, Direction.CCW);
		mBlackPath.addArc(mTopRectF, -90, 180);
		mBlackPath.addArc(mRectF, 90, 180);
		mWhitePath.addCircle(mTopRectF.centerX(), mTopRectF.centerY(),
				mTopRectF.height() / 10, Direction.CCW);
		mWhitePath.addArc(mBottomRectF, 90, 180);
		mBlackPointPath.addCircle(mBottomRectF.centerX(),
				mBottomRectF.centerY(), mBottomRectF.height() / 10,
				Direction.CCW);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawPath(mPath, mBorderPaint);
		canvas.drawPath(mPath, mWhitePaint);
		canvas.drawPath(mBlackPath, mPaint);
		canvas.drawPath(mWhitePath, mWhitePaint);
		canvas.drawPath(mBlackPointPath, mPaint);
	}
}
