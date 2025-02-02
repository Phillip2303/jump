package de.phillip.jumpandrun.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.models.AnimatedWaterTile;
import de.phillip.jumpandrun.models.Drawable;
import de.phillip.jumpandrun.models.Level;
import de.phillip.jumpandrun.models.Tile;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class LevelManager {

	private Image outsideAtlas;
	private Image waterSprites;
	private Image waterImage;
	private Level activeLevel;
	private Image[] levelTiles;
	private List<AnimatedWaterTile> animatedWaterTiles = new ArrayList<>();
	private int level = 2;
	private int oldLevel = 1;
	private Map <Integer, List<Integer>> cloudNumbers = new HashMap<>();
	private List<List<Tile>> tileRows = new ArrayList<>();

	public LevelManager() {
		outsideAtlas = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.OUTSIDE_ATLAS);
		waterSprites = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.WATER_SPRITES);
		waterImage = ResourcePool.getInstance().getWater();
		createLevel();
		initClouds();
		// canvas.getGraphicsContext2D().drawImage(wi, 0, 0);
	}

	private void initClouds() {
		cloudNumbers.put(1, Arrays.asList(5, 8));
		cloudNumbers.put(2, Arrays.asList(8, 12));
		
		cloudNumbers.put(3, List.of(8, 13));
		cloudNumbers.put(4, List.of(8, 15));

		
		cloudNumbers.put(5, Stream.of(8, 12).toList());
	}

	private void createLevelTilesFromAtlas() {
		PixelReader pr = outsideAtlas.getPixelReader();
		levelTiles = new Image[48];
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 12; i++) {
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
	
	public List<Drawable> createLevelTiles() {
		tileRows.clear();
		animatedWaterTiles.clear();
		List<Drawable> tiles = new ArrayList<>();
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
			List<Tile> column = new ArrayList<>();
			tileRows.add(column);
			for (int i = 0; i < getActiveLevel().getTilesInWidth(); i++) {
				int index = getActiveLevel().getSpriteIndex(i, j);
				Tile tile = null;
				switch (index) {
				case 48:
					tile = new AnimatedWaterTile(Game.TILES_SIZE, Game.TILES_SIZE, waterSprites, index);
					animatedWaterTiles.add((AnimatedWaterTile) tile);
					break;
				case 49:
					tile = new Tile(Game.TILES_SIZE, Game.TILES_SIZE, waterImage, index);
					break;
				default:
					tile = new Tile(Game.TILES_SIZE, Game.TILES_SIZE, levelTiles[index], index);
					break;
				}
				// System.out.println("Sprite Index: " + index);				
				tile.setDrawPosition(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
				tiles.add(tile);
				column.add(tile);
			}
		}
		return tiles;
	}
	
	public List<List<Tile>> getTileRows() {
		return tileRows;
	}
	
	public void update() {
		for(AnimatedWaterTile animatedWaterTile: animatedWaterTiles) {
			animatedWaterTile.update();
		}
	}

	private void createLevel() {
		activeLevel = new Level(level);
		createLevelTilesFromAtlas();
	}

	public Image[] getLevelTiles() {
		return levelTiles;
	}

	public Level getActiveLevel() {
		return activeLevel;
	}

	public void setLevel(int level) {
		oldLevel = this.level;
		this.level = level;
		createLevel();
	}

	/**
	 * @return the oldLevel
	 */
	public int getOldLevel() {
		return oldLevel;
	}

	public Map<Integer, List<Integer>> getCloudNumbers() {
		return cloudNumbers;
	}
}
