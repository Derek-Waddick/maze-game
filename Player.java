package test;

public class Player {
    private int x;
    private int y;
    private char[][] maze;
    private final int HEIGHT;
    private final int WIDTH;
    private final char WALL;
    private int moves = 0;

    public Player(int x, int y, char[][] maze, int HEIGHT, int WIDTH, char WALL) {
        this.x = x;
        this.y = y;
        this.maze = maze;
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        this.WALL = WALL;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int dx, int dy) {

        // Check if the move is valid before updating the position
        int newX = x + dx;
        int newY = y + dy;

        if (isValidMove(newX, newY)) 
        {
            x = newX;
            y = newY;
            moves++;
        }
    }
    
    public int getMoves() {
    	return moves;
    }

    private boolean isValidMove(int x, int y) {
        // Check if the new position is within the maze boundaries and not a wall
        return x >= 0 && x < HEIGHT && y >= 0 && y < WIDTH && maze[x][y] != WALL;
    }
}