package de.phillip.jumpandrun.controllers;

import java.util.ArrayList;
import java.util.List;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import de.phillip.jumpandrun.models.Enemy;
import de.phillip.jumpandrun.models.Level;
import de.phillip.jumpandrun.models.Player;
import de.phillip.jumpandrun.models.Tile;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.event.EventHandler;

public class EnemyManager implements EventHandler<GameEvent>{
	
	private List<Enemy> enemies = new ArrayList<>();
	private List<Tile> tiles;
	private int levelWidth;
	private Player player;
	private boolean isActive = true;
	private List<List<Tile>> tileRows;
	
	
	public List<Enemy> getEnemies() {
		return enemies;
	}
	
	public EnemyManager(Player player) {
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_SHOW_PAUSE_MENU, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_HIDE_PAUSE_MENU, this);
		this.player = player;
	}
	
	public void update() {
		if (isActive) {
			boolean enemiesAlive = false;
			for (Enemy enemy: enemies) {
				if (enemy.isActive()) {
					enemy.update();
					enemiesAlive = true;
				}
			}
			if (!enemiesAlive) {
				FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_SHOW_LEVEL_COMPLETED, null));
				isActive = false;
			}
		}
	}
	
	public void createEnemies(Level level) {
		enemies.clear();
		isActive = true;
		enemies.addAll(level.getEnemies());
		for (Enemy enemy: enemies) {
			enemy.setEnemyManager(this);
		}
	}
	
	/*
	public List<Tile> getTiles() {
		return tiles;
	}
	*/
	
	public Player getPlayer() {
		return player;
	}

	public int getLevelWidth() {
		return levelWidth;
	}

	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}
	
	public void setTileRows(List<List<Tile>> tileRows) {
		this.tileRows = tileRows;
	}
	
	public Tile getTileAt(double x, double y) {
		return tileRows.get((int) y / Game.TILES_SIZE).get((int) x / Game.TILES_SIZE);
	}
	
	public void setLevelWidth(int levelWidth) {
		this.levelWidth = levelWidth;
	}

	@Override
	public void handle(GameEvent event) {
		switch (event.getEventType().getName()) {
		case "JR_SHOW_PAUSE_MENU":
			for (Enemy enemy: enemies) {
				enemy.setEnemyAction(Enemy.IDLE);
			}
			break;
		case "JR_HIDE_PAUSE_MENU":
			for (Enemy enemy: enemies) {
				enemy.setEnemyAction(Enemy.RUNNING);
			}
			break;
		default:
			break;
		}

	}
}
