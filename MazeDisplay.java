package test;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

public class MazeDisplay extends Application {

	private char[][] mayze = new char[16][16];
	private int x = 0;
	private int y = 1;
	private int moves = 0;

	private void initialize() {
		Maze.run();

		for (int i = 0; i < 16; i++) 
		{
			for (int j = 0; j < 16; j++) 
			{
				mayze[i][j] = Maze.returnChar(i, j);
			}
		}
	}
	
	private Group updateGroup(Player player, Group group) {
		//clear group
		group.getChildren().clear();
		//recreate gridpane
		GridPane gridPane = new GridPane();
				
		for (int i = 0; i < 16; i++) 
		{
			for (int j = 0; j < 16; j++) 
			{
				mayze[i][j] = Maze.returnChar(i, j);
			}
		}
		
		for (int i = 0; i < /*mayze.length*/16; i++) 
		{
			for (int j = 0; j < /*mayze[i].length*/16; j++) 
			{
				Image image;
				if (player.getX() == i && player.getY() == j)
				{
					//player image
					image = new Image("file:/C:/Users/derek/eclipse-workspace/CS 316/Testing/src/images/player!.png/");
				}
				else if (mayze[i][j] == '#') 
				{
					//wall image
					image = new Image("file:/C:/Users/derek/eclipse-workspace/CS 316/Testing/src/images/wall3.jpg");
				} 
				else if (mayze[i][j] == 'X')
				{
					//bomb image
					image = new Image("file:/C:/Users/derek/eclipse-workspace/CS 316/Testing/src/images/bomb!.png/");
				}
				else if (mayze[i][j] == '&')
				{
					//coin / nugget image
					image = new Image("file:/C:/Users/derek/eclipse-workspace/CS 316/Testing/src/images/coin!.png/");
				}
				else if (mayze[i][j] == 'E')
				{
					//finish image
					image = new Image("file:/C:/Users/derek/eclipse-workspace/CS 316/Testing/src/images/CHECKERED.jpg/");
				}
				else 
				{
					//path image
					image = new Image("file:/C:/Users/derek/eclipse-workspace/CS 316/Testing/src/images/path3.jpg");
				}
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(29);
				imageView.setFitHeight(29);
				gridPane.add(imageView, j, i);
			}
		}
		group.getChildren().add(gridPane);
		return group;
	}

	@Override
	public void start(Stage primaryStage) {
		initialize();
		Player player = new Player(x, y, mayze, 16, 16, '#');
		
		for (int i = 0; i < 16; i++) 
		{
			for (int j = 0; j < 16; j++) 
			{
				mayze[i][j] = Maze.returnChar(i, j);
			}
		}

		Group group = new Group();
		GridPane gridPane = new GridPane();
		group.getChildren().add(gridPane);
		
		for (int i = 0; i < /*maze.length*/16; i++) 
		{
			for (int j = 0; j < /*mayze[i].length*/16; j++) 
			{
				Image image;
				if (player.getX() == i && player.getY() == j)
				{
					//player image
					image = new Image("file:/C:/Users/derek/eclipse-workspace/CS 316/Testing/src/images/player!.png/");
				}
				else if (mayze[i][j] == '#') 
				{
					//wall image
					image = new Image("file:/C:/Users/derek/eclipse-workspace/CS 316/Testing/src/images/wall3.jpg");
				} 
				else if (mayze[i][j] == 'X')
				{
					//bomb image
					image = new Image("file:/C:/Users/derek/eclipse-workspace/CS 316/Testing/src/images/bomb!.png/");
				}
				else if (mayze[i][j] == '&')
				{
					//coin / nugget image
					image = new Image("file:/C:/Users/derek/eclipse-workspace/CS 316/Testing/src/images/coin!.png/");
				}
				else if (mayze[i][j] == 'E')
				{
					//finish image
					image = new Image("file:/C:/Users/derek/eclipse-workspace/CS 316/Testing/src/images/CHECKERED.jpg/");
				}
				else 
				{
					//path image
					image = new Image("file:/C:/Users/derek/eclipse-workspace/CS 316/Testing/src/images/path3.jpg");
				}
				ImageView imageView = new ImageView(image);
				imageView.setFitWidth(29);
				imageView.setFitHeight(29);
				gridPane.add(imageView, j, i);
			}
		}
		Scene scene = new Scene(group, mayze[0].length * 29, mayze.length * 29); //maybe change scene here?
		scene.setOnKeyPressed(
				new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						x = player.getX();
						y = player.getY();
						switch(event.getCode()) {
						case W: case UP:
							Maze.checkMove(player, x - 1, y);
							//Maze.printMazeWithPlayer(player);
							break;
						case A: case LEFT:
							Maze.checkMove(player, x, y - 1);
							//Maze.printMazeWithPlayer(player);
							break;
						case S: case DOWN:
							Maze.checkMove(player, x + 1, y);
							//Maze.printMazeWithPlayer(player);
							break;
						case D: case RIGHT:
							Maze.checkMove(player, x, y + 1);
							//Maze.printMazeWithPlayer(player);
							break;
						default:
							break;
						}
						updateGroup(player, group);
					}
				});
		Image icon = new Image("file:/C:/Users/derek/eclipse-workspace/CS 316/Testing/src/images/monster.png/");
		primaryStage.getIcons().add(icon);
		primaryStage.setTitle("Maze Game");
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}