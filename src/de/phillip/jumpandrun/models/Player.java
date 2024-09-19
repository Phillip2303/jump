package de.phillip.jumpandrun.models;

import java.util.List;
import java.util.stream.Collectors;

import de.phillip.jumpandrun.Game;
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
	public static final int GROUND = 4;
	public static final int HIT = 5;
	public static final int ATTACK_1 = 6;
	public static final int ATTACK_JUMP_1 = 7;
	public static final int ATTACK_JUMP_2 = 8;
	public static final int DEFAULT_WIDTH = 64;
	public static final int DEFAULT_HEIGHT = 40;
	public static final double SPEED = 1.5 * Game.SCALE;
	public static final double JUMPSPEED = -2.5 * Game.SCALE;

	private Image playerSprite;
	private Image[][] actionSprites;
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
	private int levelWidth;

	public Player(double width, double height, Image playerSprite) {
		super(width, height);
		this.playerSprite = playerSprite;
		createActionSprites();
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.drawImage(actionSprites[playerAction][aniIndex], getDrawPosition().getX(), getDrawPosition().getY(),
				getWidth(), getHeight());
		// drawHitBox(gc);
	}

	@Override
	public void debugOut() {

	}

	@Override
	public Rectangle2D getHitBox() {
		return new Rectangle2D(getDrawPosition().getX() + xOffset, getDrawPosition().getY() + yOffset, hitboxWidth,
				hitboxHeight);
	}

	private void createActionSprites() {
		PixelReader pr = playerSprite.getPixelReader();
		actionSprites = new Image[9][6];
		for (int j = 0; j < actionSprites.length; j++) {
			for (int i = 0; i < actionSprites[j].length; i++) {
				actionSprites[j][i] = createSubImage(pr, i, j);
			}
		}
	}

	private Image createSubImage(PixelReader pr, int x, int y) {
		WritableImage wi = new WritableImage(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		PixelWriter pw = wi.getPixelWriter();
		for (int j = 0; j < DEFAULT_HEIGHT; j++) {
			for (int i = 0; i < DEFAULT_WIDTH; i++) {
				Color color = pr.getColor(x * DEFAULT_WIDTH + i, y * DEFAULT_HEIGHT + j);
				pw.setColor(i, j, color);
			}
		}
		return wi;
	}

	public void update() {
		updateAnimationTic();
		if (isJumping) {
			updateJump();
		}
		if (isFalling) {
			updateJump();
		}
	}

	private void checkFalling() {
		Point2D oldPosition = getDrawPosition();
		setDrawPosition(getDrawPosition().getX(), getDrawPosition().getY() + hitboxHeight);
		for (Tile tile : tiles) {
			Rectangle2D hitBox = tile.getHitBox();
			if (!tile.isSolid() && hitBox.intersects(getHitBox()) && hitBox.getMinX() < getHitBox().getMinX()
					&& hitBox.getMaxX() > getHitBox().getMaxX()) {
				setPlayerAction(FALLING);
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
			aniIndex = 0;
		}
	}

	private int getSpriteCount(int playerAction) {
		switch (playerAction) {
		case IDLE:
			return 5;
		case RUNNING:
			return 6;
		case JUMPING:
		case ATTACK_1:
		case ATTACK_JUMP_1:
		case ATTACK_JUMP_2:
			return 3;
		case HIT:
			return 4;
		case GROUND:
			return 2;
		case FALLING:
		default:
			return 1;
		}
	}

	public void setPlayerAction(int playerAction) {
		if (isJumping && playerAction == JUMPING) {
			return;
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
			isFalling = true;
			airSpeed = fallSpeedAfterCollision;
		default:
			break;
		}
		if (this.playerAction != playerAction && !isJumping) {
			resetAniTic();
		}
	}

	private void resetAniTic() {
		aniTic = 0;
		aniIndex = 0;
	}

	private void drawHitBox(GraphicsContext gc) {
		gc.setStroke(Color.RED);
		gc.strokeRect(getDrawPosition().getX() + xOffset, getDrawPosition().getY() + yOffset, hitboxWidth,
				hitboxHeight);
	}

	public void setTiles(List<Tile> tiles) {
		this.tiles = tiles;
	}

	public boolean canMoveHere(double direction) {
		Point2D oldPosition = getDrawPosition();
		if (isFalling) {
			setDrawPosition(getDrawPosition().getX() + direction / 2, getDrawPosition().getY());
		} else {
			setDrawPosition(getDrawPosition().getX() + direction, getDrawPosition().getY());
		}
		for (Tile tile : tiles) {
			Rectangle2D hitBox = tile.getHitBox();
			if (tile.isSolid() && hitBox.intersects(getHitBox())) {
				setDrawPosition(oldPosition.getX(), oldPosition.getY());
				return false;
			}
		}
		if (getHitBox().getMinX() < 0 || getHitBox().getMaxX() > levelWidth) {
			setDrawPosition(oldPosition.getX(), oldPosition.getY());
			return false;
		}
		if (!isJumping) {
			checkFalling();
		}
		return true;
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

	public void setLevelWidth(int levelWidth) {
		this.levelWidth = levelWidth;
	}
}
