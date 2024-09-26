package de.phillip.jumpandrun.models;

import java.util.List;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Crabby extends Enemy {
	
	public static final int CRABBY_DEFAULT_WIDTH = 72;
	public static final int CRABBY_DEFAULT_HEIGHT = 32;
	public static final int WIDTH = (int) (CRABBY_DEFAULT_WIDTH * Game.SCALE);
	public static final int HEIGHT = (int) (CRABBY_DEFAULT_HEIGHT * Game.SCALE);
	public static final double HITBOX_WIDTH = 22 * Game.SCALE;
	public static final double HITBOX_HEIGHT = 19 * Game.SCALE;
	public static final double X_OFFSET = 26 * Game.SCALE;
	public static final double Y_OFFSET = 10 * Game.SCALE;
	public static final double SPEED = 0.3 * Game.SCALE;
	
	private Image enemySprite = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.CRABBY_SPRITES);
	private Image[][] actionSprites;
	private Direction direction = Direction.LEFT;
	

	public Crabby() {
		super(WIDTH, HEIGHT, Enemy.Type.CRABBY);
		createActionSprites();
		initHitbox(X_OFFSET, Y_OFFSET, HITBOX_WIDTH, HITBOX_HEIGHT);
	}
	
	private void createActionSprites() {
		PixelReader pr = enemySprite.getPixelReader();
		actionSprites = new Image[5][9];
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
		drawHitbox(gc, Color.BLUE);
	}
	
	/*private void drawHitBox(GraphicsContext gc) {
		gc.strokeRect(getDrawPosition().getX(), getDrawPosition().getY(), getWidth(), getHeight());
	}*/
	
	@Override
	public void update() {
		updateMove();
		super.update();
	}

	private void updateMove() {
		Point2D oldPosition = getDrawPosition();
		switch (direction) {
		case LEFT:
			setDrawPosition(getDrawPosition().getX() - SPEED, getDrawPosition().getY());
			break;
		case RIGHT:
			setDrawPosition(getDrawPosition().getX() + SPEED, getDrawPosition().getY());
			break;
		default:
			break;
		}
		if (!canMoveHere(getEnemyManager().getTiles(), getDrawPosition(), getEnemyManager().getLevelWidth())) {
			if (direction == Direction.LEFT) {
				direction = Direction.RIGHT;
			} else {
				direction = Direction.LEFT;
			}
		}
	}
	
	

}
