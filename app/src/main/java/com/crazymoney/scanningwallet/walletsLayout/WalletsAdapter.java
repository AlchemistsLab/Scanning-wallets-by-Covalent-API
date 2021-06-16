package com.crazymoney.scanningwallet.walletsLayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.crazymoney.scanningwallet.R;
import com.crazymoney.scanningwallet.database.table.Wallet;
import com.crazymoney.scanningwallet.widget.OnSingleClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	public static final String TAG = WalletsAdapter.class.getSimpleName();

	private WalletsLayoutContract.Presenter presenter;
	private List<Wallet> wallets;

	public WalletsAdapter(WalletsLayoutContract.Presenter presenter, List<Wallet> wallets) {
		this.presenter = presenter;
		this.wallets = wallets;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		View view;
		view = LayoutInflater.from(context).inflate(R.layout.row_wallet, parent, false);
		return new WalletViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
		Wallet wallet = this.wallets.get(position);
		WalletViewHolder holder = (WalletViewHolder) viewHolder;
		holder.nameTextView.setText(wallet.getName());
		holder.addressTextView.setText(wallet.getAddress());
		holder.networkTextView.setText(wallet.getNetworkName());
		holder.deleteRelativeLayout.setOnClickListener(new OnSingleClickListener() {
			@Override
			public void onSingleClick(View v) {
				presenter.deleteWallet(wallet);
			}
		});
		holder.contentLayout.setOnClickListener(new OnSingleClickListener() {
			@Override
			public void onSingleClick(View v) {
				presenter.goToWalletDetailScreen(wallet);
			}
		});
	}

	@Override
	public int getItemCount() {
		if (this.wallets == null || this.wallets.size() <= 0) {
			return 0;
		}
		return this.wallets.size();
	}

	public class WalletViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.row_wallet_content)
		public LinearLayout contentLayout;
		@BindView(R.id.row_wallet_name)
		public TextView nameTextView;
		@BindView(R.id.row_wallet_address)
		public TextView addressTextView;
		@BindView(R.id.row_wallet_network)
		public TextView networkTextView;
		@BindView(R.id.row_wallet_delete)
		public RelativeLayout deleteRelativeLayout;

		public WalletViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}
