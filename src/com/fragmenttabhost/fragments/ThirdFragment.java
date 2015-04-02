package com.fragmenttabhost.fragments;

import com.example.fragmenttabhost.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ThirdFragment extends Fragment {
	private Button btn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_main_third, container,
				false);
		findView(v);
		initView();
		return v;
	}

	private void findView(View v) {
		btn = (Button) v.findViewById(R.id.fragment_main_third_btn);

	}

	private void initView() {
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "btn third clicked",
						Toast.LENGTH_SHORT).show();

			}
		});

	}
}
