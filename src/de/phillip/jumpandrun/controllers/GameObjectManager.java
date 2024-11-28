package de.phillip.jumpandrun.controllers;

import java.util.ArrayList;
import java.util.List;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.models.Enemy;
import de.phillip.jumpandrun.models.GameObject;
import de.phillip.jumpandrun.models.Level;

public class GameObjectManager {
	
	public enum Type {
		RED_POTION(0, 15, 0),
		BLUE_POTION(1, 0, 10),
		BARREL(2, 0, 0),
		BOX(3, 0, 0),
		SPIKE(4, 0, 0),
		CANNON_LEFT(5, 0, 0),
		CANNON_RIGHT(6, 0, 0),
		TREE_1(7, 0, 0),
		TREE_2(8, 0, 0),
		TREE_3(9, 0, 0);
		
		private final int colorValue;
		private final int healthValue;
		private final int powerValue;
		
		private Type(int colorValue, int healthValue, int powerValue) {
			this.colorValue = colorValue;
			this.healthValue = healthValue;
			this.powerValue = powerValue;
		}
		
		/**
		 * @return the colorValue
		 */
		public int getColorValue() {
			return colorValue;
		}

		/**
		 * @return the healthValue
		 */
		public int getHealthValue() {
			return healthValue;
		}

		/**
		 * @return the powerValue
		 */
		public int getPowerValue() {
			return powerValue;
		}
	}
	
	private List<GameObject> gameObjects = new ArrayList<>();
	
	public GameObjectManager() {
		
	}
	
	public void update() {
		for (GameObject gameObject: gameObjects) {
			if (gameObject.isActive()) {
				gameObject.update();
			}
		}
	}
	
	public void createGameObjects(Level level) {
		gameObjects.clear();
		gameObjects.addAll(level.getGameObjects());
	}

	/**
	 * @return the gameObjects
	 */
	public List<GameObject> getGameObjects() {
		return gameObjects;
	}
	
}
