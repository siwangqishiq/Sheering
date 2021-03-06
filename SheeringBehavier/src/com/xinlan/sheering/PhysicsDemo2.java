package com.xinlan.sheering;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public final class PhysicsDemo2 implements ApplicationListener {
	public static final int SC_WIDTH=1000;
	public static final int SC_HEIGHT=800;
	public OrthographicCamera camera;//摄像机
	private SpriteBatch batch;
	
	protected MoveControl move;
	
	@Override
	public void create() {
		 camera = new OrthographicCamera(SC_WIDTH,SC_HEIGHT);//初始化摄像机
		 camera.position.set(SC_WIDTH>>1, SC_HEIGHT>>1, 0);//使摄像机对准屏幕中心
		 batch = new SpriteBatch();
		 batch.getProjectionMatrix().setToOrtho2D(0, 0, SC_WIDTH, SC_HEIGHT);
		 
		 move = new MoveControl(this);
         Gdx.input.setInputProcessor(move);
	}
	
	@Override
	public void render() {
		float delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glDisable(GL20.GL_BLEND);
		//System.out.println("---->"+delta);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //TODO
        move.update(delta);
        move.draw(batch);
        batch.end();
	}
	
	@Override
	public void resize(int width, int height) {
		  
	}

	@Override
	public void pause() {
		  
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		
	}
}//end class
