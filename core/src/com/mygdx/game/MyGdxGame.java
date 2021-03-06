package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor, Runnable {
	private  SpriteBatch batch;
	private int contadorNave;
	private int vidasNave;
	private BitmapFont fontP;
	private BitmapFont font;
	private BitmapFont fontVidas;
	private boolean acabado;
	String fontV;

	private Texture imgEnemy;
	private int XEn;
	private int YEn;

	private  int[] vidasE;
	private boolean perdido;

	private Texture img;
	private Texture imgElim;
	private Texture imgbala;
	private ArrayList<Bala>balas;
	private ArrayList<Enemigo>enemigosNaves;
	private Sprite jugador;

	private Sprite balaAmiga;
	private NaveJugador nave;
	private Enemigo naveEne;
	private Bala b;
	private int ancho;
	private int contadorEne;
	private boolean bol;
	private int disparoC;

	private BalaEnemiga bE;
	private ArrayList<BalaEnemiga>balasE;

	
	@Override
	public void create () {
		perdido = false;
		acabado = false;
	vidasE = new int[7];
	contadorNave = 100;
	vidasNave = 3;

		fontV = "Vidas: ";
		font = new BitmapFont();
		fontVidas = new BitmapFont();
		fontP = new BitmapFont();
		imgEnemy = new Texture("nau2.png");
		XEn = Gdx.graphics.getWidth()/2;
		YEn = Gdx.graphics.getHeight() -80;


		Gdx.input.setInputProcessor(this);
		batch = new SpriteBatch();
		imgbala = new Texture("tret.png");
		balaAmiga = new Sprite(imgbala, 0,0,imgbala.getWidth(),imgbala.getHeight());
		balaAmiga.setY(100);
		ancho = Gdx.graphics.getWidth();

		img = new Texture("nau1.png");
		imgElim = new Texture("nau2e.png");
		jugador = new Sprite(img, 0, 0, img.getWidth(), img.getHeight());

		//balaAmig = new Bala(imgbala, 0,0,imgbala.getWidth(),imgbala.getHeight());
		balas = new ArrayList<>();
		balasE = new ArrayList<>();
		enemigosNaves = new ArrayList<>();
		jugador.setX((ancho-jugador.getWidth())/2);
		jugador.setY(100);

		contadorEne = 1;
		nave = new NaveJugador(jugador);

		disparoC = 0;
		bol = false;

		/*Thread t = new Thread(this);
		t.start();*/
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//ScreenUtils.clear(0, 0, 0, 1);



		batch.begin();
		font.getData().setScale(2.5F);
		font.draw(batch, fontV + vidasNave, 10, 60);

		nave.getSprite().draw(batch);
		if(perdido == true)
		{
			fontP.getData().setScale(10.5F);
			fontP.getColor().set(Color.RED);
			fontP.draw(batch, "HAS PERDIDO", Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/2);
		}
		if(acabado == true)
		{
			fontP.getData().setScale(10.5F);
			fontP.getColor().set(Color.GREEN);
			fontP.draw(batch, "HAS GANADO", Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/2);
		}
		if(enemigosNaves.size() != 0) {
			for (int y = 0; y < enemigosNaves.size(); y++) {
				//enemigosNaves.get(y).getNaveEnemy().draw(batch);
				batch.draw(enemigosNaves.get(y).getTexture(), enemigosNaves.get(y).getX(), enemigosNaves.get(y).getY());


			}
			if(balasE.size() != 0) {
				for (int d = 0; d < balasE.size(); d++) {
					batch.draw(balasE.get(d).getTexture(), balasE.get(d).getX(), balasE.get(d).getY());//balasE.get(d).draw(batch);
				}
			}
			if (balas.size() != 0) {
				for (int i = 0; i < balas.size(); i++) {
				Bala b2 = balas.get(i);
					Thread r = new Thread(b2);
					r.start();
					batch.draw(imgbala, b2.getX(), b2.getY());


					for (int x = 0; x < enemigosNaves.size(); x++) {
						for (int j = 0; j < balas.size(); j++) {
							if(balas.get(j).getY() > Gdx.graphics.getHeight())
							{
								balas.remove(balas.get(j));

							}
							if (enemigosNaves.get(x).getX()  < b2.getX() && b2.getX() < enemigosNaves.get(x).getX() + enemigosNaves.get(x).getTexture().getWidth()) {

								if (b2.getY() + b2.getHeight()< enemigosNaves.get(x).getY() ) {

									if (vidasE[x] != 0) {

										vidasE[x] -= 1;

									}

									else {
										enemigosNaves.get(x).setTexture(imgElim);
									}



								}
							}
						}
					}


				}


			}


			if(balasE.size() != 0 && enemigosNaves.size() != 0)
			{
				for(int i = 0; i < balasE.size(); i++)
				{
					BalaEnemiga b4 = balasE.get(i);

						if(balasE.get(i).getY() < -Gdx.graphics.getHeight() + (float)50)
						{
							balasE.remove(b4);

						}
						if (b4.getX() < nave.getSprite().getX() + nave.getSprite().getTexture().getWidth() && b4.getX() > nave.getSprite().getX()) {

							if (b4.getY() + b4.getHeight() >= 100) {
							if(nave.getSprite().getTexture() != imgElim) {
								if (contadorNave <= 0) {

									vidasNave -= 1;
									contadorNave = 100;

									if (vidasNave == 0) {
										nave.getSprite().setTexture(imgElim);
									perdido = true;
									}

								}
								contadorNave--;
							}


							}
						}

				}
			}

		}

		if(balas != null)
		{
			/*for(int z = 0; z < balas.size(); z++) {
				b.draw(batch);
				Thread r = new Thread(b);
				r.start();
			}*/
		}



		if(contadorEne <3) {
			contadorEne++;
			Thread t = new Thread(this);
			t.start();


		}

		batch.end();





	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		imgEnemy.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {

		switch(keycode)
		{
			case Input.Keys.DPAD_LEFT:
				if(nave.getSprite().getTexture() != imgElim) {
					float novaPos = nave.getSprite().getX() - 40;
					if (novaPos >= 0) {
						nave.getSprite().setX(novaPos);
					}
				}
				break;
			case Input.Keys.DPAD_RIGHT:
				if(nave.getSprite().getTexture() != imgElim) {
					float novaPos2 = nave.getSprite().getX() + 40;
					if (novaPos2 <= ancho - jugador.getWidth()) {
						nave.getSprite().setX(novaPos2);
					}
				}
				break;
			case Input.Keys.DPAD_UP:
				if(nave.getSprite().getTexture() != imgElim) {
					disparoC = 1;
					b = new Bala(imgbala, (int) nave.getSprite().getX(), (int) nave.getSprite().getY(), imgbala.getWidth(), imgbala.getHeight());
					float alt = 100;

					int pos = balas.size() - 1;
					b.setY(alt);
					b.setX(nave.getSprite().getX() + (nave.getSprite().getWidth() / 2));
					balas.add(b);
				}
				break;
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(nave.getSprite().getTexture() != imgElim) {

			nave.getSprite().setX(screenX);

			b = new Bala(imgbala, (int) nave.getSprite().getX(), (int) nave.getSprite().getY(), imgbala.getWidth(), imgbala.getHeight());
			float alt = 100;

			int pos = balas.size() - 1;
			b.setY(alt);
			b.setX(nave.getSprite().getX() + (nave.getSprite().getWidth() / 2));
			balas.add(b);
			disparoC = 1;

		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}

	@Override
	public void run() {


		if(enemigosNaves.size() == 0) {
			int i = 0;
			do {

				i = enemigosNaves.size();

				Enemigo e = new Enemigo(imgEnemy, XEn, YEn, imgEnemy.getWidth(), imgEnemy.getHeight());

				e.setX(ancho);

				enemigosNaves.add(e);
				vidasE[i] = 10;

				Thread trr = new Thread(enemigosNaves.get(i));
				trr.start();


				try {
					Thread.sleep(5000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}while(i != 6);
			int c;
			do {
				 c = 0;
				for(int z = 0; z < enemigosNaves.size(); z++) {
					if (enemigosNaves.get(z).getTexture() == imgElim)
					{
						c++;
					}
				}

			}while(c != 7);
			acabado = true;
		}
		else
		{

				boolean salir = false;
				do {
					if(enemigosNaves.size()!= 0) {

						for (int y = 0; y < enemigosNaves.size(); y++) {
							//if (enemigosNaves.get(y).getTexture() != imgElim) {
								BalaEnemiga b3 = new BalaEnemiga(imgbala, 0, 0, (int) enemigosNaves.get(y).getWidth(), (int) enemigosNaves.get(y).getHeight());
								int ancho = (int) enemigosNaves.get(y).getX() + ((int) enemigosNaves.get(y).getWidth() / 2);
								int alto = (int) enemigosNaves.get(y).getY();
								b3.setX(ancho);
								b3.setY(alto);

								Thread tx = new Thread(b3);
								if(enemigosNaves.get(y).getTexture() != imgElim) {
									tx.start();
									balasE.add(b3);

									try {
										Thread.sleep(150);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							//}
							/*else
							{
								y--;
							}*/
						}
						}

				}while(salir == false);



		}

	}


}
