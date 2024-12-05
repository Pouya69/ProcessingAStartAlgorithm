import java.util.Comparator;

public class PathComparator implements Comparator<Path> {
    @Override
    public int compare(Path p1, Path p2) {
        double p1Cost = p1.getTotalCostOfPath();
        double p2Cost = p2.getTotalCostOfPath();
        if (p1Cost < p2Cost) return -1;
        if (p1Cost > p2Cost) return 1;
        return 0;
    }
}
