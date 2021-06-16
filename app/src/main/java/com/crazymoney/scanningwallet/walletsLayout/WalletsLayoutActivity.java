package com.crazymoney.scanningwallet.walletsLayout;

import android.os.Bundle;

import com.crazymoney.scanningwallet.R;
import com.crazymoney.scanningwallet.base.BaseActivity;
import com.crazymoney.scanningwallet.injection.Injection;
import com.crazymoney.scanningwallet.utils.ActivityUtils;

import butterknife.ButterKnife;

public class WalletsLayoutActivity extends BaseActivity {
	private static final String TAG = WalletsLayoutActivity.class.getSimpleName();

	private WalletsLayoutFragment fragment;
	private WalletsLayoutPresenter presenter;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		super.setContentView(R.layout.activity_wallets_layout);
		ButterKnife.bind(this);
		this.drawFragment();
		this.createPresenter();
	}

	private void drawFragment() {
		this.fragment = WalletsLayoutFragment.newInstance();
		ActivityUtils.replaceFragmentToActivity(
				super.getSupportFragmentManager(),
				this.fragment,
				R.id.wallets_layout_content
		);
	}

	private void createPresenter() {
		this.presenter = new WalletsLayoutPresenter(
				Injection.provideContext(super.getApplicationContext()),
				this.fragment,
				Injection.provideRepository(super.getApplicationContext())
		);
	}
}
