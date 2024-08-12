package de.phillip.jumpandrun.controllers;

import de.phillip.jumpandrun.animation.GameLoopTimer;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;

public class GameController {
	
	private ScrollPane scrollPane;
	private GameLoopTimer loop;
	private boolean isStarted;
	private LayerManager layerManager;
	
	
	public GameController(ScrollPane scrollPane)  {
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
}
