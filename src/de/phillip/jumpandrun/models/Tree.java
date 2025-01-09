package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.models.Actor.Direction;
import de.phillip.jumpandrun.models.GameObject.Type;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Tree extends GameObject {
	
	private static final int DEFAULT_WIDTH_1 = 39;
	private static final int DEFAULT_WIDTH_2 = 62;
	private static final int DEFAULT_WIDTH_3 = 62;
	private static final int DEFAULT_HEIGHT_1 = 92;
	private static final int DEFAULT_HEIGHT_2 = 54;
	private static final int DEFAULT_HEIGHT_3 = 54;
	
	public static final int WIDTH_1 = (int) (DEFAULT_WIDTH_1 * Game.SCALE);
	public static final int WIDTH_2 = (int) (DEFAULT_WIDTH_2 * Game.SCALE);
	public static final int WIDTH_3 = (int) (DEFAULT_WIDTH_3 * Game.SCALE);
	public static final int HEIGHT_1 = (int) (DEFAULT_HEIGHT_1 * Game.SCALE);
	public static final int HEIGHT_2 = (int) (DEFAULT_HEIGHT_2 * Game.SCALE);
	public static final int HEIGHT_3 = (int) (DEFAULT_HEIGHT_3 * Game.SCALE);
	
	public static final int X_OFFSET_1 = (Game.TILES_SIZE / 2) - (WIDTH_1 / 2);
	public static final int X_OFFSET_2 = (int) (Game.TILES_SIZE / 2.5);
	public static final int X_OFFSET_3 = (int) (Game.TILES_SIZE / 1.65);
	public static final int Y_OFFSET_1 = Game.TILES_SIZE * 2 - HEIGHT_1;
	public static final int Y_OFFSET_2 = (int) (Game.TILES_SIZE / 1.25f) - HEIGHT_2;
	public static final int Y_OFFSET_3 = (int) (Game.TILES_SIZE / 1.25f) - HEIGHT_3;
	
	private Image[][] treeSprites = new Image[1][4];
	private int flipWidth;
	
	public Tree(double width, double height, Type type) {
		super(width, height, type);
		doAnimation(true);
		initHitbox(0, 0, width, height);
		switch (type) {
		case TREE_1:
			flipWidth = 1;
			createObjectSprites(ResourcePool.getInstance().getSpriteAtlas(ResourcePool.TREE_1_SPRITES), treeSprites, DEFAULT_WIDTH_1, DEFAULT_HEIGHT_1);
			break;
		case TREE_2:
			flipWidth = 1;
			createObjectSprites(ResourcePool.getInstance().getSpriteAtlas(ResourcePool.TREE_2_SPRITES), treeSprites, DEFAULT_WIDTH_2, DEFAULT_HEIGHT_2);
			break;
		case TREE_3:
			flipWidth = -1;
			createObjectSprites(ResourcePool.getInstance().getSpriteAtlas(ResourcePool.TREE_2_SPRITES), treeSprites, DEFAULT_WIDTH_3, DEFAULT_HEIGHT_3);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void drawToCanvas(GraphicsContext gc) {
		if (isActive()) {
			gc.drawImage(treeSprites[getSpriteIndex()][getAniIndex()], getDrawPosition().getX(), getDrawPosition().getY(),
					getWidth() * flipWidth, getHeight());
		}
	}

}
