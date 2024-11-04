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

public class PauseMenu implements Menu{
	
	private double width;
	private double height;
	private List<Drawable> actors = new ArrayList<>();
	private Image pauseBackground;
	private Image soundButtonSprites;
	private Image urmButtonSprites;
	private CanvasButton musicButton;
	private CanvasButton sfxButton;
	private CanvasButton unpauseButton;
	private CanvasButton restartButton;
	private CanvasButton menuButton;
	
	public PauseMenu(double width, double height) {
		this.width = width;
		this.height = height;
		pauseBackground = ResourcePool.getInstance().getPauseBackground();
		soundButtonSprites = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.SOUND_BUTTONS_SPRITES);
		urmButtonSprites = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.URM_BUTTONS_SPRITES);
		createButtons();
	}
	
	private void createButtons() {
		musicButton = new CanvasButton(soundButtonSprites, (int) ((width / 2) + (int) (32 * Game.SCALE)),
				CanvasButton.SOUND_V_OFFSET, CanvasButton.PAUSE_SIZE, CanvasButton.PAUSE_SIZE, 0, GameState.PAUSING);
		sfxButton = new CanvasButton(soundButtonSprites, (int) ((width / 2) + (int) (32 * Game.SCALE)),
				CanvasButton.SOUND_V_OFFSET + CanvasButton.PAUSE_SIZE + 5, CanvasButton.PAUSE_SIZE,
				CanvasButton.PAUSE_SIZE, 1, GameState.PAUSING);
		unpauseButton = new CanvasButton(urmButtonSprites,
				(int) ((width / 2) - CanvasButton.PAUSE_SIZE) - (int) (32 * Game.SCALE), CanvasButton.URM_V_OFFSET,
				CanvasButton.PAUSE_SIZE, CanvasButton.PAUSE_SIZE, 0, GameState.PAUSING);
		restartButton = new CanvasButton(urmButtonSprites, (int) ((width / 2) - CanvasButton.PAUSE_SIZE / 2),
				CanvasButton.URM_V_OFFSET, CanvasButton.PAUSE_SIZE, CanvasButton.PAUSE_SIZE, 1, GameState.PAUSING);
		menuButton = new CanvasButton(urmButtonSprites, (int) ((width / 2) + (int) (32 * Game.SCALE)),
				CanvasButton.URM_V_OFFSET, CanvasButton.PAUSE_SIZE, CanvasButton.PAUSE_SIZE, 2, GameState.PAUSING);
		actors.add(musicButton);
		actors.add(sfxButton);
		actors.add(unpauseButton);
		actors.add(restartButton);
		actors.add(menuButton);
	}
		

	@Override
	public void prepareMenu(GraphicsContext gc) {
		gc.clearRect(0, 0, width, height);
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
		if (menuButton.contains(new Point2D(x, y))) {
			menuButton.setActive(true);
		} else {
			menuButton.setActive(false);
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
		} else if (menuButton.isActive()) {
			menuButton.setClicked(true);
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
		} else if (menuButton.isActive()) {
			menuButton.setClicked(false);
			FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_SHOW_MENU, null));
		}
	}

}
