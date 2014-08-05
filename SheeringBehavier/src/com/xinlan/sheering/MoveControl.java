package com.xinlan.sheering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MoveControl extends MovingObject  implements InputProcessor
{
    public boolean isTouchDown = false;
    private Texture texture;
    private Sprite mSprite;
    private PhysicsDemo2 mContext;
    
    private float maxControllForce = 1000;
    private Vector2 force = new Vector2(0,0);
    private Vector2 upForce = new Vector2(0,maxControllForce);
    private Vector2 downForce = new Vector2(0,-maxControllForce);
    private Vector2 leftForce = new Vector2(-maxControllForce,0);
    private Vector2 rightForce = new Vector2(maxControllForce,0);
    
    private boolean isUpPressed = false;
    private boolean isDownPressed = false;
    private boolean isLeftPressed =false;
    private boolean isRightPressed = false;
    private boolean isSpacePressed = false;
    
    public MoveControl(PhysicsDemo2 mContext)
    {
        this.mContext = mContext;
        texture = new Texture(Gdx.files.internal("asset/arrow.png"));
        mSprite = new Sprite(texture);
        this.pos.set(PhysicsDemo.SC_WIDTH / 2, PhysicsDemo.SC_HEIGHT / 2);
        
        this.velocity.set(0,0);
    }

    public void update(float delta)
    {
        force.set(0,0);
        force = calculateForce();
        //加速度
        Vector2 accelerataion = force.scl(1/this.mass);
        //更新速度
        this.velocity.add(accelerataion.scl(delta));
        this.velocity.limit(this.maxSpeed);
        
        this.pos.add(this.velocity.cpy().scl(delta));
        this.wapWorld();
        mSprite.setPosition(pos.x, pos.y);
        if (this.velocity.len() > 0.1f)
        {
            heading = this.velocity.cpy().nor();
            siding = this.heading;
        }else{
            this.velocity.set(0,0);
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
    
    private Vector2 calculateForce()
    {
        Vector2 retForce = new Vector2(0,0);
        float angle = this.heading.angle();
        if(isUpPressed)
        {
//            Vector2 temp = upForce.cpy().rotate(angle);
//            retForce.add(temp);
            retForce.add(upForce);
        }
        if(isDownPressed)
        {
//            Vector2 temp = downForce.cpy().rotate(angle);
//            retForce.add(temp);
            retForce.add(downForce);
        }
        if(isLeftPressed)
        {
//            Vector2 temp = leftForce.cpy().rotate(angle);
//            retForce.add(temp);
            retForce.add(leftForce);
        }
        if(isRightPressed)
        {
//            Vector2 temp = rightForce.cpy().rotate(angle);
//            retForce.add(temp);
            retForce.add(rightForce);
        }
        
        if(isSpacePressed)//刹车
        {
            if(velocity.len()>5f){
                Vector2 focus = velocity.cpy().nor().scl(-300);
                retForce.add(focus);
            }else{
                Vector2 focus = velocity.cpy().nor().scl(-1);
                retForce.add(focus);
            }
        }
        return retForce;
    }


    @Override
    public boolean keyDown(int keycode)
    {
        if(Input.Keys.UP == keycode)
        {
            isUpPressed = true;
        }
        if(Input.Keys.DOWN == keycode)
        {
            isDownPressed = true;
        }
        if(Input.Keys.LEFT == keycode)
        {
            isLeftPressed = true;
        }
        if(Input.Keys.RIGHT == keycode)
        {
            isRightPressed = true;
        }
        if(Input.Keys.SPACE == keycode)
        {
            isSpacePressed = true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        if(Input.Keys.UP == keycode)
        {
            isUpPressed = false;
        }
        if(Input.Keys.DOWN == keycode)
        {
            isDownPressed = false;
        }
        if(Input.Keys.LEFT == keycode)
        {
            isLeftPressed = false;
        }
        if(Input.Keys.RIGHT == keycode)
        {
            isRightPressed = false;
        }
        if(Input.Keys.SPACE == keycode)
        {
            isSpacePressed = false;
        }
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
