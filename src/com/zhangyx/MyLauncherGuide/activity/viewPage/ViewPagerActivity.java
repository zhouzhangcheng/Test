package com.zhangyx.MyLauncherGuide.activity.viewPage;

import java.util.ArrayList;
import java.util.List;

import com.newgame.sdk.yyaost.MainActivity;
import com.newgame.sdk.yyaost.R;
import com.newgame.sdk.yyaost.VersionActivity;
import com.st.LauncherGuide.splash.SplashActivity;
import com.zhangyx.MyLauncherGuide.utils.AnimationUtil;
import com.zxing.activity.CaptureActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
/**
 * ViewPager 寮曞
 *com.zhangyx.MyLauncherGuide.activity.viewPage.ViewPagerActivity
 * @author Admin-zhangyx
 *
 * create at 2015-1-21 涓嬪崍4:24:29
 */
public class ViewPagerActivity extends FragmentActivity {
	private ViewPager viewPager = null;
	private ImageView img1, img2, img3,img4;
	private ArrayList<String> titles;
	private int currIndex = -1;
	private ImageView toindex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);
		initWidgets();
		// 把要显示的View装入数组
		LayoutInflater li = LayoutInflater.from(this);
		View view1 = li.inflate(R.layout.pager1, null);
		View view2 = li.inflate(R.layout.pager2, null);
		View view3 = li.inflate(R.layout.pager3, null);
		View view4 = li.inflate(R.layout.pager4, null);

		// 添加页面
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view4);
		views.add(view1);
		views.add(view2);
		views.add(view3);
		
		toindex = (ImageView) findViewById(R.id.toindex);
		toindex.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				startActivity(new Intent(ViewPagerActivity.this,
//						VersionActivity.class));
				finish();
			}
		});

		// 添加标题
		titles = new ArrayList<String>();
		titles.add("tab4");
		titles.add("tab1");
		titles.add("tab2");
		titles.add("tab3");

		picViewPagerAdapter pagerAdapter = new picViewPagerAdapter(views);
		viewPager.setAdapter(pagerAdapter);
		currIndex = 0;
		viewPager.setCurrentItem(currIndex);
		img4.setImageResource(R.drawable.page_icon_sel);
		
		view3.findViewById(R.id.tvInNew).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// TODO Auto-generated method stub
//						startActivity(new Intent(getActivity(),
//								SuccessLaunchActivity.class));
//						startActivity(new Intent(ViewPagerActivity.this,
//								VersionActivity.class));
						finish();
//						AnimationUtil.finishActivityAnimation(SplashActivity.this);
					}
				});
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				switch (arg0) {
				case 0:
					img4.setImageResource(R.drawable.page_icon_sel);
					img1.setImageResource(R.drawable.page_icon);
					img2.setImageResource(R.drawable.page_icon);
					img3.setImageResource(R.drawable.page_icon);
					break;
				case 1:
					img1.setImageResource(R.drawable.page_icon_sel);
					img4.setImageResource(R.drawable.page_icon);
					img2.setImageResource(R.drawable.page_icon);
					img3.setImageResource(R.drawable.page_icon);
					break;
				case 2:
					img2.setImageResource(R.drawable.page_icon_sel);
					img4.setImageResource(R.drawable.page_icon);
					img3.setImageResource(R.drawable.page_icon);
					img1.setImageResource(R.drawable.page_icon);
					break;
				case 3:
					img3.setImageResource(R.drawable.page_icon_sel);
					img4.setImageResource(R.drawable.page_icon);
					img2.setImageResource(R.drawable.page_icon);
					img1.setImageResource(R.drawable.page_icon);
					break;
				default:
					break;
				}
				currIndex = arg0;
				System.out.println("[MainActivity->]currIndex = " + currIndex);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initWidgets() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		img4 = (ImageView) findViewById(R.id.icon_4);
		img1 = (ImageView) findViewById(R.id.icon_1);
		img2 = (ImageView) findViewById(R.id.icon_2);
		img3 = (ImageView) findViewById(R.id.icon_3);
	}

	/**
	 * 为ViewPager添加适配器
	 * 
	 * @author Administrator
	 * 
	 */
	class picViewPagerAdapter extends PagerAdapter {

		private List<View> listViews;

		public picViewPagerAdapter(List<View> list) {
			listViews = list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return listViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(listViews.get(position));
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titles.get(position);
		}

		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(listViews.get(position));
			return listViews.get(position);
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN)
		{
			System.exit(0);
		}
		return super.onKeyDown(keyCode, event);
	}
}
