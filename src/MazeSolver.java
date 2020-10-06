import java.util.ArrayList;

public class MazeSolver {
    int xPos;
    int yPos;

    // empty constructor
    public MazeSolver() {

    }

    // constructor that assigns starting values
    public MazeSolver(int i, int j) {
        xPos = i;
        yPos = j;
    }

    // Lists needed to solve maze
    ArrayList<MazeSolver> leavesList = new ArrayList<>();
    ArrayList<MazeSolver> visitedList = new ArrayList<>();
    ArrayList<Backtrack> backtrackList = new ArrayList<>();
    ArrayList<MazeSolver> solutionList = new ArrayList<>();

    public void solveMaze() {
        // Create object and get Maze
        Main m = new Main();
        //int[][] maze = testMaze;
        //int[][] maze = testMaze2;
        int[][] maze = m.getMaze();

        // Get start tile position and create new MazeSolver at that position.
        int[] startPos = m.startTilePos(maze);
        int[] finishPos = m.finishTilePos(maze);
        MazeSolver ms = new MazeSolver(startPos[0], startPos[1]);

        while (!(ms.xPos == finishPos[0] && ms.yPos == finishPos[1])) {
            // Add positions around current MazeSolver position to the Leaves List
            addLeavesToList(new MazeSolver(ms.xPos, ms.yPos), maze);

            // Put current location into Visited List
            visitedList.add(new MazeSolver(ms.xPos, ms.yPos));

            // Move MazeSolver object to position of the first object in the leavesList and remove the first object from the leavesList
            ms.xPos = leavesList.get(0).xPos;
            ms.yPos = leavesList.get(0).yPos;
            leavesList.remove(0);

            // Create new objects that are needed to avoid NullPointerExceptions
            MazeSolver s1 = new MazeSolver(0, 0);
            MazeSolver s2 = new MazeSolver(0, 0);
            Backtrack b = new Backtrack(s1, s2);

            // Put current location into the backtrack object as current
            b.current.xPos = ms.xPos;
            b.current.yPos = ms.yPos;

            for (int i = 1; i <= visitedList.size(); i++) {
                int[] lastVisitedPosition = {visitedList.get(visitedList.size() - i).xPos, visitedList.get(visitedList.size() - i).yPos};
                int[] currentPosition = {b.current.xPos, b.current.yPos};
                int manhattanDistance = manhattanDistance(lastVisitedPosition[0], lastVisitedPosition[1], currentPosition[0], currentPosition[1]);
                if (manhattanDistance == 1) {
                    // Put last visited location into the backtrack object as previous
                    b.previous.xPos = visitedList.get(visitedList.size() - i).xPos;
                    b.previous.yPos = visitedList.get(visitedList.size() - i).yPos;
                    break;
                }
            }

            // Put backtrack object b into backtrackList
            backtrackList.add(new Backtrack(new MazeSolver(b.current.xPos, b.current.yPos), new MazeSolver(b.previous.xPos, b.previous.yPos)));
        }

        findSolution();
        m.setMaze(updateMazeWithSolution(maze));
    }

    public void addLeavesToList(MazeSolver a, int[][] maze) {
        // If top, bottom, left, or right spots are paths (not walls) and they have not been visited before, add them to leavesList
        int i = a.xPos;
        int j = a.yPos;

        try {
            if (maze[i - 1][j] != 0 && !inVisitedList(i - 1, j)) {
                leavesList.add(new MazeSolver(i - 1, j));
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        try {
            if (maze[i + 1][j] != 0 && !inVisitedList(i + 1, j)) {
                leavesList.add(new MazeSolver(i + 1, j));
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        try {
            if (maze[i][j - 1] != 0 && !inVisitedList(i, j - 1)) {
                leavesList.add(new MazeSolver(i, j - 1));
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
        try {
            if (maze[i][j + 1] != 0 && !inVisitedList(i, j + 1)) {
                leavesList.add(new MazeSolver(i, j + 1));
            }
        } catch (ArrayIndexOutOfBoundsException e) {

        }
    }

    public boolean inVisitedList(int i, int j) {
        for (int n = 0; n < visitedList.size(); n++) {
            if (visitedList.get(n).xPos == i && visitedList.get(n).yPos == j) {
                return true;
            }
        }
        return false;
    }

    public void findSolution() {
        // Find solution using the backtrackList to find the shortest way from the finish position to the start position working backwards.

        // Get the previous position based on current position and add it to the solution list.
        // Repeat this step until the current position is the end start position.
        MazeSolver current = backtrackList.get(backtrackList.size() - 1).current;
        MazeSolver previous;

        // Look at the last current position in the backtrackList, add it to the solution list.
        solutionList.add(current);

        while(!(current.equals(backtrackList.get(0).previous))) {
            previous = returnPreviousFromCurrent(current);

            solutionList.add(previous);

            current = previous;
        }
    }

    public MazeSolver returnPreviousFromCurrent(MazeSolver current) {
        for (int i = 0; i < backtrackList.size(); i++) {
            if (backtrackList.get(i).current.xPos == current.xPos && backtrackList.get(i).current.yPos == current.yPos) {
                return backtrackList.get(i).previous;
            }
        }
        return null;
    }

    public int[][] updateMazeWithSolution(int[][] maze) {
        // Go through solutionList, use coordinates of solutions to change values in the maze
        int[] a = {0, 0};
        while (solutionList.size() > 0) {
            a[0] = solutionList.get(0).xPos;
            a[1] = solutionList.get(0).yPos;
            maze[a[0]][a[1]] = 7;
            solutionList.remove(0);
        }
        // return updated maze
        return maze;
    }
    public int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x2-x1) + Math.abs(y2-y1);
    }
}