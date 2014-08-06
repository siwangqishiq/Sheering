package com.xinlan.sheering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Move extends MovingObject implements InputProcessor
{
    public boolean isTouchDown = false;
    private Texture texture;
    private Sprite mSprite;
    private PhysicsDemo mContext;

    public Move(PhysicsDemo mContext)
    {
        this.mContext = mContext;
        texture = new Texture(Gdx.files.internal("asset/arrow.png"));
        mSprite = new Sprite(texture);
        this.pos.set(PhysicsDemo.SC_WIDTH / 2, PhysicsDemo.SC_HEIGHT / 2);

        this.velocity.set(0, 0);
    }

    public void update(float delta)
    {
        Vector2 force = null;
        if (isTouchDown)
        {
            force = new Vector2(100, -100);
        }
        else
        {
            force = new Vector2(0, 0);
        }
        // 加速度
        Vector2 accelerataion = force.scl(1 / this.mass);
        // 更新速度
        this.velocity.add(accelerataion.scl(delta));
        this.velocity.limit(this.maxSpeed);

        this.pos.add(this.velocity.cpy().scl(delta));
        this.wapWorld();
        mSprite.setPosition(pos.x, pos.y);
        if (this.velocity.len() > 0.1f)
        {
            heading = this.velocity.cpy().nor();
            siding = this.heading.cpy().rotate90(-1).nor();
        }
        else
        {
            this.velocity.set(0, 0);
        }
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        isTouchDown = true;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        isTouchDown = false;
        return false;
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
