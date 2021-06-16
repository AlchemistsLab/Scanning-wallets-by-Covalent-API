package com.crazymoney.scanningwallet.database.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.lang.ref.WeakReference;

public class CovalentVolley {
	public static final String TAG = CovalentVolley.class.getSimpleName();

	private static WeakReference<CovalentVolley> INSTANCE;

	private RequestQueue requestQueue;

	public static CovalentVolley getInstance(Context context) {
		if (INSTANCE == null || INSTANCE.get() == null) {
			INSTANCE = new WeakReference<>(new CovalentVolley(context));
		}
		return INSTANCE.get();
	}

	public CovalentVolley(Context context) {
		this.requestQueue = Volley.newRequestQueue(context);
	}

	public <T> void addToRequestQueue(Request<T> request) {
		this.requestQueue.add(request);
	}
}
