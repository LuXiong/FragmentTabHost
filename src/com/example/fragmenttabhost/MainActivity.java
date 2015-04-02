package com.example.fragmenttabhost;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.fragmenttabhost.fragments.FirstFragment;
import com.fragmenttabhost.fragments.SecondFragment;
import com.fragmenttabhost.fragments.ThirdFragment;

public class MainActivity extends FragmentActivity {

	private final static String TAB_FIRST = "firstTab";
	private final static String TAB_SECOND = "secondTab";
	private final static String TAB_THIRD = "thirdTab";

	private LinearLayout mTitleLayout;
	private TabWidget mTabWidget;
	private Bundle mSavedInstanceState;
	private TabManager mTabManager;
	private TabHost mTabHost;
	private long mExitTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.mSavedInstanceState = savedInstanceState;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findView();
		initView();
	}

	private void findView() {
		mTitleLayout = (LinearLayout) findViewById(R.id.up_layout);
		mTabWidget = (TabWidget) findViewById(android.R.id.tabs);
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		mTabManager = new TabManager(this, mTabHost, android.R.id.tabcontent);
		mTabManager.addTab(
				mTabHost.newTabSpec(TAB_FIRST).setIndicator(
						View.inflate(MainActivity.this,
								R.layout.tab_host_frist, null)),
				MainActivity.MFristFragment.class, null);
		mTabManager.addTab(
				mTabHost.newTabSpec(TAB_SECOND).setIndicator(
						View.inflate(MainActivity.this,
								R.layout.tab_host_second, null)),
				MainActivity.MSecondFragment.class, null);
		mTabManager.addTab(
				mTabHost.newTabSpec(TAB_THIRD).setIndicator(
						View.inflate(MainActivity.this,
								R.layout.tab_host_third, null)),
				MainActivity.MThirdFragment.class, null);
	}

	private void initView() {
		Intent intent = getIntent();
		String tag = TAB_THIRD;
		if (this.mSavedInstanceState != null) {
			tag = this.mSavedInstanceState.getString("tab");
		}
		if (intent != null) {
			tag = intent.getStringExtra(TAB_THIRD);
		}
		if (TextUtils.isEmpty(tag)) {
			tag = TAB_THIRD;
		}
		mTabHost.setCurrentTabByTag(tag);

	}

	public void showTab() {
		TranslateAnimation tta = new TranslateAnimation(0f, 0f, -200f, 0f);
		tta.setDuration(500);
		tta.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation arg0) {
				mTitleLayout.setVisibility(View.VISIBLE);
				mTabWidget.setVisibility(View.VISIBLE);
				
			}
			
			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		tta.setFillAfter(true);
		TranslateAnimation bta = new TranslateAnimation(0f, 0, 100f, 0f);
		bta.setDuration(500);
		bta.setFillAfter(true);
		mTitleLayout.startAnimation(tta);
		mTabWidget.startAnimation(bta);
	}

	public void hideTab() {
		TranslateAnimation tta = new TranslateAnimation(0f, 0f, 0f, -100.0f);
		tta.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation anim) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation anim) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation anim) {
				mTitleLayout.setVisibility(View.GONE);
				mTabWidget.setVisibility(View.GONE);

			}
		});
		tta.setDuration(500);
		tta.setFillAfter(true);
		TranslateAnimation bta = new TranslateAnimation(0f, 0f, 0f, 200.0f);
		bta.setDuration(500);
		bta.setFillAfter(true);

		mTitleLayout.startAnimation(tta);
		mTabWidget.startAnimation(bta);
	}

	public static class MFristFragment extends FirstFragment {

	}

	public static class MSecondFragment extends SecondFragment {

	}

	public static class MThirdFragment extends ThirdFragment {

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}

	public static class TabManager implements TabHost.OnTabChangeListener {
		private final MainActivity mActivity;
		private final TabHost mTabHost;
		private final int mContainerId;
		private final HashMap<String, TabInfo> mTabs = new HashMap<String, TabInfo>();
		TabInfo mLastTab;

		static final class TabInfo {
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;
			private Fragment fragment;

			TabInfo(String _tag, Class<?> _class, Bundle _args) {
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		static class DummyTabFactory implements TabHost.TabContentFactory {
			private final Context mContext;

			public DummyTabFactory(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		public TabManager(MainActivity activity, TabHost tabHost,
				int containerId) {
			mActivity = activity;
			mTabHost = tabHost;
			mContainerId = containerId;
			mTabHost.setOnTabChangedListener(this);
		}

		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
			String tag = tabSpec.getTag();
			tabSpec.setContent(new DummyTabFactory(mActivity));
			TabInfo info = new TabInfo(tag, clss, args);
			info.fragment = mActivity.getSupportFragmentManager()
					.findFragmentByTag(tag);
			if (info.fragment != null && !info.fragment.isHidden()) {
				FragmentTransaction ft = mActivity.getSupportFragmentManager()
						.beginTransaction();
				ft.hide(info.fragment);
				ft.commit();
			}

			mTabs.put(tag, info);

			mTabHost.addTab(tabSpec);
		}

		@Override
		public void onTabChanged(String tabId) {

			TabInfo newTab = mTabs.get(tabId);

			if (mLastTab != newTab) {
				FragmentTransaction ft = mActivity.getSupportFragmentManager()
						.beginTransaction();
				if (mLastTab != null) {
					if (mLastTab.fragment != null) {
						ft.hide(mLastTab.fragment);

					}
				}
				if (newTab != null) {
					if (newTab.fragment == null) {
						newTab.fragment = Fragment.instantiate(mActivity,
								newTab.clss.getName(), newTab.args);
						ft.add(mContainerId, newTab.fragment, newTab.tag);
					} else {
						ft.show(newTab.fragment);
					}
				}

				mLastTab = newTab;
				ft.commit();
				mActivity.getSupportFragmentManager()
						.executePendingTransactions();
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();

			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
