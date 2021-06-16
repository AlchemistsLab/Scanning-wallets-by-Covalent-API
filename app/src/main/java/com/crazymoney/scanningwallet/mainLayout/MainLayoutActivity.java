package com.crazymoney.scanningwallet.mainLayout;

import android.content.Intent;
import android.os.Bundle;

import com.crazymoney.scanningwallet.base.BaseActivity;
import com.crazymoney.scanningwallet.walletsLayout.WalletsLayoutActivity;

public class MainLayoutActivity extends BaseActivity {
	private static final String TAG = MainLayoutActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		Intent intent = new Intent(this, WalletsLayoutActivity.class);
		super.startActivity(intent);
		super.finish();
	}
}
