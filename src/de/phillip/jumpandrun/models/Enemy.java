package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.controllers.EnemyManager;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class Enemy extends Actor{

	public enum Type {
		CRABBY(0, Game.TILES_SIZE, Game.TILES_SIZE * 6, 40, 10), 
		PINKSTAR(1, (int) Game.SCALE * 20, Game.TILES_SIZE * 3, 100, 20),
		SHARK(2, (int) (Game.SCALE * 20), Game.TILES_SIZE * 5, 75, 15);
		
		
		private final int colorValue;
		private final int attackDistance;
		private final int sightDistance;
		private final int health;
		private final int damage;
		
		private Type(int colorValue, int attackDistance, int sightDistance, int health, int damage) {
			this.colorValue = colorValue;
			this.attackDistance = attackDistance;
			this.sightDistance = sightDistance;
			this.health = health;
			this.damage = damage;
		}
		
		public int getColorValue() {
			return colorValue;
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
		
		public int getDamage() {
			return damage;
		}
	}

	public static final int IDLE = 0;
	public static final int RUNNING = 1;
	public static final int ATTACK = 2;
	public static final int HIT = 3;
	public static final int DEAD = 4;

	private Enemy.Type type;
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
	private boolean attacking;
	private boolean playerInSight;
	private boolean scoreFired;

	public Enemy(double width, double height, Enemy.Type type) {
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
	
	protected boolean canMoveHere(Point2D oldPosition) {
		Tile tileLeft = getEnemyManager().getTileAt(getHitBox().getMinX(), getHitBox().getMinY());
		Tile tileRight = getEnemyManager().getTileAt(getHitBox().getMaxX(), getHitBox().getMinY());
		int levelWidth = getEnemyManager().getLevelWidth();
		return canMoveHere(tileLeft, tileRight, oldPosition, levelWidth);
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
		case SHARK:
			switch (enemyAction) {
			case IDLE:
				return 8;
			case RUNNING:
				return 6;
			case ATTACK:
				return 8;
			case HIT:
				return 4;
			case DEAD:
				return 5;
			default:
				return 0;
			}
		case PINKSTAR:
			switch (enemyAction) {
			case IDLE:
				return 8;
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
	
	protected boolean canSeePlayer() {
		return checkPlayerDistance(getEnemyManager().getPlayer(), type.getSightDistance()) && !getEnemyManager().getPlayer().isDying();
	}
	
	protected boolean canAttackPlayer() {
		return checkPlayerDistance(getEnemyManager().getPlayer(), type.getAttackDistance());
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
			if (!scoreFired) {
				FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_DEAD_ENEMY, null));
				scoreFired = true;
			}
		} else {
			setEnemyAction(HIT);
		}
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void checkPlayerHit(double xOffset, double yOffset) {
		//System.out.println("Check Player Hit");
		Rectangle2D enemyAttackBox = getAttackBox(xOffset, yOffset);
		if (enemyAttackBox.intersects(enemyManager.getPlayer().getHitBox())) {
			enemyManager.getPlayer().gotHit(type.getDamage());
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
	
	protected boolean checkFalling(double hitbox_height) {
		Point2D oldPosition = getDrawPosition();
		setDrawPosition(getDrawPosition().getX(), getDrawPosition().getY() + hitbox_height + 10);
		boolean isFalling = false;
		Tile intersectingTile = getEnemyManager().getTileAt(getHitBox().getMinX(), getHitBox().getMinY());
		if (!intersectingTile.isSolid()) {
			isFalling = true;
			intersectingTile.showSpriteBox(true);
		}
		intersectingTile = getEnemyManager().getTileAt(getHitBox().getMaxX(), getHitBox().getMinY());
		if (!intersectingTile.isSolid()) {
			isFalling = true;
			intersectingTile.showSpriteBox(true);
		}
		setDrawPosition(oldPosition.getX(), oldPosition.getY());
		return isFalling;
	}

	/**
	 * @return the attacking
	 */
	public boolean isAttacking() {
		return attacking;
	}

	/**
	 * @param attacking the attacking to set
	 */
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	/**
	 * @return the playerInSight
	 */
	public boolean isPlayerInSight() {
		return playerInSight;
	}

	/**
	 * @param playerInSight the playerInSight to set
	 */
	public void setPlayerInSight(boolean playerInSight) {
		this.playerInSight = playerInSight;
	}
	
	
	//public abstract int getAttackDistance();

}
