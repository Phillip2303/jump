package de.phillip.jumpandrun.controllers;

import java.util.ArrayList;
import java.util.List;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.models.Enemy;
import de.phillip.jumpandrun.models.GameObject;
import de.phillip.jumpandrun.models.Level;

public class GameObjectManager {
	
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
