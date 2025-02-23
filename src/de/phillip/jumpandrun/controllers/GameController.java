package de.phillip.jumpandrun.controllers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.animation.GameLoopTimer;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import de.phillip.jumpandrun.models.Player;
import de.phillip.jumpandrun.utils.GameDAO;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;

public class GameController implements EventHandler<GameEvent> {

	private ScrollPane scrollPane;
	private GameLoopTimer loop;
	private boolean isStarted;
	private LayerManager layerManager;
	

	public GameController(ScrollPane scrollPane) {
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_MOVE_LEFT, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_MOVE_RIGHT, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_QUIT, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_RESET_GAME, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_RESET_H_OFFSET, this);
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
			double minX = (double) event.getData();
			if (scrollToLeft(minX)) {
				moveContent(-Player.SPEED);
				notifyScrollListener();
			}
			break;

		case "JR_MOVE_RIGHT":
			double maxX = (double) event.getData();
			if (scrollToRight(maxX)) {
				moveContent(Player.SPEED);
				notifyScrollListener();
			}
			break;
		case "JR_QUIT":
			stopGame();
			break;
		case "JR_RESET_GAME":
			scrollPane.setHvalue(0);
			break;
		case "JR_RESET_H_OFFSET":
			scrollPane.setHvalue(0);
			FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_H_OFFSET, 0));
			break;
		default:
			break;
		}
	}

	public void stopGame() {
		GameDAO.getInstance().closeConnection();
		System.exit(0);
		
	}

	private void moveContent(double movingX) {
		Bounds contentBounds = scrollPane.getContent().getBoundsInLocal();
		Bounds viewPortBounds = scrollPane.getViewportBounds();
		double hValue = movingX / (contentBounds.getWidth() - viewPortBounds.getWidth());
		double oldValue = scrollPane.getHvalue();
		scrollPane.setHvalue(oldValue + hValue);
	}

	private void notifyScrollListener() {
		Bounds contentBounds = scrollPane.getContent().getBoundsInLocal();
		Bounds viewPortBounds = scrollPane.getViewportBounds();
		int hOffset = (int) (scrollPane.getHvalue() * (contentBounds.getWidth() - viewPortBounds.getWidth()));
		FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_H_OFFSET, hOffset));
	}

	private boolean scrollToRight(double maxX) {
		if (maxX > Game.GAMEWIDTH / 2) {
			return true;
		}
		return false;
	}

	private boolean scrollToLeft(double minX) {
		Bounds contentBounds = scrollPane.getContent().getBoundsInLocal();
		Bounds viewPortBounds = scrollPane.getViewportBounds();
		int hOffset = (int) (scrollPane.getHvalue() * (contentBounds.getWidth() - viewPortBounds.getWidth()));
		if (minX < hOffset + Game.GAMEWIDTH / 2) {
			return true;
		}
		return false;
	}
	
	
}
