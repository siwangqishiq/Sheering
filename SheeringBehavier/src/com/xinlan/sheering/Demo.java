package com.xinlan.sheering;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * 
 * @author Administrator
 *
 */
public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LwjglApplicationConfiguration config= new LwjglApplicationConfiguration();
		config.fullscreen = false;
		config.width=1000;
		config.height = 600;
		config.title="物体运动";
		//new LwjglApplication(new MyApp(),config);
		//new LwjglApplication(new PhysicsDemo(),config);
		new LwjglApplication(new PhysicsDemo2(),config);
	}

}//end class
