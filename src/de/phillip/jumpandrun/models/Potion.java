package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.models.GameObject.Type;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Potion extends GameObject {
	
	public static final int DEFAULT_WIDTH = 12;
	public static final int DEFAULT_HEIGHT = 16;
	public static final int WIDTH = (int) (DEFAULT_WIDTH * Game.SCALE);
	public static final int HEIGHT = (int) (DEFAULT_HEIGHT * Game.SCALE);
	public static final double HITBOX_WIDTH = 7 * Game.SCALE;
	public static final double HITBOX_HEIGHT = 14 * Game.SCALE;
	public static final double X_OFFSET = 3 * Game.SCALE;
	public static final double Y_OFFSET = 2 * Game.SCALE;

	private Image potionSprite = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.POTION_SPRITES);
	private Image[][] potionSprites = new Image[2][7];

	public Potion(GameObject.Type type) {
		super(WIDTH, HEIGHT, type);
		doAnimation(true);
		createActionSprites(potionSprite, potionSprites, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		initHitbox(X_OFFSET, Y_OFFSET, HITBOX_WIDTH, HITBOX_HEIGHT);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		if (isActive()) {
			gc.drawImage(potionSprites[getSpriteIndex()][getAniIndex()], getDrawPosition().getX(), getDrawPosition().getY(),
					getWidth(), getHeight());
			//drawHitbox(gc, Color.CYAN);
			drawSpriteBox(gc, Color.RED);
		}
	}

}
