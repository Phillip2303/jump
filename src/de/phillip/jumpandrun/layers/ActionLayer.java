package de.phillip.jumpandrun.layers;

import java.util.ArrayList;
import java.util.List;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.controllers.LevelManager;
import de.phillip.jumpandrun.models.CanvasLayer;
import de.phillip.jumpandrun.models.Drawable;
import de.phillip.jumpandrun.models.Tile;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class ActionLayer extends Canvas implements CanvasLayer {
	
	private LevelManager levelManager;
	private List<Drawable> actors = new ArrayList<Drawable>();
	
	public ActionLayer(int width, int height, LevelManager levelManager) {
		super(width, height);
		this.levelManager = levelManager;
		createLevel();
	}

	private void createLevel() {
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) { 
			for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
				int index = levelManager.getActiveLevel().getSpriteIndex(i, j);
				System.out.println("Sprite Index: " + index);
				Image image = levelManager.getLevelTiles()[index];
				Tile tile = new Tile(Game.TILES_SIZE, Game.TILES_SIZE, image);
				tile.setDrawPosition(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
				actors.add(tile);
			}
		}
	}

	@Override
	public List<Drawable> getDrawables() {
		return actors;
	}

	@Override
	public void prepareLayer() {
		getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
	}

	@Override
	public void updateLayer(float secondsSinceLastFrame) {

	}

	@Override
	public void resetGame() {

	}

}
