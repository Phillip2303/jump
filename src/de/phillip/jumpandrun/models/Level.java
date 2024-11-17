package de.phillip.jumpandrun.models;

import java.util.List;

import de.phillip.jumpandrun.utils.ResourcePool;

public class Level {

	private int[][] levelData;
	private int levelNumber;
	List<Enemy> enemies;
	

	public Level(int levelNumber) {
		this.levelNumber = levelNumber;
		levelData = ResourcePool.getInstance().getLevelData(levelNumber);
		enemies = ResourcePool.getInstance().getEnemies(levelNumber);
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
}
