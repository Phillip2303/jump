package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.utils.GameState;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;

public class CanvasButton implements Drawable {

	public static final int MENU_DEFAULT_WIDTH = 140;
	public static final int MENU_DEFAULT_HEIGHT = 56;
	public static final int MENU_WIDTH = (int) (MENU_DEFAULT_WIDTH * Game.SCALE);
	public static final int MENU_HEIGHT = (int) (MENU_DEFAULT_HEIGHT * Game.SCALE);

	public static final int PAUSE_DEFAULT_SIZE = 42;
	public static final int PAUSE_SIZE = (int) (PAUSE_DEFAULT_SIZE * Game.SCALE);

	public static final int MENU_V_OFFSET = (int) (160 * Game.SCALE);
	public static final int SOUND_V_OFFSET = (int) (148 * Game.SCALE);
	public static final int URM_V_OFFSET = (int) (340 * Game.SCALE);

	private Image image;
	private Rectangle2D rectangle;
	private boolean isActive;
	private boolean isClicked;
	private int index;
	private int defaultWidth;
	private int defaultHeight;

	public CanvasButton(Image image, int posX, int posY, int width, int height, int index, GameState menuState) {
		this.image = image;
		this.index = index;
		switch (menuState) {
		case MENU:
			defaultWidth = MENU_DEFAULT_WIDTH;
			defaultHeight = MENU_DEFAULT_HEIGHT;
			break;
		case PAUSING:
			defaultWidth = PAUSE_DEFAULT_SIZE;
			defaultHeight = PAUSE_DEFAULT_SIZE;
			break;
		default:
			break;
		}
		rectangle = new Rectangle2D(posX, posY, width, height);
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.save();
		// gc.drawImage(image, rectangle.getMinX(), rectangle.getMinY());
		if (isActive) {
			if (isClicked) {
				gc.drawImage(image, defaultWidth * 2, index * defaultHeight, defaultWidth, defaultHeight,
						rectangle.getMinX(), rectangle.getMinY(), rectangle.getWidth(), rectangle.getHeight());
			} else {
				gc.drawImage(image, 0, index * defaultHeight, defaultWidth, defaultHeight, rectangle.getMinX(),
						rectangle.getMinY(), rectangle.getWidth(), rectangle.getHeight());
			}
		} else {
			gc.drawImage(image, defaultWidth, index * defaultHeight, defaultWidth, defaultHeight, rectangle.getMinX(),
					rectangle.getMinY(), rectangle.getWidth(), rectangle.getHeight());
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
