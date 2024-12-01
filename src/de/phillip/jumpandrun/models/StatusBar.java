package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class StatusBar implements Drawable, EventHandler<GameEvent>{
	
	private Image statusBar = ResourcePool.getInstance().getHealthPowerBar();
	private double statusBarWidth = 192 * Game.SCALE;
	private double statusBarHeight = 58 * Game.SCALE;
	private double statusBarX, statusBarY = 10 * Game.SCALE;
	private double healthBarWidth = 150 * Game.SCALE;
	private double healthBarHeight = 4 * Game.SCALE;
	private double healthBarX = 34 * Game.SCALE;
	private double healthBarY = 14 * Game.SCALE;
	private double healthWidth = healthBarWidth;
	private int hOffset;
	
	public StatusBar() {
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_H_OFFSET, this);
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		drawStatusBar(gc);
	}
	
	private void drawStatusBar(GraphicsContext gc) {
		gc.drawImage(statusBar, statusBarX + hOffset, statusBarY, statusBarWidth, statusBarHeight);
		gc.setFill(Color.RED);
		gc.fillRect(statusBarX + healthBarX + hOffset, statusBarY + healthBarY, healthWidth, healthBarHeight);
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
	
	public void reset() {
		hOffset = 0;
	}
	
	public void updateHealthBar(double currentHealth) {
		healthWidth = healthBarWidth * (currentHealth / Player.MAXHEALTH);
	}

}
