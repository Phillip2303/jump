package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Cloud extends Actor implements EventHandler<GameEvent> {

	private double x;
	private double y;
	private Image image;
	private int hOffset;
	private double speed;

	public Cloud(double x, double y, double width, double height, Image image, double speed) {
		super(width, height);
		this.x = x;
		this.y = y;
		this.image = image;
		this.speed = speed;
		FXEventBus.getInstance().addEventHandler(GameEvent.JR_H_OFFSET, this);
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.drawImage(image, x - hOffset * speed, y, getWidth(), getHeight());
	}

	@Override
	public void debugOut() {

	}

	@Override
	public void handle(GameEvent event) {
		switch (event.getEventType().getName()) {
		case "JR_H_OFFSET":
			hOffset = (int) event.getData();
			break;
		default:
			break;
		}

	}

}
