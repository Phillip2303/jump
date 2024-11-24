package de.phillip.jumpandrun.controllers;

import java.util.ArrayList;
import java.util.List;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.models.Enemy;
import de.phillip.jumpandrun.models.GameObject;
import de.phillip.jumpandrun.models.Level;

public class GameObjectManager {

	public enum Type {
		RED_POTION(0),
		BLUE_POTION(1),
		BARREL(2),
		BOX(3),
		SPIKE(4),
		CANNON_LEFT(5),
		CANNON_RIGHT(6),
		TREE_1(7),
		TREE_2(8),
		TREE_3(9);
		
		private final int colorValue;
		
		private Type(int colorValue) {
			this.colorValue = colorValue;
		}

		/**
		 * @return the colorValue
		 */
		public int getColorValue() {
			return colorValue;
		}
	}
	
	private List<GameObject> gameObjects = new ArrayList<>();
	
	public GameObjectManager() {
		
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
