/**
 * 创建时间：2014-8-10 下午9:12:47
 * @author kuxinwei
 * @since 1.0
 * @version 1.0<br>
 */
package com.kuxinwei.customview.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class RecurProgressView extends View {

	RecurColorProgressDrawable mDrawable;

	public RecurProgressView(Context context) {
		this(context, null);
	}

	public RecurProgressView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RecurProgressView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mDrawable = new RecurColorProgressDrawable(10);
		mDrawable.setCallback(this);
	}

	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		if (visibility == VISIBLE)
			mDrawable.start();
		else
			mDrawable.stop();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mDrawable.setBounds(0, 0, w, h);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mDrawable.draw(canvas);
	}

	@Override
	protected boolean verifyDrawable(Drawable who) {
		return who == mDrawable || super.verifyDrawable(who);
	}
}
