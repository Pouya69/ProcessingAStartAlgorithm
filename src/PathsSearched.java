import processing.core.PApplet;

import java.util.ArrayList;

public class PathsSearched extends GridBasics{
    ArrayList<Path> lines;

    public PathsSearched(PApplet p, float width, float height, int rowNum, int columnNum) {
        super(p, width, height, rowNum, columnNum);
        this.lines = new ArrayList<>();
    }

    void addLine(Path path) {
        lines.add(path);
    }

    void drawLine(Slot slot, Slot parentSlot) {
        final Pixel slotPixel = convertSlotLocationToPixelCoordinates(slot);
        final Pixel slotParentPixel = convertSlotLocationToPixelCoordinates(parentSlot);
        p.pushMatrix();
        p.strokeWeight(4);
        p.line(slotPixel.x, slotPixel.y, slotParentPixel.x, slotParentPixel.y);
        p.popMatrix();
    }

    void drawTree(Slot startLoc) {
        for (Path path : lines) {
            Slot prevSLot = startLoc;
            for (Slot s : path.path) {
                drawLine(s, prevSLot);
                prevSLot = s;
            }

        }
    }

    boolean findPath(Path path) {
        return this.lines.contains(path);
    }

}
