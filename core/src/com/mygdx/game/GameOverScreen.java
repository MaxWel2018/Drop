package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameOverScreen implements Screen {
    GameScreen gameScreen;
    final Drop game;
    OrthographicCamera camera;
    private Music gameOverMusic;


    public GameOverScreen(Drop game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("super_mario.mp3"));
        gameOverMusic.setLooping(true);
        gameOverMusic.play();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        GameScreen.ClearScreen(camera, game,51,0,0);
        game.font.draw(game.batch, "Game over! You Lose!\n", 350, 262);
        game.font.draw(game.batch, "You collected " +gameScreen.getCountCaughtDrops()+ " drops\n", 350, 242);

        game.font.draw(game.batch, "Press ENTER to start again.\n", 350, 220);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game));
            gameOverMusic.stop();
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
