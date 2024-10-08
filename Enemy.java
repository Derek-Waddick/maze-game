package test;

import java.util.Random;

public class Enemy {
    private int x;
    private int y;
    private int dx; // Change in x (horizontal movement)
    private int dy; // Change in y (vertical movement)
    private Random rand = new Random();
    private static final int HEIGHT = 16;
    private static final int WIDTH = 16;
    private static final char WALL = '#';
    private static char[][] maze = new char[HEIGHT][WIDTH];

    public Enemy(int x, int y, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public static void copyMaze() {
    	for (int i = 0; i < 16; i++) 
		{
			for (int j = 0; j < 16; j++) 
			{
				maze[i][j] = Maze.returnChar(i, j);
			}
		}
    }

    public static void setEnemyPosition(Enemy enemy, int x, int y) {
        enemy.setX(x);
        System.out.println("x: " + x);
        enemy.setY(y);
        System.out.println("y: " + y);
    }

    // setters used in move()
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    // moves enemy
    public void move() {
        // had some stack overflows, this is some jank code to prevent that
        int attemptCount = 0;
        while (attemptCount < 4) { // Limit the number of attempts to avoid infinite loopage
            int newX = x + dx;
            int newY = y + dy;

            // Check if the new position is within the maze boundaries and not a wall
            if (isValidMove(newX, newY)) {
                x = newX;
                y = newY;
                break; // Exit the loop once a valid move is made
            } else {
                // If hitting a wall, pick a new random direction
                pickRandomDirection();
                attemptCount++;
            }
        }
    }
    
    private int[] shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            // Simple swap
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
        return array;
    }

    private void pickRandomDirection() {
        int[] directions = {0, 1, 2, 3}; // 0 = right, 1 = down, 2 = left, 3 = up
        int[] shuffleDirections = shuffleArray(directions);

        for (int i = 0; i < 4; i++) {
            int direction = shuffleDirections[i];
            int testX = x + Maze.dx[direction];
            int testY = y + Maze.dy[direction];

            if (isValidMove(testX, testY)) {
                dx = Maze.dx[direction];
                dy = Maze.dy[direction];
                return;
            }
        }
    }
    private boolean isValidMove(int x, int y) {
        // Check if the new position is within the maze boundaries and not a wall
        return x >= 0 && x < HEIGHT && y >= 0 && y < WIDTH && maze[x][y] != WALL;
    }
}
