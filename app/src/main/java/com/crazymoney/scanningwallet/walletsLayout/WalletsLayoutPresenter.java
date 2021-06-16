package com.crazymoney.scanningwallet.walletsLayout;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.crazymoney.scanningwallet.database.Repository;
import com.crazymoney.scanningwallet.database.table.Wallet;

import java.lang.ref.WeakReference;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class WalletsLayoutPresenter implements WalletsLayoutContract.Presenter {
	private static final String TAG = WalletsLayoutPresenter.class.getSimpleName();

	private WalletsLayoutContract.View view;
	private Context context;
	private Repository repository;

	public WalletsLayoutPresenter(Context context,
								  WalletsLayoutContract.View view,
								  Repository repository) {
		this.context = checkNotNull(context, "context cannot be null!");
		this.view = checkNotNull(view, "view cannot be null!");
		this.repository = checkNotNull(repository, "view cannot be null!");
		this.view.setPresenter(this);
	}

	@Override
	public void start() {
		new GetWalletsFromDatabaseToDrawTask(this.view, this.repository).execute();
	}

	@Override
	public void deleteWallet(Wallet wallet) {
		this.repository.deleteWallet(wallet);
		new GetWalletsFromDatabaseToDrawTask(this.view, this.repository).execute();
	}

	@Override
	public void goToWalletDetailScreen(Wallet wallet) {
		this.view.goToWalletDetailScreen(wallet);
	}

	private static class GetWalletsFromDatabaseToDrawTask extends AsyncTask<Void, Void, List<Wallet>> {
		private final WeakReference<WalletsLayoutContract.View> viewWeakReference;
		private final WeakReference<Repository> repositoryWeakReference;

		public GetWalletsFromDatabaseToDrawTask(WalletsLayoutContract.View view,
												Repository repository) {
			this.viewWeakReference = new WeakReference<>(view);
			this.repositoryWeakReference = new WeakReference<>(repository);
		}

		@Override
		protected List<Wallet> doInBackground(Void... voids) {
			Repository repository = this.repositoryWeakReference.get();
			List<Wallet> wallets = repository.getWallets();
			return wallets;
		}

		@Override
		protected void onPostExecute(List<Wallet> wallets) {
			super.onPostExecute(wallets);
			WalletsLayoutContract.View view = this.viewWeakReference.get();
			view.drawWallets(wallets);
		}
	}
}
