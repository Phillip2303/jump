package de.phillip.jumpandrun.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public abstract class Enemy extends Actor {

	public enum Type {
		CRABBY, CANNON, SHARK;
	}

	public static final int IDLE = 0;
	public static final int RUNNING = 1;
	public static final int ATTACK = 2;
	public static final int HIT = 3;
	public static final int DEAD = 4;

	private Type type;
	private int aniIndex;
	private int aniTic;
	private int aniSpeed = 25;
	private int enemyAction = IDLE;

	public Enemy(double width, double height, Type type) {
		super(width, height);
		this.type = type;
	}

	private void updateAnimationTic() {
		aniTic++;
		if (aniTic >= aniSpeed) {
			aniTic = 0;
			aniIndex++;
		}
		if (aniIndex >= getSpriteCount(enemyAction)) {
			aniIndex = 0;
		}
	}

	public void setEnemyAction(int enemyAction) {
		this.enemyAction = enemyAction;
		if (this.enemyAction != enemyAction) {
			resetAniTic();
		}
	}

	private void resetAniTic() {
		aniTic = 0;
		aniIndex = 0;
	}

	private int getSpriteCount(int enemyAction) {
		switch (type) {
		case CRABBY:
			switch (enemyAction) {
			case IDLE:
				return 9;
			case RUNNING:
				return 6;
			case ATTACK:
				return 7;
			case HIT:
				return 4;
			case DEAD:
				return 5;
			default:
				return 0;
			}
		default:
			return 0;
		}
	}
	
	public int getEnemyAction() {
		return enemyAction;
	}

	

	public int getAniIndex() {
		return aniIndex;
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		//
	}

	@Override
	public void debugOut() {

	}

}