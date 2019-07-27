package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Drop extends Game {
    SpriteBatch batch;
    BitmapFont font;/*
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("C:\\Users\\krugo\\IdeaProjects\\MyGame\\core\\assets\\font\\Roboto-Regular.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter  = new FreeTypeFontGenerator.FreeTypeFontParameter();
    parameter.size = 36;
    BitmapFont font36 = generator.generateFont( parameter);*/




    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        font.dispose();

    }
}
