package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Grass extends GameObject {
	
	public static final int DEFAULT_SIZE = 32;
	public static final int SIZE = (int) (DEFAULT_SIZE * Game.SCALE);
	public static final int OFFSET = 0;
	
	private Image grassSprite = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.getInstance().GRASS_SPRITES);
	private Image[][] grassSprites = new Image[1][2];
	private int grassIndex;

	public Grass(Type type) {
		super(SIZE, SIZE, type);
		grassIndex = (type == Type.GRASS_LEFT) ? 0 : 1;
		createObjectSprites(grassSprite, grassSprites, DEFAULT_SIZE, DEFAULT_SIZE);
		initHitbox(OFFSET, OFFSET, SIZE, SIZE);
	}
	
	@Override
	public void drawToCanvas(GraphicsContext gc) {
		if (isActive()) {
			gc.drawImage(grassSprites[getSpriteIndex()][grassIndex], getDrawPosition().getX(), getDrawPosition().getY(),
					getWidth(), getHeight());
			//drawHitbox(gc, Color.CYAN);
			//drawSpriteBox(gc, Color.RED);
		}
	}

}
