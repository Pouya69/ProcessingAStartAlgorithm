import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class GridBasics {
    PApplet p;
    float width;
    float height;
    int rowNum;
    int columnNum;
    static float slotWidth;
    static float slotHeight;
    Slot foodLocation;
    List<Slot> explored;

    public GridBasics(PApplet p, float width, float height, int rowNum, int columnNum) {
        this.p = p;
        this.width = width;
        this.height = height;
        this.rowNum = rowNum;
        this.columnNum = columnNum;
        slotWidth = width / columnNum;
        slotHeight = height / rowNum;
        foodLocation = null;
        explored = new ArrayList<>();
    }

    static Slot convertPixelCoordinatesToSlotLocation(Pixel p) {
        return new Slot((int) (p.y/ slotHeight), (int) (p.x/ slotWidth));
    }

    static Slot convertPixelCoordinatesToSlotLocation(float x, float y) {
        return new Slot((int) (y/ slotHeight)+1, (int) (x/ slotWidth)+1);
    }

    static Pixel convertSlotLocationToPixelCoordinates(Slot s) {
        return new Pixel(((s.column-1) * slotWidth) + (slotWidth/2), ((s.row-1) * slotHeight) + (slotHeight/2));
    }

    void addFood(int x, int y) {
        this.foodLocation = convertPixelCoordinatesToSlotLocation(x, y);
    }

    void drawFood() {
        if (foodLocation == null) return;
        p.pushMatrix();
        p.fill(255, 0, 0);
        p.strokeWeight(0);
        final Pixel foodLocPixels = convertSlotLocationToPixelCoordinates(foodLocation);
        p.translate(foodLocPixels.x, foodLocPixels.y);
        p.fill(255, 0, 255);
        p.ellipse(0, 0, 30, 30);
        p.popMatrix();
    }

    void drawGrid() {
        p.pushMatrix();
        p.strokeWeight(1);
        for (int i = 1; i <= rowNum-1; i++) {
            final float yPos = i * slotHeight;
            p.line(0, yPos, width, yPos);
        }
        for (int i = 1; i <= columnNum-1; i++) {
            final float xPos = i * slotWidth;
            p.line(xPos, 0, xPos, height);
        }
        p.popMatrix();
    }

    boolean find(Slot s) {
        return explored.contains(s);
//        for (Slot exploredSlot : explored)
//            if (s.slotSameLocation(exploredSlot)) return true;
//        return false;
    }

    boolean hasFoodOnGrid() { return this.foodLocation != null; }
}
