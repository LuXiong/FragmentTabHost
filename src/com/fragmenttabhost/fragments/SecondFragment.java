package com.fragmenttabhost.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fragmenttabhost.R;

public class SecondFragment extends Fragment {
	private Button btn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_main_second, container,
				false);
		findView(v);
		initView();
		return v;
	}

	private void findView(View v) {
		btn = (Button) v.findViewById(R.id.fragment_main_second_btn);

	}

	private void initView() {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "btn second clicked",
						Toast.LENGTH_SHORT).show();

			}
		});

	}
}
