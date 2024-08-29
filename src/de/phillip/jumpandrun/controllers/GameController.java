package de.phillip.jumpandrun.controllers;

import de.phillip.jumpandrun.animation.GameLoopTimer;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import de.phillip.jumpandrun.models.Player;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;

public class GameController implements EventHandler<GameEvent> {
	
	private ScrollPane scrollPane;
	private GameLoopTimer loop;
	private boolean isStarted;
	private LayerManager layerManager;
	
	
	public GameController(ScrollPane scrollPane)  {
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_MOVE_LEFT, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_MOVE_RIGHT, this);
		this.scrollPane = scrollPane;
		loop = new GameLoopTimer() {

			@Override
			public void tic(float secondsSinceLastFrame) {
				layerManager.update(secondsSinceLastFrame);
			}
			
		};
	}
	
	public void startGame() {
		if (!isStarted) {
			isStarted = true;
			layerManager = new LayerManager((StackPane) scrollPane.getContent());
		}
		loop.start();
	}

	@Override
	public void handle(GameEvent event) {
		switch (event.getEventType().getName()) {
		case "JR_MOVE_LEFT":
			moveContent(-Player.SPEED);
			break;
			
		case "JR_MOVE_RIGHT":
			moveContent(Player.SPEED);
			break;
			
		default:
			break;
		}
	}
	
	private void moveContent(double movingX) {
		Bounds contentBounds = scrollPane.getContent().getBoundsInLocal();
		Bounds viewPortBounds = scrollPane.getViewportBounds();
		double hValue = movingX / (contentBounds.getWidth() - viewPortBounds.getWidth());
		double oldValue = scrollPane.getHvalue();
		scrollPane.setHvalue(oldValue + hValue);
	}
}
