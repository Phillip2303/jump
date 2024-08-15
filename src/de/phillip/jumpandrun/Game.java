package de.phillip.jumpandrun;

import de.phillip.jumpandrun.controllers.GameController;
import de.phillip.jumpandrun.utils.KeyPolling;
import de.phillip.jumpandrun.utils.ResourcePool;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Game extends Application {
	
	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.5f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAMEWIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAMEHEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	
	private GameController gameController;

	@Override
	public void start(Stage primaryStage) throws Exception {
		ResourcePool.getInstance().loadResources();
		primaryStage.setResizable(false);
		Scene scene = createContent();
		primaryStage.setScene(scene);
		KeyPolling.getInstance().pollScene(scene);
		primaryStage.show();
		gameController.startGame();
	}
	
	private Scene createContent() {
		StackPane stackPane = new StackPane();
		ScrollPane scrollPane = new ScrollPane(stackPane);
		gameController = new GameController(scrollPane);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		Scene scene = new Scene(scrollPane, GAMEWIDTH, GAMEHEIGHT);
		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
				case A: 
					scrollPane.setHvalue(scrollPane.getHvalue() - 2 / scrollPane.getWidth());
					break;
				case D:
					scrollPane.setHvalue(scrollPane.getHvalue() + 2 / scrollPane.getWidth());
					break;
			default:
				break;
			}
		});
		return scene;
		
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
