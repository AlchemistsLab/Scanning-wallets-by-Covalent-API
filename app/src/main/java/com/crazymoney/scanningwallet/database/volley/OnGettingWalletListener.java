package com.crazymoney.scanningwallet.database.volley;

import org.json.JSONObject;

public interface OnGettingWalletListener {
	void onSuccess(JSONObject result);

	void onFailed(Exception e);
}
