import processing.core.PApplet;

import java.util.PriorityQueue;
import java.util.Random;

public class GridWithHoles extends Grid{
    double[][] holes;
    public GridWithHoles(PApplet p, float width, float height, int rowNum, int columnNum, int speed) {
        super(p, width, height, rowNum, columnNum, speed);
        this.q = new PriorityQueue<Path>(new PathComparator());
        this.holes = new double[this.rowNum][this.columnNum];
        this.initializeHoles();
    }

    public GridWithHoles(PApplet p, float width, float height, int rowNum, int columnNum, int speed, double[][] holes) {
        super(p, width, height, rowNum, columnNum, speed);
        this.q = new PriorityQueue<Path>(new PathComparator());
        this.holes = new double[this.rowNum][this.columnNum];
        this.holes = holes;
    }

    void initializeHoles() {
        Random random = new Random();
        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < this.columnNum; j++) {
                this.holes[i][j] = (random.nextInt(4) + 1 == 1) ? 30.f : 1.f;
            }
        }
    }

    boolean isHole(double value) {
        return value > 1;
    }

    @Override
    void drawGrid() {
        super.drawGrid();
        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < this.columnNum; j++) {
                if (!isHole(this.holes[i][j])) continue;
                p.pushMatrix();
                p.fill(0, 0, 0);
                p.rect(j*slotWidth, i*slotHeight, slotWidth, slotHeight);
                p.popMatrix();
                // System.out.println("Drawing hole on: " + (i+1) + ", " + (j+1));
            }
        }
    }


    @Override
    void addSlotToPathAndQ(Slot slot, Path currentPath) {
        // System.out.println(slot.toString());
        slot.calculateTotalCost(0, this.holes[slot.row-1][slot.column-1], foodLocation);
        super.addSlotToPathAndQ(slot, currentPath);
    }

    Path aStar(Slot startLoc) {
        Path p = searchFoodBFSRecursive(startLoc);
        this.explored.clear();
        this.q.clear();
        this.pathsSearched.lines.clear();
        return p;
    }

    @Override
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
            // System.out.println("Food found at: " + lastSlot);
            return currentPath;
        }

        addSlotToPathAndQ(lastSlot, currentPath);

        return searchFoodBFSRecursive(startSlot);
    }


    @Override
    Path findPath(Slot startSlot) {
        return aStar(startSlot);
    }
}
