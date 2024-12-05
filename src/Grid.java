import processing.core.PApplet;

import java.util.LinkedList;
import java.util.Queue;

public class Grid extends GridBasics{
    Queue<Path> q;
    PathsSearched pathsSearched;
    int timer, initialTimer;
    Path lastPath;

    public Grid(PApplet p, float width, float height, int rowNum, int columnNum, int speed) {
        super(p, width, height, rowNum, columnNum);
        this.q = new LinkedList<>();
        this.pathsSearched = new PathsSearched(p, width, height, rowNum, columnNum);
        this.timer = speed;
        this.initialTimer = speed;
    }

    void addSlotToPathAndQ(Slot slot, Path currentPath) {
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

        for (int[] direction : directions) {
            int newRow = slot.row + direction[0];
            int newCol = slot.column + direction[1];

            if (isValidMove(newRow, newCol)) {
                Slot nextSlot = new Slot(newRow, newCol);
                Path newPathTwo = new Path(slot, nextSlot);
                if (currentPath == null) {
                    newPathTwo.addToPath(nextSlot);

                    // Add this line to pathsSearched
                    pathsSearched.addLine(newPathTwo);

                    // Add the new path to the queue
                    q.add(newPathTwo);
                    continue;
                }
                if (!explored.contains(nextSlot) && !pathsSearched.findPath(newPathTwo)) {
                    // Clone the current path and add the new slot
                    Path newPath = new Path(currentPath.path);
                    newPath.addToPath(nextSlot);

                    // Add this line to pathsSearched
                    pathsSearched.addLine(newPath);

                    // Add the new path to the queue
                    q.add(newPath);
                }
            }
        }
    }

    boolean isValidMove(int row, int col) {
        return row >= 1 && row <= rowNum && col >= 1 && col <= columnNum;
    }

    boolean hasExplored(Path path) {
        return pathsSearched.findPath(path) || find(path.getEnd());
    }


    Path searchFoodBFSRecursive(Slot startSlot) {
        if (q.isEmpty()) {
            addSlotToPathAndQ(startSlot, null);
        }
        if (q.isEmpty()) {
            System.out.println("No path to food found.");
            return null;
        }
        Path currentPath = q.poll();
        Slot lastSlot = currentPath.getEnd();

        if (find(lastSlot)) {
            return searchFoodBFSRecursive(startSlot);
        }

        explored.add(lastSlot);
        if (lastSlot.slotSameLocation(foodLocation)) {
            System.out.println("Food found at: " + lastSlot);
            return currentPath;
        }

        addSlotToPathAndQ(lastSlot, currentPath);

        return searchFoodBFSRecursive(startSlot);
    }

    Path findPath(Slot startSlot) {
        return searchFoodBFSRecursive(startSlot);
    }

//    void searchFoodBFSRecursive(Slot startSlot) {
//        // Timer handling
////        if (this.timer > 0) {
////            this.timer -= 1;
////            return;
////        }
////        this.timer = this.initialTimer;
//
//        // Base case: Empty queue
//        if (q.isEmpty()) {
//            addDirectionsForSlotToQueue(startSlot);
//        }
//
//        if (q.isEmpty()) {
//            System.out.println("No path to food found.");
//            return;
//        }
//
//        // Process current path
//        Path currentPath = q.remove();
//        System.out.println(currentPath);
//        Slot pathEnd = currentPath.getEnd();
//
//        if (!pathsSearched.findPath(currentPath) && !explored.contains(pathEnd)) {
//            pathsSearched.addLine(currentPath);
//            explored.add(pathEnd);
//
//            if (pathEnd.slotSameLocation(foodLocation)) {
//                System.out.println("FOUND FOOD at: " + pathEnd.row + ", " + pathEnd.column);
//                return;
//            }
//
//            // Enqueue neighbors
//            addDirectionsForSlotToQueue(pathEnd);
//        }
//
//        // Recursive call
//        searchFoodBFSRecursive(startSlot);
//    }
}
