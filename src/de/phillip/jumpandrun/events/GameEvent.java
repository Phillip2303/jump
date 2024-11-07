package de.phillip.jumpandrun.events;

import javafx.event.Event;
import javafx.event.EventType;

public class GameEvent extends Event {

	private static final long serialVersionUID = 5554001394529098895L;

	Object data;
	public static final EventType<GameEvent> ANY = new EventType<>(Event.ANY, "JR");

	public static final EventType<GameEvent> JR_MOVE_LEFT = new EventType<>(GameEvent.ANY, "JR_MOVE_LEFT");

	public static final EventType<GameEvent> JR_MOVE_RIGHT = new EventType<>(GameEvent.ANY, "JR_MOVE_RIGHT");

	public static final EventType<GameEvent> JR_SHOW_MENU = new EventType<>(GameEvent.ANY, "JR_SHOW_MENU");

	public static final EventType<GameEvent> JR_HIDE_MENU = new EventType<>(GameEvent.ANY, "JR_HIDE_MENU");

	public static final EventType<GameEvent> JR_SHOW_PAUSE_MENU = new EventType<>(GameEvent.ANY, "JR_SHOW_PAUSE_MENU");

	public static final EventType<GameEvent> JR_HIDE_PAUSE_MENU = new EventType<>(GameEvent.ANY, "JR_HIDE_PAUSE_MENU");

	public static final EventType<GameEvent> JR_QUIT = new EventType<>(GameEvent.ANY, "JR_QUIT");

	public static final EventType<GameEvent> JR_H_OFFSET = new EventType<>(GameEvent.ANY, "JR_H_OFFSET");
	
	public static final EventType<GameEvent> JR_SHOW_GAME_OVER = new EventType<>(GameEvent.ANY, "JR_SHOW_GAME_OVER");
	
	public static final EventType<GameEvent> JR_HIDE_GAME_OVER = new EventType<>(GameEvent.ANY, "JR_HIDE_GAME_OVER");
	
	public static final EventType<GameEvent> JR_SHOW_NEXT_LEVEL = new EventType<>(GameEvent.ANY, "JR_SHOW_NEXT_LEVEL");
	
	public static final EventType<GameEvent> JR_HIDE_NEXT_LEVEL = new EventType<>(GameEvent.ANY, "JR_HIDE_NEXT_LEVEL");
	
	public static final EventType<GameEvent> JR_RESET_LEVEL = new EventType<>(GameEvent.ANY, "JR_RESET_LEVEL");


	public GameEvent(EventType<GameEvent> eventType, Object data) {
		super(eventType);
		this.data = data;
	}

	public Object getData() {
		return data;
	}
}
