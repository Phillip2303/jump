package de.phillip.jumpandrun.controllers;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.models.Level;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class LevelManager {
	
	private Image outsideAtlas;
	private Level activeLevel;
	private Image[] levelTiles;
	private int level = 1;
	
	public LevelManager() {
		outsideAtlas = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.OUTSIDE_ATLAS);
		createLevel();
		//canvas.getGraphicsContext2D().drawImage(wi, 0, 0);
	}
	
	private void createLevelTilesFromAtlas() {
		PixelReader pr = outsideAtlas.getPixelReader();
		levelTiles = new Image[48];
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 12; i ++) {
				int index = j * 12 + i;
				levelTiles[index] = createSubImage(pr, i, j); 
			}
		}
	}
	
	private Image createSubImage(PixelReader pr, int x, int y) {
		WritableImage wi = new WritableImage(Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE);
		PixelWriter pw = wi.getPixelWriter();
		for (int j = 0; j < Game.TILES_DEFAULT_SIZE; j++) {
			for (int i = 0; i < Game.TILES_DEFAULT_SIZE; i++) {
				Color color = pr.getColor(x * Game.TILES_DEFAULT_SIZE + i, y * Game.TILES_DEFAULT_SIZE + j);
				pw.setColor(i, j, color);
			}
		}
		return wi;
	}
	
	private void createLevel() {
		activeLevel = new Level(ResourcePool.getInstance().getLevelData(level));
		createLevelTilesFromAtlas();
	}
	
	public Image[] getLevelTiles() {
		return levelTiles;
	}
	
	public Level getActiveLevel()  {
		return activeLevel;
	}
	
	private void setLevel(int level) {
		this.level = level;
		createLevel();
	}
}
