package com.fragmenttabhost.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fragmenttabhost.MainActivity;
import com.example.fragmenttabhost.R;

public class FirstFragment extends Fragment {
	private Button btn;
	private GestureDetector mGestureDetector;
	private boolean isHide =false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_main_frist, container,
				false);
		findView(v);
		initView(v);
		return v;
	}


	private void findView(View v) {
		btn = (Button) v.findViewById(R.id.fragment_main_first_btn);

	}

	private void initView(View v) {
		mGestureDetector = new GestureDetector(getActivity(),
				new MyOnGestureListener());
		v.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mGestureDetector.onTouchEvent(event);
				return true;
			}
		});
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Toast.makeText(getActivity(), "btn first clicked",
						Toast.LENGTH_SHORT).show();

			}
		});

	}

	class MyOnGestureListener extends SimpleOnGestureListener {

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if(velocityY<300&&!isHide){
				((MainActivity)getActivity()).hideTab();
				isHide = true;
			}
			if(velocityY>10&&isHide){
				((MainActivity)getActivity()).showTab();
				isHide = false;
			}
			return false;
		}

	}
}
