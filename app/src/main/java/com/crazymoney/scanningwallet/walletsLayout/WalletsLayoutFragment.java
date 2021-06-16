package com.crazymoney.scanningwallet.walletsLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crazymoney.scanningwallet.R;
import com.crazymoney.scanningwallet.database.table.Wallet;
import com.crazymoney.scanningwallet.newWalletLayout.NewWalletLayoutActivity;
import com.crazymoney.scanningwallet.walletDetailLayout.WalletDetailLayoutActivity;
import com.crazymoney.scanningwallet.widget.HamburgerListMenu;
import com.crazymoney.scanningwallet.widget.OnSingleClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.common.base.Preconditions.checkNotNull;

public class WalletsLayoutFragment extends Fragment implements WalletsLayoutContract.View {
	private static final String TAG = WalletsLayoutFragment.class.getSimpleName();

	@BindView(R.id.hamburger_drawer_layout)
	protected DrawerLayout hamburgerDrawerLayout;
	@BindView(R.id.hamburger_menu_left_drawer)
	protected ListView hamburgerDrawerList;
	@BindView(R.id.toolbar)
	protected Toolbar toolbar;
	@BindView(R.id.fragment_wallets_layout_content_books_list)
	protected RecyclerView walletsRecyclerView;
	@BindView(R.id.fragment_wallets_layout_content_create_wallet)
	protected FloatingActionButton createNewWalletButton;

	private HamburgerListMenu hamburgerListMenu;
	private WalletsAdapter walletsAdapter;

	private WalletsLayoutContract.Presenter presenter;

	public static WalletsLayoutFragment newInstance() {
		return new WalletsLayoutFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(
				R.layout.fragment_wallets,
				container,
				false
		);
		ButterKnife.bind(this, view);
		this.setUpHamburgerMenu();
		this.createNewBookOnClick();
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
	public void setPresenter(@NonNull WalletsLayoutContract.Presenter presenter) {
		this.presenter = checkNotNull(presenter);
	}

	@Override
	public void drawWallets(List<Wallet> wallets) {
		this.walletsRecyclerView.setLayoutManager(new LinearLayoutManager(super.getActivity()));
		this.walletsAdapter = new WalletsAdapter(this.presenter, wallets);
		this.walletsRecyclerView.setAdapter(this.walletsAdapter);
	}

	@Override
	public void goToWalletDetailScreen(Wallet wallet) {
		super.getActivity().runOnUiThread(() -> {
			Log.e(TAG, "goToWalletDetailScreen: " + wallet);
			Intent intent = new Intent(super.getActivity(), WalletDetailLayoutActivity.class);
			Bundle bundle = new Bundle();
			bundle.putLong("walletId", wallet.getId());
			intent.putExtras(bundle);
			super.startActivity(intent);
		});
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

	private void createNewBookOnClick() {
		this.createNewWalletButton.setOnClickListener(new OnSingleClickListener() {
			@Override
			public void onSingleClick(View v) {
				hamburgerDrawerLayout.closeDrawer(Gravity.LEFT);
				Intent intent = new Intent(
						getActivity().getApplicationContext(),
						NewWalletLayoutActivity.class
				);
				getActivity().startActivity(intent);
			}
		});
	}
}
