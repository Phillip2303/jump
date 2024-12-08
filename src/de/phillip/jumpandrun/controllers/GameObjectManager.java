package de.phillip.jumpandrun.controllers;

import java.util.ArrayList;
import java.util.List;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.models.Enemy;
import de.phillip.jumpandrun.models.GameObject;
import de.phillip.jumpandrun.models.GameObject.Type;
import de.phillip.jumpandrun.models.Level;
import de.phillip.jumpandrun.models.Potion;
import javafx.geometry.Point2D;

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
	
	public Potion getPotion(Type type, Point2D point2D) {
		Potion potion = new Potion(type);
		potion.setDrawPosition(point2D.getX(), point2D.getY());
		gameObjects.add(potion);
		return potion;
	}
	
}
