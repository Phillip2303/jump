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
		levelData = ResourcePool.getInstance().getLevelData(levelNumber);
		enemies = ResourcePool.getInstance().getEnemies(levelNumber);
		gameObjects = ResourcePool.getInstance().getGameObjects(levelNumber);
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
