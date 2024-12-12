package de.phillip.jumpandrun.controllers;

import java.util.ArrayList;
import java.util.List;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.models.Enemy;
import de.phillip.jumpandrun.models.GameObject;
import de.phillip.jumpandrun.models.GameObject.Type;
import de.phillip.jumpandrun.models.Level;
import de.phillip.jumpandrun.models.Player;
import de.phillip.jumpandrun.models.Potion;
import javafx.geometry.Point2D;

public class GameObjectManager {
	
	private List<GameObject> gameObjects = new ArrayList<>();
	private Player player;
	
	public GameObjectManager(Player player) {
		this.player = player;
	}
	
	public void update() {
		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject gameObject = gameObjects.get(i);
			if (gameObject.isActive()) {
				gameObject.update();
			}
		}
	}
	
	public void createGameObjects(Level level) {
		gameObjects.clear();
		gameObjects.addAll(level.getGameObjects());
		for (GameObject gameObject: gameObjects) {
			gameObject.setGameObjectManager(this);
		}
	}

	/**
	 * @return the gameObjects
	 */
	public List<GameObject> getGameObjects() {
		return gameObjects;
	}
	
	public Potion createPotion(Type type, Point2D point2D) {
		Potion potion = new Potion(type);
		potion.setDrawPosition(point2D.getX() + ((Game.TILES_SIZE - potion.getWidth()) / 2), point2D.getY() - 2 * Game.SCALE);
		gameObjects.add(potion);
		return potion;
	}

	public Player getPlayer() {
		return player;
	}
	
}
