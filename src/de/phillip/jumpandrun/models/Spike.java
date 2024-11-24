package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.controllers.GameObjectManager;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Spike extends GameObject {
	
	public static final int DEFAULT_WIDTH = 32;
	public static final int DEFAULT_HEIGHT = 32;
	public static final int WIDTH = (int) (DEFAULT_WIDTH * Game.SCALE);
	public static final int HEIGHT = (int) (DEFAULT_HEIGHT * Game.SCALE);
	public static final double HITBOX_WIDTH = 32 * Game.SCALE;
	public static final double HITBOX_HEIGHT = 16 * Game.SCALE;
	public static final double X_OFFSET = 0;
	public static final double Y_OFFSET = 16 * Game.SCALE;
	
	private Image spikeImage = ResourcePool.getInstance().getSpike();

	public Spike() {
		super(WIDTH, HEIGHT, GameObjectManager.Type.SPIKE);
		initHitbox(X_OFFSET, Y_OFFSET, HITBOX_WIDTH, HITBOX_HEIGHT);
	}
	
	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.drawImage(spikeImage, 0, 0, spikeImage.getWidth(), spikeImage.getHeight(), 
				getDrawPosition().getX(), getDrawPosition().getY(), WIDTH, HEIGHT);
		drawHitbox(gc, Color.CYAN);
	}

}
