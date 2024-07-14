
package de.phillip.utils;

import javafx.scene.image.Image;

public class ResourcePool {
	
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
}
