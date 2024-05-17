package com.mygdx.pairanimalgame;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class StartGameDialog extends Group {
    private final Image ready;
    private final Image go;
    private final Stage stage;
    private final MyLabel label;
    private final AnimalMatrix matrix;

    public StartGameDialog(Stage stage) {
        this.ready = new Image(GameScreen.getInstance().getPlayAtlas().findRegion("ready"));
        this.go  = new Image(GameScreen.getInstance().getPlayAtlas().findRegion("go"));
        this.stage = stage;
        this.label = new MyLabel("Level " +
                LevelText.text + " " + GameScreen.getInstance().getAnimalMatrix().rankName);
        matrix = GameScreen.getInstance().getAnimalMatrix();

        setBounds(0,0, stage.getWidth(), stage.getHeight());
        initialize();
        System.out.println("StartGameDialog");
    }

    private void initialize() {
        float scale = stage.getHeight()*0.1f/go.getHeight();

        ready.setSize(ready.getWidth()*scale, ready.getHeight()*scale);
        ready.setPosition(stage.getWidth()/2 - ready.getWidth()/2, stage.getHeight()/2 - ready.getHeight()/2);
        ready.setOrigin(ready.getWidth()/2, ready.getHeight()/2);
        addActor(ready);

        go.setSize(go.getWidth()*scale, go.getHeight()*scale);
        go.setPosition(stage.getWidth()/2 - go.getWidth()/2, stage.getHeight()/2 - go.getHeight()/2);
        go.setOrigin(go.getWidth()/2, go.getHeight()/2);
        go.setVisible(false);
        addActor(go);

        label.getFont().getData().setScale(3);
        label.setPosition(getWidth()/2 - label.getText().length()*10, getHeight()/2 + 100);
        addActor(label);



        GameManager.getInstance().onGamePaused();
        toFront();

        AudioManager.playSound(AudioManager.ready_go);
        ready.addAction(
                Actions.sequence(
                        Actions.scaleTo(5,5,0.05f, Interpolation.sineOut),
                        Actions.scaleTo(1,1,0.5f, Interpolation.bounceOut),
                        Actions.delay(0.15f),
                        Actions.run(() -> ready.setVisible(false))
                ));
        go.addAction(
                Actions.sequence(
                        Actions.delay(0.6f),
                        Actions.run(() -> go.setVisible(true)),
                        Actions.scaleTo(5,5,0.05f, Interpolation.sineOut),
                        Actions.scaleTo(1,1,0.5f, Interpolation.sineOut),
                        Actions.delay(0.05f),
                        Actions.run(() -> go.setVisible(false))
                ));

        addAction(
                Actions.sequence(
                        Actions.delay(1.22f),
                        Actions.run(() -> {
                            GameManager.getInstance().onGameResume();
                            AudioManager.playSound(AudioManager.entry);
                            setVisible(false);
                            remove();
                        })
                ));

        if(MathUtils.random(1,2)%2 == 0){
            for (AnimalCard[] row : matrix.matrix) {
                for (AnimalCard animal : row) {
                    if(animal.isVisible()) {
                        animal.addAction(
                                Actions.sequence(
                                        Actions.run(() -> animal.setY(MathUtils.random(720,1080))),
                                        Actions.delay(MathUtils.random(1,2f)),
                                        Actions.moveTo(
                                                AnimalCard.getPosX(animal.getIndexY()),
                                                AnimalCard.getPosY(animal.getIndexX()) - MathUtils.random(50,80),
                                                MathUtils.random(0.05f,0.2f), Interpolation.sineOut
                                        ),
                                        Actions.moveTo(
                                                AnimalCard.getPosX(animal.getIndexY()),
                                                AnimalCard.getPosY(animal.getIndexX()),
                                                MathUtils.random(0.1f,0.2f), Interpolation.sineIn
                                        )
                                ));
                    }
                }
            }
        }else {
            for (AnimalCard[] row : matrix.matrix) {
                for (AnimalCard animal : row) {
                    if(animal.isVisible()) {
                        animal.addAction(
                                Actions.sequence(
                                        Actions.fadeOut(0.01f),
                                        Actions.delay(MathUtils.random(1,1.5f)),
                                        Actions.fadeIn(0.1f, Interpolation.sineIn)
                                )
                        );
                        animal.addAction(
                                Actions.sequence(
                                        Actions.scaleTo(0.001f, 0.001f, 0.01f),
                                        Actions.delay(MathUtils.random(1,1.5f)),
                                        Actions.scaleTo(1f, 1f, 0.1f, Interpolation.sineIn)
                                )
                        );
                    }
                }
            }
        }


    }
}
