package de.phillip.jumpandrun.layers;

import java.util.List;

import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.models.CanvasLayer;
import de.phillip.jumpandrun.models.Drawable;
import de.phillip.jumpandrun.models.Menu;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

public class MenuLayer extends Canvas implements CanvasLayer, EventHandler<Event> {
	
	Menu menu;


	public MenuLayer(double width, double height) {
		super(width, height);
		visibleProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue) {
					listenToEvents(true);
				}else{
					listenToEvents(false);
				}
			}
		});
	}

	@Override
	public List<Drawable> getDrawables() {
		return menu.getDrawables();
	}

	@Override
	public void prepareLayer() {
		menu.prepareMenu(getGraphicsContext2D());
	}
	
	public void showMenu(Menu menu) {
		this.menu = menu;
		this.setVisible(true);
	}
	
	public void hideMenu() {
		this.setVisible(false);
	}

	@Override
	public void updateLayer(float secondsSinceLastFrame) {

	}

	@Override
	public void resetGame() {

	}

	@Override
	public void handle(Event event) {

		switch (event.getEventType().getName()) {
		case "MOUSE_PRESSED":
			menu.mousePressed();
			break;
		case "MOUSE_RELEASED":
			menu.mouseReleased();
			break;
		case "MOUSE_MOVED":
			MouseEvent mouseEvent = (MouseEvent) event;
			menu.mouseMoved(mouseEvent.getX(), mouseEvent.getY());
			break;

		default:
			break;
		}
	}


	@Override
	public void listenToEvents(boolean value) {
		if (value) {
			registerListeners();
		} else {
			unregisterListeners();
		}
	}

	private void unregisterListeners() {
		FXEventBus.getInstance().removeEventHandler(MouseEvent.MOUSE_PRESSED, this);
		FXEventBus.getInstance().removeEventHandler(MouseEvent.MOUSE_RELEASED, this);
		FXEventBus.getInstance().removeEventHandler(MouseEvent.MOUSE_MOVED, this);

	}

	private void registerListeners() {
		FXEventBus.getInstance().addEventHandler(MouseEvent.MOUSE_PRESSED, this);
		FXEventBus.getInstance().addEventHandler(MouseEvent.MOUSE_RELEASED, this);
		FXEventBus.getInstance().addEventHandler(MouseEvent.MOUSE_MOVED, this);
	}

}
