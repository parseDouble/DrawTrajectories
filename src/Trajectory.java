
import org.postgis.PGgeometry;

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
    
}
