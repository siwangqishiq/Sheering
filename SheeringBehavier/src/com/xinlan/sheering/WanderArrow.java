package com.xinlan.sheering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class WanderArrow extends MovingObject implements InputProcessor
{
    private Texture texture;
    private Sprite mSprite;
    private Vector3 touchPos = new Vector3();
    private MyApp2 mContext;

    public WanderArrow(MyApp2 mContext)
    {
        this.mContext = mContext;
        texture = new Texture(Gdx.files.internal("asset/arrow.png"));
        
        mSprite = new Sprite(texture);
        this.pos.set(300,300);
    }

    public void update(float delta)
    {
//        Vector2 force = steering.arrive(targetTo,
//                SteeringBehavior.ArriveMode.Normal);// 抵达 运动模式
        
        Vector2 force = steering.wander();
        force.scl(1 / this.mass);
        force.scl(delta);
        this.velocity.add(force);
        this.velocity.limit(this.maxSpeed);
        this.pos.add(this.velocity.cpy().scl(delta));
        
        if (this.velocity.len() > 0.5f)
        {
            heading = this.velocity.cpy().nor();
            siding = this.heading.cpy().rotate90(-1).nor();
        }
        this.wapWorld();

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
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        touchPos.set(screenX, screenY, 0);
        mContext.camera.unproject(touchPos);
        targetTo.set(touchPos.x, touchPos.y);
        return true;
    }

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
