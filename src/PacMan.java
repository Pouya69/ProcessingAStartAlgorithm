import processing.core.PApplet;

import java.util.PriorityQueue;
import java.util.Queue;

enum MovingDirection {
    UP,
    DOWN,
    LEFT,
    RIGHT,
}

public class PacMan extends GridWithHoles{
    Slot myLocation;
    boolean bIsMouthClosed;
    MovingDirection directionFacing;
    boolean bHasStartedSearch;
    Path finalPath;
    int currentIndex = 0;

    public PacMan(PApplet p, float width, float height, int rowNum, int columnNum, Slot myLocation, int speed) {
        super(p, width, height, rowNum, columnNum, speed);
        this.myLocation = myLocation;
        this.bIsMouthClosed = false;
        this.bHasStartedSearch = false;
        this.directionFacing = MovingDirection.UP;
    }

    public PacMan(PApplet p, float width, float height, int rowNum, int columnNum, Slot myLocation, int speed, double[][] holes) {
        super(p, width, height, rowNum, columnNum, speed, holes);
        this.myLocation = myLocation;
        this.bIsMouthClosed = false;
        this.bHasStartedSearch = false;
        this.directionFacing = MovingDirection.UP;
    }

    void drawPacMan() {
        if (isOnHole()) {
            p.pushMatrix();
            p.textAlign(p.CENTER);
            p.textSize(40);
            p.fill(255, 0, 0);
            p.text("PACMAN DIED!", width/2, height/2);
            p.popMatrix();
            return;
        }
        else if (hasReachedFood()) {
            p.pushMatrix();
            p.textAlign(p.CENTER);
            p.textSize(40);
            p.fill(0, 255, 0);
            p.text("PACMAN Got The Food!!", width/2, height/2);
            p.popMatrix();
        }
        final Pixel myLocPixel = convertSlotLocationToPixelCoordinates(this.myLocation);
        p.pushMatrix();
        p.translate(myLocPixel.x, myLocPixel.y);
        p.strokeWeight(0);
        p.fill(255, 255, 0);
        if (bIsMouthClosed) {
            p.ellipse(0, 0, slotWidth, slotHeight);
            p.popMatrix();
            return;
        }
        switch (this.directionFacing) {
            case UP -> p.rotate(p.PI / 2);
            case DOWN -> p.rotate(-p.PI / 2);
            case RIGHT -> p.rotate(p.PI);
        }
        p.arc(0, 0, (float) (slotWidth*(0.75)), (float) (slotHeight*(0.75)), -5*p.PI/6, 5*p.PI/6);
        // p.arc();
        p.popMatrix();
    }

    boolean isOnHole() {
        //System.out.println("PACMAN : " +this.myLocation.toString());
        return isHole(this.holes[this.myLocation.row-1][this.myLocation.column-1]);
    }

    void updatePacMan() {
        if (!hasFoodOnGrid() || hasReachedFood() || isOnHole()) return;
        if (!this.bHasStartedSearch) {
            this.bHasStartedSearch = true;
            finalPath = findPath(new Slot(this.myLocation));
            return;
        }
        if (finalPath != null) {
            if (this.timer > 0) {
                this.timer--;
                return;
            }
            if (currentIndex >= finalPath.path.size()-1) {
                this.myLocation = foodLocation;
                return;
            }
            this.timer = this.initialTimer;
            Slot slot = finalPath.path.get(currentIndex);
            if (slot.column > this.myLocation.column)
                movePacMan(MovingDirection.RIGHT);
            else if (slot.column < this.myLocation.column)
                movePacMan(MovingDirection.LEFT);
            else if (slot.row < this.myLocation.row)
                movePacMan(MovingDirection.UP);
            else if (slot.row > this.myLocation.row)
                movePacMan(MovingDirection.DOWN);
            currentIndex++;
        }
    }

    boolean hasReachedFood() {
        return foodLocation.slotSameLocation(this.myLocation);
    }

    void movePacMan(MovingDirection direction) {
        this.directionFacing = direction;
        switch (this.directionFacing) {
            case UP -> this.myLocation.row -= 1;
            case DOWN -> this.myLocation.row += 1;
            case LEFT -> this.myLocation.column -= 1;
            case RIGHT -> this.myLocation.column += 1;
        }
        this.bIsMouthClosed = !this.bIsMouthClosed;
    }
}
