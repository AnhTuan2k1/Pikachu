package com.mygdx.pairanimalgame;

public interface IAdsController {
    void showBannerAds(boolean show);
    void showInterstitialAd();
    boolean isInterstitialAdLoaded();
    void showRewardedAd();
    boolean isRewardedAdLoaded();
}
