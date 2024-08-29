
package de.phillip.jumpandrun.utils;

import de.phillip.jumpandrun.Game;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ResourcePool {
	
	private static final String ATLAS_PATH = "/assets/sprites/";
	private static final String LEVEL_PATH = "/assets/levels/";
	public static final String OUTSIDE_ATLAS = "outside_sprites.png";
	public static final String PLAYER_ATLAS = "player_sprites.png";
	public static final String LEVEL_1 = "level_one_data_long.png";
	
	private static ResourcePool resourcePool;
	private Image background;

	
	private ResourcePool() {
		
	}
	
	public static ResourcePool getInstance() {
		if (resourcePool == null) {
			resourcePool = new ResourcePool();
		}
		return resourcePool;
	}

	public Image getBackground() {
		return background;
	}
	
	public void loadResources() {
		background = new Image(getClass().getResource("/assets/images/galaxy.jpg").toString());
	}
	
	public Image getSpriteAtlas(String atlas) {
		return new Image (getClass().getResource(ATLAS_PATH + atlas).toString());
	}
	
	public Image getLevelAtlas(String atlas) {
		return new Image (getClass().getResource(LEVEL_PATH + atlas).toString());
	}
	
	public int[][] getLevelData(int level) {
		Image levelAtlas = null;
		switch (level) {
			case 1:
				levelAtlas = getLevelAtlas(LEVEL_1);
				break;
			default:
				break;
		}
		int[][] levelData = new int[Game.TILES_IN_HEIGHT][(int) levelAtlas.getWidth()];
		for (int j = 0; j < levelAtlas.getHeight(); j++) {
			for (int i = 0; i < levelAtlas.getWidth(); i++) {
				Color color = levelAtlas.getPixelReader().getColor(i, j);
				int colorValue = (int) (color.getRed() * 255);
				if (colorValue >= 48) {
					colorValue = 0;
				}
				//System.out.println("Color value " + colorValue);
				levelData[j][i] = colorValue;
			}
		}
		return levelData;
	}
}
