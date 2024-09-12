package de.phillip.jumpandrun.layers;

import java.util.ArrayList;
import java.util.List;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.models.CanvasLayer;
import de.phillip.jumpandrun.models.Drawable;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class PauseLayer extends Canvas implements CanvasLayer, EventHandler<Event> {
	
	private List<Drawable> actors = new ArrayList<>();
	private Image pauseBackground;
	private Image soundButtonSprites;
	private boolean isDrawable;
	
	public PauseLayer(double width, double height) {
		super(width, height);
		FXEventBus.getInstance().addEventHandler(MouseEvent.MOUSE_PRESSED, this);
		FXEventBus.getInstance().addEventHandler(MouseEvent.MOUSE_RELEASED, this);
		FXEventBus.getInstance().addEventHandler(MouseEvent.MOUSE_MOVED, this);
		pauseBackground = ResourcePool.getInstance().getPauseBackground();
		soundButtonSprites = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.SOUND_BUTTONS_SPRITES);
		createButtons();
	}

	private void createButtons() {
		
	}

	@Override
	public List<Drawable> getDrawables() {
		return actors;
	}

	@Override
	public void prepareLayer() {
		getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
		getGraphicsContext2D().drawImage(pauseBackground, 0, 0, pauseBackground.getWidth(), pauseBackground.getHeight(),
			Game.GAMEWIDTH / 2 - ((pauseBackground.getWidth() * Game.SCALE) / 2), 80, pauseBackground.getWidth() * Game.SCALE, pauseBackground.getHeight() * Game.SCALE);
	}

	@Override
	public void updateLayer(float secondsSinceLastFrame) {

	}

	@Override
	public void resetGame() {

	}

	@Override
	public void handle(Event event) {
		
		switch (event.getEventType().getName()) {
		case "MOUSE_PRESSED":

			break;
		case "MOUSE_RELEASED":

			break;
		case "MOUSE_MOVED":

			break;
			
		default:
			break;
		}
	}

	@Override
	public void setDrawable(boolean value) {
		isDrawable = value;
	}

	@Override
	public boolean isDrawable() {
		return isDrawable;
	}

}
