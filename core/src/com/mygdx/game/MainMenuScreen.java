package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenuScreen implements Screen {
    final Drop game;
    OrthographicCamera camera;


    public MainMenuScreen(Drop game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        GameScreen.ClearScreen(camera, game,0.3f,0.2f,0.9f);
        game.font.draw(game.batch, "Touch Screen To start\n", 350, 480/2-8);
        game.batch.end();

        if (Gdx.input.isTouched()) {
        game.setScreen(new GameScreen(game));
        dispose();
    }


}

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
