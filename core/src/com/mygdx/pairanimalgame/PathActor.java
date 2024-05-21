package com.mygdx.pairanimalgame;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Timer;

import java.util.Random;

class PathActor extends Actor {
    static TextureRegion horizontalLine;
    static Texture horizontalLine2;
    static TextureRegion verticalLine1;
    static Texture verticalLine2;
    private Path path;
    float timeXCount = 0;
    float timeYCount = 0;
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    public PathActor(Path path) {
        this.path = path;

        setVisible(true);
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                setVisible(false);
            }
        }, 0.3f);

        if (verticalLine2==null||horizontalLine2==null){
            AssetManager assetMN = ConnectAnimalGame.getInstance().getAssetMN();
            verticalLine2 = assetMN.get("effect/laser1.png", Texture.class);
            horizontalLine2 = assetMN.get("effect/laser1_2.png", Texture.class);

            verticalLine2.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
            horizontalLine2.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(timeXCount < 0.2f) timeXCount+=delta;
        else timeXCount = timeXCount - 0.2f;
        if(timeYCount < 0.1f) timeYCount+=delta;
        else timeYCount = timeYCount - 0.1f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        toFront();
        for (int i = 0; i < path.points.size() - 1; i++) {
            Path.Point p1 = path.points.get(i);
            Path.Point p2 = path.points.get(i + 1);

            float posX1 = AnimalCard.getPosX(p1.y) + AnimalCard.width*AnimalCard.getAnimalScale()/2;
            float posY1 = AnimalCard.getPosY(p1.x) + (AnimalCard.height+15)*AnimalCard.getAnimalScale()/2;
            float posX2 = AnimalCard.getPosX(p2.y) + AnimalCard.width*AnimalCard.getAnimalScale()/2;
            float posY2 = AnimalCard.getPosY(p2.x) + (AnimalCard.height+15)*AnimalCard.getAnimalScale()/2;

            if (posX1 == posX2) {
                // Vertical line
                float length = Math.abs(posY2 - posY1);
                float width = verticalLine2.getWidth() * AnimalCard.getAnimalScale()*0.7f;
                float height = length/(verticalLine2.getHeight()* AnimalCard.getAnimalScale()*0.7f);
                //batch.draw(verticalLine1, posX1 - width/2, Math.min(posY1, posY2), width, length);
                batch.draw(verticalLine2, posX1 - width/2, Math.min(posY1, posY2), width, length, timeYCount>0.05?1:0, timeXCount>0.1?height:0, timeYCount>0.05?0:1, timeXCount>0.1?0:height);
            } else if (posY1 == posY2) {
                // Horizontal line
                float length = Math.abs(posX2 - posX1);
                float width = length/(horizontalLine2.getWidth()* AnimalCard.getAnimalScale()*0.7f);
                float height = horizontalLine2.getHeight() * AnimalCard.getAnimalScale()*0.7f;
                //batch.draw(horizontalLine, Math.min(posX1, posX2), posY1-height/2, length, height);
                batch.draw(horizontalLine2, Math.min(posX1, posX2), posY1-height/2, length, height, timeXCount>0.1?width:0, timeYCount>0.05?1:0, timeXCount>0.1?0:width, timeYCount>0.05?0:1);
            }
        }
    }


/*    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end(); // End the batch before drawing with ShapeRenderer
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN);

        for (int i = 0; i < path.points.size() - 1; i++) {
            Path.Point p1 = path.points.get(i);
            Path.Point p2 = path.points.get(i + 1);

            float posX1 = AnimalCard.getPosX(p1.y) + AnimalCard.width*AnimalCard.getAnimalScale()/2;
            float posY1 = AnimalCard.getPosY(p1.x) + (AnimalCard.height+15)*AnimalCard.getAnimalScale()/2;
            float posX2 = AnimalCard.getPosX(p2.y) + AnimalCard.width*AnimalCard.getAnimalScale()/2;
            float posY2 = AnimalCard.getPosY(p2.x) + (AnimalCard.height+15)*AnimalCard.getAnimalScale()/2;

            shapeRenderer.line(posX1, posY1, posX2, posY2);
        }

        shapeRenderer.end();
        batch.begin(); // Begin batch after finished drawing
    }*/

    private void setupColorAnimation() {
        // Đổi màu ngẫu nhiên
        addAction(Actions.forever(Actions.sequence(
                Actions.color(new Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1), 0.5f),
                Actions.delay(0.2f)
        )));
    }
}
