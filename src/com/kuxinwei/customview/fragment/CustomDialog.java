/**
 * 创建时间：2014-7-18 下午3:42:02
 * @author Zheng Xinwei
 * @since 1.0
 * @version 1.0<br>
 */
package com.kuxinwei.customview.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kuxinwei.customview.R;

public class CustomDialog extends DialogFragment {

	public static final String TYPE = "type";
	public static final String MESSAGE = "message";

	public static final int TYPE_WAITING = 1;

	public static CustomDialog newInstance(int type, String message) {
		CustomDialog dialog = new CustomDialog();
		Bundle args = new Bundle();
		args.putInt(TYPE, type);
		args.putString(MESSAGE, message);
		dialog.setArguments(args);
		return dialog;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		int type = getArguments().getInt(TYPE);
		if (type == TYPE_WAITING) {
			return createWaitingDialog(getArguments().getString(MESSAGE));
		} else
			return null;
	};

	private Dialog createWaitingDialog(String message) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_waiting_dialog, null);
		TextView mDialogMessage = (TextView) view
				.findViewById(R.id.dialog_message);
		String content = null;
		if ((content = getArguments().getString(MESSAGE)) == null)
			content = "请等待...";
		mDialogMessage.setText(content);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		return builder.setView(view).setCancelable(false).create();
	}
}
