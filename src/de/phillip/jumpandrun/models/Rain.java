package de.phillip.jumpandrun.models;

import java.util.Random;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.models.GameObject.Type;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Rain extends GameObject {

	public static final int DEFAULT_WIDTH = 3;
	public static final int DEFAULT_HEIGHT = 12;
	public static final int WIDTH = (int) (DEFAULT_WIDTH * Game.SCALE);
	public static final int HEIGHT = (int) (DEFAULT_HEIGHT * Game.SCALE);
	
	private Image rainParticle = ResourcePool.getInstance().getRain();
	private Point2D[] drops;
	private Random random = new Random();
	private float rainSpeed = 1.25f;
	private int xOffset = 0;

	public Rain() {
		super(WIDTH, HEIGHT, GameObject.Type.RAIN);
		drops = new Point2D[1000];
		initDrops();
	}
	
	@Override
	public void drawToCanvas(GraphicsContext gc) {
		if (isActive()) {
			for (int i = 0; i < drops.length; i++) {
				Point2D point = drops[i];
				gc.drawImage(rainParticle, 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, 
						point.getX() - xOffset, point.getY(), WIDTH, HEIGHT);
			}
		}
	}
	
	public void update() {
		
	}
	
	private void initDrops() {
		for (int i = 0; i < drops.length; i++) {
			drops[i] = getRandomPos();
		}
	}

	private Point2D getRandomPos() {
		Point2D point = new Point2D(getNewX(), random.nextInt(Game.GAMEHEIGHT));
		return point;
	}
	
	private double getNewX() {
		return Game.GAMEWIDTH  + random.nextInt((int) (Game.GAMEWIDTH * 3f)) + xOffset;
	}

}
