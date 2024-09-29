package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.controllers.EnemyManager;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public abstract class Enemy extends Actor {

	public enum Type {
		CRABBY(0), 
		CANNON(1), 
		SHARK(2);
		
		private final int value;
		
		private Type(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}
	
	public enum Direction {
		LEFT,
		RIGHT;
	}

	public static final int IDLE = 0;
	public static final int RUNNING = 1;
	public static final int ATTACK = 2;
	public static final int HIT = 3;
	public static final int DEAD = 4;

	private Type type;
	private int aniIndex;
	private int aniTic;
	private int aniSpeed = 25;
	private int enemyAction = RUNNING;
	private EnemyManager enemyManager;
	private int tileY;
	private boolean isTileY;

	public Enemy(double width, double height, Type type) {
		super(width, height);
		this.type = type;
	}
	
	public EnemyManager getEnemyManager() {
		return enemyManager;
	}

	public void setEnemyManager(EnemyManager enemyManager) {
		this.enemyManager = enemyManager;
	}

	
	public void update() {
		updateAnimationTic();
	}

	private void updateAnimationTic() {
		aniTic++;
		if (aniTic >= aniSpeed) {
			aniTic = 0;
			aniIndex++;
		}
		if (aniIndex >= getSpriteCount(enemyAction)) {
			aniIndex = 0;
		}
	}

	public void setEnemyAction(int enemyAction) {
		this.enemyAction = enemyAction;
		if (this.enemyAction != enemyAction) {
			resetAniTic();
		}
	}

	private void resetAniTic() {
		aniTic = 0;
		aniIndex = 0;
	}

	private int getSpriteCount(int enemyAction) {
		switch (type) {
		case CRABBY:
			switch (enemyAction) {
			case IDLE:
				return 9;
			case RUNNING:
				return 6;
			case ATTACK:
				return 7;
			case HIT:
				return 4;
			case DEAD:
				return 5;
			default:
				return 0;
			}
		default:
			return 0;
		}
	}
	
	public int getEnemyAction() {
		return enemyAction;
	}

	

	public int getAniIndex() {
		return aniIndex;
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		//
	}

	@Override
	public void debugOut() {

	}
	
	@Override
	public void setDrawPosition(double x, double y) {
		super.setDrawPosition(x, y);
		if (!isTileY) {
			tileY = (int) (getHitBox().getMinY() / Game.TILES_SIZE);
			isTileY = true;
		}
	}
	
	protected boolean canSeePlayer(Player player) {
		int playerTileY = (int) (player.getHitBox().getMinY() / Game.TILES_SIZE);
		if (tileY == playerTileY) {
			if (isPlayerInSight(player)) {
				moveTowardsPlayer();
				return true;
			}
		}
		return false;
	}

	private void moveTowardsPlayer() {
		
	}

	private boolean isPlayerInSight(Player player) {
		int distance = (int) Math.abs(getHitBox().getMinX() - player.getHitBox().getMinX());
		return distance <= getAttackDistance() * 5;
	}
	
	public abstract int getAttackDistance();

}
