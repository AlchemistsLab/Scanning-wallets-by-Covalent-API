package com.crazymoney.scanningwallet.database.volley;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CovalentStringRequest extends StringRequest {

	public CovalentStringRequest(int method,
								 String url,
								 Response.Listener<String> listener,
								 Response.ErrorListener errorListener) {
		super(method, url, listener, errorListener);
		this.setRetryPolicy();
	}

	@Override
	public Map<String, String> getHeaders() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-agent", "Mozilla: Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.3 Mozilla/5.0 (Macintosh; Intel Mac OS X x.y; rv:42.0) Gecko/20100101 Firefox/43.4.");
		return headers;
	}

	@Override
	public String getBodyContentType() {
		return "application/json; charset=utf-8";
	}

	private void setRetryPolicy() {
		RetryPolicy retryPolicy = new DefaultRetryPolicy(
				10000,
				5,
				0.7f
		);
		super.setRetryPolicy(retryPolicy);
	}
}
