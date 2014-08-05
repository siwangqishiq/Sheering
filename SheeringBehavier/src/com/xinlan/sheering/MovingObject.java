package com.xinlan.sheering;

import com.badlogic.gdx.math.Vector2;

public class MovingObject extends GameObjectBase {
	protected Vector2 velocity = new Vector2();// �ٶ�����
	protected Vector2 heading = new Vector2();// ��������
	protected Vector2 siding = new Vector2();// ��ֱ����
	protected Vector2 targetTo = new Vector2();//Ŀ�굽��λ��
	
	protected float headAngle=0;//����Ƕ�
	protected float maxSpeed=2200;// ����ٶ�
	protected float maxTurnSpeed;// ���ת���ٶ�
	protected float mass;//����
	
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
