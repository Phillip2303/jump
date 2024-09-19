package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Crabby extends Enemy {
	
	public static final int CRABBY_DEFAULT_WIDTH = 72;
	public static final int CRABBY_DEFAULT_HEIGHT = 32;
	public static final int CRABBY_WIDTH = (int) (CRABBY_DEFAULT_WIDTH * Game.SCALE);
	public static final int CRABBY_HEIGHT = (int) (CRABBY_DEFAULT_HEIGHT * Game.SCALE);
	
	private Image enemySprite = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.CRABBY_SPRITES);
	private Image[][] actionSprites;
	

	public Crabby() {
		super(CRABBY_WIDTH, CRABBY_HEIGHT, Enemy.Type.CRABBY);
		createActionSprites();
	}
	
	private void createActionSprites() {
		PixelReader pr = enemySprite.getPixelReader();
		actionSprites = new Image[9][5];
		for (int j = 0; j < actionSprites.length; j++) {
			for (int i = 0; i < actionSprites[j].length; i++) {
				actionSprites[j][i] = createSubImage(pr, i, j);
			}
		}
	}
	
	private Image createSubImage(PixelReader pr, int x, int y) {
		WritableImage wi = new WritableImage(CRABBY_DEFAULT_WIDTH, CRABBY_DEFAULT_HEIGHT);
		PixelWriter pw = wi.getPixelWriter();
		for (int j = 0; j < CRABBY_DEFAULT_HEIGHT; j++) {
			for (int i = 0; i < CRABBY_DEFAULT_WIDTH; i++) {
				Color color = pr.getColor(x * CRABBY_DEFAULT_WIDTH + i, y * CRABBY_DEFAULT_HEIGHT + j);
				pw.setColor(i, j, color);
			}
		}
		return wi;
	}
	
	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.drawImage(actionSprites[getEnemyAction()][getAniIndex()], getDrawPosition().getX(), getDrawPosition().getY(),
				getWidth(), getHeight());
		drawHitBox(gc);
	}
	
	private void drawHitBox(GraphicsContext gc) {
		gc.strokeRect(getDrawPosition().getX(), getDrawPosition().getY(), getWidth(), getHeight());
	}

}
