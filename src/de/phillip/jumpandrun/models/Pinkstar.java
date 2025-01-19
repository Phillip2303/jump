package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Pinkstar extends Enemy {

	public static final int DEFAULT_WIDTH = 34;
	public static final int DEFAULT_HEIGHT = 30;
	public static final int WIDTH = (int) (DEFAULT_WIDTH * Game.SCALE);
	public static final int HEIGHT = (int) (DEFAULT_HEIGHT * Game.SCALE);
	//public static final double HITBOX_WIDTH = 22 * Game.SCALE;
	//public static final double HITBOX_HEIGHT = 19 * Game.SCALE;
	public static final double X_OFFSET = 9 * Game.SCALE;
	public static final double Y_OFFSET = 7 * Game.SCALE;
	//public static final double SPEED = 0.3 * Game.SCALE;

	private Image[][] pinkstarSprites = new Image[5][8];
	private Direction direction = Direction.LEFT;
	
	public Pinkstar() {
		super(WIDTH, HEIGHT, Enemy.Type.PINKSTAR);
		createObjectSprites(ResourcePool.getInstance().getSpriteAtlas(ResourcePool.PINKSTAR_SPRITES), pinkstarSprites, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		//initHitbox(X_OFFSET, Y_OFFSET, HITBOX_WIDTH, HITBOX_HEIGHT);
		//initAttackBox(82 * Game.SCALE, HITBOX_HEIGHT);
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		if (isActive()) {
			gc.drawImage(pinkstarSprites[getEnemyAction()][getAniIndex()], getDrawPosition().getX(), getDrawPosition().getY(),
					getWidth(), getHeight());
			drawSpriteBox(gc, Color.PINK);
			//drawHitbox(gc, Color.PINK);
			//drawAttackBox(gc, Color.ORANGE);
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
			//updateMove();
			break;
		case DEAD:
			break;
		default:
			break;
		}
		super.update();
	}

	/*private void updateMove() {
		Point2D oldPosition = getDrawPosition();
		switch (direction) {
		case LEFT:
			setDrawPosition(getDrawPosition().getX() - SPEED, getDrawPosition().getY());
			break;
		case RIGHT:
			setDrawPosition(getDrawPosition().getX() + SPEED, getDrawPosition().getY());
			break;
		default:
			break;
		}
		if (!canMoveHere(getEnemyManager().getTiles(), getDrawPosition(), getEnemyManager().getLevelWidth())) {
			if (direction == Direction.LEFT) {
				direction = Direction.RIGHT;
			} else {
				direction = Direction.LEFT;
			}
		} else {
			if (checkFalling()) {
				if (direction == Direction.LEFT) {
					direction = Direction.RIGHT;
				} else {
					direction = Direction.LEFT;
				}
			} else {
				if (canSeePlayer(getEnemyManager().getPlayer()) && !getEnemyManager().getPlayer().isDying()) {
					moveTowardsPlayer();
					if (canAttackPlayer(getEnemyManager().getPlayer())) {
						setEnemyAction(ATTACK);
					}
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

	/*private boolean checkFalling() {
		Point2D oldPosition = getDrawPosition();
		setDrawPosition(getDrawPosition().getX(), getDrawPosition().getY() + HITBOX_HEIGHT);
		boolean isFalling = false;
		for (Tile tile : getEnemyManager().getTiles()) {
			Rectangle2D hitBox = tile.getHitBox();
			if (!tile.isSolid() && hitBox.intersects(getHitBox())
					&& (hitBox.getMinX() < getHitBox().getMinX() || hitBox.getMaxX() > getHitBox().getMaxX())) {
				isFalling = true;
				break;
			}
		}
		setDrawPosition(oldPosition.getX(), oldPosition.getY());
		return isFalling;
	}*/
	
	
}
