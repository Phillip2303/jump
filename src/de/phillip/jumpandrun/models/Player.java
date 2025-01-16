package de.phillip.jumpandrun.models;

import java.util.List;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.controllers.EnemyManager;
import de.phillip.jumpandrun.controllers.GameObjectManager;
import de.phillip.jumpandrun.controllers.LevelManager;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Player extends Actor {

	public static final int IDLE = 0;
	public static final int RUNNING = 1;
	public static final int JUMPING = 2;
	public static final int FALLING = 3;
	public static final int ATTACK = 4;
	public static final int HIT = 5;
	public static final int DEAD = 6;
	public static final int DEFAULT_WIDTH = 64;
	public static final int DEFAULT_HEIGHT = 40;
	public static final double SPEED = 1.5 * Game.SCALE;
	public static final double JUMPSPEED = -2.5 * Game.SCALE;
	//public static final double MAXHEALTH = 100;

	private Image playerSprite;
	private Image[][] actionSprites = new Image[7][8];
	private int playerAction = IDLE;
	private int aniIndex;
	private int aniTic;
	private int aniSpeed = 25;
	private double hitboxWidth = 20 * Game.SCALE;
	private double hitboxHeight = 27 * Game.SCALE;
	private double xOffset = 21 * Game.SCALE;
	private double yOffset = 4 * Game.SCALE;
	private List<Tile> tiles;
	private double airSpeed = 0;
	private double fallSpeedAfterCollision = 0.5 * Game.SCALE;
	private double gravity = 0.04 * Game.SCALE;
	private boolean isJumping = false;
	private boolean isFalling = false;
	private boolean isAttacking = false;
	private int levelWidth;
	/*private double statusBarWidth = 192 * Game.SCALE;
	private double statusBarHeight = 58 * Game.SCALE;
	private double statusBarX, statusBarY = 10 * Game.SCALE;
	private double healthBarWidth = 150 * Game.SCALE;
	private double healthBarHeight = 4 * Game.SCALE;
	private double healthBarX = 34 * Game.SCALE;
	private double healthBarY = 14 * Game.SCALE;*/
	//private double currentHealth = MAXHEALTH;
	//private double oldHealth = currentHealth;
	/*private double healthWidth = healthBarWidth;
	private int hOffset;*/
	private int flipX = 0;
	private int flipWidth = 1;
	private EnemyManager enemyManager;
	private GameObjectManager gameObjectManager;
	private LevelManager levelManager;
	private double yOffsetAttackBox = 10 * Game.SCALE;
	private boolean attackChecked;
	private boolean dead;
	private boolean dying;
	private PlayerStatus playerStatus;
	private boolean deadOnGround = false;

	public Player(double width, double height, Image playerSprite) {
		super(width, height);
		playerStatus = new PlayerStatus();
		initHitbox(xOffset, yOffset, hitboxWidth, hitboxHeight);
		initAttackBox(20 * Game.SCALE, 20 * Game.SCALE);
		this.playerSprite = playerSprite;
		createObjectSprites(playerSprite, actionSprites, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public void setEnemyManager(EnemyManager enemyManager) {
		this.enemyManager = enemyManager;
	}
	
	public void setGameObjectManager(GameObjectManager gameObjectManager) {
		this.gameObjectManager = gameObjectManager;
	}
	
	public void setLevelManager(LevelManager levelManager) {
		this.levelManager = levelManager;
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.drawImage(actionSprites[playerAction][aniIndex], getDrawPosition().getX() + flipX, getDrawPosition().getY(),
				getWidth() * flipWidth, getHeight());
		playerStatus.drawToCanvas(gc);
		drawHitbox(gc, Color.RED);
		drawAttackBox(gc, Color.GREEN);
		
	}
	
	private void drawAttackBox(GraphicsContext gc, Color color) {
		super.drawAttackBox(gc, color, getXOffsetAttackBox(), yOffsetAttackBox);
	}
	
	private double getXOffsetAttackBox() {
		switch (getDirection()) {
		case LEFT:
			return xOffset - hitboxWidth;
		case RIGHT:
			return hitboxWidth + xOffset;
		default:
			return hitboxWidth + xOffset + 10 * Game.SCALE;
		}
	}

	@Override
	public void debugOut() {

	}

	public void update() {
		if (!dead) {
			updateAnimationTic();
			if (!dying) {
				if (isJumping) {
					updateJump();
				}
				if (isFalling) {
					updateJump();
				}
				if (isAttacking) {
					checkAttack();
				}
				checkGameObjects();
				checkForWater();
			} else {
				if (!deadOnGround) {
					checkDeadFalling();
				} 
			}
		}
	}

	private void checkGameObjects() {
		for (GameObject gameObject: gameObjectManager.getGameObjects()) {
			switch (gameObject.getType()) {
			case SPIKE:
				checkForSpikeTrap(gameObject);
				break;
			case RED_POTION:
				checkForPotionsTouch((Potion) gameObject);
				break;
			case BLUE_POTION:
				checkForPotionsTouch((Potion) gameObject);
				break;
			case BOX, BARREL:
				if (isAttacking) {
					checkForContainerHit((Container) gameObject);
				}
				break;
			case CANNON_LEFT, CANNON_RIGHT:
				break;
			case CANNON_BALL:
				checkForCannonHit((CannonBall) gameObject);
				break;
			default:
				break;
			}
		}
	}

	private void checkForPotionsTouch(Potion gameObject) {
		if (gameObject.isActive() && getHitBox().intersects(gameObject.getHitBox())) {
			drinkPotion(gameObject);
		}
	}

	private void drinkPotion(Potion gameObject) {
		//drink potion
		switch (gameObject.getType()) {
		case RED_POTION:
			playerStatus.increaseHealth();
			break;
		case BLUE_POTION:
			//power bar
			playerStatus.increasePower();
			break;
		default:
			break;
		}
		gameObject.setActive(false);
	}

	private void checkForContainerHit(Container gameObject) {
		if (gameObject.isActive() && !gameObject.isAnimate() && getAttackBox(getXOffsetAttackBox(), yOffsetAttackBox).intersects(gameObject.getHitBox())) {
			gameObject.gotHit();
		}
		
	}

	private void checkForSpikeTrap(GameObject gameObject) {
		if (getHitBox().intersects(gameObject.getHitBox())) {
			playerStatus.setCurrentHealth(0);
			setPlayerAction(DEAD);
			deadOnGround = true;
			//FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_SHOW_GAME_OVER, null));
			//setDrawPosition(getDrawPosition().getX(), getDrawPosition().getY() - Game.TILES_SIZE);
		}
	}
	
	private void checkForWater() {
		for (Tile tile: tiles) {
			if (getHitBox().intersects(tile.getHitBox()) && tile.isWater()) {
				System.out.println("Is Water");
				playerStatus.setCurrentHealth(0);
				setPlayerAction(DEAD);
				//deadOnGround = true;
			}
		}
	}
	
	private void checkForCannonHit(CannonBall cannonBall) {
		if (cannonBall.isActive() && getHitBox().intersects(cannonBall.getHitBox())) {
			gotHit(20);
			cannonBall.setActive(false);
		}
	}
	
	public void checkDeadFalling() {
		setDrawPosition(getDrawPosition().getX(), getDrawPosition().getY() + fallSpeedAfterCollision);
		System.out.println("Test");
		for (Tile tile : tiles) {
			Rectangle2D hitBox = tile.getHitBox();
			if (hitBox.intersects(getHitBox()) && ((tile.isSolid()) || getHitBox().getMaxY() + 2 * Game.SCALE >= Game.TILES_IN_HEIGHT * Game.TILES_SIZE)) {
				System.out.println("Is Solid");
				deadOnGround = true;
				tile.showSpriteBox(true);
				return;
			}
		}
	}

	private void checkAttack() {
		if (attackChecked || aniIndex != 1) {
			return;
		}
		attackChecked = true;
		for (Enemy enemy: enemyManager.getEnemies()) {
			if (enemy.isActive()) {
				if (enemy.getHitBox().intersects(getAttackBox(getXOffsetAttackBox(), yOffsetAttackBox))) {
					enemy.gotHit(10);
					//System.out.println("Treffer");
					return;
				}
			}
		}
	}

	public void checkFalling() {
		Point2D oldPosition = getDrawPosition();
		setDrawPosition(getDrawPosition().getX(), getDrawPosition().getY() + hitboxHeight);
		for (Tile tile : tiles) {
			Rectangle2D hitBox = tile.getHitBox();
			if (isFalling) { 
				if (!tile.isSolid() && hitBox.intersects(getHitBox())) {
					setPlayerAction(FALLING);
				}
			} else {
				if (!tile.isSolid() && hitBox.intersects(getHitBox()) && hitBox.getMinX() < getHitBox().getMinX()
						&& hitBox.getMaxX() > getHitBox().getMaxX()) {
					setPlayerAction(FALLING);
				}
			}
		}
		setDrawPosition(oldPosition.getX(), oldPosition.getY());
	}

	private void updateJump() {
		if (canJumpHere(airSpeed)) {
			setDrawPosition(getDrawPosition().getX(), getDrawPosition().getY() + airSpeed);
			airSpeed += gravity;
		} else {
			if (airSpeed > 0) {
				// System.out.println("Can not jump");
				resetJumping();
			} else {
				airSpeed = fallSpeedAfterCollision;
			}
		}
	}

	private void resetJumping() {
		isJumping = false;
		isFalling = false;
		airSpeed = 0;
		setPlayerAction(IDLE);
	}

	private void updateAnimationTic() {
		aniTic++;
		if (aniTic >= aniSpeed) {
			aniTic = 0;
			aniIndex++;
		}
		if (aniIndex >= getSpriteCount(playerAction)) {
			if (dying) {
				dead = true;
				aniIndex = 7;
				FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_SHOW_GAME_OVER, null));
			} else {
				aniIndex = 0;
			}
			isAttacking = false;
			attackChecked = false;
		}
	}

	private int getSpriteCount(int playerAction) {
		switch (playerAction) {
		case IDLE:
			return 5;
		case RUNNING:
			return 6;
		case JUMPING, ATTACK:
			return 3;
		case FALLING:
			return 1;
		case HIT:
			return 4;
		case DEAD:
			return 8;
		default:
			return 1;
		}
	}

	public void setPlayerAction(int playerAction) {
		if (isJumping && playerAction == JUMPING) {
			return;
		}
		if (isAttacking) {
			return;
		}
		if (dying) {
			return;
		}
		if (this.playerAction != playerAction && !isJumping) {
			resetAniTic();
		}
		this.playerAction = playerAction;
		switch (playerAction) {
		case JUMPING:
			if (isFalling) {
				this.playerAction = FALLING;
			} else {
				isJumping = true;
				airSpeed = JUMPSPEED;
			}
			break;
		case RUNNING:
			if (isJumping) {
				this.playerAction = JUMPING;
			}
			if (isFalling) {
				this.playerAction = FALLING;
			}
			break;
		case IDLE:
			if (isJumping) {
				this.playerAction = JUMPING;
			}
			if (isFalling) {
				this.playerAction = FALLING;
			}
			break;
		case FALLING:
			if (!isFalling) {
				isFalling = true;
				airSpeed = fallSpeedAfterCollision;
			}
			break;
		case ATTACK:
			isAttacking = true;
			break;
		case DEAD:
			dying = true;
			aniTic = 0;
			aniIndex = 1;
			break;
		default:
			break;
		}
	}

	private void resetAniTic() {
		aniTic = 0;
		aniIndex = 0;
	}

	/*private void drawHitBox(GraphicsContext gc) {
		gc.setStroke(Color.RED);
		gc.strokeRect(getDrawPosition().getX() + xOffset, getDrawPosition().getY() + yOffset, hitboxWidth,
				hitboxHeight);
	}*/

	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}

	public boolean canMoveHere(double direction) {
		checkDirection(direction);
		Point2D oldPosition = getDrawPosition();
		if (isFalling) {
			setDrawPosition(getDrawPosition().getX() + direction / 2, getDrawPosition().getY());
		} else {
			setDrawPosition(getDrawPosition().getX() + direction, getDrawPosition().getY());
		}
		if (super.canMoveHere(tiles, oldPosition, levelWidth)) {
			if (!isJumping) {
				checkFalling();
			}
			return true;
		} else {
			return false;
		}
	
	}

	private void checkDirection(double direction) {
		Double result = Math.signum(direction);
		if (result.compareTo(1.0) == 0) {
			flipX = 0;
			flipWidth = 1;
			setDirection(Direction.RIGHT);
		} else if (result.compareTo(-1.0) == 0) {
			flipX = (int) getWidth();
			flipWidth = -1;
			setDirection(Direction.LEFT);
		}
	}

	public boolean canJumpHere(double speed) {
		Point2D oldPosition = getDrawPosition();
		setDrawPosition(getDrawPosition().getX(), getDrawPosition().getY() + speed);
		for (Tile tile : tiles) {
			Rectangle2D hitBox = tile.getHitBox();
			if (tile.isSolid() && hitBox.intersects(getHitBox())) {
				int compare = Double.compare(speed, 0);
				if (compare < 0) {
					setDrawPosition(oldPosition.getX(), oldPosition.getY());
				} else {
					setDrawPosition(oldPosition.getX(), tile.getDrawPosition().getY() - (yOffset + hitboxHeight));
				}
				return false;
			}
		}
		if (getHitBox().getMaxY() > Game.GAMEHEIGHT) {
			setDrawPosition(oldPosition.getX(), oldPosition.getY());
			return false;
		}
		setDrawPosition(oldPosition.getX(), oldPosition.getY());
		return true;
	}
	
	public void gotHit(int amount) {
	//	System.out.println("Got Hit");
		playerStatus.decreaseHealth(amount);
		if (playerStatus.getCurrentHealth() <= 0) {
			setPlayerAction(DEAD);
			//FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_SHOW_GAME_OVER, null));
		} else {
			//System.out.println("Treffer");
		}
	}

	public void setLevelWidth(int levelWidth) {
		this.levelWidth = levelWidth;
	}

	/*public boolean isDead() {
		return dead;
	}*/
	
	public boolean isDying() {
		return dying;
	}
	
	public void reset(boolean nextLevel) {
		if (!nextLevel) {
			if (levelManager.getOldLevel() == levelManager.getActiveLevel().getLevelNumber()) {
				//reset level
				playerStatus.resetLevel();
			} else {
				//reset game
				playerStatus.resetGame();
			}
		} else {
			//next level
			playerStatus.nextLevel();
		}
		playerStatus.reset();
		airSpeed = 0;
		dead = false;
		dying = false;
		deadOnGround = false;
		setPlayerAction(IDLE);
	}
}
