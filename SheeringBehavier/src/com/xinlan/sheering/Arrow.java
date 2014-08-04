package com.xinlan.sheering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Arrow extends MovingObject implements InputProcessor
{
    private Texture texture;
    private Texture crossTexture;
    private float cross_width, cross_height;
    private Sprite mSprite;
    private SteeringBehavior steering;
    private Vector3 touchPos = new Vector3();
    private Vector2 target_to = new Vector2();
    private MyApp mContext;

    public Arrow(MyApp mContext)
    {
        this.mContext = mContext;
        texture = new Texture(Gdx.files.internal("asset/arrow.png"));
        crossTexture = new Texture(Gdx.files.internal("asset/cross.png"));
        cross_width = crossTexture.getWidth() / 2;
        cross_height = crossTexture.getHeight() / 2;

        mSprite = new Sprite(texture);
        steering = new SteeringBehavior();

        // this.velocity.set(20,20);
    }

    public void update(float delta)
    {
       // Vector2 force = steering.calcluateForce();
        Vector2 force = steering.seek(target_to);//抵达 运动模式
        
        
        force.scl(1 / this.mass);
        force.scl(delta);
        this.velocity.add(force);
        // System.out.println(this.velocity.x +"      "+this.velocity.y );
        this.velocity.limit(this.maxSpeed);
        // System.out.println(this.velocity.x +"      "+this.velocity.y );
        this.pos.add(this.velocity.cpy().scl(delta));

        if (this.velocity.len() > 0.00000001f)
        {
            heading = this.velocity.cpy().nor();
            siding = this.heading;
        }

        this.wapWorld();
        // System.out.println(pos.x+"     "+pos.y);
        mSprite.setPosition(pos.x, pos.y);
    }

    public void draw(SpriteBatch batch)
    {
        mSprite.setOriginCenter();
        float cur = this.heading.angle();
        mSprite.rotate(-this.headAngle);
        this.headAngle = cur;
        mSprite.rotate(headAngle);

        mSprite.draw(batch);

        batch.draw(crossTexture, target_to.x - cross_width, target_to.y
                - cross_height);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        // System.out.println(screenX+"   "+screenY);
        touchPos.set(screenX, screenY, 0);
        mContext.camera.unproject(touchPos);
        target_to.set(touchPos.x, touchPos.y);
        // System.out.println("--->"+target_to.x+"   "+target_to.y);
        return true;
    }

    private  final class SteeringBehavior
    {
        private Vector2 force = new Vector2();

        public Vector2 calcluateForce()
        {
            force.set(0, 0);
            return force;
        }

        public Vector2 seek(Vector2 target)
        {
            Vector2 temp = target.cpy();
            Vector2 desiredVelocity = temp.sub(pos).nor().scl(maxSpeed);
            return desiredVelocity.sub(velocity);
        }
    }//end inner class

    @Override
    public boolean keyDown(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        return false;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }
}// end class
