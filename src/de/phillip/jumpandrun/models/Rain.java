package de.phillip.jumpandrun.models;

import java.util.Random;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import de.phillip.jumpandrun.models.GameObject.Type;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Rain extends GameObject implements EventHandler<GameEvent>{

	public static final int DEFAULT_WIDTH = 3;
	public static final int DEFAULT_HEIGHT = 12;
	public static final int WIDTH = (int) (DEFAULT_WIDTH * Game.SCALE);
	public static final int HEIGHT = (int) (DEFAULT_HEIGHT * Game.SCALE);
	public static final float RAINSPEED = 1.25f * Game.SCALE;
	
	private Image rainParticle = ResourcePool.getInstance().getRain();
	private Drop[] drops;
	private Random random = new Random();
	//private int xOffset = 0;
	private int hOffset;

	public Rain() {
		super(WIDTH, HEIGHT, GameObject.Type.RAIN);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_H_OFFSET, this);
		drops = new Drop[1000];
		initDrops();
	}
	
	@Override
	public void drawToCanvas(GraphicsContext gc) {
		if (isActive()) {
			for (int i = 0; i < drops.length; i++) {
				Drop point = drops[i];
				gc.drawImage(rainParticle, 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT, 
						point.getX(), point.getY(), WIDTH, HEIGHT);
			}
		}
	}
	
	public void update() {
		for (Drop drop: drops) {
			drop.setY(drop.getY() + RAINSPEED);
			if (drop.getY() >= Game.GAMEHEIGHT) {
				drop.setY(-20);
				drop.setX(getNewX());
			}
		}
	}
	
	private void initDrops() {
		for (int i = 0; i < drops.length; i++) {
			drops[i] = getRandomPos();
		}
	}

	private Drop getRandomPos() {
		Drop drop = new Drop(getNewX(), random.nextInt(Game.GAMEHEIGHT));
		return drop;
	}
	
	private double getNewX() {
		return random.nextInt((int) (Game.GAMEWIDTH * 3f)) + hOffset;
	}

	@Override
	public void handle(GameEvent event) {
		switch (event.getEventType().getName()) {
		case "JR_H_OFFSET":
			hOffset = (int) event.getData();
			break;
		default:
			break;
		}

	}

}
