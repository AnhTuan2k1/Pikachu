package com.mygdx.pairanimalgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    private final ConnectAnimalGame game;
    private final AssetManager assetMN;
    public final Stage stage;
    private TextureAtlas animalAtlas;
    private TextureAtlas playAtlas;
    private TextureAtlas commonAtlas;
    public TextureAtlas getPlayAtlas() {
        return playAtlas;
    }
    private EffectMN effectMN;
    private AnimalMatrix animalMatrix;
    public AnimalMatrix getAnimalMatrix() {
        return animalMatrix;
    }
    private GameData gameData;
    private TimerBar timerbar;
    private PopupDialog pauseWindow;
    private static GameScreen instance;
    public static GameScreen getInstance(){
        if(instance == null)
            throw new IllegalArgumentException("GameScreen is not initialized yet");
        else return instance;
    }

    private GameScreen(ConnectAnimalGame game) {
        System.out.println("GameScreen create");
        this.game = game;
        this.assetMN = game.getAssetMN();
        this.stage = game.getStage();
        GameScreen.instance = this;
    }
    public GameScreen(ConnectAnimalGame game, GameData gameData) {
        this(game);
        this.gameData = gameData;
    }
    @Override
    public void show() {
        System.out.println("GameScreen show");
        assetMN.finishLoading();

        animalAtlas = assetMN.get("texture/animal.atlas", TextureAtlas.class);
        playAtlas = assetMN.get("texture/play.atlas", TextureAtlas.class);
        commonAtlas = assetMN.get("texture/common.atlas", TextureAtlas.class);
        effectMN = effectMN == null ? new EffectMN(assetMN) : effectMN;

        animalMatrix = new AnimalMatrix(stage, animalAtlas, effectMN, gameData);
        createUI();
        //animalMatrix.adjustOderDraw();

        ConnectAnimalGame.getInstance().filterAsset();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.1f, 1);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("GameScreen resize");
    }

    @Override
    public void pause() {
        System.out.println("GameScreen pause");
        GameData.save(new GameData(animalMatrix));
    }
    @Override
    public void resume() {
        System.out.println("GameScreen resume");
    }

    @Override
    public void hide() {
        System.out.println("GameScreen hide");
        GameData.save(new GameData(animalMatrix));
        animalMatrix = null;
        animalAtlas = null;
        playAtlas = null;
        stage.clear();
        GameManager.getInstance().clear();
    }

    @Override
    public void dispose() {
        System.out.println("GameScreen dispose");
        effectMN.dispose();
    }

    private void createUI() {
        VerticalGroup verticalGroup = new MyVerticalGroup(stage);
        verticalGroup.addActor(createHintButton());
        verticalGroup.addActor(createRandomButton());
        verticalGroup.addActor(createRocketButton());

        stage.addActor(verticalGroup);
        stage.addActor(createTimeBar());
        stage.addActor(createPauseButton());
        stage.addActor(createPauseButton2());
        stage.addActor(createScoreTextLabel());
        stage.addActor(createScoreLabel());
        stage.addActor(createLevelTextLabel());
        stage.addActor(createPauseWindow());
        stage.addActor(createBackGround());
    }

    private Actor createPauseWindow(){
        // right button for dialog
        MyButton left = new MyButton(commonAtlas.findRegion("btn_red"));
        left.setText("Exit");
        left.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseWindow.hideDialog(()->game.setScreen(new MainMenuScreen(game)));
            }
        });

        // left button for dialog
        MyButton right = new MyButton(commonAtlas.findRegion("btn_yellow"));
        right.setText("Resume");
        right.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pauseWindow.hideDialog();
            }
        });

        // middle setting content
        Table contentTable = new Table();
        contentTable.add(new ToggleButton(playAtlas.findRegion("sound1"),
                playAtlas.findRegion("sound2"))).pad(10);
        contentTable.add(new ToggleButton(playAtlas.findRegion("vibrate1"),
                playAtlas.findRegion("vibrate2"))).pad(10);
        contentTable.add(new ToggleButton(playAtlas.findRegion("Music1"),
                playAtlas.findRegion("Music2"))).pad(10);

        ScrollPane scrollPane = new ScrollPane(contentTable);
        scrollPane.setScrollingDisabled(false, true); // Chỉ cho phép cuộn ngang

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.add(scrollPane).expandX().fillX(); // Mở rộng và lấp đầy theo chiều ngang


        pauseWindow = new PopupDialog(stage, commonAtlas, right, left, mainTable);
        return pauseWindow;
    }
    private Actor createBackGround() {
        return new BackGround(stage,
                assetMN.get("texture/bg.atlas", TextureAtlas.class).findRegion("bg1"));
    }

    private Actor createTimeBar() {
        timerbar = new TimerBar(stage, playAtlas.findRegion("barframe"),
                playAtlas.findRegion("bar"), gameData.getRemainSeconds(), animalMatrix);
        return timerbar;
    }

    private Actor createLevelTextLabel() {
        LevelText levelText = new LevelText(gameData.getCurrentLevel()+"/"+Rank.maxLevel(gameData.getRankName()));
        levelText.setPosition(150, stage.getHeight() - 20);

        return levelText;
    }

    private Actor createScoreTextLabel(){
        MyLabel label = new MyLabel("Score: ");
        label.setPosition(20, stage.getHeight() - 20);

        return label;
    }
    private Actor createScoreLabel(){
        Score score = new Score(String.valueOf(gameData.getScore()));
        score.setPosition(70, stage.getHeight() - 20);

        return score;
    }
    private Actor createPauseButton() {
        PauseButton pauseButton = new PauseButton(playAtlas);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               // game.setScreen(new MainMenuScreen(game));
                pauseWindow.showDialog();
            }
        });
        pauseButton.setPosition(stage.getWidth() - 70, stage.getHeight() - 60);
        return pauseButton;
    }

    private Actor createPauseButton2() {
        PauseButton pauseButton = new PauseButton(playAtlas);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameData.removePreferences(gameData.getRankName());
            }
        });
        return pauseButton;
    }

    private Actor createHintButton() {
        MyButton hintbutton = new HintButton(playAtlas);
        hintbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Path.Point p1 = Pikachu.possiblePath.points.get(0);
                Path.Point p2 = Pikachu.possiblePath.points.get(1);
                animalMatrix.matrix[p1.x][p1.y].setSelected(true);
                animalMatrix.matrix[p2.x][p2.y].setSelected(true);
            }
        });
        //hintbutton.setPosition(0, Gdx.graphics.getHeight() - 20 - 60);
        return hintbutton;
    }

    private Actor createRandomButton() {
        MyButton randomButton = new RandomButton(playAtlas);
        randomButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Pikachu.shuffleMatrixExceptInvisible(animalMatrix.matrix);
            }
        });

        //randomButton.setPosition(0, Gdx.graphics.getHeight()- 20 - 120);
        return randomButton;
    }

    private Actor createRocketButton() {
        MyButton rocketButton = new RocketButton(playAtlas);
        rocketButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Pikachu.shuffleMatrixExceptInvisible(animalMatrix.matrix);
            }
        });

        //rocketButton.setPosition(0, Gdx.graphics.getHeight()- 20 - 180);
        return rocketButton;
    }
}
