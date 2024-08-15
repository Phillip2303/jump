package de.phillip.jumpandrun.utils;

import java.util.HashSet;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyPolling {
	private static Scene scene;
	private static final Set<KeyCode> currentKeysDown = new HashSet<>();
	private static KeyPolling keyPolling;
	
	private KeyPolling() {
		
	}
	
	public static KeyPolling getInstance () {
		if(keyPolling == null) {
			keyPolling = new KeyPolling();
		}
		return keyPolling;
	}
	
	public void pollScene(Scene scene) {
		clearKeys();
		removeCurrentKeyHandlers();
		setScene(scene);
	}
	
	private void clearKeys() {
		currentKeysDown.clear();
	}
	
	private void removeCurrentKeyHandlers() {
		if (KeyPolling.scene != null) {
			KeyPolling.scene.setOnKeyPressed(null);
			KeyPolling.scene.setOnKeyReleased(null);
		}
	}
	
	private void setScene(Scene scene) {
		KeyPolling.scene = scene;
		KeyPolling.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				//System.out.println("Add: " + event.getCode().toString());
				currentKeysDown.add(event.getCode());
			}
		});
		KeyPolling.scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				//System.out.println("Remove: " + event.getCode().toString());
				currentKeysDown.remove(event.getCode());
			}
			
		});
	}
		
	public boolean isDown(KeyCode keyCode) {
		return currentKeysDown.contains(keyCode);
	}
}
