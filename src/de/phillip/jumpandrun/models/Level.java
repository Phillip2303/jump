package de.phillip.jumpandrun.models;

import java.util.Collection;
import java.util.List;

import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.geometry.Point2D;

public class Level {

	private int[][] levelData;
	private int levelNumber;
	private List<Enemy> enemies;
	private List<GameObject> gameObjects;
	private Point2D playerSpawn;
	

	public Level(int levelNumber) {
		this.levelNumber = levelNumber;
		ResourcePool.getInstance().loadLevelData(levelNumber);
		playerSpawn = ResourcePool.getInstance().getPlayerSpawn();
		levelData = ResourcePool.getInstance().getLevelData();
		enemies = ResourcePool.getInstance().getLevelEnemies();
		gameObjects = ResourcePool.getInstance().getLevelGameObjects();
	}

	public int getSpriteIndex(int x, int y) {
		return levelData[y][x];
	}

	public int getTilesInWidth() {
		return levelData[0].length;
	}

	/**
	 * @return the levelNumber
	 */
	public int getLevelNumber() {
		return levelNumber;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	/**
	 * @return the playerSpawn
	 */
	public Point2D getPlayerSpawn() {
		return playerSpawn;
	}
}
