/**
 * 创建时间：2014-8-25 上午8:44:42
 * @author kuxinwei
 * @since 1.0
 * @version 1.0<br>
 */
package com.kuxinwei.customview.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Path.FillType;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class BWClockView extends View {
	float mStrokeWitdht = 2;
	final static String CLOCK_TEXT = "壹贰叄肆伍陸柒捌玖拾拾\n壹拾\n贰";
	int mTextSize = 30;
	int color = Color.BLACK;
	Paint mTextPaint;
	Paint mFillPaint;
	Paint mWhitePaint;
	Path mCoverPath = new Path();
	Path mICoverPath;
	RectF mRect = new RectF();
	Rect mTextRect = new Rect();
	PointF mCenterPoint = new PointF();

	List<PointF> mPointFs = new ArrayList<PointF>();
	PorterDuffXfermode mPorterDuffXfermode = new PorterDuffXfermode(Mode.XOR);

	public BWClockView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mWhitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mFillPaint.setColor(Color.BLACK);
		mWhitePaint.setColor(Color.WHITE);
		mTextPaint.setColor(Color.WHITE);
		mFillPaint.setStyle(Style.FILL);
		mWhitePaint.setStyle(Style.FILL);

		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setTextAlign(Align.CENTER);
		mTextPaint.setFakeBoldText(true);
		mTextPaint.getTextBounds(CLOCK_TEXT, 0, 1, mTextRect);
		mTextPaint.setXfermode(mPorterDuffXfermode);
		mFillPaint.setXfermode(mPorterDuffXfermode);
		// mWhitePaint.setXfermode(mPorterDuffXfermode);
		initPath();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		float size = Math.min(w, h);
		mRect.set(0 + 1, 0 + 1, size - 1, size - 1);
		mCenterPoint.set(size * 0.5f, size * 0.5f);
		mTextSize = (int) (size / 12);
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.getTextBounds(CLOCK_TEXT, 0, 1, mTextRect);
		initPath();
	}

	private void initPath() {
		mCoverPath.reset();
		mCoverPath.setFillType(FillType.EVEN_ODD);
		mCoverPath.moveTo(mCenterPoint.x, mCenterPoint.y);
		mCoverPath.arcTo(mRect, 0, 180);
		mCoverPath.addCircle(mCenterPoint.x, mCenterPoint.y,
				mCenterPoint.x - 1, Direction.CW);
		float sRadius = mRect.height() * 0.4f;
		float offset = mRect.height() * 0.1f;
		mPointFs.clear();
		for (int angle = 30; angle < 390; angle += 30) {
			float x = sRadius * (1 + (float) Math.sin(Math.toRadians(angle)));
			float y = sRadius * (1 - (float) Math.cos(Math.toRadians(angle)));
			mPointFs.add(new PointF(offset + x, offset + y));
		}
		mICoverPath = new Path();
		mICoverPath.setFillType(FillType.EVEN_ODD);
		mICoverPath.moveTo(mCenterPoint.x, mCenterPoint.y);
		mICoverPath.arcTo(mRect, 180, 180);
		mICoverPath.addCircle(mCenterPoint.x, mCenterPoint.y,
				mCenterPoint.x - 1, Direction.CW);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int offset = 0;
		canvas.drawPath(mICoverPath, mWhitePaint);
		canvas.drawPath(mCoverPath, mFillPaint);
		int size = mPointFs.size();
		for (int pos = 0; pos < size; pos++) {
			PointF mPointF = mPointFs.get(pos);
			offset += drawNumberText(canvas, mPointF, CLOCK_TEXT, pos, offset);
		}
	}

	private int drawNumberText(Canvas canvas, PointF mPoint, String num,
			int pos, int offset) {
		int length = 1;
		if (pos >= 10)
			length = 3;
		float x = mPoint.x;
		float y = mPoint.y;
		char ch = 0;
		for (int i = 0; i < length; i++) {
			ch = num.charAt(pos + offset + i);
			if (ch == '\n') {
				y += mTextRect.height() + 1;
			} else {
				canvas.drawText(num, pos + i + offset, pos + i + 1 + offset, x,
						y + mTextRect.height() * 0.5f, mTextPaint);
			}
		}
		return length - 1;
	}
}
