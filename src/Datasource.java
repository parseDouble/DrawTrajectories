
/**
 *
 * @author insan3
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.postgis.PGgeometry;

import java.sql.Statement;
import java.util.ArrayList;
import org.postgis.PGgeometry;

/**
 *
 * @author insan3
 */
public class Datasource {

    private final String user = "postgres";
    private final String passwd = "tirocinio2015";

  // URL per la connessione alla base di dati e' formato dai seguenti
    // componenti: <protocollo>://<hosource()st del server>/<nome base di dati>.
    private final String url = "jdbc:postgresql://localhost:5432/postgres";

    // Driver da utilizzare per la connessione e l'esecuzione delle query.
    private final String driver = "org.postgresql.Driver";

    public Datasource() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Datasource.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<Trajectory> getTrajectoryFromDB() {
        
        
        
        
        
        // Dichiarazione delle variabili necessarie
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        ArrayList<Trajectory> out=new ArrayList<>();
        
        
        

        try {
            
            
            
            con = DriverManager.getConnection(url, user, passwd);
((org.postgresql.PGConnection)con).addDataType("geometry",Class.forName("org.postgis.PGgeometry"));
            ((org.postgresql.PGConnection)con).addDataType("box3d",Class.forName("org.postgis.PGbox3d"));
            stmt = con.createStatement();
            String query
                    = "SELECT owner, geom, trId "
                    + "FROM trajectories "
                    + "WHERE trid='20080929093447' OR  trid= '20080927231533'   OR trid='20081119011305' OR trid='20081101024405'";
            rs = stmt.executeQuery(query);
            
            while(rs.next()){
                out.add(makeTrajectory(rs));
            }
            con.close();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Datasource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return out;
    }

    private Trajectory makeTrajectory(ResultSet rs) throws SQLException {
        
        String owner = rs.getString(1);
        String trId = rs.getString(3);

        return new Trajectory(owner,(PGgeometry)rs.getObject(2), trId); //errore qui su get object.
        
    }

}
