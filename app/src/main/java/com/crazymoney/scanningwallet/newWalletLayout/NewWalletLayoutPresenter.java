package com.crazymoney.scanningwallet.newWalletLayout;

import android.content.Context;
import android.text.TextUtils;

import com.crazymoney.scanningwallet.database.Repository;
import com.crazymoney.scanningwallet.database.table.Wallet;

import static com.google.common.base.Preconditions.checkNotNull;

public class NewWalletLayoutPresenter implements NewWalletLayoutContract.Presenter {
	private static final String TAG = NewWalletLayoutPresenter.class.getSimpleName();

	private NewWalletLayoutContract.View view;
	private Context context;
	private Repository repository;

	public NewWalletLayoutPresenter(Context context,
									NewWalletLayoutContract.View view,
									Repository repository) {
		this.context = checkNotNull(context, "context cannot be null!");
		this.view = checkNotNull(view, "view cannot be null!");
		this.repository = checkNotNull(repository, "view cannot be null!");
		this.view.setPresenter(this);
	}

	@Override
	public void start() {
	}

	@Override
	public void saveAddress(String name, String address, int network) {
		Wallet wallet = new Wallet();
		wallet.setName(name);
		wallet.setAddress(address);
		wallet.setNetwork(network);
		this.repository.createWallet(wallet);
		this.view.goBack();
	}

	@Override
	public int getNetwork(boolean isETH,
						  boolean isBSC,
						  boolean isFantom,
						  boolean isAvalanche,
						  boolean isMatic,
						  boolean isMoonbeam) {
		if (isETH) {
			return Wallet.Network.ETH.ordinal();
		}
		if (isBSC) {
			return Wallet.Network.BSC.ordinal();
		}
		if (isFantom) {
			return Wallet.Network.FANTOM.ordinal();
		}
		if (isAvalanche) {
			return Wallet.Network.AVALANCHE.ordinal();
		}
		if (isMatic) {
			return Wallet.Network.MATIC.ordinal();
		}
		if (isMoonbeam) {
			return Wallet.Network.MOONBEAM.ordinal();
		}

		return -1;
	}

	@Override
	public void setAddress(String address) {
		if (!TextUtils.isEmpty(address)) {
			this.view.setAddress(address);
		}
	}
}
