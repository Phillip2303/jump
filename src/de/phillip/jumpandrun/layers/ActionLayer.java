package de.phillip.jumpandrun.layers;

import java.util.List;

import de.phillip.jumpandrun.models.CanvasLayer;
import de.phillip.jumpandrun.models.Drawable;
import javafx.scene.canvas.Canvas;

public class ActionLayer extends Canvas implements CanvasLayer {

	@Override
	public List<Drawable> getDrawables() {

		return null;
	}

	@Override
	public void prepareLayer() {

	}

	@Override
	public void updateLayer(float secondsSinceLastFrame) {

	}

	@Override
	public void resetGame() {

	}

}
