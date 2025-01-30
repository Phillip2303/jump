package de.phillip.jumpandrun.models;

import java.util.Random;

import de.phillip.jumpandrun.controllers.GameObjectManager;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import javafx.scene.canvas.GraphicsContext;

public class GameObject extends Actor {
	
	public enum Type {
		RED_POTION(0, 60, 0),
		BLUE_POTION(1, 0, 30),
		BARREL(2, 0, 0),
		BOX(3, 0, 0),
		SPIKE(4, 0, 0),
		CANNON_LEFT(5, 0, 0),
		CANNON_RIGHT(6, 0, 0),
		TREE_1(7, 0, 0),
		TREE_2(8, 0, 0),
		TREE_3(9, 0, 0),
		CANNON_BALL(10, 0, 0),
		GRASS_LEFT(11, 0, 0),
		GRASS_RIGHT(12, 0, 0),
		SHIP(13, 0 , 0),
		RAIN(14, 0, 0);
		
		private static final Type[] GRASSTYPES = { GRASS_LEFT, GRASS_RIGHT };
		private static final Random RND = new Random();
		
		private final int colorValue;
		private final int healthValue;
		private final int powerValue;
		
		public static Type randomGrass() {
			return GRASSTYPES[RND.nextInt(GRASSTYPES.length)];
		}
		
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
	private boolean animate;
	private GameObjectManager gameObjectManager;

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
		if (animate) {
			updateAnimationTic();
		}
	}

	private void updateAnimationTic() {
		aniTic++;
		if (aniTic >= aniSpeed) {
			aniTic = 0;
			aniIndex++;
			if (aniIndex >= getSpriteCount()) {
				if (getType() == Type.BARREL || getType() == Type.BOX) {
					setActive(false);
					openContainer();
				} else if (getType() == Type.CANNON_LEFT || getType() == Type.CANNON_RIGHT) {
					animate = false;
					aniIndex = 0;
				} else {
					aniIndex = 0;
				}
			}
		}
	}
	
	private void openContainer() {
		switch (getType()) {
		case BOX:
			FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_CREATE_RED_POTION, getDrawPosition()));
			break;
		case BARREL:
			FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_CREATE_BLUE_POTION, getDrawPosition()));
			break;
		}
	}

	private int getSpriteCount() {
		switch (type) {
		case RED_POTION, BLUE_POTION:
			return 7;
		case BOX, BARREL:
			return 8;
		case CANNON_LEFT, CANNON_RIGHT:
			return 7;
		case TREE_1, TREE_2, TREE_3, SHIP:
			return 4;
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
		case CANNON_LEFT, CANNON_RIGHT:
			return 0;
		case GRASS_LEFT, GRASS_RIGHT:
			return 0;
		case TREE_1, TREE_2, TREE_3, SHIP:
			return 0;
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
	
	public void doAnimation(boolean animate) {
		this.animate = animate;
	}
	
	public boolean isAnimate()  {
		return animate;
	}
	
	public void setGameObjectManager(GameObjectManager gameObjectManager) {
		this.gameObjectManager = gameObjectManager;
	}

	public GameObjectManager getGameObjectManager() {
		return gameObjectManager;
	}
}
