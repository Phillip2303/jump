package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Shark extends Enemy {

	public static final int DEFAULT_WIDTH = 34;
	public static final int DEFAULT_HEIGHT = 30;
	public static final int WIDTH = (int) (DEFAULT_WIDTH * Game.SCALE);
	public static final int HEIGHT = (int) (DEFAULT_HEIGHT * Game.SCALE);
	public static final double HITBOX_WIDTH = 18 * Game.SCALE;
	public static final double HITBOX_HEIGHT = 22 * Game.SCALE;
	public static final double X_OFFSET = 8 * Game.SCALE;
	public static final double Y_OFFSET = 4 * Game.SCALE;
	public static final double SPEED = 0.35 * Game.SCALE;
	public static final double ATTACK_SPEED = SPEED * 4;

	private Image[][] sharkSprites = new Image[5][8];
	private Direction direction = Direction.LEFT;
	private int flipX;
	private int flipWidth;
	private double walkSpeed = SPEED;
	
	public Shark() {
		super(WIDTH, HEIGHT, Enemy.Type.SHARK);
	//	showSpriteBox(true);
		createObjectSprites(ResourcePool.getInstance().getSpriteAtlas(ResourcePool.SHARK_SPRITES), sharkSprites, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		initHitbox(X_OFFSET, Y_OFFSET, HITBOX_WIDTH, HITBOX_HEIGHT);
		initAttackBox(WIDTH, HEIGHT);
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		if (isActive()) {
			gc.drawImage(sharkSprites[getEnemyAction()][getAniIndex()], getDrawPosition().getX() + flipX, getDrawPosition().getY(),
					getWidth() * flipWidth, getHeight());
			//drawSpriteBox(gc, Color.BLUE);
			//drawHitbox(gc, Color.RED);
			//drawAttackBox(gc, Color.ORANGE);
		}
	}
	
	private void changeDirection(Direction direction) {
		if (direction == Direction.LEFT) {
			flipX = 0;
			flipWidth = 1;
		} else {
			flipX = (int) getWidth();
			flipWidth = -1;
		}
		this.direction = direction;
	}
	
	private void drawAttackBox(GraphicsContext gc, Color color) {
		super.drawAttackBox(gc, color, getXOffsetAttackBox(), 0);
	}
	
	private double getXOffsetAttackBox() {
		return 0;
	}

	/*
	 * private void drawHitBox(GraphicsContext gc) {
	 * gc.strokeRect(getDrawPosition().getX(), getDrawPosition().getY(), getWidth(),
	 * getHeight()); }
	 */

	@Override
	public void update() {
		switch (getEnemyAction()) {
		case IDLE:
			setEnemyAction(RUNNING);
			break;
		case ATTACK:
		//	System.out.println("Attack");
			if (getAniIndex() == 0) {
				setAttackChecked(false);
			}
			if (getAniIndex() == 3 && isAttackChecked() == false) {
				checkPlayerHit(getXOffsetAttackBox(), Y_OFFSET);
				setAttackChecked(true);
			}
			break;
		case HIT:
			break;
		case RUNNING:
			updateMove();
			break;
		case DEAD:
			break;
		default:
			break;
		}
		super.update();
	}

	private void updateMove() {
		Point2D oldPosition = getDrawPosition();
		if (!isAttacking()) {
			setDrawPosition(oldPosition.getX() + direction.getValue() * walkSpeed, oldPosition.getY());
		}
		if (!canMoveHere(getEnemyManager().getTiles(), oldPosition, getEnemyManager().getLevelWidth())) {
			if (direction == Direction.LEFT) {
				changeDirection(Direction.RIGHT);
			} else {
				changeDirection(Direction.LEFT);
			}
		} else {
			if (checkFalling(HITBOX_HEIGHT)) {
				if (direction == Direction.LEFT) {
					changeDirection(Direction.RIGHT);
				} else {
					changeDirection(Direction.LEFT);
				}
			} else {
				if (canSeePlayer()) {
					walkSpeed = ATTACK_SPEED;
					if (!isPlayerInSight()) {
						moveTowardsPlayer();
					}
					setPlayerInSight(true);
					if (canAttackPlayer()) {
						setAttacking(true);
						//System.out.println("Attack");
						setEnemyAction(ATTACK);
					} else {
						setAttacking(false);
					}
				} else {
					walkSpeed = SPEED;
					setPlayerInSight(false);
					setAttacking(false);
				}
			}

		}
	}
	
	private void moveTowardsPlayer() {
		if (getEnemyManager().getPlayer().getHitBox().getMaxX() < getHitBox().getMinX()) {
			changeDirection(Direction.LEFT);
		} else {
			changeDirection(Direction.RIGHT);
		}
	}

}
