package de.phillip.jumpandrun.controllers;

import java.util.ArrayList;
import java.util.List;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.models.Crabby;
import de.phillip.jumpandrun.models.Drawable;
import de.phillip.jumpandrun.models.Enemy;
import de.phillip.jumpandrun.models.Tile;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class EnemyManager {
	
	private List<Drawable> enemies = new ArrayList<>();
	
	public List<Drawable> getDrawables() {
		return enemies;
	}
	
	public EnemyManager() {

	}
	
	private void update() {
		
	}
	
	public void createEnemies(int level) {
		List<Point2D> enemyPositions = ResourcePool.getInstance().getEnemyPositions(level, Enemy.Type.CRABBY);
		for (Point2D point: enemyPositions) {
			Crabby crabby = new Crabby();
			crabby.setDrawPosition(point.getX(), point.getY());
			enemies.add(crabby);
		}
	}
}
