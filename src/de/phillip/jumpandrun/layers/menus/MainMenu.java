package de.phillip.jumpandrun.layers.menus;

import java.util.ArrayList;
import java.util.List;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import de.phillip.jumpandrun.models.CanvasButton;
import de.phillip.jumpandrun.models.Drawable;
import de.phillip.jumpandrun.models.Menu;
import de.phillip.jumpandrun.utils.GameState;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class MainMenu implements Menu {
	
	private Image menuBackground;
	private Image buttonSprites;
	private List<Drawable> drawables = new ArrayList<>();
	private CanvasButton play;
	private CanvasButton options;
	private CanvasButton quit;
	private double width;
	private double height;
	
	public MainMenu(double width, double height) {
		this.width = width;
		this.height = height;
		menuBackground = ResourcePool.getInstance().getMenuBackground();
		buttonSprites = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.BUTTON_SPRITES);
		createButtons();
	}
	
	private void createButtons() {
		play = new CanvasButton(buttonSprites, (int) (width / 2 - CanvasButton.MENU_WIDTH / 2),
				CanvasButton.MENU_V_OFFSET, CanvasButton.MENU_WIDTH, CanvasButton.MENU_HEIGHT, 0, GameState.MENU);
		options = new CanvasButton(buttonSprites, (int) (width / 2 - CanvasButton.MENU_WIDTH / 2),
				CanvasButton.MENU_V_OFFSET + CanvasButton.MENU_HEIGHT, CanvasButton.MENU_WIDTH,
				CanvasButton.MENU_HEIGHT, 1, GameState.MENU);
		quit = new CanvasButton(buttonSprites, (int) (width / 2 - CanvasButton.MENU_WIDTH / 2),
				CanvasButton.MENU_V_OFFSET + 2 * CanvasButton.MENU_HEIGHT, CanvasButton.MENU_WIDTH,
				CanvasButton.MENU_HEIGHT, 2, GameState.MENU);
		drawables.add(play);
		drawables.add(options);
		drawables.add(quit);
	}

	@Override
	public void prepareMenu(GraphicsContext gc) {
		gc.clearRect(0, 0, width, height);
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, width, height);
		gc.drawImage(menuBackground, 0, 0, menuBackground.getWidth(), menuBackground.getHeight(),
				Game.GAMEWIDTH / 2 - ((menuBackground.getWidth() * Game.SCALE) / 2), 80,
				menuBackground.getWidth() * Game.SCALE, menuBackground.getHeight() * Game.SCALE);
	}

	@Override
	public List<Drawable> getDrawables() {
		return drawables;
	}
	
	public void mouseReleased() {
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

	public void mousePressed() {
		if (play.isActive()) {
			play.setClicked(true);
		} else if (options.isActive()) {
			options.setClicked(true);
		} else if (quit.isActive()) {
			quit.setClicked(true);
		}
	}

	public void mouseMoved(double x, double y) {
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

}
