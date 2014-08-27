/**
 * 创建时间：2014-8-25 下午8:09:44
 * @author kuxinwei
 * @since 1.0
 * @version 1.0<br>
 */
package com.kuxinwei.customview.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuxinwei.customview.R;

public class PageAdapter extends BaseAdapter {

	private Context context;

	class BitmapCache {
		final int MAX_SIZE = (int) Runtime.getRuntime().maxMemory() / 8;
		LruCache<Integer, Bitmap> mLruCache = new LruCache<Integer, Bitmap>(
				MAX_SIZE) {
			@Override
			protected int sizeOf(Integer key, Bitmap value) {
				return value.getByteCount();
			}
		};

		public Bitmap getBitmap(View view, int resId) {
			Bitmap mBitmap = mLruCache.get(resId);
			if (mBitmap == null) {
				mBitmap = getScaleBitmap(view, resId);
				mLruCache.put(resId, mBitmap);
			}
			return mBitmap;
		}
	}

	public Bitmap getScaleBitmap(View view, int resId) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), resId, options);
		options.inSampleSize = calInSampleSize(view, options);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(context.getResources(), resId,
				options);
	}

	private int calInSampleSize(View view, Options options) {
		int reqHeight = view.getHeight();
		int reqWidth = view.getWidth();
		int sampleSize = 1;
		int outHeight = options.outHeight;
		int outWidth = options.outWidth;

		if (outHeight > reqHeight || outWidth > reqWidth) {
			final int halfHeight = outHeight >> 1;
			final int halfWidth = outWidth >> 1;
			while (halfHeight / sampleSize > reqHeight
					|| halfWidth / sampleSize > reqWidth) {
				sampleSize <<= 1;
			}
		}
		return sampleSize;
	}

	BitmapCache mBitmapCache;

	public PageAdapter(Context context) {
		mBitmapCache = new BitmapCache();
	}

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					android.R.layout.simple_expandable_list_item_1, parent,
					false);
			mHolder = new ViewHolder(convertView);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		mHolder.mImageView.setImageBitmap(mBitmapCache.getBitmap(
				mHolder.mImageView, R.drawable.ic_launcher));
		return convertView;
	}

	static class ViewHolder {
		TextView mTextView;
		ImageView mImageView;

		public ViewHolder(View convertView) {
			mTextView = (TextView) convertView.findViewById(android.R.id.text1);
		}
	}

}
