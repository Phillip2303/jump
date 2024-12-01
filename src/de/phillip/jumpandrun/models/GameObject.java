package de.phillip.jumpandrun.models;

import javafx.scene.canvas.GraphicsContext;

public class GameObject extends Actor {
	
	public enum Type {
		RED_POTION(0, 15, 0),
		BLUE_POTION(1, 0, 10),
		BARREL(2, 0, 0),
		BOX(3, 0, 0),
		SPIKE(4, 0, 0),
		CANNON_LEFT(5, 0, 0),
		CANNON_RIGHT(6, 0, 0),
		TREE_1(7, 0, 0),
		TREE_2(8, 0, 0),
		TREE_3(9, 0, 0);
		
		private final int colorValue;
		private final int healthValue;
		private final int powerValue;
		
		private Type(int colorValue, int healthValue, int powerValue) {
			this.colorValue = colorValue;
			this.healthValue = healthValue;
			this.powerValue = powerValue;
		}
		
		/**
		 * @return the colorValue
		 */
		public int getColorValue() {
			return colorValue;
		}
	
		/**
		 * @return the healthValue
		 */
		public int getHealthValue() {
			return healthValue;
		}
	
		/**
		 * @return the powerValue
		 */
		public int getPowerValue() {
			return powerValue;
		}
	}

	private GameObject.Type type;
	private boolean active = true;
	private int aniIndex;
	private int aniTic;
	private int aniSpeed = 25;

	public GameObject(double width, double height, GameObject.Type type) {
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
				if (getType() == Type.BARREL || getType() == Type.BOX) {
					setActive(false);
				} else {
					aniIndex = 0;
				}
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

	public GameObject.Type getType() {
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
	
	public void setAniIndex(int aniIndex) {
		this.aniIndex = aniIndex;
	}
}
