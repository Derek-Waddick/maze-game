package test;

import java.util.Random;

public class Maze {
    private static final int WIDTH = 16;
    private static final int HEIGHT = 16;
    private static final char WALL = '#';
    private static final char PATH = '.';
    //private static final char START = 'S';
    private static final char END = 'E';
    private static final char EXPLOSIVE = 'X';
    private static final char NUGGET = '&';
    private static int score = 0;
    private static Random rand = new Random();

    static int dx[] = {-1, 0, 1, 0}; //These values represent changes in coordinates to move in different directions. dx0 = right, dx1 = down, dx2 = left dx3 = up
    static int dy[] = {0, -1, 0, 1};  //Same as above dy0 = right, dy1 = down, dy2 = left dy3 = up

    private static char[][] maze = new char[HEIGHT][WIDTH]; //stores the maze itself

  //initializes the maze as entirely walls
    private static void initializeMaze() { 
        for (int i = 0; i < WIDTH; i++) 
        {
            for (int j = 0; j < HEIGHT; j++) 
            {
                maze[j][i] = WALL; 
            }
        }
    }

    //This tells the path generator to leave the outer rim alone as to not have any holes in the border of the maze.
    private static boolean isValid(int x, int y) {
        return x >= 1 && x < HEIGHT -1 && y >= 1 && y < WIDTH - 1 && maze[x][y] == WALL;
    }

    //Carve the path that makes the maze from the block of walls it initialized as
    private static void generateMaze(int x, int y) {
        maze[x][y] = PATH;

        //set direction values for inside the loop
        int[] directions = {0, 1, 2, 3};
        for (int i = 0; i < 4; i++) {

        //pick a random direction
            int randomIndex = rand.nextInt(4);
            //store it then shuffle the direction
            int temp = directions[i];
            directions[i] = directions[randomIndex];
            directions[randomIndex] = temp;
        }



        //This for loop does the actual path building
        //iterate once for every direction
        for (int i = 0; i < 4; i++) {
        //retrieve current direction, which was shuffled in the above for loop
            int direction = directions[i];
            //This part takes the direction and draws a straight line for two array values
            int nextX = x + dx[direction] * 2;
            int nextY = y + dy[direction] * 2;

            //This makes sure we don't jump out of the maze, and carves the path
            if (isValid(nextX, nextY)) {
            //find the point of the jump at the end of the previous for loop
                int wallX = x + dx[direction];
                int wallY = y + dy[direction];
                //turn the walls into paths
                maze[wallX][wallY] = PATH;
                //recurse from current coordinates till complete
                generateMaze(nextX, nextY);
            }
        }
    }

    //This is where we throw in the explosives, I set the value at a high value for chain testing but this can be toyed with
    //For simplicity sake, it can only turn a path into an explosive/nugget
    private static void placeItems() {
    	
    	//Place explosives
        for (int i = 0; i < HEIGHT; i++) 
        {
            for (int j = 0; j < WIDTH; j++) 
            {
                if (maze[i][j] == PATH && rand.nextDouble() < 0.08) 
                {
                    maze[i][j] = EXPLOSIVE;
                }
            }
        }
        
        //place gold nuggets
        for (int i = 0; i < HEIGHT; i++) 
        {
            for (int j = 0; j < WIDTH; j++) 
            {
                if (maze[i][j] == PATH && rand.nextDouble() < 0.05) 
                {
                    maze[i][j] = NUGGET;
                }
            }
        }
    }
    
    private static void explode(int x, int y) {
        maze[x][y] = PATH; // Explode the current explosive turning it into a path

        for (int i = 0; i < 4; i++) 
        {
            int newX = x + dx[i];
            int newY = y + dy[i];

            if (newX >= 0 && newX < HEIGHT && newY >= 0 && newY < WIDTH) 
            {
                char target = maze[newX][newY];
                if (target == EXPLOSIVE) 
                {
                    explode(newX, newY); // Recursively explode adjacent explosives
                } 
                else if (target == WALL) 
                {
                    maze[newX][newY] = PATH; // Turn adjacent walls into paths
                }
            }
        }
    }
    
    public static void printMazeWithPlayer(Player player) {
        for (int i = 0; i < HEIGHT; i++) 
        {
            for (int j = 0; j < WIDTH; j++) 
            {
                if (i == player.getX() && j == player.getY()) 
                {
                    System.out.print("P ");
                } 
                else 
                {
                    System.out.print(maze[i][j] + " ");
                }
                
            }
            System.out.println();
        }
    }
    
    public static void updateMazeWithPlayer(Player player, int lastX, int lastY) {
    	for (int i = 0; i < HEIGHT; i++) 
    	{
            for (int j = 0; j < WIDTH; j++) 
            {
            	if (i == player.getX() && j == player.getY()) 
            	{
                    maze[i][j] = 'P';
                } 
            }
        }
    	maze[lastX][lastY] = '.';
    }
    
    public static void printMaze() {
    	for (int i = 0; i < HEIGHT; i++) 
    	{
            for (int j = 0; j < WIDTH; j++) 
            {
            	System.out.print(maze[i][j] + " ");
            }
            System.out.println();
    	}
    }
    
    public static char[][] copyMaze(char[][] mayze) {
    	char[][] maze = new char[16][16];
    	for (int i = 0; i < mayze[0].length; i++)
    	{
    		for (int j = 0; j < mayze.length; j++)
    		{
    			maze[i][j] = mayze[i][j];
    		}
    	}
		return maze;
    }
    
    public static char returnChar(int x, int y) {
		return maze[x][y];
    }
    
    public static void checkMove(Player player, int newX, int newY) {
    	if (newX >= 0 && newX < HEIGHT && newY >= 0 && newY < WIDTH) 
    	{
            char target = maze[newX][newY];
            if (target == EXPLOSIVE) 
            {
                explode(newX, newY); // If the player steps on an explosive, explode it
            } 
            else if (target == PATH) 
            {
                player.move(newX - player.getX(), newY - player.getY());
            } 
            else if (target == NUGGET) 
            {
                score += 10; // Increase score when collecting a gold nugget
                maze[newX][newY] = PATH;
                player.move(newX - player.getX(), newY - player.getY());
            } 
            else if (target == END) 
            {
            	System.out.println("Congratulations! You reached the end of the maze. Your score is: " + score);
            	System.out.printf("You completed the maze in %d moves.", player.getMoves());
            }
        }
    }

    public static void run() {
        initializeMaze();
        int startX = 0;
        int startY = 1;
        int endX = HEIGHT - 2;
        int endY = WIDTH - 3;
        generateMaze(startX, startY);
        placeItems();
        maze[endX][endY] = END;
    }
}