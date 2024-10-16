package de.phillip.jumpandrun.layers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.controllers.EnemyManager;
import de.phillip.jumpandrun.controllers.LevelManager;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import de.phillip.jumpandrun.models.Actor;
import de.phillip.jumpandrun.models.CanvasLayer;
import de.phillip.jumpandrun.models.Drawable;
import de.phillip.jumpandrun.models.Player;
import de.phillip.jumpandrun.models.Tile;
import de.phillip.jumpandrun.utils.GameState;
import de.phillip.jumpandrun.utils.KeyPolling;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class ActionLayer extends Canvas implements CanvasLayer {

	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private List<Drawable> actors = new ArrayList<Drawable>();
	private Player player;
	private KeyPolling kp = KeyPolling.getInstance();
	private boolean isListening = true;
	private boolean hasStarted = true;

	public ActionLayer(int width, int height, LevelManager levelManager) {
		super(width, height);
		this.levelManager = levelManager;
		createLevel();
		createPlayer();
		enemyManager = new EnemyManager(player);
		player.setEnemyManager(enemyManager);
		enemyManager.createEnemies(1);
		enemyManager.setTiles(actors.stream().filter(actor -> actor instanceof Tile).map(actor -> (Tile) actor)
				.collect(Collectors.toList()));
		enemyManager.setLevelWidth((int) getWidth());
		actors.addAll(enemyManager.getEnemies());
	}

	private void createLevel() {
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
			for (int i = 0; i < levelManager.getActiveLevel().getTilesInWidth(); i++) {
				int index = levelManager.getActiveLevel().getSpriteIndex(i, j);
				// System.out.println("Sprite Index: " + index);
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
		player.setTiles(actors.stream().filter(actor -> actor instanceof Tile).map(actor -> (Tile) actor)
				.collect(Collectors.toList()));
		player.setDrawPosition(215, 196);
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
		if (isListening) {
			updateKeyEvents(secondsSinceLastFrame);
		}
		player.update();
		enemyManager.update();
	}

	@Override
	public void resetGame() {

	}

	@Override
	public void listenToEvents(boolean value) {
		isListening = value;
	}

	private void updateKeyEvents(float secondsSinceLastFrame) {
		if (kp.isDown(KeyCode.A)) {
			if (player.canMoveHere(-Player.SPEED)) {
				player.setPlayerAction(Player.RUNNING);
				FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_MOVE_LEFT, player.getHitBox().getMinX()));
			}
		}
		if (kp.isDown(KeyCode.D)) {
			if (player.canMoveHere(Player.SPEED)) {
				player.setPlayerAction(Player.RUNNING);
				FXEventBus.getInstance()
						.fireEvent(new GameEvent(GameEvent.JR_MOVE_RIGHT, player.getHitBox().getMaxX()));
			}
		}
		if (kp.isDown(KeyCode.J)) {
			// System.out.println("Player Action Jump");
			if (player.canJumpHere(Player.JUMPSPEED)) {
				// System.out.println("Can Jump");
				player.setPlayerAction(Player.JUMPING);
			}
		}
		if (kp.isDown(KeyCode.E)) {
			player.setPlayerAction(Player.ATTACK);
		}
		if (kp.isDown(KeyCode.ESCAPE)) {
			// GameState.state = GameState.MENU;
			FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_SHOW_MENU, null));
		}
		if (kp.isDown(KeyCode.P)) {
			FXEventBus.getInstance().fireEvent(new GameEvent(GameEvent.JR_SHOW_PAUSE_MENU, null));
		}
		if (!kp.keysPressed()) {
			if (hasStarted) {
				player.checkFalling();
				hasStarted = false;
			} else {
				player.setPlayerAction(Player.IDLE);
			}
		}
	}
}
