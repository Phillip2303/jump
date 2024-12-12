package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Cannon extends GameObject {
	
	public static final int DEFAULT_WIDTH = 40;
	public static final int DEFAULT_HEIGHT = 26;
	public static final int WIDTH = (int) (DEFAULT_WIDTH * Game.SCALE);
	public static final int HEIGHT = (int) (DEFAULT_HEIGHT * Game.SCALE);
	public static final double HITBOX_WIDTH = 40 * Game.SCALE;
	public static final double HITBOX_HEIGHT = 26 * Game.SCALE;
	public static final double X_OFFSET = -4 * Game.SCALE;
	public static final double Y_OFFSET = 6 * Game.SCALE;
	
	public Image cannonSprite = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.CANNON_SPRITES);
	public Image[][] cannonSprites = new Image[1][7];

	public Cannon(Type type) {
		super(WIDTH, HEIGHT, type);
		initHitbox(0, 0, HITBOX_WIDTH, HITBOX_HEIGHT);
		createActionSprites(cannonSprite, cannonSprites, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	
	
	@Override
	public void drawToCanvas(GraphicsContext gc) {
		if (isActive()) {
			gc.drawImage(cannonSprites[getSpriteIndex()][getAniIndex()], getDrawPosition().getX(), getDrawPosition().getY(),
					getWidth(), getHeight());
			//drawSpriteBox(gc, Color.LIME);
			//drawHitbox(gc, Color.CYAN);
		}
	}
	
	@Override
	public void update() {
		super.update();
		if (canShootPlayer()) {
			doAnimation(true);
		}
	}



	private boolean canShootPlayer() {
		int playerTileY = (int) (getGameObjectManager().getPlayer().getHitBox().getMinY() / Game.TILES_SIZE);
		int tileY = (int) (getHitBox().getMinY() / Game.TILES_SIZE);
		if (playerTileY == tileY) {
			return true;
		} else {
			return false;
		}
	}

}
