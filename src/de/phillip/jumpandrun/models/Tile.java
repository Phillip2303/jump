package de.phillip.jumpandrun.models;

import java.util.Arrays;
import java.util.List;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.models.GameObject.Type;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Tile extends Actor {

	private Image imageSprite;
	private boolean solid = true;
	private boolean water = false;
	private Grass grass;
	private int index;
	private List<Integer> grassTiles = Arrays.asList(0, 1, 2, 3, 30, 31, 33, 34, 35, 36, 37, 38, 39);

	public Tile(double width, double height, Image imageSprite, int index) {
		super(width, height);
		showSpriteBox(false);
		this.index = index;
		initHitbox(0, 0, width, height);
		if (index == 11 || index == 48 || index == 49) {
			solid = false;
		}
		if (grassTiles.contains(index)) {
			grass = new Grass(Type.randomGrass());
		}
		this.imageSprite = imageSprite;
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.drawImage(imageSprite, 0, 0, imageSprite.getWidth(), imageSprite.getHeight(), getDrawPosition().getX(),
				getDrawPosition().getY(), Game.TILES_SIZE, Game.TILES_SIZE);
	//	drawHitbox(gc, Color.BLACK);
		drawSpriteBox(gc, Color.BLACK);
		if (grass != null) {
			grass.drawToCanvas(gc);
		}
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
	
	public boolean isWater() {
		return water;
	}
	
	@Override
	public void setDrawPosition(double x, double y) {
		if (grass != null) {
			grass.setDrawPosition(x, y - Grass.SIZE);
		}
		super.setDrawPosition(x, y);
	}
	
	public void setWater(boolean value) {
		water = value;
	}

}
