package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;

public class CanvasButton implements Drawable {
	
	public static final int DEFAULT_WIDTH = 140;
	public static final int DEFAULT_HEIGHT = 56;
	public static final int WIDTH = (int) (DEFAULT_WIDTH * Game.SCALE);
	public static final int HEIGHT = (int) (DEFAULT_HEIGHT * Game.SCALE);
	public static final int V_OFFSET = (int) (160 * Game.SCALE);
	
	private Image image;
	private Rectangle2D rectangle;
	private boolean isActive;
	private boolean isClicked;
	private int index;
	

	public CanvasButton(Image image, int posX, int posY, int width, int height, int index) {
		this.image = image;
		this.index = index;
		rectangle = new Rectangle2D(posX, posY, width, height);
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.save();
		//gc.drawImage(image, rectangle.getMinX(), rectangle.getMinY());
		if (isActive) {
			if (isClicked) {
				gc.drawImage(image, DEFAULT_WIDTH * 2, index * DEFAULT_HEIGHT, DEFAULT_WIDTH, DEFAULT_HEIGHT, 
						rectangle.getMinX(), rectangle.getMinY(), rectangle.getWidth(), rectangle.getHeight());
			} else {
				gc.drawImage(image, 0, index * DEFAULT_HEIGHT, DEFAULT_WIDTH, DEFAULT_HEIGHT, 
						rectangle.getMinX(), rectangle.getMinY(), rectangle.getWidth(), rectangle.getHeight());
			}
		} else {
			gc.drawImage(image, DEFAULT_WIDTH, index * DEFAULT_HEIGHT, DEFAULT_WIDTH, DEFAULT_HEIGHT, 
					rectangle.getMinX(), rectangle.getMinY(), rectangle.getWidth(), rectangle.getHeight());
		}
		gc.restore();
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public boolean contains(Point2D point) {
		return rectangle.contains(point);
	}
	
	public void setPosition(int posX, int posY) {
		rectangle = new Rectangle2D(posX, posY, rectangle.getWidth(), rectangle.getHeight());
	}

	public void setClicked(boolean isClicked) {
		this.isClicked = isClicked;
	}

}
