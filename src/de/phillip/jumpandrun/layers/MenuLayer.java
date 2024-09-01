package de.phillip.jumpandrun.layers;

import java.util.List;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.models.CanvasLayer;
import de.phillip.jumpandrun.models.Drawable;
import de.phillip.jumpandrun.models.Player;
import de.phillip.jumpandrun.utils.GameState;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MenuLayer extends Canvas implements CanvasLayer, EventHandler<Event> {
	
	private Image menuBackground;
	
	public MenuLayer(double width, double height) {
		super(width, height);
		FXEventBus.getInstance().addEventHandler(MouseEvent.MOUSE_CLICKED, this);
		FXEventBus.getInstance().addEventHandler(MouseEvent.MOUSE_MOVED, this);
		menuBackground = ResourcePool.getInstance().getMenuBackground();
	}

	@Override
	public List<Drawable> getDrawables() {

		return null;
	}

	@Override
	public void prepareLayer() {
		getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
		getGraphicsContext2D().setFill(Color.BLACK);
		getGraphicsContext2D().fillRect(0, 0, getWidth(), getHeight());
		getGraphicsContext2D().drawImage(menuBackground, 0, 0, menuBackground.getWidth(), menuBackground.getHeight(),
			Game.GAMEWIDTH / 2 - ((menuBackground.getWidth() * Game.SCALE) / 2), 80, menuBackground.getWidth() * Game.SCALE, menuBackground.getHeight() * Game.SCALE);
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
		case "MOUSE_CLICKED":
			GameState.state = GameState.PLAYING;
			break;
			
		case "MOUSE_MOVED":
			break;
			
		default:
			break;
		}
	}

}
