package com.crazymoney.scanningwallet.walletDetailLayout;

import com.crazymoney.scanningwallet.base.BasePresenter;
import com.crazymoney.scanningwallet.base.BaseView;
import com.crazymoney.scanningwallet.database.table.WalletItem;

import java.util.List;

public interface WalletDetailLayoutContract {
	interface View extends BaseView<Presenter> {

		void setTittle(String title);

		void showError(String message);

		void setVisibilityOfItems(boolean isVisible);

		void displayItems(List<WalletItem> items);

		void showLoadingDialog();

		void hideLoadingDialog();
	}

	interface Presenter extends BasePresenter {
		void setWalletId(long walletId);
	}
}
