/**
 * 创建时间：2014-8-25 上午9:32:04
 * @author kuxinwei
 * @since 1.0
 * @version 1.0<br>
 */
package com.kuxinwei.customview.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuxinwei.customview.R;

public class BWClockFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_bwclock, container, false);
	}
}
