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
    private Vector3 touchPos = new Vector3();
    private MyApp mContext;

    public Arrow(MyApp mContext)
    {
        this.mContext = mContext;
        texture = new Texture(Gdx.files.internal("asset/arrow.png"));
        crossTexture = new Texture(Gdx.files.internal("asset/cross.png"));
        cross_width = crossTexture.getWidth() / 2;
        cross_height = crossTexture.getHeight() / 2;

        mSprite = new Sprite(texture);
        // this.velocity.set(20,20);
    }

    public void update(float delta)
    {
        // Vector2 force = steering.calcluateForce();
        // Vector2 force = steering.seek(targetTo);//抵达 运动模式
        // Vector2 force = steering.seek(targetTo);
        // Vector2 force = steering.flee(targetTo);
        Vector2 force = steering.arrive(targetTo,
                SteeringBehavior.ArriveMode.Normal);// 抵达 运动模式

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
        else
        {
            this.velocity.set(0, 0);
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
        batch.draw(crossTexture, targetTo.x - cross_width, targetTo.y
                - cross_height);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        // System.out.println(screenX+"   "+screenY);
        touchPos.set(screenX, screenY, 0);
        mContext.camera.unproject(touchPos);
        targetTo.set(touchPos.x, touchPos.y);
        // System.out.println("--->"+target_to.x+"   "+target_to.y);
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
