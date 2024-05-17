package com.mygdx.pairanimalgame;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class AndroidLauncher extends AndroidApplication {
	protected AdView adView;

	//protected View gameView;
	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;

	protected Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case SHOW_ADS:
				{
					adView.setVisibility(View.VISIBLE);
					break;
				}
				case HIDE_ADS:
				{
					adView.setVisibility(View.GONE);
					break;
				}
			}
		}
	};
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new ConnectAnimalGame(), config);

		initializeRelativeLayout(config);
	}

	private void initializeRelativeLayout(AndroidApplicationConfiguration config) {
		View gameView = initializeForView(new ConnectAnimalGame(), config);

		// Khởi tạo AdMob
		MobileAds.initialize(this, initializationStatus -> {});

		// Tạo một AdView và đặt các thuộc tính
		adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId("ca-app-pub-2135695156084823/6674464140"); // Thay thế bằng Banner Ad Unit ID của bạn

		// Yêu cầu quảng cáo
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);

		// Thêm AdView vào layout
		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT
		);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		layout.addView(adView, adParams);

		setContentView(layout);
	}

	public void showAd() {
		adView.setVisibility(View.VISIBLE);
	}

	public void hideAd() {
		adView.setVisibility(View.GONE);
	}
}
