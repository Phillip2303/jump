package de.phillip.jumpandrun.models;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;

public abstract class Actor implements Drawable{

	private double width;
	private double height;
	private Point2D position;
	private double rotation;
	private Point2D currentThrustVector = new Point2D(0, 0);
	private boolean debug;
	
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
	
	public abstract void drawToCanvas(GraphicsContext gc);
	
	public abstract void debugOut();
}
