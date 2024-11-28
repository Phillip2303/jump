package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.controllers.GameObjectManager.Type;
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
	private Image[][] potionSprites;

	public Potion(Type type) {
		super(WIDTH, HEIGHT, type);
		createSprites();
		initHitbox(X_OFFSET, Y_OFFSET, HITBOX_WIDTH, HITBOX_HEIGHT);
		// TODO Auto-generated constructor stub
	}
	
	private void createSprites() {
		PixelReader pr = potionSprite.getPixelReader();
		potionSprites = new Image[2][7];
		for (int j = 0; j < potionSprites.length; j++) {
			for (int i = 0; i < potionSprites[j].length; i++) {
				potionSprites[j][i] = createSubImage(pr, i, j);
			}
		}
	}

	private Image createSubImage(PixelReader pr, int x, int y) {
		WritableImage wi = new WritableImage(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		PixelWriter pw = wi.getPixelWriter();
		for (int j = 0; j < DEFAULT_HEIGHT; j++) {
			for (int i = 0; i < DEFAULT_WIDTH; i++) {
				Color color = pr.getColor(x * DEFAULT_WIDTH + i, y * DEFAULT_HEIGHT + j);
				pw.setColor(i, j, color);
			}
		}
		return wi;
	}
	
	@Override
	public void drawToCanvas(GraphicsContext gc) {
		if (isActive()) {
			gc.drawImage(potionSprites[getSpriteIndex()][getAniIndex()], getDrawPosition().getX(), getDrawPosition().getY(),
					getWidth(), getHeight());
			//drawHitbox(gc, Color.CYAN);
		}
	}

}
