package com.crazymoney.scanningwallet.utils;

import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.crazymoney.scanningwallet.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PopupUtil {
	private static final String TAG = PopupUtil.class.getSimpleName();

	public static void showLoading(ImageView loadingImageView) {
		loadingImageView.setVisibility(View.VISIBLE);
		loadingImageView.setImageResource(R.drawable.loading);
		final AnimationDrawable loadAnimation = (AnimationDrawable) loadingImageView.getDrawable();
		loadingImageView.setVisibility(View.VISIBLE);
		loadAnimation.start();
	}

	public static void setForceShowIcon(PopupMenu popupMenu) {
		try {
			Field[] fields = popupMenu.getClass().getDeclaredFields();
			for (Field field : fields) {
				if ("mPopup".equals(field.getName())) {
					field.setAccessible(true);
					Object menuPopupHelper = field.get(popupMenu);
					Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
					Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
					setForceIcons.invoke(menuPopupHelper, true);
					break;
				}
			}
		} catch (Throwable e) {
			Log.e(TAG, "setForceShowIcon", e);
		}
	}
}
