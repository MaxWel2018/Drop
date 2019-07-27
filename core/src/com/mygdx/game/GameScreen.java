package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;
import java.util.Random;

public class GameScreen implements Screen {

	final Drop game;

	private Texture dropImage;
	private Texture bucketImage;

	private Sound dropSound;
	private	Music rainMusic;

	private OrthographicCamera camera;
	private Rectangle bucket;
	private Array<Rectangle> raindrops;

	private Vector3 touchPos;
	private Random random = new Random();

	 int getCountCaughtDrops() {
		return countCaughtDrops;
	}

	private 	long lastDropTime;
	private int speedDrop = 200;
	private int countCaughtDrops = 0;

	private int livesLeft = 3;

	GameScreen(final Drop drop) {
		this.game = drop;


		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));

		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		rainMusic.setLooping(true);
		rainMusic.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false,  800, 480);

		game.batch = new SpriteBatch();

		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		raindrops = new Array<>();
		spawnRaindrop();

	}



	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}


	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		ClearScreen(camera, game,0.5f,0.3f,0.23f);
		game.batch.draw(bucketImage, bucket.x, bucket.y);
		game.font.draw(game.batch, "Drops caught: " + countCaughtDrops, 670, 470);
		game.font.draw(game.batch, "Lives left: " + livesLeft, 10, 470);
		for(Rectangle raindrop: raindrops) {
			game.batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		game.batch.end();

		if (Gdx.input.isTouched()) {  // есть ли прикосновение
			touchPos = new Vector3();  // трехмерный вектор
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);  // преобразование координат
			camera.unproject(touchPos);        // прикосновения/мыши в систему координат камеры
			bucket.x = (int)touchPos.x - 64 / 2; // изменяем положение ведра что бы оно было по центру клика
//			bucket.y = touchPos.y - 64 / 2;

		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) bucket.x -= 600 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) bucket.x += 600 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.A)) bucket.x -= 600 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Keys.D)) bucket.x += 600 * Gdx.graphics.getDeltaTime();
		if (bucket.x < 0) bucket.x = 0;
		if (bucket.x > 800 - 64) bucket.x = 800 - 64;

		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) {
			int n = random.nextInt(100);

		 if (n > 75) {
				spawnRaindrops(2);
			} else spawnRaindrops(1);
			spawnRaindrop();  // Капают капли
		}
		Iterator<Rectangle> iter = raindrops.iterator();
		boolean flag =false;

		while(iter.hasNext()) {
			if (livesLeft <= 0) {
				rainMusic.stop();
				game.setScreen(new GameOverScreen(game,this));

				flag = true;
				break;

			}
			Rectangle raindrop = iter.next();
			if(raindrop.y + 64 < 0){ iter.remove();
				livesLeft--;
			}
			raindrop.y -= speedDrop * Gdx.graphics.getDeltaTime();
			if(raindrop.overlaps(bucket)) {
				countCaughtDrops++;
				if (countCaughtDrops % 3 == 0) {
					speedDrop += 45;
				}
				dropSound.play();
				iter.remove();
			}
		}
		if(flag){

		}

	}

	void spawnRaindrops(int countCaughtDrops) {
		for (int i = 0; i < countCaughtDrops; i++) {
			spawnRaindrop();

		}
	}

	static void ClearScreen(OrthographicCamera camera, Drop game,float r,float g,float b) {
		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);  //использовать систему координат камеры
		game.batch.begin();
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
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		game.batch.dispose();
	}

	public Drop getGame() {
		return game;
	}

	public Texture getDropImage() {
		return dropImage;
	}

	public void setDropImage(Texture dropImage) {
		this.dropImage = dropImage;
	}

	public Texture getBucketImage() {
		return bucketImage;
	}

	public void setBucketImage(Texture bucketImage) {
		this.bucketImage = bucketImage;
	}

	public Sound getDropSound() {
		return dropSound;
	}

	public void setDropSound(Sound dropSound) {
		this.dropSound = dropSound;
	}

	public Music getRainMusic() {
		return rainMusic;
	}

	public void setRainMusic(Music rainMusic) {
		this.rainMusic = rainMusic;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	public Rectangle getBucket() {
		return bucket;
	}

	public void setBucket(Rectangle bucket) {
		this.bucket = bucket;
	}

	public Array<Rectangle> getRaindrops() {
		return raindrops;
	}

	public void setRaindrops(Array<Rectangle> raindrops) {
		this.raindrops = raindrops;
	}

	public Vector3 getTouchPos() {
		return touchPos;
	}

	public void setTouchPos(Vector3 touchPos) {
		this.touchPos = touchPos;
	}

	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public long getLastDropTime() {
		return lastDropTime;
	}

	public void setLastDropTime(long lastDropTime) {
		this.lastDropTime = lastDropTime;
	}

	public int getSpeedDrop() {
		return speedDrop;
	}

	public void setSpeedDrop(int speedDrop) {
		this.speedDrop = speedDrop;
	}

	public void setCountCaughtDrops(int countCaughtDrops) {
		this.countCaughtDrops = countCaughtDrops;
	}

	public int getLivesLeft() {
		return livesLeft;
	}

	public void setLivesLeft(int livesLeft) {
		this.livesLeft = livesLeft;
	}
}