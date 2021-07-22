package com.crazymoney.scanningwallet.walletDetailLayout;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.crazymoney.scanningwallet.R;
import com.crazymoney.scanningwallet.database.Repository;
import com.crazymoney.scanningwallet.database.table.Wallet;
import com.crazymoney.scanningwallet.database.table.WalletItem;
import com.crazymoney.scanningwallet.database.volley.OnGettingWalletListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class WalletDetailLayoutPresenter implements WalletDetailLayoutContract.Presenter {
	private static final String TAG = WalletDetailLayoutPresenter.class.getSimpleName();

	private WalletDetailLayoutContract.View view;
	private Context context;
	private Repository repository;
	private Wallet wallet;
	private List<WalletItem> walletItems;

	public WalletDetailLayoutPresenter(Context context,
									   WalletDetailLayoutContract.View view,
									   Repository repository) {
		this.context = checkNotNull(context, "context cannot be null!");
		this.view = checkNotNull(view, "view cannot be null!");
		this.repository = checkNotNull(repository, "view cannot be null!");
		this.view.setPresenter(this);
	}

	@Override
	public void start() {
		Log.e(TAG, "start");
		if (this.wallet != null) {
			this.view.setTittle(this.wallet.getName());
			this.view.showLoadingDialog();
			this.repository.retrieveDataByCovalent(
					this.wallet,
					new OnGettingWalletListener() {
						@Override
						public void onSuccess(JSONObject result) {
							view.hideLoadingDialog();
							parseResponse(result);
						}

						@Override
						public void onFailed(Exception e) {
							view.hideLoadingDialog();
							String message = e.getMessage();
							Log.e(TAG, "start: " + message);
							if (TextUtils.isEmpty(message)) {
								message = context.getString(R.string.error_network_exception_general);
							}
							view.showError(message);
						}
					}
			);
		}
	}

	@Override
	public void setWalletId(long walletId) {
		Log.e(TAG, "setWalletId");
		if (walletId > 0) {
			this.wallet = this.repository.getWallet(walletId);
		}
	}

	@Override
	public void searchTokens(String text) {
		if (this.walletItems != null && this.walletItems.size() > 0) {
			List<WalletItem> result = new ArrayList<>();
			for (WalletItem item : this.walletItems) {
				if (item.getName().toUpperCase().contains(text)) {
					result.add(item);
				}
			}
			this.view.displayItems(result);
		}
	}

	private void parseResponse(JSONObject result) {
		try {
			Log.e(TAG, "parseResponse: " + result);
			if (result == null) {
				this.view.showError(this.context.getString(R.string.error_network_exception_general));
				return;
			}
			if (result.has("error")) {
				boolean isError = result.getBoolean("error");
				String message = result.getString("error_message");
				if (isError) {
					this.view.showError(message);
					return;
				} else {
					this.parseItems(result);
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "parseResponse", e);
			this.view.showError(this.context.getString(R.string.error_network_exception_general));
		}
	}

	private void parseItems(JSONObject result) throws Exception {
		JSONObject data = result.getJSONObject("data");
		JSONArray itemsJsonArray = data.getJSONArray("items");
		if (itemsJsonArray != null && itemsJsonArray.length() > 0) {
			final int LENGTH = itemsJsonArray.length();
			this.walletItems = new ArrayList<>();
			for (int i = 0; i < LENGTH; i++) {
				WalletItem item = this.parseItem(itemsJsonArray.getJSONObject(i));
				if (item != null) {
					this.walletItems.add(item);
				}
			}
			Collections.sort(this.walletItems);
			this.view.setVisibilityOfItems(true);
			this.view.displayItems(this.walletItems);
			this.displayTotalBalance(this.walletItems);
			this.displayPieChart(this.walletItems);
		} else {
			this.view.setVisibilityOfItems(false);
		}
	}

	private WalletItem parseItem(JSONObject itemObject) {
		try {
			long balance = itemObject.getLong("balance");
			if (balance <= 0) {
				return null;
			}
			WalletItem walletItem = new WalletItem();
			walletItem.setName(itemObject.getString("contract_ticker_symbol"));
			walletItem.setLogo(itemObject.getString("logo_url"));
			walletItem.setBalance(balance);
			walletItem.setQuote(itemObject.getDouble("quote"));
			walletItem.setQuoteRate(itemObject.getDouble("quote_rate"));
			walletItem.setContractDecimals(itemObject.getLong("contract_decimals"));
			return walletItem;
		} catch (Exception e) {
			Log.e(TAG, "parseItem", e);
			return null;
		}
	}

	private void displayTotalBalance(List<WalletItem> walletItems) {
		double balance = this.getTotalBalance(walletItems);
		String balanceString = String.format("%,.4f", balance);
		this.view.displayBalance(" " + balanceString + " USD");
	}

	private void displayPieChart(List<WalletItem> walletItems) {
		double balance = this.getTotalBalance(walletItems);
		Map<WalletItem, Double> pieEntries = new HashMap<>();
		if (walletItems != null && walletItems.size() > 0) {
			for (WalletItem item : walletItems) {
				if (item.getQuote() > 0) {
					pieEntries.put(item, item.getQuote() / balance);
				}
			}
		}
		this.view.displayPieChart(pieEntries);
	}

	private double getTotalBalance(List<WalletItem> walletItems) {
		double balance = 0;
		if (walletItems != null && walletItems.size() > 0) {
			for (WalletItem item : walletItems) {
				balance += item.getQuote();
			}
		}
		return balance;
	}
}
