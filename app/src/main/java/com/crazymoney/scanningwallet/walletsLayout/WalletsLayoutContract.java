package com.crazymoney.scanningwallet.walletsLayout;

import com.crazymoney.scanningwallet.base.BasePresenter;
import com.crazymoney.scanningwallet.base.BaseView;
import com.crazymoney.scanningwallet.database.table.Wallet;

import java.util.List;

public interface WalletsLayoutContract {
	interface View extends BaseView<Presenter> {
		void drawWallets(List<Wallet> wallets);

		void goToWalletDetailScreen(Wallet wallet);
	}

	interface Presenter extends BasePresenter {
		void deleteWallet(Wallet wallet);

		void goToWalletDetailScreen(Wallet wallet);
	}
}
