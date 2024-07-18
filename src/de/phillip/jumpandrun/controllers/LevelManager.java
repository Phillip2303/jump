package de.phillip.jumpandrun.controllers;

import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class LevelManager {
	
	private final static int TILES_DEFAULT_SIZE = 32;
	private final static float SCALE = 1.5f;
	private final static int TILES_IN_WIDTH = 26;
	private final static int TILES_IN_HEIGHT = 14;
	private final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	private final static int GAMEWIDTH = TILES_SIZE * TILES_IN_WIDTH;
	private final static int GAMEHEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	private StackPane stackPane;
	private Canvas canvas;
	
	public LevelManager(StackPane stackPane) {
		this.stackPane = stackPane;
		canvas = new Canvas(GAMEWIDTH, GAMEHEIGHT);
		
		Image outsideAtlas = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.OUTSIDE_ATLAS);
		PixelReader pr = outsideAtlas.getPixelReader();
		
		WritableImage wi = new WritableImage(TILES_SIZE, TILES_SIZE);
		
		PixelWriter pw = wi.getPixelWriter();
		
		for (int y = 0; y < TILES_SIZE; y++) {
			for (int x = 0; x < TILES_SIZE; x++) {
				Color color = pr.getColor(x, y);
				pw.setColor(x, y, color);
			}
		}
		
		canvas.getGraphicsContext2D().drawImage(wi, 0, 0);
		stackPane.getChildren().add(canvas);
		
	}
	
	public void update(float secondsSinceLastFrame) {
		
	}
}
