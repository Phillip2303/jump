package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tile extends Actor {
	
	private Image imageSprite;

	public Tile(double width, double height, Image imageSprite) {
		super(width, height);
		this.imageSprite = imageSprite;
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.drawImage(imageSprite, 0, 0, imageSprite.getWidth(), imageSprite.getHeight(), 
				getDrawPosition().getX(), getDrawPosition().getY(), Game.TILES_SIZE, Game.TILES_SIZE);
	}

	@Override
	public void debugOut() {

	}

}
