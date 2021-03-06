package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bala extends Sprite implements Runnable{

    public Bala(Texture region, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(region, srcX, srcY, srcWidth, srcHeight);
    }

    public void run()
    {

        float alturabala =getY();
       int altura = Gdx.graphics.getBackBufferHeight() + 50;
        for(float x = alturabala; x < altura; x = x+8)
        {
            setY(x);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
