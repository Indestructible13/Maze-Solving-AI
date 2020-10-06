import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class Main extends Application {
    private static Pane root = new Pane();

    // Create labels with text
    private Label titleText = new Label("Maze-solving AI");
    private Label bodyText = new Label("Click the black wall tiles to turn them into path tiles.\n"
                                            + "Then click the \"Solve\" button to solve the maze!");
    private Label bodyText2 = new Label("The purple path is the shortest path through the maze.");
    private Label bodyText3 = new Label("Color Key:" +
                                            "\n Black - Wall" +
                                            "\n White - Hallway" +
                                            "\n Yellow - Corner" +
                                            "\n Blue - Intersection" +
                                            "\n Red - Dead End" +
                                            "\n Lime - Start Tile" +
                                            "\n Green - Finish Tile" +
                                            "\n Purple - Shortest Path" +
                                            "\n Orange - Error (You shouldn't see any)");

    // Create buttons
    private Button solveButton = new Button("Solve");
    private Button resetMazeButton = new Button("Reset Maze");
    private Button setStartButton = new Button("Choose Start Tile");
    private Button setFinishButton = new Button("Choose Finish Tile");
    private Button testButton = new Button("Test");

    // Create static array
    private static int[][] maze = new int[20][10];

    @Override
    public void start(Stage primaryStage) {
        // Set text layout
        titleText.setLayoutX(10);
        titleText.setLayoutY(10);

        bodyText.setLayoutX(10);
        bodyText.setLayoutY(30);

        bodyText2.setLayoutX(10);
        bodyText2.setLayoutY(70);

        bodyText3.setLayoutX(400);
        bodyText3.setLayoutY(10);

        // Set button layout
        setStartButton.setLayoutX(10);
        setStartButton.setLayoutY(100);
        setStartButton.setPrefWidth(150);

        setFinishButton.setLayoutX(170);
        setFinishButton.setLayoutY(100);
        setFinishButton.setPrefWidth(150);

        solveButton.setLayoutX(10);
        solveButton.setLayoutY(140);
        solveButton.setPrefWidth(150);

        resetMazeButton.setLayoutX(170);
        resetMazeButton.setLayoutY(140);
        resetMazeButton.setPrefWidth(150);

        testButton.setLayoutX(330);
        testButton.setLayoutY(140);
        testButton.setPrefWidth(90);

        // Fill maze
        fillMaze(maze);

        // Display maze on startup
        showMaze(maze);

        // Make buttons work
        resetMazeButton.setOnMouseClicked(e -> {
            fillMaze(maze);
            showMaze(maze);
        });
        solveButton.setOnMouseClicked(e -> {
            MazeSolver ms = new MazeSolver();
            ms.solveMaze();
            showMaze(maze);
        });
        setStartButton.setOnMouseClicked(e -> {
            if (!startTileExists(maze)) {
                createStartTiles();
            } else System.out.println("Can't have more than 1 start tile!");
        });
        setFinishButton.setOnMouseClicked(e -> {
            if (!finishTileExists(maze)) {
                createFinishTiles();
            } else System.out.println("Can't have more than 1 finish tile!");

        });
        testButton.setOnMouseClicked(e -> {
            // Used to test various methods
            System.out.println("Testing...");
        });

        //root.getChildren().addAll(titleText, bodyText, solveButton, resetMazeButton, testButton); Done by showMaze() method
        primaryStage.setTitle("Maze-solving AI");
        primaryStage.setScene(new Scene(root, 750, 450));
        primaryStage.show();
    }

    public Main() {

    }

    public int[][] getMaze() {
        return maze;
    }

    public void setMaze(int [][] newMaze) {
        maze = newMaze;
    }

    public void fillMaze(int[][] maze) {
        // Fill maze array with 0's
        // Used on startup and to reset the maze
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                maze[i][j] = 0;
            }
        }
    }

    public void showMaze(int[][] maze) {
        // Prevent layering more and more rectangles on top of each other
        root.getChildren().clear();
        root.getChildren().addAll(titleText, bodyText, bodyText2, bodyText3, solveButton, resetMazeButton, setStartButton, setFinishButton);

        // Add a bunch of rectangles colored based on maze matrix.
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                // if the number is 0 (wall), make the rectangle black.
                // else if the number is 1 (hallway), make the rectangle white.
                // else if the number is 2 (corner), make the rectangle yellow.
                // else if the number is 3 (intersection), make the rectangle blue.
                // else if the number is 4 (dead end), make the rectangle red.
                // else (error), make the rectangle orange.
                if (maze[i][j] == 0) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.BLACK);
                    a.setOnMouseClicked(e -> {
                        clickTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else if (maze[i][j] == 1) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.WHITE);
                    a.setOnMouseClicked(e -> {
                        clickTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else if (maze[i][j] == 2) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.YELLOW);
                    a.setOnMouseClicked(e -> {
                        clickTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else if (maze[i][j] == 3) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.BLUE);
                    a.setOnMouseClicked(e -> {
                        clickTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else if (maze[i][j] == 4) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.RED);
                    a.setOnMouseClicked(e -> {
                        clickTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else if (maze[i][j] == 5) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.LIME);
                    a.setOnMouseClicked(e -> {
                        clickTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else if (maze[i][j] == 6) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.GREEN);
                    a.setOnMouseClicked(e -> {
                        clickTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else if (maze[i][j] == 7) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.PURPLE);
                    a.setOnMouseClicked(e -> {
                        clickTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else {
                    // ERROR IF THIS HAPPENS
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.ORANGE);
                    a.setOnMouseClicked(e -> {
                        clickTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                }
            }
        }
    }

    public void clickTileEvent(Rectangle a) {
        // Change tile color and update neighboring tile colors on click
        // if it is not a wall, make it a wall and change to BLACK
        // else if it is hallway, change to WHITE
        // else if it is a corner, change to YELLOW
        // else if it is an intersection, change to BLUE
        // else if it is a dead end, change to RED
        // else (error) change to ORANGE

        int i = ((int) a.getX() - 10) / 15;
        int j = ((int) a.getY() - 250) / 15;

        if (!isWall(i, j)) {
            // Change the rectangle color
            a.setFill(Color.BLACK);

            // Change the matrix value at those coordinates
            maze[i][j] = 0;

            // Print out matrix coordinates and value for this rectangle
            //System.out.println("maze[i][j] -> (" + i + ", " + j + ") == " + maze[i][j]);

            // This pattern is repeated for the other else if statements below
        } else if (isHallway(i, j)) {
            a.setFill(Color.WHITE);
            maze[i][j] = 1;
            //System.out.println("maze[i][j] -> (" + i + ", " + j + ") == " + maze[i][j]);
        } else if (isCorner(i, j)) {
            a.setFill(Color.YELLOW);
            maze[i][j] = 2;
            //System.out.println("maze[i][j] -> (" + i + ", " + j + ") == " + maze[i][j]);
        } else if (isIntersection(i, j)) {
            a.setFill(Color.BLUE);
            maze[i][j] = 3;
            //System.out.println("maze[i][j] -> (" + i + ", " + j + ") == " + maze[i][j]);
        } else if (isDeadEnd(i, j)) {
            a.setFill(Color.RED);
            maze[i][j] = 4;
            //System.out.println("maze[i][j] -> (" + i + ", " + j + ") == " + maze[i][j]);
        } else {
            // ERROR IF THIS HAPPENS
            a.setFill(Color.ORANGE);
            //System.out.println("maze[i][j] -> (" + i + ", " + j + ") == " + maze[i][j]);
        }

        // Update the values in neighboring tiles.
        updateNeighbors(i, j);

        // Show the updated values using new rectangles.
        showMaze(maze);
    }

    public boolean isWall(int i, int j) {
        return maze[i][j] == 0;
    }

    public boolean isHallway(int i, int j) {
        // Returns true if the top and bottom are walls while the left and right are not
        // or if the left and right are walls while the top and bottom are not.
        return (topNeighborValue(i, j) == 0 && bottomNeighborValue(i, j) == 0 && leftNeighborValue(i, j) != 0 && rightNeighborValue(i, j) != 0
                || topNeighborValue(i, j) != 0 && bottomNeighborValue(i, j) != 0 && leftNeighborValue(i, j) == 0 && rightNeighborValue(i, j) == 0);
    }

    public boolean isCorner(int i, int j) {
        // Returns true if two adjacent sides are walls, while the other two are not.
        return (topNeighborValue(i, j) == 0 && bottomNeighborValue(i, j) != 0 && leftNeighborValue(i, j) == 0 && rightNeighborValue(i, j) != 0
                || topNeighborValue(i, j) == 0 && bottomNeighborValue(i, j) != 0 && leftNeighborValue(i, j) != 0 && rightNeighborValue(i, j) == 0
                || topNeighborValue(i, j) != 0 && bottomNeighborValue(i, j) == 0 && leftNeighborValue(i, j) != 0 && rightNeighborValue(i, j) == 0
                || topNeighborValue(i, j) != 0 && bottomNeighborValue(i, j) == 0 && leftNeighborValue(i, j) == 0 && rightNeighborValue(i, j) != 0);
    }

    public boolean isIntersection(int i, int j) {
        // Returns true if 3 or 4 sides are not walls.
        return (topNeighborValue(i, j) == 0 && bottomNeighborValue(i, j) != 0 && leftNeighborValue(i, j) != 0 && rightNeighborValue(i, j) != 0
                || topNeighborValue(i, j) != 0 && bottomNeighborValue(i, j) == 0 && leftNeighborValue(i, j) != 0 && rightNeighborValue(i, j) != 0
                || topNeighborValue(i, j) != 0 && bottomNeighborValue(i, j) != 0 && leftNeighborValue(i, j) == 0 && rightNeighborValue(i, j) != 0
                || topNeighborValue(i, j) != 0 && bottomNeighborValue(i, j) != 0 && leftNeighborValue(i, j) != 0 && rightNeighborValue(i, j) == 0
                || topNeighborValue(i, j) != 0 && bottomNeighborValue(i, j) != 0 && leftNeighborValue(i, j) != 0 && rightNeighborValue(i, j) != 0);
    }

    public boolean isDeadEnd(int i, int j) {
        // Returns true if 3 or 4 sides are walls.
        return (topNeighborValue(i, j) != 0 && bottomNeighborValue(i, j) == 0 && leftNeighborValue(i, j) == 0 && rightNeighborValue(i, j) == 0
                || topNeighborValue(i, j) == 0 && bottomNeighborValue(i, j) != 0 && leftNeighborValue(i, j) == 0 && rightNeighborValue(i, j) == 0
                || topNeighborValue(i, j) == 0 && bottomNeighborValue(i, j) == 0 && leftNeighborValue(i, j) != 0 && rightNeighborValue(i, j) == 0
                || topNeighborValue(i, j) == 0 && bottomNeighborValue(i, j) == 0 && leftNeighborValue(i, j) == 0 && rightNeighborValue(i, j) != 0
                || topNeighborValue(i, j) == 0 && bottomNeighborValue(i, j) == 0 && leftNeighborValue(i, j) == 0 && rightNeighborValue(i, j) == 0);
    }

    public boolean isStartTile(int i, int j) {
        return maze[i][j] == 5;
    }

    public boolean isFinishTile(int i, int j) {
        return maze[i][j] == 6;
    }

    public int topNeighborValue(int i, int j) {
        // Return the value of the space above in the maze matrix.
        try {
            return maze[i][j - 1];
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }

    public int bottomNeighborValue(int i, int j) {
        // Return the value of the space below in the maze matrix.
        try {
            return maze[i][j + 1];
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }

    public int leftNeighborValue(int i, int j) {
        // Return the value of the space to the left in the maze matrix.
        try {
            return maze[i - 1][j];
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }

    public int rightNeighborValue(int i, int j) {
        // Return the value of the space to the right in the maze matrix.
        try {
            return maze[i + 1][j];
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }

    public void updateNeighbors(int i, int j) {
        // Takes in a tile's location. Updates the tiles around it.
        try {
            updateTileValue(i + 1, j);
        } catch (IndexOutOfBoundsException e) {

        }

        try {
            updateTileValue(i - 1, j);
        } catch (IndexOutOfBoundsException e) {

        }

        try {
            updateTileValue(i, j + 1);
        } catch (IndexOutOfBoundsException e) {

        }

        try {
            updateTileValue(i, j - 1);
        } catch (IndexOutOfBoundsException e) {

        }

    }

    public void updateTileValue(int i, int j) {
        if (isStartTile(i, j)) {
            maze[i][j] = 5;
        } else if (isFinishTile(i, j)) {
            maze[i][j] = 6;
        } else if (isWall(i, j)) {
            maze[i][j] = 0;
        } else if (isHallway(i, j)) {
            maze[i][j] = 1;
        } else if (isCorner(i, j)) {
            maze[i][j] = 2;
        } else if (isIntersection(i, j)) {
            maze[i][j] = 3;
        } else if (isDeadEnd(i, j)) {
            maze[i][j] = 4;
        } else maze[i][j] = 7;
    }

    public void createStartTiles() {
        /* Delete old tiles
         * Create new tiles with new click events
         * New click events change values at start location
         * Delete new tiles and replace with original tiles
         */

        // Prevent layering more and more rectangles on top of each other by deleting everything and only replacing with what is necessary
        root.getChildren().clear();
        root.getChildren().addAll(titleText, bodyText, solveButton, resetMazeButton, setStartButton, setFinishButton, testButton);


        // Add a bunch of rectangles colored based on maze matrix.
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                // if the number is 0 (wall), make the rectangle black.
                // else if the number is 1 (hallway), make the rectangle white.
                // else if the number is 2 (corner), make the rectangle yellow.
                // else if the number is 3 (intersection), make the rectangle blue.
                // else if the number is 4 (dead end), make the rectangle red.
                // else if the number is 5 (start), make the rectangle
                // else (error), make the rectangle orange.

                // Only allow tiles on the edge
                if (i > 0 && i < maze.length - 1 & j > 0 && j < maze[i].length - 1) {
                    continue;
                }

                if (maze[i][j] == 0) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.BLACK);
                    a.setOnMouseClicked(e -> {
                        clickStartTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else if (maze[i][j] == 1) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.WHITE);
                    a.setOnMouseClicked(e -> {
                        clickStartTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else if (maze[i][j] == 2) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.YELLOW);
                    a.setOnMouseClicked(e -> {
                        clickStartTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else if (maze[i][j] == 3) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.BLUE);
                    a.setOnMouseClicked(e -> {
                        clickStartTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else if (maze[i][j] == 4) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.RED);
                    a.setOnMouseClicked(e -> {
                        clickStartTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else if (maze[i][j] == 5) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.LIME);
                    a.setOnMouseClicked(e -> {
                        clickStartTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else if (maze[i][j] == 6) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.GREEN);
                    a.setOnMouseClicked(e -> {
                        clickStartTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                }
                else {
                    // ERROR IF THIS HAPPENS
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.ORANGE);
                    a.setOnMouseClicked(e -> {
                        clickStartTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                }
            }
        }
    }

    public void clickStartTileEvent(Rectangle a) {
        int i = ((int) a.getX() - 10) / 15;
        int j = ((int) a.getY() - 250) / 15;

        maze[i][j] = 5;
        showMaze(maze);
    }

    public boolean startTileExists(int[][] maze) {
        // Returns true if a finish tile already exists
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[] startTilePos(int[][] maze) {
        int[] a = {0, 0};

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 5) {
                    a = new int[]{i, j};
                }
            }
        }
        return a;
    }

    public void createFinishTiles() {
        /* Delete old tiles
         * Create new tiles with new click events
         * New click events change values at finish location
         * Delete new tiles and replace with original tiles
         */

        // Prevent layering more and more rectangles on top of each other by deleting everything and only replacing with what is necessary
        root.getChildren().clear();
        root.getChildren().addAll(titleText, bodyText, solveButton, resetMazeButton, setStartButton, setFinishButton, testButton);

        // Add a bunch of rectangles colored based on maze matrix.
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                // if the number is 0 (wall), make the rectangle black.
                // else if the number is 1 (hallway), make the rectangle white.
                // else if the number is 2 (corner), make the rectangle yellow.
                // else if the number is 3 (intersection), make the rectangle blue.
                // else if the number is 4 (dead end), make the rectangle red.
                // else if the number is 5 (start), make the rectangle
                // else (error), make the rectangle orange.

                // Only allow tiles on the edge
                if (i > 0 && i < maze.length - 1 & j > 0 && j < maze[i].length - 1) {
                    continue;
                }

                // Create tiles
                if (maze[i][j] == 0) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.BLACK);
                    a.setOnMouseClicked(e -> {
                        clickFinishTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else if (maze[i][j] == 1) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.WHITE);
                    a.setOnMouseClicked(e -> {
                        clickFinishTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else if (maze[i][j] == 2) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.YELLOW);
                    a.setOnMouseClicked(e -> {
                        clickFinishTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else if (maze[i][j] == 3) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.BLUE);
                    a.setOnMouseClicked(e -> {
                        clickFinishTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else if (maze[i][j] == 4) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.RED);
                    a.setOnMouseClicked(e -> {
                        clickFinishTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else if (maze[i][j] == 5) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.LIME);
                    a.setOnMouseClicked(e -> {
                        clickFinishTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                } else if (maze[i][j] == 6) {
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.GREEN);
                    a.setOnMouseClicked(e -> {
                        clickFinishTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                }
                else {
                    // ERROR IF THIS HAPPENS
                    Rectangle a = new Rectangle(i*15+10, j*15+250, 14, 14);
                    a.setFill(Color.ORANGE);
                    a.setOnMouseClicked(e -> {
                        clickFinishTileEvent(a);
                    });
                    root.getChildren().addAll(a);
                }
            }
        }
    }

    public void clickFinishTileEvent(Rectangle a) {
        int i = ((int) a.getX() - 10) / 15;
        int j = ((int) a.getY() - 250) / 15;

        maze[i][j] = 6;
        showMaze(maze);
    }

    public boolean finishTileExists(int[][] maze) {
        // Returns true if a finish tile already exists
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 6) {
                    return true;
                }
            }
        }
        return false;
    }

    public int[] finishTilePos(int[][] maze) {
        int[] a = {0, 0};

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 6) {
                    a = new int[]{i, j};
                }
            }
        }
        return a;
    }
}
