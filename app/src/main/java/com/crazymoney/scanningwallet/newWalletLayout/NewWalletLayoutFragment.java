package com.crazymoney.scanningwallet.newWalletLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.crazymoney.scanningwallet.R;
import com.crazymoney.scanningwallet.walletsLayout.WalletsLayoutActivity;
import com.crazymoney.scanningwallet.widget.HamburgerListMenu;
import com.crazymoney.scanningwallet.widget.OnSingleClickListener;
import com.google.zxing.integration.android.IntentIntegrator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.google.common.base.Preconditions.checkNotNull;

public class NewWalletLayoutFragment extends Fragment implements NewWalletLayoutContract.View {
	private static final String TAG = NewWalletLayoutFragment.class.getSimpleName();

	@BindView(R.id.hamburger_drawer_layout)
	protected DrawerLayout hamburgerDrawerLayout;
	@BindView(R.id.hamburger_menu_left_drawer)
	protected ListView hamburgerDrawerList;
	@BindView(R.id.toolbar)
	protected Toolbar toolbar;
	@BindView(R.id.new_wallet_name)
	protected EditText nameEditText;
	@BindView(R.id.new_wallet_address)
	protected EditText addressEditText;
	@BindView(R.id.new_wallet_camera_button)
	protected ImageButton cameraImageButton;
	@BindView(R.id.radio_eth)
	protected RadioButton ethRadioButton;
	@BindView(R.id.radio_bsc)
	protected RadioButton bscRadioButton;
	@BindView(R.id.radio_fantom)
	protected RadioButton fantomRadioButton;
	@BindView(R.id.radio_avalanche)
	protected RadioButton avalancheRadioButton;
	@BindView(R.id.radio_matic)
	protected RadioButton maticRadioButton;
	@BindView(R.id.radio_moonbeam)
	protected RadioButton moonbeamRadioButton;
	@BindView(R.id.new_wallet_save)
	protected Button saveButton;

	private HamburgerListMenu hamburgerListMenu;
	private NewWalletLayoutContract.Presenter presenter;

	public static NewWalletLayoutFragment newInstance() {
		return new NewWalletLayoutFragment();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(
				R.layout.fragment_new_wallet,
				container,
				false
		);
		ButterKnife.bind(this, view);
		this.drawTittle();
		this.setUpHamburgerMenu();
		this.setupTextListeners();
		this.setupCameraListeners();
		this.setSaveButtonOnClick();
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
	public void setPresenter(@NonNull NewWalletLayoutContract.Presenter presenter) {
		this.presenter = checkNotNull(presenter);
	}

	@Override
	public void goBack() {
		Intent intent = new Intent(super.getActivity(), WalletsLayoutActivity.class);
		super.startActivity(intent);
	}

	@Override
	public void setAddress(String address) {
		this.addressEditText.setText(address);
	}

	@OnClick(R.id.hamburger_icon)
	protected void hamburgerIconOnClick() {
		this.hamburgerDrawerLayout.openDrawer(Gravity.LEFT);
	}

	public void drawTittle() {
		TextView titleTextView = this.toolbar.findViewById(R.id.toolbar_title);
		titleTextView.setText(super.getActivity().getString(R.string.create_a_new_wallet));
	}

	private void setUpHamburgerMenu() {
		this.hamburgerListMenu = new HamburgerListMenu(
				super.getActivity(),
				this.hamburgerDrawerLayout,
				this.hamburgerDrawerList
		);
		this.hamburgerListMenu.setUpHamburgerMenu();
	}

	private void setupTextListeners() {
		this.saveButton.setEnabled(false);
		this.nameEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				setEnableButton(TextUtils.isEmpty(s.toString().trim()));
			}
		});
		this.addressEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				setEnableButton(TextUtils.isEmpty(s.toString().trim()));
			}
		});
	}

	private void setupCameraListeners() {
		this.cameraImageButton.setOnClickListener(new OnSingleClickListener() {
			@Override
			public void onSingleClick(View v) {
				new IntentIntegrator(getActivity()).initiateScan();
			}
		});
	}

	private void setEnableButton(boolean isEmptyText) {
		int network = this.getNetwork();
		if (network < 0) {
			this.saveButton.setEnabled(false);
		} else {
			this.saveButton.setEnabled(!isEmptyText);
		}
	}

	private void setSaveButtonOnClick() {
		this.saveButton.setOnClickListener(new OnSingleClickListener() {
			@Override
			public void onSingleClick(View v) {
				saveNewAddress();
			}
		});
	}

	private void saveNewAddress() {
		String name = this.nameEditText.getText().toString();
		String address = this.addressEditText.getText().toString();
		int network = this.getNetwork();
		this.presenter.saveAddress(name, address, network);
	}

	private int getNetwork() {
		boolean isETH = this.ethRadioButton.isChecked();
		boolean isBSC = this.bscRadioButton.isChecked();
		boolean isFantom = this.fantomRadioButton.isChecked();
		boolean isAvalanche = this.avalancheRadioButton.isChecked();
		boolean isMatic = this.maticRadioButton.isChecked();
		boolean isMoonbeam = this.moonbeamRadioButton.isChecked();
		return this.presenter.getNetwork(isETH, isBSC, isFantom, isAvalanche, isMatic, isMoonbeam);
	}
}
