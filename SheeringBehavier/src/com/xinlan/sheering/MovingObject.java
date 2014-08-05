package com.xinlan.sheering;

import com.badlogic.gdx.math.Vector2;

public class MovingObject extends GameObjectBase {
	protected Vector2 velocity = new Vector2();// 速度向量
	protected Vector2 heading = new Vector2();// 朝向向量
	protected Vector2 siding = new Vector2();// 垂直向量
	protected Vector2 targetTo = new Vector2();//目标到达位置
	
	protected float headAngle=0;//朝向角度
	protected float maxSpeed=2200;// 最大速度
	protected float maxTurnSpeed;// 最大转弯速度
	protected float mass;//质量
	
	protected SteeringBehavior steering;
	
	public MovingObject() {
		mass = 1f;
		steering = new SteeringBehavior(this);
	}

	protected void wapWorld() {
		if (this.pos.x < 0) {
			pos.x = MyApp.SC_WIDTH;
		} else if (this.pos.x > MyApp.SC_WIDTH) {
			pos.x = 0;
		}// end if

		if (this.pos.y < 0) {
			pos.y= MyApp.SC_HEIGHT;
		} else if (pos.y > MyApp.SC_HEIGHT) {
			pos.y  = 0;
		}// end if
		
	}
}// end class
