package com.crazymoney.scanningwallet.database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.crazymoney.scanningwallet.database.datasource.WalletDataSource;
import com.crazymoney.scanningwallet.database.table.Wallet;
import com.crazymoney.scanningwallet.database.volley.CovalentStringRequest;
import com.crazymoney.scanningwallet.database.volley.CovalentVolley;
import com.crazymoney.scanningwallet.database.volley.OnGettingWalletListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class Repository {
	private static final String TAG = Repository.class.getSimpleName();

	private static Repository INSTANCE = null;

	private final WalletDataSource walletDataSource;
	private final CovalentVolley covalentVolley;

	public static Repository getInstance(@NonNull WalletDataSource walletDataSource,
										 @NonNull CovalentVolley covalentVolley) {
		if (INSTANCE == null) {
			INSTANCE = new Repository(walletDataSource, covalentVolley);
		}
		return INSTANCE;
	}

	public static void destroyInstance() {
		INSTANCE = null;
	}

	private Repository(@NonNull WalletDataSource walletDataSource,
					   @NonNull CovalentVolley covalentVolley) {
		this.walletDataSource = checkNotNull(walletDataSource);
		this.covalentVolley = checkNotNull(covalentVolley);
	}

	public void deleteWallet(Wallet wallet) {
		this.walletDataSource.deleteWallet(wallet);
	}

	public void createWallet(Wallet wallet) {
		this.walletDataSource.createWallet(wallet);
	}

	public List<Wallet> getWallets() {
		return this.walletDataSource.getWallets();
	}

	public Wallet getWallet(long id) {
		return this.walletDataSource.getWallet(id);
	}

	public void retrieveDataByCovalent(final Wallet wallet, final OnGettingWalletListener listener) {
		String url = this.getUrlOfCovalentApi(wallet);
		Log.e(TAG, "retrieveDataByCovalent: " + url);
		StringRequest request = new CovalentStringRequest(
				StringRequest.Method.GET,
				url,
				response -> {
					try {
						Log.e(TAG, "response:" + response);
						JSONObject result = new JSONObject(response);
						listener.onSuccess(result);
					} catch (Exception e) {
						listener.onFailed(e);
					}
				},
				error -> {
//					Log.e(TAG, "onFailed: " + error.getCause());
//					listener.onFailed(error);
				}) {

			@Override
			protected VolleyError parseNetworkError(VolleyError volleyError) {
				try {
					String responseBody = new String(volleyError.networkResponse.data, "utf-8");
					JSONObject data = new JSONObject(responseBody);
					String message = getErrorMessage(data);
					Log.e(TAG, "parseNetworkError: " + message);
					listener.onFailed(new Exception(message));
				} catch (Exception e) {
					listener.onFailed(new Exception());
					Log.e(TAG, "parseNetworkError", e);
				}
				return volleyError;
			}
		};
		this.covalentVolley.addToRequestQueue(request);
	}

	private String getUrlOfCovalentApi(Wallet wallet) {
		int chainId = wallet.getChainId();
		String url = "https://api.covalenthq.com/v1/" + chainId + "/address/" + wallet.getAddress() + "/balances_v2/?&key=ckey_edc1619f340849c6939fba54856";
		return url;
	}

	private String getErrorMessage(JSONObject data) throws Exception {
		String message = data.getString("error_message");
		return message;
	}
}
