package poo.khet;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

@Deprecated
public class KhetGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	int dummyVar;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("chelivery.jpg"); // RIP
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1f,1f,1f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 40, 10);
		batch.end();
	}
}
