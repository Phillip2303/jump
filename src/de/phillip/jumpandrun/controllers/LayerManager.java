package de.phillip.jumpandrun.controllers;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.layers.ActionLayer;
import de.phillip.jumpandrun.rendering.Renderer;
import de.phillip.jumpandrun.utils.ResourcePool;
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
	
	public LayerManager(StackPane stackPane) {
		this.stackPane = stackPane;
		levelManager = new LevelManager();
		renderer = new Renderer();
		actionLayer = new ActionLayer(levelManager.getActiveLevel().getTilesInWidth() * Game.TILES_SIZE, Game.GAMEHEIGHT, levelManager);
		renderer.registerCanvasLayer(actionLayer);
		stackPane.getChildren().add(actionLayer);
		
	}
	
	public void update(float secondsSinceLastFrame) {
		renderer.prepare();
		actionLayer.updateLayer(secondsSinceLastFrame);
		renderer.render();
	}
}
