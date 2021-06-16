package com.crazymoney.scanningwallet.walletDetailLayout;

import android.os.Bundle;
import android.util.Log;

import com.crazymoney.scanningwallet.R;
import com.crazymoney.scanningwallet.base.BaseActivity;
import com.crazymoney.scanningwallet.injection.Injection;
import com.crazymoney.scanningwallet.utils.ActivityUtils;

import butterknife.ButterKnife;

public class WalletDetailLayoutActivity extends BaseActivity {
	private static final String TAG = WalletDetailLayoutActivity.class.getSimpleName();

	private WalletDetailLayoutFragment fragment;
	private WalletDetailLayoutPresenter presenter;
	private long walletId;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		super.setContentView(R.layout.activity_wallet_detail_layout);
		ButterKnife.bind(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		Bundle bundle = super.getIntent().getExtras();
		this.walletId = bundle.getLong("walletId", -1);
		Log.e(TAG, "walletId: " + walletId);
		this.drawFragment();
		this.createPresenter();
	}

	private void drawFragment() {
		this.fragment = WalletDetailLayoutFragment.newInstance();
		ActivityUtils.replaceFragmentToActivity(
				super.getSupportFragmentManager(),
				this.fragment,
				R.id.wallet_detail_layout_content
		);
	}

	private void createPresenter() {
		this.presenter = new WalletDetailLayoutPresenter(
				Injection.provideContext(super.getApplicationContext()),
				this.fragment,
				Injection.provideRepository(super.getApplicationContext())
		);
		this.presenter.setWalletId(this.walletId);
	}
}
