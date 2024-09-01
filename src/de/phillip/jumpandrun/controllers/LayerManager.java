package de.phillip.jumpandrun.controllers;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.layers.ActionLayer;
import de.phillip.jumpandrun.layers.MenuLayer;
import de.phillip.jumpandrun.rendering.Renderer;
import de.phillip.jumpandrun.utils.GameState;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class LayerManager {
	
	
	private StackPane stackPane;
	private LevelManager levelManager;
	private Renderer renderer;
	private ActionLayer actionLayer;
	private MenuLayer menuLayer;
	
	public LayerManager(StackPane stackPane) {
		this.stackPane = stackPane;
		levelManager = new LevelManager();
		renderer = new Renderer();
		actionLayer = new ActionLayer(levelManager.getActiveLevel().getTilesInWidth() * Game.TILES_SIZE, Game.GAMEHEIGHT, levelManager);
		menuLayer = new MenuLayer(Game.GAMEWIDTH, Game.GAMEHEIGHT);
		renderer.registerCanvasLayer(actionLayer);
		renderer.registerMenuLayer(menuLayer);
		stackPane.getChildren().add(0, actionLayer);
		StackPane.setAlignment(menuLayer, Pos.TOP_LEFT);
		stackPane.getChildren().add(1, menuLayer);
		
	}
	
	public void update(float secondsSinceLastFrame) {
		switch (GameState.state) {
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
}
