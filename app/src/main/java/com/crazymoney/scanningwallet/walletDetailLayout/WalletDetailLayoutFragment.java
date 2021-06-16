package com.crazymoney.scanningwallet.walletDetailLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crazymoney.scanningwallet.R;
import com.crazymoney.scanningwallet.base.BaseActivity;
import com.crazymoney.scanningwallet.database.table.WalletItem;
import com.crazymoney.scanningwallet.widget.HamburgerListMenu;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.common.base.Preconditions.checkNotNull;

public class WalletDetailLayoutFragment extends Fragment implements WalletDetailLayoutContract.View {
	private static final String TAG = WalletDetailLayoutFragment.class.getSimpleName();

	@BindView(R.id.hamburger_drawer_layout)
	protected DrawerLayout hamburgerDrawerLayout;
	@BindView(R.id.hamburger_menu_left_drawer)
	protected ListView hamburgerDrawerList;
	@BindView(R.id.toolbar)
	protected Toolbar toolbar;
	@BindView(R.id.fragment_wallet_detail_layout_item_list)
	protected RecyclerView itemsRecyclerView;

	private HamburgerListMenu hamburgerListMenu;
	private WalletDetailLayoutContract.Presenter presenter;
	private WalletItemsAdapter adapter;

	public static WalletDetailLayoutFragment newInstance() {
		return new WalletDetailLayoutFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(
				R.layout.fragment_wallet_detail,
				container,
				false
		);
		ButterKnife.bind(this, view);
		this.setUpHamburgerMenu();
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		this.presenter.start();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void setPresenter(@NonNull WalletDetailLayoutContract.Presenter presenter) {
		this.presenter = checkNotNull(presenter);
	}

	@Override
	public void setTittle(String title) {
		TextView titleTextView = this.toolbar.findViewById(R.id.toolbar_title);
		titleTextView.setText(title);
	}

	@Override
	public void setVisibilityOfItems(boolean isVisible) {
		if (isVisible) {
			this.itemsRecyclerView.setVisibility(View.VISIBLE);
		} else {
			this.itemsRecyclerView.setVisibility(View.GONE);
		}
	}

	@Override
	public void displayItems(List<WalletItem> items) {
		if (super.getActivity() != null) {
			super.getActivity().runOnUiThread(() -> {
				this.itemsRecyclerView.setLayoutManager(new LinearLayoutManager(super.getActivity()));
				this.adapter = new WalletItemsAdapter(items);
				this.itemsRecyclerView.setAdapter(adapter);
			});
		}
	}

	@Override
	public void showLoadingDialog() {
		BaseActivity baseActivity = (BaseActivity) super.getActivity();
		if (baseActivity != null) {
			baseActivity.showLoadingDialog(baseActivity);
		}
	}

	@Override
	public void hideLoadingDialog() {
		BaseActivity baseActivity = (BaseActivity) super.getActivity();
		if (baseActivity != null) {
			baseActivity.hideLoadingDialog();
		}
	}

	@Override
	public void showError(String message) {
		BaseActivity baseActivity = (BaseActivity) super.getActivity();
		if (baseActivity != null) {
			baseActivity.showErrorDialog(baseActivity.getString(R.string.app_name), message);
		}
	}

	@OnClick(R.id.hamburger_icon)
	protected void hamburgerIconOnClick() {
		this.hamburgerDrawerLayout.openDrawer(Gravity.LEFT);
	}

	private void setUpHamburgerMenu() {
		this.hamburgerListMenu = new HamburgerListMenu(
				super.getActivity(),
				this.hamburgerDrawerLayout,
				this.hamburgerDrawerList
		);
		this.hamburgerListMenu.setUpHamburgerMenu();
	}
}
