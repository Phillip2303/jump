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

public class PauseMenu implements Menu{
	
	public static final int BUTTON_DEFAULT_SIZE = 42;
	public static final int BUTTON_SIZE = (int) (BUTTON_DEFAULT_SIZE * Game.SCALE);
	private static final int SOUND_V_OFFSET = (int) (148 * Game.SCALE);
	private static final int URM_V_OFFSET = (int) (340 * Game.SCALE);
	

	private List<Drawable> actors = new ArrayList<>();
	private Image pauseBackground;
	private Image soundButtonSprites;
	private Image urmButtonSprites;
	private CanvasButton musicButton;
	private CanvasButton sfxButton;
	private CanvasButton unpauseButton;
	private CanvasButton restartButton;
	private CanvasButton mainMenuButton;
	
	public PauseMenu() {
		pauseBackground = ResourcePool.getInstance().getPauseBackground();
		soundButtonSprites = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.SOUND_BUTTONS_SPRITES);
		urmButtonSprites = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.URM_BUTTONS_SPRITES);
		createButtons();
	}
	
	private void createButtons() {
		musicButton = new CanvasButton(soundButtonSprites, (int) ((Game.GAMEWIDTH / 2) + (int) (32 * Game.SCALE)),
				SOUND_V_OFFSET, BUTTON_DEFAULT_SIZE, BUTTON_DEFAULT_SIZE, 0);
		sfxButton = new CanvasButton(soundButtonSprites, (int) ((Game.GAMEWIDTH / 2) + (int) (32 * Game.SCALE)),
				SOUND_V_OFFSET + BUTTON_SIZE + 5, BUTTON_DEFAULT_SIZE,
				BUTTON_DEFAULT_SIZE, 1);
		unpauseButton = new CanvasButton(urmButtonSprites,
				(int) ((Game.GAMEWIDTH / 2) - BUTTON_SIZE) - (int) (32 * Game.SCALE), URM_V_OFFSET,
				BUTTON_DEFAULT_SIZE, BUTTON_DEFAULT_SIZE, 0);
		restartButton = new CanvasButton(urmButtonSprites, (int) ((Game.GAMEWIDTH / 2) - BUTTON_SIZE / 2),
				URM_V_OFFSET, BUTTON_DEFAULT_SIZE, BUTTON_DEFAULT_SIZE, 1);
		mainMenuButton = new CanvasButton(urmButtonSprites, (int) ((Game.GAMEWIDTH / 2) + (int) (32 * Game.SCALE)),
				URM_V_OFFSET, BUTTON_DEFAULT_SIZE, BUTTON_DEFAULT_SIZE, 2);
		actors.add(musicButton);
		actors.add(sfxButton);
		actors.add(unpauseButton);
		actors.add(restartButton);
		actors.add(mainMenuButton);
	}
		

	@Override
	public void prepareMenu(GraphicsContext gc) {
		gc.clearRect(0, 0, Game.GAMEWIDTH, Game.GAMEHEIGHT);
		gc.drawImage(pauseBackground, 0, 0, pauseBackground.getWidth(), pauseBackground.getHeight(),
				Game.GAMEWIDTH / 2 - ((pauseBackground.getWidth() * Game.SCALE) / 2), 60,
				pauseBackground.getWidth() * Game.SCALE, pauseBackground.getHeight() * Game.SCALE);
	}

	@Override
	public List<Drawable> getDrawables() {
		return actors;
	}

	@Override
	public void mouseMoved(double x, double y) {
		if (musicButton.contains(new Point2D(x, y))) {
			musicButton.setActive(true);
		} else {
			musicButton.setActive(false);
		}
		if (sfxButton.contains(new Point2D(x, y))) {
			sfxButton.setActive(true);
		} else {
			sfxButton.setActive(false);
		}
		if (unpauseButton.contains(new Point2D(x, y))) {
			unpauseButton.setActive(true);
		} else {
			unpauseButton.setActive(false);
		}
		if (restartButton.contains(new Point2D(x, y))) {
			restartButton.setActive(true);
		} else {
			restartButton.setActive(false);
		}
		if (mainMenuButton.contains(new Point2D(x, y))) {
			mainMenuButton.setActive(true);
		} else {
			mainMenuButton.setActive(false);
		}
	}

	@Override
	public void mousePressed() {
		if (musicButton.isActive()) {
			musicButton.setClicked(true);
		} else if (sfxButton.isActive()) {
			sfxButton.setClicked(true);
		} else if (unpauseButton.isActive()) {
			unpauseButton.setClicked(true);
		} else if (restartButton.isActive()) {
			restartButton.setClicked(true);
		} else if (mainMenuButton.isActive()) {
			mainMenuButton.setClicked(true);
		}
	}

	@Override
	public void mouseReleased() {
		if (musicButton.isActive()) {
			musicButton.setClicked(false);
			// FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_HIDE_MENU,
			// null));
		} else if (sfxButton.isActive()) {
			sfxButton.setClicked(false);
		} else if (unpauseButton.isActive()) {
			unpauseButton.setClicked(false);
			FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_HIDE_PAUSE_MENU, null));
		} else if (restartButton.isActive()) {
			restartButton.setClicked(false);
		} else if (mainMenuButton.isActive()) {
			mainMenuButton.setClicked(false);
			FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_SHOW_MENU, null));
		}
	}

}
