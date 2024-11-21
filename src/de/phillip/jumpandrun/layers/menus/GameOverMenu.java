package de.phillip.jumpandrun.layers.menus;

import java.util.ArrayList;
import java.util.List;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import de.phillip.jumpandrun.models.CanvasButton;
import de.phillip.jumpandrun.models.Drawable;
import de.phillip.jumpandrun.models.Menu;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class GameOverMenu implements Menu{
	
	private static final int URM_V_OFFSET = (int) (166 * Game.SCALE);
	private static final int BG_V_OFFSET = (int) (63 * Game.SCALE);
	
	private List<Drawable> actors = new ArrayList<>();
	private Image gameOverBackground;
	private Image urmButtonSprites;
	
	private CanvasButton restartButton;
	private CanvasButton mainMenuButton;
	
	public GameOverMenu() {
		gameOverBackground = ResourcePool.getInstance().getGameOverBackground();
		urmButtonSprites = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.URM_BUTTONS_SPRITES);
		createButtons();
	}

	private void createButtons() {
		mainMenuButton = new CanvasButton(urmButtonSprites,
				(int) ((Game.GAMEWIDTH / 2) - PauseMenu.BUTTON_SIZE) - (int) (32 * Game.SCALE), URM_V_OFFSET,
				PauseMenu.BUTTON_DEFAULT_SIZE, PauseMenu.BUTTON_DEFAULT_SIZE, 2);
		restartButton = new CanvasButton(urmButtonSprites, (int) ((Game.GAMEWIDTH / 2) + (int) (32 * Game.SCALE)),
				URM_V_OFFSET, PauseMenu.BUTTON_DEFAULT_SIZE, PauseMenu.BUTTON_DEFAULT_SIZE, 0);
		actors.add(mainMenuButton);
		actors.add(restartButton);
	}

	@Override
	public void prepareMenu(GraphicsContext gc) {
		gc.clearRect(0, 0, Game.GAMEWIDTH, Game.GAMEHEIGHT);
		gc.drawImage(gameOverBackground, 0, 0, gameOverBackground.getWidth(), gameOverBackground.getHeight(),
				Game.GAMEWIDTH / 2 - ((gameOverBackground.getWidth() * Game.SCALE) / 2), BG_V_OFFSET,
				gameOverBackground.getWidth() * Game.SCALE, gameOverBackground.getHeight() * Game.SCALE);
		
	}

	@Override
	public List<Drawable> getDrawables() {
		return actors;
	}

	@Override
	public void mousePressed() {
		if (mainMenuButton.isActive()) {
			mainMenuButton.setClicked(true);
		} else if (restartButton.isActive()) {
			restartButton.setClicked(true);
		}
	}

	@Override
	public void mouseReleased() {
		if (mainMenuButton.isActive()) {
			mainMenuButton.setClicked(false);
			FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_RESET_H_OFFSET, null));
			FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_RESET_GAME, Boolean.FALSE));
			FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_SHOW_MENU, null));
		} else if (restartButton.isActive()) {
			restartButton.setClicked(false);
			FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_RESET_H_OFFSET, null));
			FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_RESET_GAME, Boolean.TRUE));
		}
	}

	@Override
	public void mouseMoved(double x, double y) {
		if (mainMenuButton.contains(new Point2D(x, y))) {
			mainMenuButton.setActive(true);
		} else {
			mainMenuButton.setActive(false);
		}
		if (restartButton.contains(new Point2D(x, y))) {
			restartButton.setActive(true);
		} else {
			restartButton.setActive(false);
		}
		
	}

}
