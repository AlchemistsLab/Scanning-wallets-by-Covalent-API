package com.crazymoney.scanningwallet.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.crazymoney.scanningwallet.R;

import java.util.List;

public class HamburgerMenuAdapter extends ArrayAdapter<HamburgerMenuItem> {
	private Context context;

	public HamburgerMenuAdapter(Context context, List<HamburgerMenuItem> hamburgerMenu) {
		super(context, 0, hamburgerMenu);
		this.context = context;
	}

	@Override
	public View getView(int position, View rowView, ViewGroup parent) {
		HamburgerMenuItem menu = getItem(position);
		LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE
		);
		if (menu.isASeparator()) {
			rowView = inflater.inflate(R.layout.hamburger_menu_divider, parent, false);
		} else {
			rowView = inflater.inflate(R.layout.hamburger_menu_item, parent, false);
			TextView itemName = rowView.findViewById(R.id.hamburger_menu_item_name);
			itemName.setText(menu.getItemName());
			itemName.setTag(menu.getItemName());
		}
		return rowView;
	}
}
