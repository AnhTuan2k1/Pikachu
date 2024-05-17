package com.mygdx.pairanimalgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
    static Music backgroundMusic;
    static AssetManager assetMN;
    static final String click = "audio/sound/click.mp3";
    static final String click1 = "audio/sound/click1.mp3";
    static final String e1 = "audio/sound/e1.mp3";
    static final String e2 = "audio/sound/e2.mp3";
    static final String e3 = "audio/sound/e3.mp3";
    static final String e4 = "audio/sound/e4.mp3";
    static final String e5 = "audio/sound/e5.mp3";
    static final String e6 = "audio/sound/e6.mp3";
    static final String entry = "audio/sound/entry.mp3";
    static final String ready_go = "audio/sound/ready_go.mp3";
    public static void loadSound(AssetManager assetMN){
        AudioManager.assetMN = assetMN;

        assetMN.load(click, Sound.class);
        assetMN.load(click1, Sound.class);
        assetMN.load(e1, Sound.class);
        assetMN.load(e2, Sound.class);
        assetMN.load(e3, Sound.class);
        assetMN.load(e4, Sound.class);
        assetMN.load(e5, Sound.class);
        assetMN.load(e6, Sound.class);
        assetMN.load(entry, Sound.class);
        assetMN.load("audio/sound/excellent.mp3", Sound.class);
        assetMN.load("audio/sound/glass.mp3", Sound.class);
        assetMN.load("audio/sound/great.mp3", Sound.class);
        assetMN.load("audio/sound/hammer_on.mp3", Sound.class);
        assetMN.load("audio/sound/hammer_ready.mp3", Sound.class);
        assetMN.load("audio/sound/lose.mp3", Sound.class);
        assetMN.load("audio/sound/match.mp3", Sound.class);
        assetMN.load("audio/sound/nice.mp3", Sound.class);
        assetMN.load("audio/sound/noel0.mp3", Sound.class);
        assetMN.load("audio/sound/noel1.mp3", Sound.class);
        assetMN.load("audio/sound/noel2.mp3", Sound.class);
        assetMN.load("audio/sound/perfect.mp3", Sound.class);
        assetMN.load("audio/sound/pick.mp3", Sound.class);
        assetMN.load(ready_go, Sound.class);
        assetMN.load("audio/sound/rocket_launch.mp3", Sound.class);
        assetMN.load("audio/sound/rocketlaunch.mp3", Sound.class);
        assetMN.load("audio/sound/rocketonboard.mp3", Sound.class);
        assetMN.load("audio/sound/score.mp3", Sound.class);
        assetMN.load("audio/sound/unbelievable.mp3", Sound.class);
        assetMN.load("audio/sound/unlockrank.mp3", Sound.class);
        assetMN.load("audio/sound/win.mp3", Sound.class);
    }

    public static void playSound(String soundName){
        assetMN.get(soundName, Sound.class).play();
    }
    public static void play_eSound(int sound){
        switch (sound){
            case 1:
                assetMN.get(e1, Sound.class).play();
                break;
            case 2:
                assetMN.get(e2, Sound.class).play();
                break;
            case 3:
                assetMN.get(e3, Sound.class).play();
                break;
            case 4:
                assetMN.get(e4, Sound.class).play();
                break;
            case 5:
                assetMN.get(e5, Sound.class).play();
                break;
            default: assetMN.get(e6, Sound.class).play();
        }

    }
    public static void playMusic(){
        if(backgroundMusic == null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/music/bg.mp3"));
                    backgroundMusic.setLooping(true);
                    backgroundMusic.play();
                }
            }).start();

        }
        else if(!backgroundMusic.isPlaying()) backgroundMusic.play();
    }

    public static void stopMusic(){
        if(backgroundMusic != null) backgroundMusic.stop();
    }

    public static void pauseMusic(){
        if(backgroundMusic != null) backgroundMusic.pause();
    }
}
