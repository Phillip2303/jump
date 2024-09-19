package de.phillip.jumpandrun.events;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;

public class FXEventBus {

	private static FXEventBus fxEventBus;
	private Group group = new Group();

	private FXEventBus() {

	}

	public static FXEventBus getInstance() {
		if (fxEventBus == null) {
			fxEventBus = new FXEventBus();
		}
		return fxEventBus;
	}

	public <T extends Event> void addEventHandler(EventType<T> type, EventHandler<? super T> handler) {
		group.addEventHandler(type, handler);
	}

	public <T extends Event> void removeEventHandler(EventType<T> type, EventHandler<? super T> handler) {
		group.removeEventHandler(type, handler);
	}

	public void fireEvent(Event event) {
		group.fireEvent(event);
	}

}
