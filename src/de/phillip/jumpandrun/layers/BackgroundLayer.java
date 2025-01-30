package de.phillip.jumpandrun.layers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.controllers.LevelManager;
import de.phillip.jumpandrun.models.CanvasLayer;
import de.phillip.jumpandrun.models.Cloud;
import de.phillip.jumpandrun.models.Drawable;
import de.phillip.jumpandrun.models.Rain;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class BackgroundLayer extends Canvas implements CanvasLayer {

	private static final int BIGCLOUD_WIDTH_DEFAULT = 448;
	private static final int BIGCLOUD_HEIGHT_DEFAULT = 101;
	private static final int BIGCLOUD_WIDTH = (int) (BIGCLOUD_WIDTH_DEFAULT * Game.SCALE);
	private static final int BIGCLOUD_HEIGHT = (int) (BIGCLOUD_HEIGHT_DEFAULT * Game.SCALE);
	private static final int SMALLCLOUD_WIDTH_DEFAULT = 74;
	private static final int SMALLCLOUD_HEIGHT_DEFAULT = 24;
	private static final int SMALLCLOUD_WIDTH = (int) (SMALLCLOUD_WIDTH_DEFAULT * Game.SCALE);
	private static final int SMALLCLOUD_HEIGHT = (int) (SMALLCLOUD_HEIGHT_DEFAULT * Game.SCALE);
	private static final double SMALLCLOUD_SPEED = 0.7;
	private static final double BIGCLOUD_SPEED = 0.3;

	private Image playingBg;
	private Image bigClouds;
	private Image smallClouds;
	private LevelManager levelManager;
	private List<Drawable> actors = new ArrayList<Drawable>();
	private Rain rain = new Rain();
	private Random random = new Random();
	private boolean drawRain;

	public BackgroundLayer(double width, double height, LevelManager levelManager) {
		super(width, height);
		this.levelManager = levelManager;
		playingBg = ResourcePool.getInstance().getPlayingBg();
		bigClouds = ResourcePool.getInstance().getBigClouds();
		smallClouds = ResourcePool.getInstance().getSmallClouds();
		createBigClouds(levelManager.getCloudNumbers().get(levelManager.getActiveLevel().getLevelNumber()).get(0));
		createSmallClouds(levelManager.getCloudNumbers().get(levelManager.getActiveLevel().getLevelNumber()).get(1));
		setLevelWithRain();

	}
	
	private void setLevelWithRain() {
		/*if (random.nextFloat() >= 0.8f)	{
			drawRain = true;
			actors.add(rain);
		}*/
		drawRain = true;
		actors.add(rain);
	}


	@Override
	public List<Drawable> getDrawables() {
		return actors;
	}

	@Override
	public void prepareLayer() {
		getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
		getGraphicsContext2D().drawImage(playingBg, 0, 0, getWidth(), getHeight());
	}

	@Override
	public void updateLayer(float secondsSinceLastFrame) {
		if (drawRain) {
			rain.update();
		}
	}

	@Override
	public void buildLevel(boolean nextLevel) {
		reset();
		setWidth(levelManager.getActiveLevel().getTilesInWidth() * Game.TILES_SIZE);
		setLevelWithRain();
		createBigClouds(levelManager.getCloudNumbers().get(levelManager.getActiveLevel().getLevelNumber()).get(0));
		createSmallClouds(levelManager.getCloudNumbers().get(levelManager.getActiveLevel().getLevelNumber()).get(1));
	}
	
	private void reset() {
		drawRain = false;
		actors.clear();
	}

	private void createBigClouds(int count) {
		for (int i = 0; i < count; i++) {
			Cloud bigCloud = new Cloud(i * BIGCLOUD_WIDTH, 204 * Game.SCALE, BIGCLOUD_WIDTH, BIGCLOUD_HEIGHT, bigClouds,
					BIGCLOUD_SPEED);
			actors.add(bigCloud);
		}
	}

	private void createSmallClouds(int count) {
		Random random = new Random();
		for (int i = 0; i < count; i++) {
			int yPos = random.nextInt((int) (100 * Game.SCALE)) + (int) (90 * Game.SCALE);
			Cloud smallCloud = new Cloud(4 * i * SMALLCLOUD_WIDTH, yPos, SMALLCLOUD_WIDTH, SMALLCLOUD_HEIGHT,
					smallClouds, SMALLCLOUD_SPEED);
			actors.add(smallCloud);
		}
	}

	@Override
	public void listenToEvents(boolean value) {
		// TODO Auto-generated method stub

	}

}
