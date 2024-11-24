package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.controllers.GameObjectManager;
import javafx.scene.canvas.GraphicsContext;

public class GameObject extends Actor {
	
	GameObjectManager.Type type;

	public GameObject(double width, double height, GameObjectManager.Type type) {
		super(width, height);
		this.type = type;
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debugOut() {
		// TODO Auto-generated method stub

	}

	public GameObjectManager.Type getType() {
		return type;
	}

}
