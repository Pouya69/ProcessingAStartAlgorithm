import processing.core.*;
import processing.event.*;

public class ProcessingMain extends PApplet {
    GridWithHoles grid;
    PacMan pacMan;
    int speed = 30;

    @Override
    public void settings() {
        size(800, 800);
    }

    @Override
    public void setup() {
        grid = new GridWithHoles(this, width, height, 8, 8, 0);
        frameRate(60);
    }

    @Override
    public void draw() {
        background(200);
        grid.drawGrid();
        if (pacMan == null) {
            grid.drawFood();
            return;
        }
        if (!pacMan.hasReachedFood())  grid.drawFood();
        pacMan.pathsSearched.drawTree(pacMan.myLocation);
        // pacMan.drawGrid();
        pacMan.updatePacMan();
        pacMan.drawPacMan();

    }


    @Override
    public void mousePressed() {
        if (grid.foodLocation == null) {
            this.grid.addFood(mouseX, mouseY);
            return;
        }
        if (pacMan == null) {
            System.out.println();
            pacMan = new PacMan(grid.p, grid.width, grid.height, grid.rowNum, grid.columnNum, GridBasics.convertPixelCoordinatesToSlotLocation(mouseX, mouseY), speed, grid.holes);
            pacMan.foodLocation = grid.foodLocation;
        }
    }
}
