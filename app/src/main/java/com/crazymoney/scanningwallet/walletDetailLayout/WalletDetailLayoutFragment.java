package com.crazymoney.scanningwallet.walletDetailLayout;

import android.graphics.Color;
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
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	@BindView(R.id.fragment_wallet_detail_piechart)
	protected PieChart portfolioPieChart;
	@BindView(R.id.fragment_wallet_detail_balance)
	protected TextView balanceTextView;
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
	public void displayPieChart(Map<WalletItem, Double> pieEntries) {
		this.portfolioPieChart.setVisibility(View.VISIBLE);
		this.setupPieChart();
		this.loadPieChartData(pieEntries);
	}

	@Override
	public void displayBalance(String balance) {
		this.balanceTextView.setVisibility(View.VISIBLE);
		String text = super.getString(R.string.balance) + balance;
		this.balanceTextView.setText(text);
	}

	@Override
	public void displayItems(List<WalletItem> items) {
		if (super.getActivity() != null) {
			super.getActivity().runOnUiThread(() -> {
				this.itemsRecyclerView.setVisibility(View.VISIBLE);
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
			baseActivity.runOnUiThread(() -> {
				this.portfolioPieChart.setVisibility(View.GONE);
				this.balanceTextView.setVisibility(View.GONE);
			});
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

	private void setupPieChart() {
		this.portfolioPieChart.setDrawHoleEnabled(true);
		this.portfolioPieChart.setUsePercentValues(true);
		this.portfolioPieChart.setEntryLabelTextSize(12);
		this.portfolioPieChart.setEntryLabelColor(Color.BLACK);
		this.portfolioPieChart.setCenterText("Portfolio");
		this.portfolioPieChart.setCenterTextSize(18);
		this.portfolioPieChart.getDescription().setEnabled(false);

		Legend l = this.portfolioPieChart.getLegend();
		l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
		l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
		l.setOrientation(Legend.LegendOrientation.VERTICAL);
		l.setDrawInside(false);
		l.setEnabled(true);
	}

	private void loadPieChartData(Map<WalletItem, Double> pieEntries) {
		List<PieEntry> entries = new ArrayList<>();
		for (Map.Entry<WalletItem, Double> entry : pieEntries.entrySet()) {
			WalletItem walletItem = entry.getKey();
			Double value = entry.getValue();
			entries.add(new PieEntry(value.floatValue(), walletItem.getName()));
		}

		ArrayList<Integer> colors = new ArrayList<>();
		for (int color : ColorTemplate.MATERIAL_COLORS) {
			colors.add(color);
		}

		for (int color : ColorTemplate.VORDIPLOM_COLORS) {
			colors.add(color);
		}

		PieDataSet dataSet = new PieDataSet(entries, "Portfolio");
		dataSet.setColors(colors);

		PieData data = new PieData(dataSet);
		data.setDrawValues(true);
		data.setValueFormatter(new PercentFormatter(this.portfolioPieChart));
		data.setValueTextSize(12f);
		data.setValueTextColor(Color.BLACK);

		this.portfolioPieChart.setData(data);
		this.portfolioPieChart.invalidate();

		this.portfolioPieChart.animateY(1400, Easing.EaseInOutQuad);
	}
}
