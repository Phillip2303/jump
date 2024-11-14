package de.phillip.jumpandrun.controllers;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import de.phillip.jumpandrun.layers.ActionLayer;
import de.phillip.jumpandrun.layers.BackgroundLayer;
import de.phillip.jumpandrun.layers.MenuLayer;
import de.phillip.jumpandrun.layers.menus.GameOverMenu;
import de.phillip.jumpandrun.layers.menus.MainMenu;
import de.phillip.jumpandrun.layers.menus.NextLevelMenu;
import de.phillip.jumpandrun.layers.menus.PauseMenu;
import de.phillip.jumpandrun.rendering.Renderer;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

public class LayerManager implements EventHandler<GameEvent> {


	private StackPane stackPane;
	private LevelManager levelManager;
	private Renderer renderer;
	private ActionLayer actionLayer;
	private MenuLayer menuLayer;
	private BackgroundLayer backgroundLayer;
	//private PauseLayer pauseLayer;
	// private GameState state = GameState.MENU;
	private MainMenu mainMenu;
	private PauseMenu pauseMenu;
	private GameOverMenu gameOverMenu;
	private NextLevelMenu nextLevelMenu;

	public LayerManager(StackPane stackPane) {
		this.stackPane = stackPane;
		levelManager = new LevelManager();
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_SHOW_MENU, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_HIDE_MENU, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_H_OFFSET, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_SHOW_PAUSE_MENU, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_HIDE_PAUSE_MENU, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_SHOW_GAME_OVER, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_SHOW_NEXT_LEVEL, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_HIDE_NEXT_LEVEL, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_RESET_GAME, this);
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
		mainMenu = new MainMenu();
		pauseMenu = new PauseMenu();
		gameOverMenu = new GameOverMenu();
		nextLevelMenu = new NextLevelMenu();
		
		menuLayer.setVisible(false);
	}

	public void update(float secondsSinceLastFrame) {
		renderer.prepare();
		actionLayer.updateLayer(secondsSinceLastFrame);
		renderer.render();
	}
	
	private void resetToLevel(int level) {
		levelManager.setLevel(level);
		actionLayer.resetLevel();
	}
	
	@Override
	public void handle(GameEvent event) {
		switch (event.getEventType().getName()) {
		case "JR_SHOW_MENU":
			menuLayer.showMenu(mainMenu);
			actionLayer.listenToEvents(false);
			break;
		case "JR_HIDE_MENU":
			menuLayer.hideMenu();
			actionLayer.listenToEvents(true);
			break;
		case "JR_SHOW_PAUSE_MENU":
			menuLayer.showMenu(pauseMenu);
			actionLayer.listenToEvents(false);
			break;
		case "JR_HIDE_PAUSE_MENU":
			menuLayer.hideMenu();
			actionLayer.listenToEvents(true);
			break;
		case "JR_SHOW_GAME_OVER":
			menuLayer.showMenu(gameOverMenu);
			actionLayer.listenToEvents(false);
			break;
		case "JR_SHOW_NEXT_LEVEL":
			menuLayer.showMenu(nextLevelMenu);
			actionLayer.listenToEvents(false);
			break;
		case "JR_HIDE_NEXT_LEVEL":
			break;
		case "JR_RESET_GAME":
			resetToLevel(1);
			menuLayer.hideMenu();
			actionLayer.listenToEvents(true);
			break;
		case "JR_H_OFFSET":
			int hOffset = (int) event.getData();
			menuLayer.setTranslateX(hOffset);
			break;
		default:
			break;
		}

	}

	/*private void setGameState(GameState state) {
		menuLayer.setTranslateX(hOffset);
		switch (state) {
		case MENU:
			menuLayer.showMenu(mainMenu);
			actionLayer.listenToEvents(false);
			break;
		case PAUSING:
			menuLayer.showMenu(pauseMenu);
			actionLayer.listenToEvents(false);
			break;
		case PLAYING:
			menuLayer.hideMenu();
			actionLayer.listenToEvents(true);
			break;
		case GAMEOVER:
			menuLayer.showMenu(gameOverMenu);
			actionLayer.listenToEvents(false);
			break;
		default:
			break;
		}
	}*/
}
