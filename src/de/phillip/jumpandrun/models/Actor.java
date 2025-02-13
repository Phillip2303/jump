package de.phillip.jumpandrun.models;

import java.util.List;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public abstract class Actor implements Drawable {
	
	public enum Direction {
		LEFT(-1),
		RIGHT(1),
		UP(-1),
		DOWN(1);
		
		private int value;
		
		private Direction(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	private double width;
	private double height;
	private Point2D position;
	private double rotation;
	private Point2D currentThrustVector = new Point2D(0, 0);
	private boolean debug;
	private double xOffset;
	private double yOffset;
	private double hitboxWidth, hitboxHeight;
	private double attackBoxWidth, attackBoxHeight;
	private Direction direction = Direction.RIGHT;
	private boolean showSpriteBox;

	public Actor(double width, double height) {
		this.width = width;
		this.height = height;
	}

	private void move(Point2D vector) {
		this.position = this.position.add(vector);
	}

	public Point2D getCenter() {
		Point2D pos = getDrawPosition();
		return new Point2D(pos.getX() + width / 2, pos.getY() + height / 2);
	}

	public void setDrawPosition(double x, double y) {
		position = new Point2D(x, y);
	}

	public Point2D getDrawPosition() {
		return position;
	}

	public Point2D getPeak() {
		Point2D pos = getDrawPosition();
		return new Point2D(pos.getX(), pos.getY() + height);
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public double getRotation() {
		return rotation;
	}

	public void update() {
		move(currentThrustVector);
	}

	public Point2D calculateNewThrust(double value, double angle) {
		return new Point2D((float) Math.sin(angle) * value, (float) Math.cos(angle) * value);
	}

	public Point2D calculateNewSideThrust(double value, double angle) {
		return new Point2D((float) Math.cos(angle) * value, (float) Math.sin(angle) * value);
	}

	public void setCurrentThrustVector(double value) {
		currentThrustVector = calculateNewThrust(value, Math.toRadians(-getRotation()));
	}

	public void setCurrentThrustVector(Point2D currentThrustVector) {
		this.currentThrustVector = currentThrustVector;
	}

	public Point2D getCurrentThrustVector() {
		return currentThrustVector;
	}

	public boolean intersects(Actor actor) {
		Rectangle2D me = new Rectangle2D(position.getX(), position.getY(), getWidth(), getHeight());
		return me.intersects(new Rectangle2D(actor.getDrawPosition().getX(), actor.getDrawPosition().getY(),
				actor.getWidth(), actor.getHeight()));
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void transformContext(GraphicsContext graphicsContext) {
		Point2D center = getCenter();
		Rotate rotate = new Rotate(getRotation(), center.getX(), center.getY());
		graphicsContext.setTransform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(),
				rotate.getTy());
	}

	public Rectangle2D getHitBox() {
		return new Rectangle2D(position.getX() + xOffset, position.getY() + yOffset, hitboxWidth, hitboxHeight);
	}
	
	public void initHitbox(double xOffset, double yOffset, double hitboxWidth, double hitboxHeight) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.hitboxWidth = hitboxWidth;
		this.hitboxHeight = hitboxHeight;
	}
	
	public void initAttackBox(double attackBoxWidth, double attackBoxHeight) {
		this.attackBoxWidth = attackBoxWidth;
		this.attackBoxHeight = attackBoxHeight;
	}
	
	public Rectangle2D getAttackBox(double xOffset, double yOffset) {
		return new Rectangle2D(position.getX() + xOffset, position.getY() + yOffset, attackBoxWidth, attackBoxHeight);
	}
	
	public void drawHitbox(GraphicsContext gc, Color color) {
		gc.setStroke(color);
		gc.strokeRect(position.getX() + xOffset, position.getY() + yOffset, hitboxWidth,
				hitboxHeight);
	} 
	
	public void drawAttackBox(GraphicsContext gc, Color color, double xOffset, double yOffset) {
		gc.setStroke(color);
		gc.strokeRect(position.getX() + xOffset, position.getY() + yOffset, attackBoxWidth,
				attackBoxHeight);
	}
	
	public void drawSpriteBox(GraphicsContext gc, Color color) {
		if (showSpriteBox) {
			gc.setStroke(color);
			gc.strokeRect(position.getX(), position.getY(), getWidth(), getHeight());
		}
	}
	
	public void showSpriteBox(boolean value) {
		showSpriteBox = value;
	}
	
	public boolean canMoveHere(Tile intersectingTile, Point2D oldPosition, int levelWidth) {
		if (intersectingTile.isSolid()) {
			intersectingTile.showSpriteBox(true);
			setDrawPosition(oldPosition.getX(), oldPosition.getY());
			return false;
		}
		if (getHitBox().getMinX() < 0 || getHitBox().getMaxX() > levelWidth) {
			setDrawPosition(oldPosition.getX(), oldPosition.getY());
			return false;
		}
		return true;
	}
	
	public boolean canMoveHere(Tile tileLeft, Tile tileRight, Point2D oldPosition, int levelWidth) {
		if (tileLeft.isSolid()) {
			tileLeft.showSpriteBox(true);
			setDrawPosition(oldPosition.getX(), oldPosition.getY());
			return false;
		}
		if (tileRight.isSolid()) {
			tileRight.showSpriteBox(true);
			setDrawPosition(oldPosition.getX(), oldPosition.getY());
			return false;
		}
		if (getHitBox().getMinX() < 0 || getHitBox().getMaxX() > levelWidth) {
			setDrawPosition(oldPosition.getX(), oldPosition.getY());
			return false;
		}
		return true;
	}
	
	public void createObjectSprites(Image sprite, Image[][] actionSprites, int defaultWidth, int defaultHeight) {
		PixelReader pr = sprite.getPixelReader();
		for (int j = 0; j < actionSprites.length; j++) {
			for (int i = 0; i < actionSprites[j].length; i++) {
				actionSprites[j][i] = createSubImage(pr, i, j, defaultWidth, defaultHeight);
			}
		}
	}

	private Image createSubImage(PixelReader pr, int x, int y, int defaultWidth, int defaultHeight) {
		WritableImage wi = new WritableImage(defaultWidth, defaultHeight);
		PixelWriter pw = wi.getPixelWriter();
		for (int j = 0; j < defaultHeight; j++) {
			for (int i = 0; i < defaultWidth; i++) {
				Color color = pr.getColor(x * defaultWidth + i, y * defaultHeight + j);
				pw.setColor(i, j, color);
			}
		}
		return wi;
	}

	public abstract void drawToCanvas(GraphicsContext gc);

	public abstract void debugOut();

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
