package com.crazymoney.scanningwallet.newWalletLayout;

import android.os.Bundle;

import com.crazymoney.scanningwallet.R;
import com.crazymoney.scanningwallet.base.BaseActivity;
import com.crazymoney.scanningwallet.injection.Injection;
import com.crazymoney.scanningwallet.utils.ActivityUtils;

import butterknife.ButterKnife;

public class NewWalletLayoutActivity extends BaseActivity {
	private static final String TAG = NewWalletLayoutActivity.class.getSimpleName();

	private NewWalletLayoutFragment fragment;
	private NewWalletLayoutPresenter presenter;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		super.setContentView(R.layout.activity_new_wallet_layout);
		ButterKnife.bind(this);
		this.drawFragment();
		this.createPresenter();
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
		this.presenter = new NewWalletLayoutPresenter(
				Injection.provideContext(super.getApplicationContext()),
				this.fragment,
				Injection.provideRepository(super.getApplicationContext())
		);
	}
}
