package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class NaveJugador {

    private Sprite sprite;

    public NaveJugador(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
