package com.mygdx.pairanimalgame;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import androidx.annotation.NonNull;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class AndroidLauncher extends AndroidApplication implements IAdsController{
	private AdView adView;
	private InterstitialAd interstitialAd;
	private Handler adHandler;

	//protected View gameView;
	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;

	protected Handler handler = new Handler(Looper.getMainLooper())
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
		adHandler = new Handler(Looper.getMainLooper());
		initializeRelativeLayout(config);
		loadInterstitialAd();
	}

	private void initializeRelativeLayout(AndroidApplicationConfiguration config) {
		View gameView = initializeForView(new ConnectAnimalGame(this), config);

		// Khởi tạo AdMob
		MobileAds.initialize(this, initializationStatus -> {});

		// Tạo một AdView và đặt các thuộc tính
		adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId("ca-app-pub-2135695156084823/6674464140");

		// Yêu cầu quảng cáo
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);

		// Thêm AdView, gameView vào layout
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

	private void loadInterstitialAd() {
		AdRequest adRequest = new AdRequest.Builder().build();
		InterstitialAd.load(this, "ca-app-pub-2135695156084823/9144515126", adRequest, new InterstitialAdLoadCallback() {
			@Override
			public void onAdLoaded(@NonNull InterstitialAd ad) {
				// The interstitial ad was loaded.
				interstitialAd = ad;
			}
			@Override
			public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
				// Handle the error
				interstitialAd = null;
			}

		});
	}

	public void showInterstitialAd() {
		adHandler.post(() -> {
            if (interstitialAd != null) {
                interstitialAd.show(AndroidLauncher.this);
                // Sau khi hiển thị, tải quảng cáo mới
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Quảng cáo đã bị đóng
                        interstitialAd = null;
                        loadInterstitialAd(); // Tải quảng cáo mới
                    }
                });
            } else {
                loadInterstitialAd(); // Tải quảng cáo nếu chưa có sẵn
            }
        });
	}

	@Override
	public boolean isInterstitialAdLoaded() {
		return interstitialAd != null;
	}

	@Override
	public void showBannerAds(boolean isShow) {
		//handler.sendEmptyMessage(isShow ? SHOW_ADS : HIDE_ADS);
		adHandler.post(() -> adView.setVisibility(isShow ? View.VISIBLE : View.GONE));
		/*new Thread(() -> {
            try {
                Thread.sleep(isShow?0:1000);
                Gdx.app.postRunnable(() -> {
                    adView.setVisibility(isShow ? View.VISIBLE : View.GONE);
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();*/
	}
}
