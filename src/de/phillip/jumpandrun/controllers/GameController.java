package de.phillip.jumpandrun.controllers;

import de.phillip.jumpandrun.animation.GameLoopTimer;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;

public class GameController {
	
	private ScrollPane scrollPane;
	private GameLoopTimer loop;
	private boolean isStarted;
	private LevelManager levelManager;
	
	
	public GameController(ScrollPane scrollPane)  {
		this.scrollPane = scrollPane;
		loop = new GameLoopTimer() {

			@Override
			public void tic(float secondsSinceLastFrame) {
				levelManager.update(secondsSinceLastFrame);
			}
			
		};
	}
	
	public void startGame() {
		if (!isStarted) {
			isStarted = true;
			levelManager = new LevelManager((StackPane) scrollPane.getContent());
		}
		loop.start();
	}
}
