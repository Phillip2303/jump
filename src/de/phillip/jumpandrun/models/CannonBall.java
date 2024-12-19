package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CannonBall extends GameObject {
	
	public static final int DEFAULT_WIDTH = 15;
	public static final int DEFAULT_HEIGHT = 15;
	public static final int WIDTH = (int) (DEFAULT_WIDTH * Game.SCALE);
	public static final int HEIGHT = (int) (DEFAULT_HEIGHT * Game.SCALE);
	public static final float SPEED = 0.75f * Game.SCALE;
	public static final double HITBOX_WIDTH = 15 * Game.SCALE;
	public static final double HITBOX_HEIGHT = 15 * Game.SCALE;
	
	private Direction direction;
	private Image ballImage = ResourcePool.getInstance().getCannonBall();

	public CannonBall(Direction direction, Point2D startPos) {
		super(WIDTH, HEIGHT, Type.CANNON_BALL);
		this.direction = direction;
		initHitbox(0, 0, HITBOX_WIDTH, HITBOX_HEIGHT);
		/*switch (direction) {
		case LEFT:
			setDrawPosition();
			break;
		case RIGHT:
			setDrawPosition();
			break;
		default:
			break;
		}*/
		setDrawPosition(startPos.getX(), startPos.getY());
	}
	
	@Override
	public void drawToCanvas(GraphicsContext gc) {
		if (isActive()) {
			gc.drawImage(ballImage, 0, 0, ballImage.getWidth(), ballImage.getHeight(), 
					getDrawPosition().getX(), getDrawPosition().getY(), WIDTH, HEIGHT);
		}
	}
	
	@Override
	public void update() {
		setDrawPosition(getDrawPosition().getX() + SPEED * direction.getValue(), getDrawPosition().getY());
	}

}
