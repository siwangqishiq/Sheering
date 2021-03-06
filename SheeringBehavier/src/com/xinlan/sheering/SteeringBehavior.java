package com.xinlan.sheering;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class SteeringBehavior
{
    private MovingObject vehicle;

    public static enum ArriveMode
    {
        Slow(3), Normal(2), Fast(1);
        int value;

        private ArriveMode(int mode)
        {
            this.value = mode;
        }
    };

    public SteeringBehavior(MovingObject context)
    {
        this.vehicle = context;
    }

    /**
     * 靠近模式
     * 
     * @param target
     * @return
     */
    public Vector2 seek(final Vector2 target)
    {
        Vector2 temp = target.cpy();
        Vector2 desiredVelocity = temp.sub(vehicle.pos).nor()
                .scl(vehicle.maxSpeed);
        return desiredVelocity.sub(vehicle.velocity);
    }

    /**
     * 躲避模式
     * 
     * @param target
     * @return
     */
    public Vector2 flee(final Vector2 target)
    {
        final float panicDistance = 250;// 预警距离
        if (target.cpy().sub(vehicle.pos).len() > panicDistance)
        {
            return new Vector2(0, 0);
        }

        Vector2 temp = vehicle.pos.cpy();
        Vector2 desiredVelocity = temp.sub(target).nor().scl(vehicle.maxSpeed);
        return desiredVelocity.sub(vehicle.velocity);
    }

    /**
     * 抵达模式
     * 
     * @param target
     * @param mode
     * @return
     */
    public Vector2 arrive(final Vector2 target, ArriveMode mode)
    {
        Vector2 toTarget = target.cpy().sub(vehicle.pos);
        float distance = toTarget.len();
        if (distance > 0f)
        {
            float factor = 0.8f;
            float speed = Math.min(distance / (factor * mode.value),
                    vehicle.maxSpeed);
            Vector2 desiredVelocity = toTarget.scl(speed / distance);
            return desiredVelocity.sub(vehicle.velocity);
        }// end if

        return new Vector2(0, 0);
    }

    /**
     * 漫步 搜索模式
     * 
     * @return
     */
    public Vector2 wander()
    {
        float wanderJitter = 10f;// 随机扰动最大值
        float wanderRadius = 10f;// wander圈半径
        float wanderDistance = 5f;// wander圈突出距离
        Vector2 target = vehicle.pos.cpy();
        target.rotate(this.vehicle.heading.angle());
        System.out.println("pos--->"+vehicle.pos.x+"     "+vehicle.pos.y);
        System.out.println("--->"+target.x+"     "+target.y);
        Vector2 wanderTarget = target.add(MathUtils.random(-1, 1)
                * wanderJitter, MathUtils.random(-1, 1) * wanderJitter);
        System.out.println(wanderTarget.x+"     "+wanderTarget.y);
        wanderTarget.nor();
        wanderTarget.scl(wanderRadius);
        wanderTarget.add(wanderDistance, 0);

        return wanderTarget.sub(vehicle.pos);
    }

}// end class
