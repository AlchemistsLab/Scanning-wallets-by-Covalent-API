package com.crazymoney.scanningwallet.walletDetailLayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.crazymoney.scanningwallet.R;
import com.crazymoney.scanningwallet.database.table.WalletItem;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WalletItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	public static final String TAG = WalletItemsAdapter.class.getSimpleName();

	private List<WalletItem> items;

	public WalletItemsAdapter(List<WalletItem> items) {
		this.items = items;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Context context = parent.getContext();
		View view = LayoutInflater.from(context).inflate(R.layout.row_wallet_item, parent, false);
		return new WalletItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
		WalletItem item = this.items.get(position);
		WalletItemViewHolder holder = (WalletItemViewHolder) viewHolder;

		holder.nameTextView.setText(item.getName());
		holder.logoView.setImageURI(item.getLogo());
		holder.balanceTextView.setText(item.formatBalance());
		holder.usdValueTextView.setText("≈ " + item.formatUsdValue() + " USD");
	}

	@Override
	public int getItemCount() {
		if (this.items == null || this.items.size() <= 0) {
			return 0;
		}
		return this.items.size();
	}

	public class WalletItemViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.row_wallet_item_name)
		public TextView nameTextView;
		@BindView(R.id.row_wallet_item_logo)
		public SimpleDraweeView logoView;
		@BindView(R.id.row_wallet_item_balance)
		public TextView balanceTextView;
		@BindView(R.id.row_wallet_item_usd_value)
		public TextView usdValueTextView;

		public WalletItemViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}
	}
}
