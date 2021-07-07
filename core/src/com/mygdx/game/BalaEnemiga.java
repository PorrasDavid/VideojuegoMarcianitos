package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BalaEnemiga extends Sprite implements Runnable{
    public BalaEnemiga(Texture region, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(region, srcX, srcY, srcWidth, srcHeight);
    }

    @Override
    public void run() {
        float alturabala =getY();
        float altura = -Gdx.graphics.getHeight();
        for(float x = alturabala; x > altura; x = x-8)
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
