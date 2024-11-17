
package de.phillip.jumpandrun.utils;

import java.util.ArrayList;
import java.util.List;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.models.Crabby;
import de.phillip.jumpandrun.models.Enemy;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ResourcePool {

	private static final String ATLAS_PATH = "/assets/sprites/";
	private static final String LEVEL_PATH = "/assets/levels/";
	public static final String OUTSIDE_ATLAS = "outside_sprites.png";
	public static final String PLAYER_ATLAS = "player_sprites.png";
	public static final String LEVEL_1 = "level_one_data_long.png";
	public static final String BUTTON_SPRITES = "button_sprites.png";
	public static final String SOUND_BUTTONS_SPRITES = "sound_button_sprites.png";
	public static final String URM_BUTTONS_SPRITES = "urm_buttons_sprites.png";
	public static final String CRABBY_SPRITES = "crabby_sprites.png";

	private static ResourcePool resourcePool;
	// private Image background;
	private Image menuBackground;
	private Image bigClouds;
	private Image smallClouds;
	private Image playingBg;
	private Image pauseBackground;
	private Image gameOverBackground;
	private Image levelCompletedBackground;
	private Image healthPowerBar;

	private ResourcePool() {

	}

	public static ResourcePool getInstance() {
		if (resourcePool == null) {
			resourcePool = new ResourcePool();
		}
		return resourcePool;
	}

	/*
	 * public Image getBackground() { return background; }
	 */

	public Image getMenuBackground() {
		return menuBackground;
	}

	public Image getHealthPowerBar() {
		return healthPowerBar;
	}

	public Image getPauseBackground() {
		return pauseBackground;
	}

	public void loadResources() {
		// background = new
		// Image(getClass().getResource("/assets/images/galaxy.jpg").toString());
		menuBackground = new Image(getClass().getResource("/assets/images/menu_background.png").toString());
		pauseBackground = new Image(getClass().getResource("/assets/images/pause_menu_background.png").toString());
		gameOverBackground = new Image(getClass().getResource("/assets/images/death_menu_background.png").toString());
		levelCompletedBackground = new Image(getClass().getResource("/assets/images/completed_background.png").toString());
		bigClouds = new Image(getClass().getResource("/assets/images/big_clouds.png").toString());
		smallClouds = new Image(getClass().getResource("/assets/images/small_clouds.png").toString());
		playingBg = new Image(getClass().getResource("/assets/images/playing_bg_img.png").toString());
		healthPowerBar = new Image(getClass().getResource("/assets/images/health_power_bar.png").toString());
	}

	public Image getSpriteAtlas(String atlas) {
		return new Image(getClass().getResource(ATLAS_PATH + atlas).toString());
	}

	public Image getLevelAtlas(String atlas) {
		return new Image(getClass().getResource(LEVEL_PATH + atlas).toString());
	}

	public int[][] getLevelData(int level) {
		String resourcePath = LEVEL_PATH + String.valueOf(level) + ".png"; 
		Image levelAtlas = new Image(getClass().getResource(resourcePath).toString());
		int[][] levelData = new int[Game.TILES_IN_HEIGHT][(int) levelAtlas.getWidth()];
		for (int j = 0; j < levelAtlas.getHeight(); j++) {
			for (int i = 0; i < levelAtlas.getWidth(); i++) {
				Color color = levelAtlas.getPixelReader().getColor(i, j);
				int colorValue = (int) (color.getRed() * 255);
				if (colorValue >= 48) {
					colorValue = 0;
				}
				// System.out.println("Color value " + colorValue);
				levelData[j][i] = colorValue;
			}
		}
		return levelData;
	}

	public Image getBigClouds() {
		return bigClouds;
	}

	public Image getSmallClouds() {
		return smallClouds;
	}

	public Image getPlayingBg() {
		return playingBg;
	}
	
	public List<Enemy> getEnemies(int level) {
		String resourcePath = LEVEL_PATH + String.valueOf(level) + ".png"; 
		Image levelAtlas = new Image(getClass().getResource(resourcePath).toString());		
		List<Enemy> enemies = new ArrayList<>();
		for (int j = 0; j < levelAtlas.getHeight(); j++) {
			for (int i = 0; i < levelAtlas.getWidth(); i++) {
				Color color = levelAtlas.getPixelReader().getColor(i, j);
				int colorValue = (int) (color.getGreen() * 255);
				if (colorValue == Enemy.Type.CRABBY.getColorValue()) {
					Crabby crabby = createCrabby(i, j); 
					enemies.add(crabby);
				} else if (colorValue == Enemy.Type.CANNON.getColorValue()) {
					createCannon();
				} else if (colorValue == Enemy.Type.SHARK.getColorValue()) {
					createShark();
				}
			}
		}
		return enemies;
		
	}

	public Image getGameOverBackground() {
		return gameOverBackground;
	}

	/**
	 * @return the levelCompletedBackround
	 */
	public Image getLevelCompletedBackground() {
		return levelCompletedBackground;
	}

	private void createShark() {
		// TODO Auto-generated method stub
		
	}

	private void createCannon() {
		// TODO Auto-generated method stub
		
	}

	private Crabby createCrabby(int i, int j) {
		Crabby crabby = new Crabby();
		crabby.setDrawPosition(i * Game.TILES_SIZE, j * Game.TILES_SIZE + (Crabby.HEIGHT - Crabby.Y_OFFSET - Crabby.HITBOX_HEIGHT));
		return crabby;
	}
}
