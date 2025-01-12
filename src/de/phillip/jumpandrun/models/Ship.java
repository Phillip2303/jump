package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Ship extends GameObject {
	
	public static final int DEFAULT_WIDTH = 78;
	public static final int DEFAULT_HEIGHT = 72;
	public static final int WIDTH = (int) (DEFAULT_WIDTH * Game.SCALE);
	public static final int HEIGHT = (int) (DEFAULT_HEIGHT * Game.SCALE);
	
	/*public static final double X_OFFSET_LEFT = -3 * Game.SCALE;
	public static final double X_OFFSET_RIGHT = 29 * Game.SCALE;
	public static final double Y_OFFSET = 5 * Game.SCALE;*/
	
	public Image[][] shipSprites = new Image[1][4];
	private float heightDelta;
	private float immersionDepth = 5f * Game.SCALE;
	private float immersionSpeed = 0.05f * Game.SCALE;
	private Direction direction = Direction.DOWN;

	public Ship() {
		super(WIDTH, HEIGHT, GameObject.Type.SHIP);
		doAnimation(true);
		createObjectSprites(ResourcePool.getInstance().getSpriteAtlas(ResourcePool.SHIP_SPRITES), shipSprites, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	@Override
	public void drawToCanvas(GraphicsContext gc) {
		if (isActive()) {
			gc.drawImage(shipSprites[getSpriteIndex()][getAniIndex()], getDrawPosition().getX(), getDrawPosition().getY() + heightDelta,
					getWidth(), getHeight());
		}
	}
	
	@Override
	public void update() {
		heightDelta += immersionSpeed * direction.getValue();
		heightDelta = Math.max(Math.min(heightDelta, immersionDepth), 0);
		if (heightDelta == immersionDepth) {
			direction = Direction.UP;
		} else if (heightDelta == 0) {
			direction = Direction.DOWN;
		}
		super.update();
	}
}
