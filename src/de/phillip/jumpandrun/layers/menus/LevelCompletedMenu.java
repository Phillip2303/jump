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

public class LevelCompletedMenu implements Menu{
	
	private static final int URM_V_OFFSET = (int) (196 * Game.SCALE);
	private static final int BG_V_OFFSET = (int) (63 * Game.SCALE);
	
	private List<Drawable> actors = new ArrayList<>();
	private Image levelCompletedBackground;
	private Image urmButtonSprites;
	
	private CanvasButton nextLevelButton;
	private CanvasButton mainMenuButton;
	
	public LevelCompletedMenu() {
		levelCompletedBackground = ResourcePool.getInstance().getLevelCompletedBackground();
		urmButtonSprites = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.URM_BUTTONS_SPRITES);
		createButtons();
	}

	private void createButtons() {
		mainMenuButton = new CanvasButton(urmButtonSprites,
				(int) ((Game.GAMEWIDTH / 2) - PauseMenu.BUTTON_SIZE) - (int) (32 * Game.SCALE), URM_V_OFFSET,
				PauseMenu.BUTTON_DEFAULT_SIZE, PauseMenu.BUTTON_DEFAULT_SIZE, 2);
		nextLevelButton = new CanvasButton(urmButtonSprites, (int) ((Game.GAMEWIDTH / 2) + (int) (32 * Game.SCALE)),
				URM_V_OFFSET, PauseMenu.BUTTON_DEFAULT_SIZE, PauseMenu.BUTTON_DEFAULT_SIZE, 0);
		actors.add(mainMenuButton);
		actors.add(nextLevelButton);
		
	}

	@Override
	public void prepareMenu(GraphicsContext gc) {
		gc.clearRect(0, 0, Game.GAMEWIDTH, Game.GAMEHEIGHT);
		gc.drawImage(levelCompletedBackground, 0, 0, levelCompletedBackground.getWidth(), levelCompletedBackground.getHeight(),
				Game.GAMEWIDTH / 2 - ((levelCompletedBackground.getWidth() * Game.SCALE) / 2), BG_V_OFFSET,
				levelCompletedBackground.getWidth() * Game.SCALE, levelCompletedBackground.getHeight() * Game.SCALE);
	}

	@Override
	public List<Drawable> getDrawables() {
		return actors;
	}

	@Override
	public void mouseMoved(double x, double y) {
		if (mainMenuButton.contains(new Point2D(x, y))) {
			mainMenuButton.setActive(true);
		} else {
			mainMenuButton.setActive(false);
		}
		if (nextLevelButton.contains(new Point2D(x, y))) {
			nextLevelButton.setActive(true);
		} else {
			nextLevelButton.setActive(false);
		}
		
	}

	@Override
	public void mousePressed() {
		if (mainMenuButton.isActive()) {
			mainMenuButton.setClicked(true);
		} else if (nextLevelButton.isActive()) {
			nextLevelButton.setClicked(true);
		}
		
	}

	@Override
	public void mouseReleased() {
		if (mainMenuButton.isActive()) {
			mainMenuButton.setClicked(false);
			FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_SHOW_MENU, null));
		} else if (nextLevelButton.isActive()) {
			nextLevelButton.setClicked(false);
			FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_NEXT_LEVEL, null));
		}
		
	}

}
