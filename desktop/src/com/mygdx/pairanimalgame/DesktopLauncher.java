package com.mygdx.pairanimalgame;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.pairanimalgame.PairAnimalGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher implements IAdsController{
	private static DesktopLauncher application;

	public static void main (String[] arg) {
		if (application == null) {
			application = new DesktopLauncher();
		}

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1280, 720);
		config.setTitle("PairAnimal Game");
		new Lwjgl3Application(new ConnectAnimalGame(application), config);
	}

	@Override
	public void showBannerAds(boolean show) {

	}

	@Override
	public void showInterstitialAd() {

	}

	@Override
	public boolean isInterstitialAdLoaded() {
		return false;
	}
}
