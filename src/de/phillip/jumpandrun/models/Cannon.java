package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import de.phillip.jumpandrun.models.Actor.Direction;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Cannon extends GameObject implements EventHandler<GameEvent> {
	
	public static final int DEFAULT_WIDTH = 40;
	public static final int DEFAULT_HEIGHT = 26;
	public static final int WIDTH = (int) (DEFAULT_WIDTH * Game.SCALE);
	public static final int HEIGHT = (int) (DEFAULT_HEIGHT * Game.SCALE);
	public static final double HITBOX_WIDTH = 40 * Game.SCALE;
	public static final double HITBOX_HEIGHT = 26 * Game.SCALE;
	public static final double X_OFFSET = -4 * Game.SCALE;
	public static final double Y_OFFSET = 6 * Game.SCALE;
	
	public Image cannonSprite = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.CANNON_SPRITES);
	public Image[][] cannonSprites = new Image[1][7];
	private int flipX;
	private int flipWidth;
	private Direction direction;
	private int hOffset;

	public Cannon(Type type) {
		super(WIDTH, HEIGHT, type);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_H_OFFSET, this);
		initHitbox(0, 0, HITBOX_WIDTH, HITBOX_HEIGHT);
		createObjectSprites(cannonSprite, cannonSprites, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		if (type == Type.CANNON_LEFT) {
			flipX = 0;
			flipWidth = 1;
			direction = Direction.LEFT;
		} else {
			flipX = (int) getWidth();
			flipWidth = -1;
			direction = Direction.RIGHT;
		}
	}
	
	
	
	@Override
	public void drawToCanvas(GraphicsContext gc) {
		if (isActive()) {
			gc.drawImage(cannonSprites[getSpriteIndex()][getAniIndex()], getDrawPosition().getX() + flipX, getDrawPosition().getY(),
					getWidth() * flipWidth, getHeight());
			//drawSpriteBox(gc, Color.LIME);
			//drawHitbox(gc, Color.CYAN);
		}
	}
	
	@Override
	public void update() {
		if (canShootPlayer() && !isAnimate() && !getGameObjectManager().getPlayer().isDying()) {
			shoot();
			doAnimation(true);
		}
		super.update();
	}



	private void shoot() {
		CannonBall ball = new CannonBall(direction, getDrawPosition());
		FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_SHOOT_BALL, ball));
	}



	private boolean canShootPlayer() {
		int playerTileY = (int) (getGameObjectManager().getPlayer().getHitBox().getMinY() / Game.TILES_SIZE);
		int playerTileX = (int) (getGameObjectManager().getPlayer().getHitBox().getMinX() / Game.TILES_SIZE);
		int cannonTileY = (int) (getHitBox().getMinY() / Game.TILES_SIZE);
		if (playerTileY == cannonTileY && isInViewport()) {
			switch (direction) {
			case LEFT:
				if (getGameObjectManager().getPlayer().getHitBox().getMinX() < getHitBox().getMinX() && clearSight(playerTileY)) {
					return true;
				}
				return false;
			case RIGHT:
				if (getGameObjectManager().getPlayer().getHitBox().getMaxX() > getHitBox().getMaxX() && clearSight(playerTileY)) {
					return true;
				}
				return false;
			default:
				return false;
			}
		} else {
			return false;
		}
	}
	
	private boolean isInViewport() {
		if (getHitBox().getMinX() < Game.GAMEWIDTH + hOffset && getHitBox().getMaxX() > hOffset) {
			return true;
		} else {
			return false;
		}
	}

	private boolean clearSight(int playerTileY) {
		for (Tile tile : getGameObjectManager().getTiles()) {
			Rectangle2D tileHitBox = tile.getHitBox();
			switch (direction) {
			case LEFT:
				if ((playerTileY == (int) tileHitBox.getMinY() / Game.TILES_SIZE) && 
						(tileHitBox.getMinX() > getGameObjectManager().getPlayer().getHitBox().getMaxX()) &&
						(tileHitBox.getMaxX() < getHitBox().getMinX()) && tile.isSolid()) {
					return false;
				}
				break;
			case RIGHT:
				if ((playerTileY == (int) tileHitBox.getMinY() / Game.TILES_SIZE) && 
						(tileHitBox.getMaxX() < getGameObjectManager().getPlayer().getHitBox().getMinX()) &&
						(tileHitBox.getMinX() > getHitBox().getMaxX()) && tile.isSolid()) {
					return false;
				}
				break;
			default:
				break;
			}
		}	
		return true;
	}



	@Override
	public void handle(GameEvent event) {
		switch (event.getEventType().getName()) {
		case "JR_H_OFFSET":
			hOffset = (int) event.getData();
		}
		
	}
}
