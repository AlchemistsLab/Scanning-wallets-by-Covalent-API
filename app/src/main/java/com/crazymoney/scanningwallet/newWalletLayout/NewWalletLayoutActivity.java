package com.crazymoney.scanningwallet.newWalletLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.crazymoney.scanningwallet.R;
import com.crazymoney.scanningwallet.base.BaseActivity;
import com.crazymoney.scanningwallet.injection.Injection;
import com.crazymoney.scanningwallet.utils.ActivityUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.ButterKnife;

public class NewWalletLayoutActivity extends BaseActivity {
	private static final String TAG = NewWalletLayoutActivity.class.getSimpleName();

	private NewWalletLayoutFragment fragment;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		super.setContentView(R.layout.activity_new_wallet_layout);
		ButterKnife.bind(this);
		this.drawFragment();
		this.createPresenter();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
		if (result != null) {
			if (result.getContents() == null) {
				Log.e(TAG, "Cancelled");
			} else {
				String address = result.getContents();
				Log.e(TAG, "Scanned: " + address);
				this.presenter.setAddress(address);
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	private void drawFragment() {
		this.fragment = NewWalletLayoutFragment.newInstance();
		ActivityUtils.replaceFragmentToActivity(
				super.getSupportFragmentManager(),
				this.fragment,
				R.id.new_wallet_layout_content
		);
	}

	private void createPresenter() {
		new NewWalletLayoutPresenter(
				Injection.provideContext(super.getApplicationContext()),
				this.fragment,
				Injection.provideRepository(super.getApplicationContext())
		);
	}
}
