import java.util.ArrayList;

public class Path {
    ArrayList<Slot> path;

    public Path(Slot startSlot, Slot endSlot) {
        this.path = new ArrayList<>();
        this.path.add(startSlot);
        this.path.add(endSlot);
    }

    public Path(ArrayList<Slot> path) {
        this.path = new ArrayList<>(path);
    }

    double getTotalCostOfPath() {
        double total = 0.f;
        for (Slot s : this.path) {
            total += s.cost;
        }
        return total;
    }

    void addToPath(Slot s) {
        this.path.add(s);
    }

    Slot getEnd() {
        return this.path.get(this.path.size()-1);
    }

    Slot getBeginning() {
        return this.path.get(0);
    }

    boolean isPathEqual(Path p) {
        return (this.getBeginning().slotSameLocation(p.getBeginning()) && this.getEnd().slotSameLocation(p.getEnd())) ||
                (this.getBeginning().slotSameLocation(p.getEnd()) && this.getEnd().slotSameLocation(p.getBeginning()));
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("path = ");
        for (Slot slot : path) {
            s.append(slot.toString()).append(" -> ");
        }
        return s.append(";").toString();
    }
}
