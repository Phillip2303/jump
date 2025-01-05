package de.phillip.jumpandrun.models;

import de.phillip.jumpandrun.Game;
import de.phillip.jumpandrun.events.FXEventBus;
import de.phillip.jumpandrun.events.GameEvent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class AnimatedWaterTile extends Tile {
	
	private Image[][] actionSprites = new Image[1][4];
	private int aniIndex;
	private int aniTic;
	private int aniSpeed = 25;

	public AnimatedWaterTile(double width, double height, Image imageSprite, int index) {
		super(width, height, imageSprite, index);
		createObjectSprites(imageSprite, actionSprites, Game.TILES_DEFAULT_SIZE, Game.TILES_DEFAULT_SIZE);
	}

	@Override
	public void drawToCanvas(GraphicsContext gc) {
		gc.drawImage(actionSprites[0][aniIndex], getDrawPosition().getX(), getDrawPosition().getY(),
				getWidth(), getHeight());
		drawHitbox(gc, Color.BLUE);
	}
	
	public void update() {
		updateAnimationTic();
	}

	private void updateAnimationTic() {
		aniTic++;
		if (aniTic >= aniSpeed) {
			aniTic = 0;
			aniIndex++;
		}
		if (aniIndex >= 3) {
			aniIndex = 0;
		}
	}
}
