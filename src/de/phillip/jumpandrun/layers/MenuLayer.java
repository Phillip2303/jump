package de.phillip.jumpandrun.layers;

import java.util.ArrayList;
import java.util.List;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import de.phillip.jumpandrun.models.CanvasButton;
import de.phillip.jumpandrun.models.CanvasLayer;
import de.phillip.jumpandrun.models.Drawable;
import de.phillip.jumpandrun.models.Player;
import de.phillip.jumpandrun.utils.GameState;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MenuLayer extends Canvas implements CanvasLayer, EventHandler<Event> {

	private Image menuBackground;
	private Image buttonSprites;
	private List<Drawable> drawables = new ArrayList<>();
	private CanvasButton play;
	private CanvasButton options;
	private CanvasButton quit;
	private int vOffset;

	public MenuLayer(double width, double height) {
		super(width, height);
		menuBackground = ResourcePool.getInstance().getMenuBackground();
		buttonSprites = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.BUTTON_SPRITES);
		createButtons();
	}

	@Override
	public List<Drawable> getDrawables() {
		return drawables;
	}

	@Override
	public void prepareLayer() {
		getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
		getGraphicsContext2D().setFill(Color.BLACK);
		getGraphicsContext2D().fillRect(0, 0, getWidth(), getHeight());
		getGraphicsContext2D().drawImage(menuBackground, 0, 0, menuBackground.getWidth(), menuBackground.getHeight(),
				Game.GAMEWIDTH / 2 - ((menuBackground.getWidth() * Game.SCALE) / 2), 80,
				menuBackground.getWidth() * Game.SCALE, menuBackground.getHeight() * Game.SCALE);
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
			mousePressed();
			break;
		case "MOUSE_RELEASED":
			mouseReleased();
			break;
		case "MOUSE_MOVED":
			MouseEvent mouseEvent = (MouseEvent) event;
			mouseMoved(mouseEvent.getX(), mouseEvent.getY());
			break;

		default:
			break;
		}
	}

	private void mouseReleased() {
		if (play.isActive()) {
			play.setClicked(false);
			FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_HIDE_MENU, null));
		} else if (options.isActive()) {
			options.setClicked(false);
		} else if (quit.isActive()) {
			quit.setClicked(false);
			FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_QUIT, null));

		}
	}

	private void mousePressed() {
		if (play.isActive()) {
			play.setClicked(true);
		} else if (options.isActive()) {
			options.setClicked(true);
		} else if (quit.isActive()) {
			quit.setClicked(true);
		}
	}

	private void mouseMoved(double x, double y) {
		if (play.contains(new Point2D(x, y))) {
			play.setActive(true);
		} else {
			play.setActive(false);
		}
		if (options.contains(new Point2D(x, y))) {
			options.setActive(true);
		} else {
			options.setActive(false);
		}
		if (quit.contains(new Point2D(x, y))) {
			quit.setActive(true);
		} else {
			quit.setActive(false);
		}
	}

	private void createButtons() {
		play = new CanvasButton(buttonSprites, (int) (getWidth() / 2 - CanvasButton.MENU_WIDTH / 2),
				CanvasButton.MENU_V_OFFSET, CanvasButton.MENU_WIDTH, CanvasButton.MENU_HEIGHT, 0, GameState.MENU);
		options = new CanvasButton(buttonSprites, (int) (getWidth() / 2 - CanvasButton.MENU_WIDTH / 2),
				CanvasButton.MENU_V_OFFSET + CanvasButton.MENU_HEIGHT, CanvasButton.MENU_WIDTH,
				CanvasButton.MENU_HEIGHT, 1, GameState.MENU);
		quit = new CanvasButton(buttonSprites, (int) (getWidth() / 2 - CanvasButton.MENU_WIDTH / 2),
				CanvasButton.MENU_V_OFFSET + 2 * CanvasButton.MENU_HEIGHT, CanvasButton.MENU_WIDTH,
				CanvasButton.MENU_HEIGHT, 2, GameState.MENU);
		drawables.add(play);
		drawables.add(options);
		drawables.add(quit);
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
