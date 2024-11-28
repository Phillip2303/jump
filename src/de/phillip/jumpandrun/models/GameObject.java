package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.controllers.GameObjectManager;
import javafx.scene.canvas.GraphicsContext;

public class GameObject extends Actor {
	
	private GameObjectManager.Type type;
	private boolean active = true;
	private int aniIndex;
	private int aniTic;
	private int aniSpeed = 25;

	public GameObject(double width, double height, GameObjectManager.Type type) {
		super(width, height);
		this.type = type;
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugOut() {
		// TODO Auto-generated method stub

	}
	
	public void update() {
		updateAnimationTic();
	}

	private void updateAnimationTic() {
		aniTic++;
		if (aniTic >= aniSpeed) {
			aniTic = 0;
			aniIndex++;
			if (aniIndex >= getSpriteCount()) {
				aniIndex = 0;
			}
		}
	}
	
	private int getSpriteCount() {
		switch (type) {
		case RED_POTION, BLUE_POTION:
			return 7;
		case BOX, BARREL:
			return 8;
		default:
			return 0;
		}
	}

	public GameObjectManager.Type getType() {
		return type;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public int getSpriteIndex() {
		switch (type) {
		case RED_POTION:
			return 1;
		case BLUE_POTION:
			return 0;
		case BOX:
			return 0;
		case BARREL:
			return 1;
		default:
			return 0;
		}
	}

	public int getAniIndex() {
		return aniIndex;
	}
}
