package com.crazymoney.scanningwallet.widget;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.crazymoney.scanningwallet.R;
import com.crazymoney.scanningwallet.newWalletLayout.NewWalletLayoutActivity;
import com.crazymoney.scanningwallet.walletsLayout.WalletsLayoutActivity;

import java.util.ArrayList;

public class HamburgerListMenu {
	private static String TAG = HamburgerListMenu.class.getSimpleName();

	private DrawerLayout hamburgerDrawerLayout;
	private ListView hamburgerDrawerList;
	private ViewGroup header;
	private Context context;
	private HamburgerMenuAdapter hamburgerMenuAdapter;

	public HamburgerListMenu(Context context,
							 DrawerLayout hamburgerDrawerLayout,
							 ListView hamburgerDrawerList) {
		this.hamburgerDrawerLayout = hamburgerDrawerLayout;
		this.hamburgerDrawerList = hamburgerDrawerList;
		this.context = context;
	}

	public void setUpHamburgerMenu() {
		this.drawHeader();
		this.fillDrawerListView();
	}

	private void drawHeader() {
		LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE
		);
		this.header = (ViewGroup) inflater.inflate(
				R.layout.hamburger_menu_header,
				this.hamburgerDrawerList,
				false
		);
		TextView appVersionTextView = this.header.findViewById(
				R.id.hamburger_menu_header_app_version
		);
		String version = this.context.getString(R.string.version);
		appVersionTextView.setText(
				this.context.getString(R.string.version_text) + version
		);
		this.hamburgerDrawerList.addHeaderView(this.header, null, false);

		LinearLayout walletsLayout = this.header.findViewById(R.id.hamburger_menu_wallets);
		walletsLayout.setOnClickListener(new OnSingleClickListener() {
			@Override
			public void onSingleClick(View v) {
				showWallets();
			}
		});
		LinearLayout createANewWalletLayout = this.header.findViewById(R.id.hamburger_menu_create_a_new_wallet);
		createANewWalletLayout.setOnClickListener(new OnSingleClickListener() {
			@Override
			public void onSingleClick(View v) {
				goToCreateANewWallet();
			}
		});
	}

	private void fillDrawerListView() {
		this.hamburgerMenuAdapter = new HamburgerMenuAdapter(
				this.context,
				new ArrayList<>()
		);
		this.hamburgerDrawerList.setAdapter(this.hamburgerMenuAdapter);
	}

	private void showWallets() {
		this.hamburgerDrawerLayout.closeDrawer(Gravity.LEFT);
		Intent intent = new Intent(this.context.getApplicationContext(), WalletsLayoutActivity.class);
		this.context.startActivity(intent);
	}

	private void goToCreateANewWallet() {
		this.hamburgerDrawerLayout.closeDrawer(Gravity.LEFT);
		Intent intent = new Intent(this.context.getApplicationContext(), NewWalletLayoutActivity.class);
		this.context.startActivity(intent);
	}
}
