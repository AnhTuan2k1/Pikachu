package com.mygdx.pairanimalgame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MyButton extends Actor {
    private final TextureRegion textureUp;   // Texture khi nút không được nhấn
    private final TextureRegion textureDown; // Texture khi nút được nhấn
    private boolean isPressed = false; // Trạng thái có đang được nhấn hay không
    private String text;
    static BitmapFont font;

    public MyButton(TextureRegion imageUp, TextureRegion imageDown) {
        this.textureUp = imageUp;
        this.textureDown = imageDown;

        // Thiết lập kích thước mặc định cho actor này
        setBounds(getX(), getY(), textureUp.getRegionWidth(), textureUp.getRegionHeight());

        // Thiết lập tính khả dụng của Actor để nhận sự kiện đầu vào
        setTouchable(Touchable.enabled);

        // Thêm listener để xử lý sự kiện nhấn
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isPressed = true;
                return true; // Trả về true để chỉ ra rằng sự kiện đã được xử lý
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isPressed = false;
            }
        });

        if(MyButton.font == null){
            MyButton.font = new BitmapFont(Gdx.files.internal("font/comic.fnt"));
            font.getData().setScale(0.7f);
            font.setColor(Color.WHITE);
        }
    }
    public MyButton(TextureRegion image) {
        this(image, image);
    }

    public MyButton(TextureRegion image, ConnectAnimalGame game, String rank) {
        this(image, image);
        this.text = rank;
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game, GameData.load(rank)));
            }
        });
    }

    public MyButton(TextureRegion image, ConnectAnimalGame game, String rank, PopupDialog noticeWindow) {
        this(image, image);
        this.text = rank;
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                noticeWindow.setTextlabel("Continue last ["+rank+"] game");
                noticeWindow.showDialog(
                        ()->{
                            GameData.removePreferences(rank);
                            game.setScreen(new GameScreen(game, GameData.load(rank)));
                        },
                        ()->game.setScreen(new GameScreen(game, GameData.load(rank)))
                );
            }
        });
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        // Vẽ texture tương ứng với trạng thái của nút
        TextureRegion currentTexture = isPressed ? textureDown : textureUp;
        batch.draw(currentTexture, getX(), getY(), getWidth(), getHeight());
        if(text != null) {

            float textHeight = font.getLineHeight();

            font.draw(batch, text, getX() + (float) textureUp.getRegionWidth() / 2 - text.length()*font.getXHeight()/2 - 10,
                    getY() + (float) textureUp.getRegionHeight() / 2 + textHeight/2 - 4);
        }
    }

    public void setText(String text) {
        this.text = text;
    }
}