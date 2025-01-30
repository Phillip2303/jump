package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class PlayerStatus implements Drawable, EventHandler<GameEvent>{
	
	public static final double MAXHEALTH = 120;
	public static final double MAXPOWER = 10;
	
	
	private Image statusBar = ResourcePool.getInstance().getHealthPowerBar();
	private double statusBarWidth = 192 * Game.SCALE;
	private double statusBarHeight = 58 * Game.SCALE;
	private double statusBarX, statusBarY = 10 * Game.SCALE;
	
	private double healthBarWidth = 150 * Game.SCALE;
	private double healthBarHeight = 4 * Game.SCALE;
	private double healthBarX = 34 * Game.SCALE;
	private double healthBarY = 14 * Game.SCALE;
	private double healthWidth = healthBarWidth;
	
	private double powerBarWidth = 100 * Game.SCALE;
	private double powerBarHeight = 3 * Game.SCALE;
	private double powerBarX = 46 * Game.SCALE;
	private double powerBarY = 33 * Game.SCALE;
	private double powerWidth = 0;
	
	private int hOffset;
	private double currentHealth = MAXHEALTH;
	private double oldHealth = currentHealth;
	private double currentPower = 0;
	private double oldPower = currentPower;
	
	public PlayerStatus() {
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_H_OFFSET, this);
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.drawImage(statusBar, statusBarX + hOffset, statusBarY, statusBarWidth, statusBarHeight);
		drawHealthBar(gc);
		drawPowerBar(gc);
	}
	
	private void drawHealthBar(GraphicsContext gc) {
		gc.setFill(Color.RED);
		gc.fillRect(statusBarX + healthBarX + hOffset, statusBarY + healthBarY, healthWidth, healthBarHeight);
	}
	
	private void drawPowerBar(GraphicsContext gc) {
		gc.setFill(Color.ORANGE);
		gc.fillRect(statusBarX + powerBarX + hOffset, statusBarY + powerBarY, powerWidth, powerBarHeight);
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
	
	private void updateHealthBar(double currentHealth) {
		healthWidth = healthBarWidth * (currentHealth / MAXHEALTH);
	}
	
	public void setCurrentHealth(double currentHealth) {
		this.currentHealth = currentHealth;
		updateHealthBar(currentHealth);
	}
	
	public double getCurrentHealth() {
		return currentHealth;
	}
	
	public void increaseHealth() {
		if (currentHealth < MAXHEALTH) {
			currentHealth += 10;
		}
		updateHealthBar(currentHealth);
	}
	
	public void decreaseHealth(int amount) {
		currentHealth -= amount;
		updateHealthBar(currentHealth);
	}
	
	public void resetLevel() {
		setCurrentHealth(oldHealth);
		setCurrentPower(oldPower);
	}
	
	public void resetGame() {
		setCurrentHealth(MAXHEALTH);
		oldHealth = MAXHEALTH;
		setCurrentPower(0);
		oldPower = 0;
	}
	
	public void nextLevel() {
		oldHealth = currentHealth;
		oldPower = currentPower;
	}
	
	private void updatePowerBar(double currentPower) {
		powerWidth = (powerBarWidth / MAXPOWER) * currentPower;
	}
	
	public void increasePower() {
		if (currentPower < MAXPOWER) {
			currentPower += 2;
		}
		updatePowerBar(currentPower);
	}
	
	public void setCurrentPower(double currentPower) {
		this.currentPower = currentPower;
		updatePowerBar(currentPower);
	}

	public double getCurrentPower() {
		return currentPower;
	}

}
