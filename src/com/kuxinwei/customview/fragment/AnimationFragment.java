/**
 * 创建时间：2014-8-10 下午2:21:46
 * @author kuxinwei
 * @since 1.0
 * @version 1.0<br>
 */
package com.kuxinwei.customview.fragment;

import com.kuxinwei.customview.R;
import com.kuxinwei.customview.drawable.CustomView;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AnimationFragment extends Fragment {

	CustomView mCustomView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_animation, container, false);
	}
}
