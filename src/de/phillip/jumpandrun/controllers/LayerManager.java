package de.phillip.jumpandrun.controllers;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import de.phillip.jumpandrun.layers.ActionLayer;
import de.phillip.jumpandrun.layers.BackgroundLayer;
import de.phillip.jumpandrun.layers.MenuLayer;
import de.phillip.jumpandrun.layers.PauseLayer;
import de.phillip.jumpandrun.layers.menus.MainMenu;
import de.phillip.jumpandrun.layers.menus.PauseMenu;
import de.phillip.jumpandrun.rendering.Renderer;
import de.phillip.jumpandrun.utils.GameState;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class LayerManager implements EventHandler<GameEvent> {


	private StackPane stackPane;
	private LevelManager levelManager;
	private Renderer renderer;
	private ActionLayer actionLayer;
	private MenuLayer menuLayer;
	private BackgroundLayer backgroundLayer;
	//private PauseLayer pauseLayer;
	// private GameState state = GameState.MENU;
	private int hOffset;
	private MainMenu mainMenu;
	private PauseMenu pauseMenu;

	public LayerManager(StackPane stackPane) {
		this.stackPane = stackPane;
		levelManager = new LevelManager();
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_SHOW_MENU, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_HIDE_MENU, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_H_OFFSET, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_SHOW_PAUSE_MENU, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_HIDE_PAUSE_MENU, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_SHOW_GAME_OVER, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_HIDE_GAME_OVER, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_SHOW_NEXT_LEVEL, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_HIDE_NEXT_LEVEL, this);
		renderer = new Renderer();
		actionLayer = new ActionLayer(levelManager.getActiveLevel().getTilesInWidth() * Game.TILES_SIZE,
				Game.GAMEHEIGHT, levelManager);
		menuLayer = new MenuLayer(Game.GAMEWIDTH, Game.GAMEHEIGHT);
		backgroundLayer = new BackgroundLayer(levelManager.getActiveLevel().getTilesInWidth() * Game.TILES_SIZE,
				Game.GAMEHEIGHT);
		//pauseLayer = new PauseLayer(Game.GAMEWIDTH, Game.GAMEHEIGHT);
		renderer.registerCanvasLayer(actionLayer);
		renderer.registerCanvasLayer(menuLayer);
		renderer.registerCanvasLayer(backgroundLayer);
		//renderer.registerCanvasLayer(pauseLayer);
		stackPane.getChildren().add(0, backgroundLayer);
		stackPane.getChildren().add(1, actionLayer);
		StackPane.setAlignment(menuLayer, Pos.TOP_LEFT);
		stackPane.getChildren().add(2, menuLayer);
		//StackPane.setAlignment(pauseLayer, Pos.TOP_LEFT);
		//stackPane.getChildren().add(3, pauseLayer);
		mainMenu = new MainMenu(Game.GAMEWIDTH, Game.GAMEHEIGHT);
		pauseMenu = new PauseMenu(Game.GAMEWIDTH, Game.GAMEHEIGHT);
	}

	public void update(float secondsSinceLastFrame) {
		renderer.prepare();
		actionLayer.updateLayer(secondsSinceLastFrame);
		renderer.render();
	}

	@Override
	public void handle(GameEvent event) {
		switch (event.getEventType().getName()) {
		case "JR_SHOW_MENU":
		//	menuLayer.setTranslateX(hOffset);
			setGameState(GameState.MENU);
			break;
		case "JR_HIDE_MENU":
			setGameState(GameState.PLAYING);
			break;
		case "JR_SHOW_PAUSE_MENU":
			//pauseLayer.setTranslateX(hOffset);
			setGameState(GameState.PAUSING);
			break;
		case "JR_HIDE_PAUSE_MENU":
			setGameState(GameState.PLAYING);
			break;
		case "JR_H_OFFSET":
			this.hOffset = (int) event.getData();
			break;
		case "JR_SHOW_GAME_OVER":
			break;
		case "JR_HIDE_GAME_OVER":
			break;
		case "JR_SHOW_NEXT_LEVEL":
			break;
		case "JR_HIDE_NEXT_LEVEL":
			break;
		default:
			break;
		}

	}

	private void setGameState(GameState state) {
		menuLayer.setTranslateX(hOffset);
		switch (state) {
		case MENU:
			menuLayer.showMenu(mainMenu);
			menuLayer.setVisible(true);
			//menuLayer.listenToEvents(true);
			//pauseLayer.setVisible(false);
			//pauseLayer.listenToEvents(false);
			actionLayer.listenToEvents(false);
			break;
		case PAUSING:
			menuLayer.showMenu(pauseMenu);
			menuLayer.setVisible(true);
			//menuLayer.listenToEvents(false);
			//pauseLayer.setVisible(true);
			//pauseLayer.listenToEvents(true);
			actionLayer.listenToEvents(false);
			break;
		case PLAYING:
			menuLayer.setVisible(false);
			//menuLayer.listenToEvents(false);
			//pauseLayer.setVisible(false);
			//pauseLayer.listenToEvents(false);
			actionLayer.listenToEvents(true);
			/*
			 * renderer.prepare(); actionLayer.updateLayer(secondsSinceLastFrame);
			 * renderer.render();
			 */
			break;
		default:
			break;
		}
	}
}
