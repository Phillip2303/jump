package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Tile extends Actor {

	private Image imageSprite;
	private boolean solid = true;

	public Tile(double width, double height, Image imageSprite, int index) {
		super(width, height);
		initHitbox(0, 0, width, height);
		if (index == 11) {
			solid = false;
		}
		this.imageSprite = imageSprite;
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.drawImage(imageSprite, 0, 0, imageSprite.getWidth(), imageSprite.getHeight(), getDrawPosition().getX(),
				getDrawPosition().getY(), Game.TILES_SIZE, Game.TILES_SIZE);
		drawHitbox(gc, Color.BLACK);
	}

	/*private void drawHitBox(GraphicsContext gc) {
		gc.strokeRect(getDrawPosition().getX(), getDrawPosition().getY(), getWidth(), getHeight());
	}*/

	@Override
	public void debugOut() {

	}

	public boolean isSolid() {
		return solid;
	}

}
