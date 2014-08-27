package com.kuxinwei.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Path.FillType;
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
	private RectF mRectF = new RectF();
	private RectF mTopRectF = new RectF();
	private RectF mBottomRectF = new RectF();
	private PointF mCenterPoint = new PointF();
	private float degrees = 0.0f;
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
		float centerX = width * 0.5f;
		float centerY = height * 0.5f;
		mCenterPoint.set(centerX, centerY);
		float halfW = mBorderWidth * 0.5f;
		float forthW = centerX * 0.5f;
		mRectF.set(0 + halfW, 0 + halfW, width - halfW, height - halfW);
		mTopRectF.set(forthW + halfW, 0 + halfW, centerX + forthW - halfW,
				centerY);
		mBottomRectF.set(forthW + halfW, centerY, centerX + forthW - halfW,
				height - halfW);
		mBlackPath.setFillType(FillType.EVEN_ODD);
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
		mBlackPath.reset();
		mPath.addOval(mRectF, Direction.CCW);
		mBlackPath.addArc(mTopRectF, -90, 180);
		mBlackPath.addArc(mRectF, 90, 180);
		mBlackPath.addArc(mBottomRectF, 90, 180);
		mBlackPath.addCircle(mBottomRectF.centerX(), mBottomRectF.centerY(),
				mBottomRectF.width() * 0.16f, Direction.CCW); // 绘制上面的黑色小圆点
		mBlackPath.addCircle(mTopRectF.centerX(), mTopRectF.centerY(),
				mTopRectF.width() * 0.16f, Direction.CCW); // 再画一遍，以此来使其透出小白点
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.rotate(degrees, mCenterPoint.x, mCenterPoint.y);
		canvas.drawPath(mPath, mBorderPaint);
		canvas.drawPath(mPath, mWhitePaint);
		canvas.drawPath(mBlackPath, mPaint);
		increate();
	}

	private void increate() {
		degrees += 4;
		invalidate();
	}
}
