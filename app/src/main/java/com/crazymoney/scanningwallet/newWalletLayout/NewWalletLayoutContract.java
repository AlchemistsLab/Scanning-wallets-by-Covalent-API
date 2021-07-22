package com.crazymoney.scanningwallet.newWalletLayout;

import com.crazymoney.scanningwallet.base.BasePresenter;
import com.crazymoney.scanningwallet.base.BaseView;

public interface NewWalletLayoutContract {
	interface View extends BaseView<Presenter> {
		void goBack();

		void setAddress(String address);
	}

	interface Presenter extends BasePresenter {
		void saveAddress(String name, String address, int network);

		int getNetwork(boolean isETH,
					   boolean isBSC,
					   boolean isFantom,
					   boolean isAvalanche,
					   boolean isMatic,
					   boolean isMoonbeam);

		void setAddress(String address);
	}
}
