package de.phillip.jumpandrun.layers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.controllers.LevelManager;
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
			for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
				int index = levelManager.getActiveLevel().getSpriteIndex(i, j);
				System.out.println("Sprite Index: " + index);
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
		player.setDrawPosition(200, 200);
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
		Point2D oldPosition = player.getDrawPosition();
		if (kp.isDown(KeyCode.A)) {
			if (player.canMoveHere(-Player.SPEED))  {
				player.setPlayerAction(Player.RUNNING);
			}
		} 
		if (kp.isDown(KeyCode.D)) {
			if (player.canMoveHere(Player.SPEED)) {
				player.setPlayerAction(Player.RUNNING);
			}
		}  
		if (kp.isDown(KeyCode.W)) {
			player.setDrawPosition(player.getDrawPosition().getX(), player.getDrawPosition().getY() - Player.SPEED);
			if (canMoveHere(player.getHitBox())) {
				player.setPlayerAction(Player.RUNNING);
			} else {
				player.setDrawPosition(oldPosition.getX(), oldPosition.getY());
			}
		}  
		if (kp.isDown(KeyCode.S)) {
			player.setDrawPosition(player.getDrawPosition().getX(), player.getDrawPosition().getY() + Player.SPEED);
			if (canMoveHere(player.getHitBox())) {
				player.setPlayerAction(Player.RUNNING);
			} else {
				player.setDrawPosition(oldPosition.getX(), oldPosition.getY());
			}
		} 
		if (kp.isDown(KeyCode.SPACE)) {
			if (player.canJumpHere(Player.JUMPSPEED)) {
				System.out.println("Player Action Jump");
				player.setPlayerAction(Player.JUMPING);
			}
		}
		if (!kp.keysPressed()) {
			player.setPlayerAction(Player.IDLE);
		}
		player.update();
	}

	private boolean canMoveHere(Rectangle2D playerRect) {
		List<Tile> tiles = actors.stream().filter(actor -> actor instanceof Tile).map(actor -> (Tile) actor).collect(Collectors.toList());
		for (Tile tile: tiles) {
			Rectangle2D hitBox = tile.getHitBox();
			if (tile.isSolid() && hitBox.intersects(playerRect)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void resetGame() {

	}

}
