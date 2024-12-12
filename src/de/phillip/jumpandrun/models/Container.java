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

public class Container extends GameObject {
	
	public static final int DEFAULT_WIDTH = 40;
	public static final int DEFAULT_HEIGHT = 30;
	public static final int WIDTH = (int) (DEFAULT_WIDTH * Game.SCALE);
	public static final int HEIGHT = (int) (DEFAULT_HEIGHT * Game.SCALE);
	public static final double BOX_HITBOX_WIDTH = 25 * Game.SCALE;
	public static final double BOX_HITBOX_HEIGHT = 18 * Game.SCALE;
	public static final double BOX_X_OFFSET = 7 * Game.SCALE;
	public static final double BOX_Y_OFFSET = 12 * Game.SCALE;
	public static final double BARREL_HITBOX_WIDTH = 23 * Game.SCALE;
	public static final double BARREL_HITBOX_HEIGHT = 25 * Game.SCALE;
	public static final double BARREL_X_OFFSET = 8 * Game.SCALE;
	public static final double BARREL_Y_OFFSET = 5 * Game.SCALE;
	
	private Image containerSprite = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.CONTAINER_SPRITES);
	private Image[][] containerSprites = new Image[2][8];

	public Container(GameObject.Type type) {
		super(WIDTH, HEIGHT, type);
		createActionSprites(containerSprite, containerSprites, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		if (type == GameObject.Type.BOX) {
			initHitbox(BOX_X_OFFSET, BOX_Y_OFFSET, BOX_HITBOX_WIDTH, BOX_HITBOX_HEIGHT);
		} else {
			initHitbox(BARREL_X_OFFSET, BARREL_Y_OFFSET, BARREL_HITBOX_WIDTH, BARREL_HITBOX_HEIGHT);
		}
		
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void drawToCanvas(GraphicsContext gc) {
		if (isActive()) {
			gc.drawImage(containerSprites[getSpriteIndex()][getAniIndex()], getDrawPosition().getX(), getDrawPosition().getY(),
					getWidth(), getHeight());
			drawSpriteBox(gc, Color.LIME);
			drawHitbox(gc, Color.CYAN);
		}
	}
	
	public void gotHit() {
		System.out.println("Container got hit");
		setAniIndex(2);
		doAnimation(true);
	}

}
