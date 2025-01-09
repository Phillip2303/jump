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
import de.phillip.jumpandrun.models.Ship;
import de.phillip.jumpandrun.models.Tile;
import javafx.geometry.Point2D;

public class GameObjectManager {
	
	private List<GameObject> gameObjects = new ArrayList<>();
	private Player player;
	private List<Tile> tiles;
	private int levelWidth;
	
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
		if (level.getLevelNumber() == 1) {
			Ship ship = new Ship();
			ship.setDrawPosition(100 * Game.SCALE, 288 * Game.SCALE);
			gameObjects.add(ship);
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

	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}
	
	public List<Tile> getTiles() {
		return tiles;
	}

	/**
	 * @return the levelWidth
	 */
	public int getLevelWidth() {
		return levelWidth;
	}

	/**
	 * @param levelWidth the levelWidth to set
	 */
	public void setLevelWidth(int levelWidth) {
		this.levelWidth = levelWidth;
	}
	
}
