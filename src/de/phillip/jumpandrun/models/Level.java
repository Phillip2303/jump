package de.phillip.jumpandrun.models;

import java.util.Collection;
import java.util.List;

import de.phillip.jumpandrun.utils.ResourcePool;

public class Level {

	private int[][] levelData;
	private int levelNumber;
	private List<Enemy> enemies;
	private List<GameObject> gameObjects;
	

	public Level(int levelNumber) {
		this.levelNumber = levelNumber;
		ResourcePool.getInstance().loadLevelData(levelNumber);
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
}
