
package de.phillip.jumpandrun.utils;

import javafx.scene.image.Image;

public class ResourcePool {
	
	private static final String ATLAS_PATH = "/assets/sprites/";
	private static final String LEVEL_PATH = "/assets/levels/";
	public static final String OUTSIDE_ATLAS = "outside_sprites.png";
	public static final String PLAYER_ATLAS = "player_sprites.png";
	public static final String LEVEL_1 = "level_one_data.png";
	
	private static ResourcePool resourcePool;
	private Image background;

	
	private ResourcePool() {
		
	}
	
	public static ResourcePool getInstance() {
		if (resourcePool == null) {
			resourcePool = new ResourcePool();
		}
		return resourcePool;
	}

	public Image getBackground() {
		return background;
	}
	
	public void loadResources() {
		background = new Image(getClass().getResource("/assets/images/galaxy.jpg").toString());
	}
	
	public Image getSpriteAtlas(String atlas) {
		return new Image (getClass().getResource(ATLAS_PATH + atlas).toString());
	}
}
