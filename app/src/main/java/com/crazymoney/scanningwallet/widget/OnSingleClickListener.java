package com.crazymoney.scanningwallet.widget;

import android.os.SystemClock;
import android.view.View;

public abstract class OnSingleClickListener implements View.OnClickListener {
	private static final long MIN_CLICK_INTERVAL = 800;
	private long lastClickTime;

	public abstract void onSingleClick(View v);

	@Override
	public final void onClick(View v) {
		long currentClickTime = SystemClock.uptimeMillis();
		long elapsedTime = currentClickTime - this.lastClickTime;
		if (elapsedTime <= MIN_CLICK_INTERVAL) {
			return;
		} else {
			this.lastClickTime = currentClickTime;
			this.onSingleClick(v);
		}
	}
}
