package de.phillip.jumpandrun.controllers;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import de.phillip.jumpandrun.layers.ActionLayer;
import de.phillip.jumpandrun.layers.BackgroundLayer;
import de.phillip.jumpandrun.layers.MenuLayer;
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

public class LayerManager implements EventHandler<GameEvent>{
	
	
	private StackPane stackPane;
	private LevelManager levelManager;
	private Renderer renderer;
	private ActionLayer actionLayer;
	private MenuLayer menuLayer;
	private BackgroundLayer backgroundLayer;
	private GameState state = GameState.MENU;
	private int hOffset;
	
	public LayerManager(StackPane stackPane) {
		this.stackPane = stackPane;
		levelManager = new LevelManager();
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_SHOW_MENU, this);
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_HIDE_MENU, this);
		renderer = new Renderer();
		actionLayer = new ActionLayer(levelManager.getActiveLevel().getTilesInWidth() * Game.TILES_SIZE, Game.GAMEHEIGHT, levelManager);
		menuLayer = new MenuLayer(Game.GAMEWIDTH, Game.GAMEHEIGHT);
		backgroundLayer = new BackgroundLayer(levelManager.getActiveLevel().getTilesInWidth() * Game.TILES_SIZE, Game.GAMEHEIGHT);
		renderer.registerCanvasLayer(actionLayer);
		renderer.registerMenuLayer(menuLayer);
		renderer.registerCanvasLayer(backgroundLayer);
		stackPane.getChildren().add(0, backgroundLayer);
		stackPane.getChildren().add(1, actionLayer);
		StackPane.setAlignment(menuLayer, Pos.TOP_LEFT);
		stackPane.getChildren().add(2, menuLayer);
	}
	
	public void update(float secondsSinceLastFrame) {
		switch (state) {
			case MENU: 
				menuLayer.setVisible(true);
				renderer.prepareMenu();
				renderer.renderMenu();
				break;
			case PLAYING:
				menuLayer.setVisible(false);
				renderer.prepare();
				actionLayer.updateLayer(secondsSinceLastFrame);
				renderer.render();
				break;
			default:
				break;
		}
	}

	@Override
	public void handle(GameEvent event) {
		switch(event.getEventType().getName()) {
			case "JR_SHOW_MENU": 
				menuLayer.setTranslateX(hOffset);
				state = GameState.MENU;
				break;
			case "JR_HIDE_MENU": 
				state = GameState.PLAYING;
				break;
			default:
				break;
		}
		
	}
	
	public void setScrollPaneOffset(int hOffset) {
		this.hOffset = hOffset;
	}
}
