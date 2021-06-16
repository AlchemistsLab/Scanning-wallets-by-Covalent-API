package com.crazymoney.scanningwallet.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import com.crazymoney.scanningwallet.R;
import com.crazymoney.scanningwallet.widget.LoadingDialog;

public class BaseActivity extends AppCompatActivity {
	private static final String TAG = BaseActivity.class.getSimpleName();

	private LoadingDialog mLoadingDialog;
	protected Handler mHandler;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.mHandler = new Handler();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}

	@Override
	public void onPause() {
		super.onPause();
		hideLoadingDialog();
	}

	public void hideLoadingDialog() {
		mHandler.post(() -> {
			try {
				if (mLoadingDialog != null) {
					mLoadingDialog.dismiss();
				}
				mLoadingDialog = null;
			} catch (Exception e) {
				Log.e(TAG, "hideLoadingDialog", e);
			}
		});
	}

	public void showConfirmDialog(final String title, final String message,
								  final DialogInterface.OnClickListener yesOnClickListener) {
		showConfirmDialog(title, message, yesOnClickListener, null, getString(R.string.btn_yes),
				getString(R.string.btn_no), true);
	}

	public void showConfirmDialog(final String title, final String message,
								  final DialogInterface.OnClickListener yesOnClickListener,
								  final DialogInterface.OnClickListener noOnClickListener,
								  final boolean isCancelable) {
		showConfirmDialog(
				title, message, yesOnClickListener, noOnClickListener, getString(R.string.btn_yes),
				getString(R.string.btn_no), isCancelable);
	}

	public void showConfirmDialog(final String title, final String message,
								  DialogInterface.OnClickListener yesOnClickListener,
								  DialogInterface.OnClickListener noOnClickListener,
								  final String yesTitle, final String noTitle,
								  final boolean isCancelable) {
		try {
			if (noOnClickListener == null) {
				noOnClickListener = (dialogInterface, i) -> dialogInterface.cancel();
			}

			if (yesOnClickListener == null) {
				yesOnClickListener = (dialogInterface, i) -> dialogInterface.cancel();
			}

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					new ContextThemeWrapper(this, R.style.AlertDialog));
			alertDialogBuilder.setTitle(title);
			alertDialogBuilder
					.setMessage(message)
					.setCancelable(isCancelable)
					.setPositiveButton(yesTitle, yesOnClickListener)
					.setNegativeButton(noTitle, noOnClickListener);
			AlertDialog confirm = alertDialogBuilder.create();
			confirm.show();
		} catch (Exception ex) {
			Log.e(TAG, "createConfirmDialog", ex);
		}
	}

	public void showErrorDialog(int stringResourceId) {
		showErrorDialog(getString(R.string.app_name), getString(stringResourceId));
	}

	public void showErrorDialog(int titleResourceId, int stringResourceId) {
		showErrorDialog(getString(titleResourceId), getString(stringResourceId));
	}

	public void showErrorDialog(final Throwable t) {
		String message = t.getLocalizedMessage();
		if (message == null || message.startsWith("java.net")) {
			message = super.getString(R.string.error_network_exception_general);
		}
		this.showErrorDialog(super.getString(R.string.app_name), message);
	}

	public void showErrorDialog(final String title, final String error) {
		mHandler.post(() -> showErrorDialogOnScreen(title, error, getString(R.string.dismiss), false));
	}

	public void showErrorDialog(final String title, final String error, final boolean isCancelable) {
		mHandler.post(() -> showErrorDialogOnScreen(title, error, getString(R.string.dismiss), isCancelable));
	}

	public void showErrorDialog(final String title, final String error, final String button, final boolean isCancelable) {
		mHandler.post(() -> showErrorDialogOnScreen(title, error, button, isCancelable));
	}

	private void showErrorDialogOnScreen(String title, String error, String closeButtonTitle, boolean isCancelable) {
		try {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					new ContextThemeWrapper(this, R.style.AlertDialog)
			);
			alertDialogBuilder.setTitle(title);
			alertDialogBuilder
					.setMessage(error)
					.setCancelable(isCancelable)
					.setPositiveButton(closeButtonTitle, (dialogInterface, i) -> dialogInterface.cancel());
			AlertDialog errorDialog = alertDialogBuilder.create();
			errorDialog.show();
		} catch (Exception e) {
			Log.e(TAG, "showErrorDialog", e);
		}
	}

	public void showLoadingDialog(final Activity activity) {
		Log.e(TAG, "showLoadingDialog");
		hideLoadingDialog();
		if (mLoadingDialog == null && !isFinishing() && !activity.isFinishing()) {
			mHandler.post(() -> {
				try {
					mLoadingDialog = new LoadingDialog(activity);
					if (mLoadingDialog != null && !mLoadingDialog.isShowing()
							&& !activity.isFinishing() && !isFinishing()) {
						mLoadingDialog.show();
					}
				} catch (Exception e) {
					Log.e(TAG, "showLoadingDialog", e);
					hideLoadingDialog();
				}
			});
		}
	}

	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
	}
}
