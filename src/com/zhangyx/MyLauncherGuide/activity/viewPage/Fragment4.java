package com.zhangyx.MyLauncherGuide.activity.viewPage;

import com.newgame.sdk.yyaost.R;
import com.zhangyx.MyLauncherGuide.utils.AnimationUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class Fragment4 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_4, container, false);
		view.findViewById(R.id.tvInNew).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// TODO Auto-generated method stub
//						startActivity(new Intent(getActivity(),
//								SuccessLaunchActivity.class));
						AnimationUtil.finishActivityAnimation(getActivity());
//						AnimationUtil.finishActivityAnimation(SplashActivity.this);
					}
				});
		return view;
	}

}
