package com.crazymoney.scanningwallet.widget;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.crazymoney.scanningwallet.R;
import com.crazymoney.scanningwallet.utils.PopupUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingDialog extends Dialog {
	@BindView(R.id.loading)
	protected ImageView mLoadingImageView;

	private static final String TAG = LoadingDialog.class.getSimpleName();

	public LoadingDialog(Activity activity) {
		super(activity);
	}

	private void drawComponentView() {
		setCancelable(false);
		mLoadingImageView.setVisibility(View.VISIBLE);
		PopupUtil.showLoading(mLoadingImageView);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.dialog_loading);
		ButterKnife.bind(this);
		drawComponentView();
	}

}
