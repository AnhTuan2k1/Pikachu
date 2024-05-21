package com.mygdx.pairanimalgame;

import java.util.LinkedList;

public class GameManager {
    private LinkedList<IGameObserver> observers;
    private boolean isPaused;
    private static GameManager instance;
    public static GameManager getInstance(){
        if(instance == null) return new GameManager();
        else return instance;
    }
    private GameManager() {
        this.observers = new LinkedList<>();
        this.isPaused = false;
        instance = this;
    }
    public boolean isPaused() {
        return isPaused;
    }
    public void setPaused(boolean paused) {
        isPaused = paused;
        notifyObservers();
        ConnectAnimalGame.getInstance().getAdsController().showBannerAds(paused);
    }

    public void onGamePaused()
    {
        if(!isPaused) setPaused(true);
    }

    public void onGameResume()
    {
        if(isPaused) setPaused(false);
    }

    private void notifyObservers()
    {
        for (IGameObserver observer: observers) {
            observer.OnGamePaused(isPaused);
        }
    }

    public void registerObserver(IGameObserver observer)
    {
        observers.add(observer);
    }

    public void unregisterObserver(IGameObserver observer)
    {
        observers.remove(observer);
    }

    public void clear() {
        observers.clear();
    }
}
