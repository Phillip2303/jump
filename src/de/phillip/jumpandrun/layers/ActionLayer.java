package de.phillip.jumpandrun.layers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.controllers.LevelManager;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import de.phillip.jumpandrun.models.Actor;
import de.phillip.jumpandrun.models.CanvasLayer;
import de.phillip.jumpandrun.models.Drawable;
import de.phillip.jumpandrun.models.Player;
import de.phillip.jumpandrun.models.Tile;
import de.phillip.jumpandrun.utils.KeyPolling;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class ActionLayer extends Canvas implements CanvasLayer {
	
	private LevelManager levelManager;
	private List<Drawable> actors = new ArrayList<Drawable>();
	private Player player;
	private KeyPolling kp = KeyPolling.getInstance();
	
	public ActionLayer(int width, int height, LevelManager levelManager) {
		super(width, height);
		this.levelManager = levelManager;
		createLevel();
		createPlayer();
	}

	private void createLevel() {
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) { 
			for (int i = 0; i < levelManager.getActiveLevel().getTilesInWidth(); i++) {
				int index = levelManager.getActiveLevel().getSpriteIndex(i, j);
				//System.out.println("Sprite Index: " + index);
				Image image = levelManager.getLevelTiles()[index];
				Tile tile = new Tile(Game.TILES_SIZE, Game.TILES_SIZE, image, index);
				tile.setDrawPosition(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
				actors.add(tile);
			}
		}
	}
	
	private void createPlayer() {
		Image image = ResourcePool.getInstance().getSpriteAtlas(ResourcePool.PLAYER_ATLAS);
		player = new Player(Player.DEFAULT_WIDTH * Game.SCALE, Player.DEFAULT_HEIGHT * Game.SCALE, image);
		player.setTiles(actors.stream().filter(actor -> actor instanceof Tile).map(actor -> (Tile) actor).collect(Collectors.toList()));
		player.setDrawPosition(200, 196);
		player.setLevelWidth((int) getWidth());
		actors.add(player);
	}

	@Override
	public List<Drawable> getDrawables() {
		return actors;
	}

	@Override
	public void prepareLayer() {
		getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
	}

	@Override
	public void updateLayer(float secondsSinceLastFrame) {
		if (kp.isDown(KeyCode.A)) {
			if (player.canMoveHere(-Player.SPEED))  {
				player.setPlayerAction(Player.RUNNING);
				FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_MOVE_LEFT, null));
			}
		} 
		if (kp.isDown(KeyCode.D)) {
			if (player.canMoveHere(Player.SPEED)) {
				player.setPlayerAction(Player.RUNNING);
				FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_MOVE_RIGHT, null));
			}
		}
		if (kp.isDown(KeyCode.J)) {
			System.out.println("Player Action Jump");
			if (player.canJumpHere(Player.JUMPSPEED)) {
				System.out.println("Can Jump");
				player.setPlayerAction(Player.JUMPING);
			}
		}
		if (!kp.keysPressed()) {
			player.setPlayerAction(Player.IDLE);
		}
		player.update();
	}
	
	@Override
	public void resetGame() {

	}

}
