package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.utils.ResourcePool;

public class Level {

	private int[][] levelData;
	private int levelNumber;
	

	public Level(int levelNumber) {
		this.levelNumber = levelNumber;
		levelData = ResourcePool.getInstance().getLevelData(levelNumber);
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
}
