
package de.phillip.jumpandrun.utils;

import java.util.ArrayList;
import java.util.List;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.models.Cannon;
import de.phillip.jumpandrun.models.Container;
import de.phillip.jumpandrun.models.Crabby;
import de.phillip.jumpandrun.models.Enemy;
import de.phillip.jumpandrun.models.GameObject;
import de.phillip.jumpandrun.models.GameObject.Type;
import de.phillip.jumpandrun.models.Pinkstar;
import de.phillip.jumpandrun.models.Potion;
import de.phillip.jumpandrun.models.Shark;
import de.phillip.jumpandrun.models.Ship;
import de.phillip.jumpandrun.models.Spike;
import de.phillip.jumpandrun.models.Tree;
import javafx.geometry.Point2D;
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
	public static final String POTION_SPRITES = "potions_sprites.png";
	public static final String CONTAINER_SPRITES = "objects_sprites.png";
	public static final String CANNON_SPRITES = "cannon_sprites.png";
	public static final String GRASS_SPRITES = "grass_sprites.png";
	public static final String WATER_SPRITES = "water_sprites.png";
	public static final String TREE_1_SPRITES = "tree_one_sprites.png";
	public static final String TREE_2_SPRITES = "tree_two_sprites.png";
	public static final String SHIP_SPRITES = "ship_sprites.png";
	public static final String PINKSTAR_SPRITES = "pinkstar_sprites.png";
	public static final String SHARK_SPRITES = "shark_sprites.png";

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
	private Image spike;
	private Image cannonBall;
	private Image water;
	private Image rain;
	private int[][] levelData;
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private List<GameObject> gameObjects = new ArrayList<GameObject>();
	private Point2D playerSpawn = new Point2D(215, 196);

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
		spike = new Image(getClass().getResource("/assets/images/spikes_atlas.png").toString());
		cannonBall = new Image(getClass().getResource("/assets/images/ball.png").toString());
		water = new Image(getClass().getResource("/assets/images/water.png").toString());
		rain = new Image(getClass().getResource("/assets/images/rain_particle.png").toString());
	}

	public Image getSpriteAtlas(String atlas) {
		return new Image(getClass().getResource(ATLAS_PATH + atlas).toString());
	}

	public Image getLevelAtlas(String atlas) {
		return new Image(getClass().getResource(LEVEL_PATH + atlas).toString());
	}
	
	public void loadLevelData(int level) {
		enemies.clear();
		gameObjects.clear();
		String resourcePath = LEVEL_PATH + String.valueOf(level) + ".png"; 
		Image levelAtlas = new Image(getClass().getResource(resourcePath).toString());
		levelData = new int[Game.TILES_IN_HEIGHT][(int) levelAtlas.getWidth()];
		for (int j = 0; j < levelAtlas.getHeight(); j++) {
			for (int i = 0; i < levelAtlas.getWidth(); i++) {
				Color color = levelAtlas.getPixelReader().getColor(i, j);
				int redValue = (int) (color.getRed() * 255);
				if (redValue >= 50) {
					redValue = 0;
				}
				levelData[j][i] = redValue;
				
				
				int greenValue = (int) (color.getGreen() * 255);
				if (greenValue == Enemy.Type.CRABBY.getColorValue()) {
					Crabby crabby = createCrabby(i, j); 
					enemies.add(crabby);
				} else if (greenValue == Enemy.Type.PINKSTAR.getColorValue()) {
					Pinkstar pinkstar = createPinkstar(i, j);
					enemies.add(pinkstar);
				} else if (greenValue == Enemy.Type.SHARK.getColorValue()) {
					Shark shark = createShark(i, j);
					enemies.add(shark);
				}
				
				int greenPlayerValue = (int) (color.getGreen() * 255);
				if (greenPlayerValue == 100) {
					playerSpawn = new Point2D(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
				}
				
				
				int blueValue = (int) (color.getBlue() * 255);
				if (blueValue == GameObject.Type.SPIKE.getColorValue()) {
					Spike spike = createSpikes(i, j);
					gameObjects.add(spike);
				} else if (blueValue == GameObject.Type.BLUE_POTION.getColorValue()) {
					Potion bluePotion = createPotion(i, j, GameObject.Type.BLUE_POTION);
					gameObjects.add(bluePotion);
				} else if (blueValue == GameObject.Type.RED_POTION.getColorValue()) {
					Potion redPotion = createPotion(i, j, GameObject.Type.RED_POTION);
					gameObjects.add(redPotion);
				} else if (blueValue == GameObject.Type.BOX.getColorValue()) {
					Container box = createContainer(i, j, GameObject.Type.BOX);
					gameObjects.add(box);
				} else if (blueValue == GameObject.Type.BARREL.getColorValue()) {
					Container barrel = createContainer(i, j, GameObject.Type.BARREL);
					gameObjects.add(barrel);
				} else if (blueValue == GameObject.Type.CANNON_LEFT.getColorValue()) {
					Cannon cannonLeft = createCannon(i, j, GameObject.Type.CANNON_LEFT);
					gameObjects.add(cannonLeft);
				} else if (blueValue == GameObject.Type.CANNON_RIGHT.getColorValue()) {
					Cannon cannonRight = createCannon(i, j, GameObject.Type.CANNON_RIGHT);
					gameObjects.add(cannonRight);
				} else if (blueValue == GameObject.Type.TREE_1.getColorValue()) {
					Tree tree1 = createTree(i, j, GameObject.Type.TREE_1);
					gameObjects.add(tree1);
				} else if (blueValue == GameObject.Type.TREE_2.getColorValue()) {
					Tree tree2 = createTree(i, j, GameObject.Type.TREE_2);
					gameObjects.add(tree2);
				} else if (blueValue == GameObject.Type.TREE_3.getColorValue()) {
					Tree tree3 = createTree(i, j, GameObject.Type.TREE_3);
					gameObjects.add(tree3);
				}
				
			}
		}
	}

	public Image getBigClouds() {
		return bigClouds;
	}

	public Image getSmallClouds() {
		return smallClouds;
	}

	public Image getWater() {
		return water;
	}

	public Image getPlayingBg() {
		return playingBg;
	}
	
	private Cannon createCannon(int i, int j, GameObject.Type type) {
		Cannon cannon = new Cannon(type);
		cannon.setDrawPosition(i * Game.TILES_SIZE + Cannon.X_OFFSET, j * Game.TILES_SIZE + Cannon.Y_OFFSET);
		return cannon;
	}

	private Spike createSpikes(int i, int j) {
		Spike spike = new Spike();
		spike.setDrawPosition(i * Game.TILES_SIZE, j * Game.TILES_SIZE + (Spike.HEIGHT - Spike.Y_OFFSET - Spike.HITBOX_HEIGHT));
		return spike;
		
	}
	
	private Potion createPotion(int i, int j, GameObject.Type type) {
		//System.out.println("Create Potion Type: " + type);
		Potion potion = new Potion(type);
		potion.setDrawPosition(i * Game.TILES_SIZE + ((Game.TILES_SIZE - potion.getWidth()) / 2), j * Game.TILES_SIZE + (Potion.HEIGHT - Potion.Y_OFFSET - Potion.HITBOX_HEIGHT));
		return potion;
	}
	
	private Container createContainer(int i, int j, GameObject.Type type) {
		//System.out.println("Create Container Type: " + type);
		Container container = new Container(type);
		if (type == GameObject.Type.BOX) {
			container.setDrawPosition(i * Game.TILES_SIZE, j * Game.TILES_SIZE + 2 * Game.SCALE);	
		} else 
			container.setDrawPosition(i * Game.TILES_SIZE, j * Game.TILES_SIZE + 2 * Game.SCALE);	
		return container;
	}
	
	private Tree createTree(int i, int j, Type type) {
		switch (type) {
		case TREE_1:
			Tree tree1 = new Tree(Tree.WIDTH_1, Tree.HEIGHT_1, type);
			tree1.setDrawPosition(i * Game.TILES_SIZE + Tree.X_OFFSET_1, j * Game.TILES_SIZE + Tree.Y_OFFSET_1);
			return tree1;
		case TREE_2:
			Tree tree2 = new Tree(Tree.WIDTH_2, Tree.HEIGHT_2, type);
			tree2.setDrawPosition(i * Game.TILES_SIZE + Tree.X_OFFSET_2, j * Game.TILES_SIZE + Tree.Y_OFFSET_2);
			return tree2;
		case TREE_3:
			Tree tree3 = new Tree(Tree.WIDTH_3, Tree.HEIGHT_3, type);
			tree3.setDrawPosition(i * Game.TILES_SIZE + Tree.X_OFFSET_3, j * Game.TILES_SIZE + Tree.Y_OFFSET_3);
			return tree3;
		default:
			return null;
		}
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

	/**
	 * @return the spike
	 */
	public Image getSpike() {
		return spike;
	}

	private Shark createShark(int i, int j) {
		Shark shark= new Shark();
		shark.setDrawPosition(i * Game.TILES_SIZE, j * Game.TILES_SIZE + Game.SCALE * 6);
		return shark;
	}

	private Crabby createCrabby(int i, int j) {
		Crabby crabby = new Crabby();
		crabby.setDrawPosition(i * Game.TILES_SIZE, j * Game.TILES_SIZE + (Crabby.HEIGHT - Crabby.Y_OFFSET - Crabby.HITBOX_HEIGHT));
		return crabby;
	}
	
	private Pinkstar createPinkstar(int i, int j) {
		Pinkstar pinkstar= new Pinkstar();
		pinkstar.setDrawPosition(i * Game.TILES_SIZE, j * Game.TILES_SIZE + Game.SCALE * 5);
		return pinkstar;
	}

	public Image getCannonBall() {
		return cannonBall;
	}

	public int[][] getLevelData() {
		return levelData;
	}

	public List<Enemy> getLevelEnemies() {
		return enemies;
	}

	public List<GameObject> getLevelGameObjects() {
		return gameObjects;
	}

	/**
	 * @return the playerSpawn
	 */
	public Point2D getPlayerSpawn() {
		return playerSpawn;
	}

	/**
	 * @return the rain
	 */
	public Image getRain() {
		return rain;
	} 
}
