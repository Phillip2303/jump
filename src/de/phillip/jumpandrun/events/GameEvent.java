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
		
	public static final EventType<GameEvent> JR_SHOW_LEVEL_COMPLETED = new EventType<>(GameEvent.ANY, "JR_SHOW_LEVEL_COMPLETED");
	
	public static final EventType<GameEvent> JR_NEXT_LEVEL = new EventType<>(GameEvent.ANY, "JR_NEXT_LEVEL");
	
	public static final EventType<GameEvent> JR_RESET_LEVEL = new EventType<>(GameEvent.ANY, "JR_RESET_LEVEL");
	
	public static final EventType<GameEvent> JR_RESET_GAME = new EventType<>(GameEvent.ANY, "JR_RESET_GAME");
	
	public static final EventType<GameEvent> JR_RESET_H_OFFSET = new EventType<>(GameEvent.ANY, "JR_RESET_H_OFFSET");
	
	public static final EventType<GameEvent> JR_CREATE_RED_POTION = new EventType<>(GameEvent.ANY, "JR_CREATE_RED_POTION");
	
	public static final EventType<GameEvent> JR_CREATE_BLUE_POTION = new EventType<>(GameEvent.ANY, "JR_CREATE_BLUE_POTION");
	
	public static final EventType<GameEvent> JR_SHOOT_BALL = new EventType<>(GameEvent.ANY, "JR_SHOOT_BALL");



	public GameEvent(EventType<GameEvent> eventType, Object data) {
		super(eventType);
		this.data = data;
	}

	public Object getData() {
		return data;
	}
}
