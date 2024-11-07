package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.controllers.EnemyManager;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class Enemy extends Actor {

	public enum Type {
		CRABBY(0, Game.TILES_SIZE, Game.TILES_SIZE * 5, 30), 
		CANNON(1, Game.TILES_SIZE, Game.TILES_SIZE * 5, 100), 
		SHARK(2, Game.TILES_SIZE, Game.TILES_SIZE * 5, 50);
		
		private final int value;
		private final int attackDistance;
		private final int sightDistance;
		private final int health;
		
		private Type(int value, int attackDistance, int sightDistance, int health) {
			this.value = value;
			this.attackDistance = attackDistance;
			this.sightDistance = sightDistance;
			this.health = health;
		}
		
		public int getValue() {
			return value;
		}

		public int getAttackDistance() {
			return attackDistance;
		}

		public int getSightDistance() {
			return sightDistance;
		}

		public int getHealth() {
			return health;
		}
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
	private int currentHealth;
	private boolean active = true;
	private boolean attackChecked = false;

	public Enemy(double width, double height, Type type) {
		super(width, height);
		this.type = type;
		currentHealth = this.type.getHealth();
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
			if (aniIndex >= getSpriteCount(enemyAction)) {
				aniIndex = 0;
				if (enemyAction == ATTACK || enemyAction == HIT) {
					enemyAction = IDLE;
				}
				if (enemyAction == DEAD) {
					active = false;
				}
			}
		}
	/*	if (aniIndex >= getSpriteCount(enemyAction)) {
			aniIndex = 0;
			if (enemyAction == ATTACK) {
				enemyAction = IDLE;
			}
		}*/
	}

	public void setActive(boolean active) {
		this.active = active;
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
		return checkPlayerDistance(player, type.getSightDistance());
	}
	
	protected boolean canAttackPlayer(Player player) {
		return checkPlayerDistance(player, type.getAttackDistance());
	}
	
	private boolean checkPlayerDistance(Player player, int distance) {
		int playerTileY = (int) (player.getHitBox().getMinY() / Game.TILES_SIZE);
		if (tileY == playerTileY) {
			int xPos = (int) Math.abs(getHitBox().getMinX() - player.getHitBox().getMinX());
			return xPos <= distance;
		} 
		return false;
	}
	
	public void gotHit(int amount) {
		currentHealth -= amount;
		if (currentHealth <= 0) {
			setEnemyAction(DEAD);
		} else {
			setEnemyAction(HIT);
		}
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void checkPlayerHit(double xOffset, double yOffset) {
		Rectangle2D enemyAttackBox = getAttackBox(xOffset, yOffset);
		if (enemyAttackBox.intersects(enemyManager.getPlayer().getHitBox())) {
			enemyManager.getPlayer().gotHit(20);
		}
	}

	/**
	 * @return the attackChecked
	 */
	public boolean isAttackChecked() {
		return attackChecked;
	}

	/**
	 * @param attackChecked the attackChecked to set
	 */
	public void setAttackChecked(boolean attackChecked) {
		this.attackChecked = attackChecked;
	}
	
	
	//public abstract int getAttackDistance();

}
