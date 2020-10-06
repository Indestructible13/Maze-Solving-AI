public class Backtrack {
    MazeSolver current;
    MazeSolver previous;

    // empty constructor
    public Backtrack() {

    }

    // constructor that assigns values for current and previous
    public Backtrack(MazeSolver newCurrent, MazeSolver newPrevious) {
        current = newCurrent;
        previous = newPrevious;
    }
}