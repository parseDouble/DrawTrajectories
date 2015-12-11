
import java.awt.List;
import java.awt.geom.Line2D;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import org.postgis.LineString;
import org.postgis.PGgeometry;
import org.postgis.Point;

/**
 *
 * @author insan3
 */
public class Trajectory {

    private String owner;
    private PGgeometry points;
    private String trId;

    public Trajectory() {
        this.owner = "";
        this.points = null;
        this.trId = "";
    }

    public Trajectory(String owner, PGgeometry points, String trId) {
        this.owner = owner;
        this.points = points;
        this.trId = trId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public PGgeometry getPoints() {
        return points;
    }

    public void setPoints(PGgeometry points) {
        this.points = points;
    }

    public String getTrId() {
        return trId;
    }

    public void setTrId(String trId) {
        this.trId = trId;
    }

    public static ArrayList<long[]> getCoord2d(ArrayList<Trajectory> t, int x, int y) throws SQLException {
        ArrayList<long[]> out = new ArrayList<>();

        double xMaxP = Double.MIN_VALUE;
        double yMaxP = Double.MIN_VALUE;
        double xMinP = Double.MAX_VALUE;
        double yMinP = Double.MAX_VALUE;

        //inizio a scorrere le traiettorie
        for (Trajectory t1 : t) {

            LineString multi = new LineString(t1.getPoints().toString());
            Point p[] = multi.getPoints();

            for (Point p1 : p) {
                if (xMinP > p1.getX()) {
                    xMinP = p1.getX();
                }
                if (yMinP > p1.getY()) {
                    yMinP = p1.getY();
                }

                if (xMaxP < p1.getX()) {
                    xMaxP = p1.getX();
                }
                if (yMaxP < p1.getY()) {
                    yMaxP = p1.getY();
                }
            }
        }

        //a questo punto del codice ho trovato i minimi ed i massimi validi per ogni traiettoria
        double diff1 = xMaxP - xMinP;
        double diff2 = yMaxP - yMinP;

        for (Trajectory t1 : t) {
            LineString multi = new LineString(t1.getPoints().toString());
            Point p[] = multi.getPoints();

            for (Point p1 : p) {

                long d[] = new long[4];
                
                d[0] = Long.parseLong(t1.getTrId());
                                                
                d[1] = (int)((p1.getX() - xMinP) / (diff1 / x));
                d[2] = (int) (y-(p1.getY() - yMinP) / (diff2 / y));
                d[3] = (int) p1.getZ();
                out.add(d);

                System.out.println(d[0]+" "+d[1] + "  " + d[2]);
            }

        }

        return out;
    }
}
