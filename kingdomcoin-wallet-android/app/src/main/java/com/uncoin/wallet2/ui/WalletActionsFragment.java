/*
 * Copyright 2013-2014 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.uncoin.wallet2.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.google.bitcoin.core.Address;
import com.uncoin.wallet2.R;
import com.uncoin.wallet2.WalletApplication;

/**
 * @author Andreas Schildbach, Litecoin Dev Team
 */
public final class WalletActionsFragment extends Fragment
{
	private WalletActivity activity;

	@Override
	public void onAttach(final Activity activity)
	{
		super.onAttach(activity);

		this.activity = (WalletActivity) activity;
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
	{
		final View view = inflater.inflate(R.layout.wallet_actions_fragment, container);

		final Address selectedAddress = ((WalletApplication) activity.getApplication()).determineSelectedAddress();
		final String address  = selectedAddress.toString();

		final View snsButton = view.findViewById(R.id.button1);
		snsButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);

				final String address_string = address.replaceAll(" ","").replaceAll("\n","").replaceAll("\t","").replaceAll(" ","");

				sendIntent.putExtra(Intent.EXTRA_TEXT, address_string);
				sendIntent.setType("text/plain");
				startActivity(sendIntent);
			}
		});

		final View requestButton = view.findViewById(R.id.wallet_actions_request);
		requestButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				activity.handleRequestCoins();
			}
		});

		final View sendButton = view.findViewById(R.id.wallet_actions_send);
		sendButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				activity.handleSendCoins();
			}
		});

		final View sendQrButton = view.findViewById(R.id.wallet_actions_send_qr);
		sendQrButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				activity.handleScan();
			}
		});

		final View settingBtn = view.findViewById(R.id.settingBtn);
		settingBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(final View v)
			{
				startActivity(new Intent(v.getContext(), PreferencesActivity.class));
			}
		});


		return view;
	}

	@Override
	public void onResume()
	{
		super.onResume();

		updateView();
	}

	private void updateView()
	{
		final boolean showActions = !getResources().getBoolean(R.bool.wallet_actions_top);

		final View view = getView();
		final ViewParent parent = view.getParent();
		final View fragment = parent instanceof FrameLayout ? (FrameLayout) parent : view;
		fragment.setVisibility(showActions ? View.VISIBLE : View.GONE);
	}
}
