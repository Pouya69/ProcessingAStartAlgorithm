public class Slot {
    int row;
    int column;
    double cost;

    public Slot(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Slot(Slot s) {
        this.row = s.row;
        this.column = s.column;
    }

    double calculateEuclideanDistanceToFood(Slot destination) {
        return Math.sqrt(Math.pow(destination.column - this.column, 2) + Math.pow(destination.row - this.row, 2));
    }

    void calculateTotalCost(double costSoFar, double costToThis, Slot foodLocation) {
        cost = costSoFar+costToThis+calculateEuclideanDistanceToFood(foodLocation);
    }



    public String toString() { return row + "," + column + " | Cost: " + this.cost; }

    boolean slotSameLocation(Slot s) {
        return (s.column == this.column && s.row == this.row);
    }
}
