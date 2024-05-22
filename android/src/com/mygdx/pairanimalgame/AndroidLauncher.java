package com.mygdx.pairanimalgame;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import androidx.annotation.NonNull;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class AndroidLauncher extends AndroidApplication implements IAdsController{
	private AdView adView;
	private InterstitialAd interstitialAd;
	private RewardedAd mRewardedAd;
	private boolean isRewardedAdLoading = false;
	private Handler adHandler;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new ConnectAnimalGame(), config);
		adHandler = new Handler(Looper.getMainLooper());
		initializeRelativeLayout(config);
		loadInterstitialAd();
		loadRewardedAd();
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
	private void loadRewardedAd() {
		if (isRewardedAdLoading || mRewardedAd != null) {
			return;
		}

		isRewardedAdLoading = true;
		AdRequest adRequest = new AdRequest.Builder().build();
		RewardedAd.load(this, "ca-app-pub-2135695156084823/6501012249", adRequest, new RewardedAdLoadCallback() {
			@Override
			public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
				mRewardedAd = rewardedAd;
				isRewardedAdLoading = false;
			}

			@Override
			public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
				mRewardedAd = null;
				isRewardedAdLoading = false;
			}
		});
	}
	@Override
	public void showRewardedAd() {
		adHandler.post(()->{
			if (mRewardedAd != null) {
				mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
					@Override
					public void onAdDismissedFullScreenContent() {
						// Quảng cáo đã bị đóng
						mRewardedAd = null;
						loadRewardedAd();
					}

					@Override
					public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
						mRewardedAd = null;
						loadRewardedAd();
					}
				});
				mRewardedAd.show(this, new OnUserEarnedRewardListener() {
					@Override
					public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
						// Người dùng đã xem xong quảng cáo và nhận được phần thưởng
						// Bạn có thể xử lý phần thưởng tại đây
					}
				});

				// Sau khi hiển thị quảng cáo, tải lại quảng cáo mới
				mRewardedAd = null;
				loadRewardedAd();
			}
		});

	}

	@Override
	public boolean isRewardedAdLoaded() {
		return mRewardedAd != null;
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
