package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.models.Actor.Direction;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Crabby extends Enemy {

	public static final int DEFAULT_WIDTH = 72;
	public static final int DEFAULT_HEIGHT = 32;
	public static final int WIDTH = (int) (DEFAULT_WIDTH * Game.SCALE);
	public static final int HEIGHT = (int) (DEFAULT_HEIGHT * Game.SCALE);
	public static final double HITBOX_WIDTH = 22 * Game.SCALE;
	public static final double HITBOX_HEIGHT = 19 * Game.SCALE;
	public static final double X_OFFSET = 26 * Game.SCALE;
	public static final double Y_OFFSET = 10 * Game.SCALE;
	public static final double SPEED = 0.3 * Game.SCALE;

	private Image enemySprite = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.CRABBY_SPRITES);
	private Image[][] actionSprites = new Image[5][9];
	private Direction direction = Direction.LEFT;

	public Crabby() {
		super(WIDTH, HEIGHT, Enemy.Type.CRABBY);
//		showSpriteBox(true);
		createObjectSprites(enemySprite, actionSprites, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		initHitbox(X_OFFSET, Y_OFFSET, HITBOX_WIDTH, HITBOX_HEIGHT);
		initAttackBox(82 * Game.SCALE, HITBOX_HEIGHT);
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		if (isActive()) {
			gc.drawImage(actionSprites[getEnemyAction()][getAniIndex()], getDrawPosition().getX(), getDrawPosition().getY(),
					getWidth(), getHeight());
			drawHitbox(gc, Color.BLUE);
			//drawAttackBox(gc, Color.ORANGE);
		//	drawSpriteBox(gc, Color.ORANGE);
		}
	}
	
	private void drawAttackBox(GraphicsContext gc, Color color) {
		super.drawAttackBox(gc, color, getXOffsetAttackBox(), Y_OFFSET);
	}
	
	private double getXOffsetAttackBox() {
		return X_OFFSET - (31 * Game.SCALE);
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
		setDrawPosition(oldPosition.getX() + direction.getValue() * SPEED, oldPosition.getY());
		Tile intersectingTile;
		if (getDirection() == Direction.RIGHT) {
			intersectingTile = getEnemyManager().getTileAt(getHitBox().getMaxX(), getHitBox().getMinY());
		} else {
			intersectingTile = getEnemyManager().getTileAt(getHitBox().getMinX(), getHitBox().getMinY());
		}
		if (!canMoveHere(intersectingTile, oldPosition, getEnemyManager().getLevelWidth())) {
			if (direction == Direction.LEFT) {
				direction = Direction.RIGHT;
			} else {
				direction = Direction.LEFT;
			}
		} else {
			if (checkFalling(HITBOX_HEIGHT)) {
				if (direction == Direction.LEFT) {
					direction = Direction.RIGHT;
				} else {
					direction = Direction.LEFT;
				}
			} else {
				if (canSeePlayer()) {
					if (!isPlayerInSight()) {
						moveTowardsPlayer();
					}
					setPlayerInSight(true);
					if (canAttackPlayer()) {
						setEnemyAction(ATTACK);
					}
				} else {
					setPlayerInSight(false);
				}
			}

		}
	}
	
	private void moveTowardsPlayer() {
		if (getEnemyManager().getPlayer().getHitBox().getMaxX() < getHitBox().getMinX()) {
			direction = Direction.LEFT;
		} else {
			direction = Direction.RIGHT;
		}
	}
	
}
