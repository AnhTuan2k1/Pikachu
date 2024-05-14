package com.mygdx.pairanimalgame;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.gdx.graphics.Color;
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
    static TextureRegion verticalLine1;
    static TextureRegion verticalLine2;
    private Path path;
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    public PathActor(Path path) {
        this.path = path;

        setVisible(true);
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                setVisible(false);
            }
        }, 0.2f);

        if (horizontalLine==null||verticalLine1==null){
            TextureAtlas atlas = GameScreen.getInstance().getPlayAtlas();
            verticalLine1 = atlas.findRegion("laser1");
            verticalLine2 = atlas.findRegion("laser2");
            horizontalLine = atlas.findRegion("laser12");
        }
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
                //TextureRegion line = new Random().nextInt(2)/2==0?verticalLine1:verticalLine2;
                float length = Math.abs(posY2 - posY1);
                float width = verticalLine1.getRegionWidth() * AnimalCard.getAnimalScale()*0.7f;
                batch.draw(verticalLine1, posX1 - width/2, Math.min(posY1, posY2), width, length);
            } else if (posY1 == posY2) {
                // Horizontal line
                float length = Math.abs(posX2 - posX1);
                float height = horizontalLine.getRegionHeight() * AnimalCard.getAnimalScale()*0.7f;
                batch.draw(horizontalLine, Math.min(posX1, posX2), posY1-height/2, length, height);

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
