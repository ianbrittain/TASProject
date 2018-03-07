import java.sql.SQLException;
import java.text.SimpleDateFormat;
import org.junit.*;
import static org.junit.Assert.*;
import tasproject.Punch;
import tasproject.TASDatabase;

public class Feature2 {
    
    private TASDatabase db;
    
    @Before
    public void setup() throws SQLException, InstantiationException, IllegalAccessException {
        db = new TASDatabase();
    }
    
    @Test
    public void testInsertCheckPunch() throws SQLException, InstantiationException, IllegalAccessException {
		
        /* Create New Punch Object */

        Punch p1 = new Punch("021890C0", 101, 1);
		
        /* Get Punch Properties */
        
        String badgeid = p1.getBadgeid();
        String originaltimestamp = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(p1.getOriginaltimestamp().getTime());
        int terminalid = p1.getTerminalid();
        int eventtypeid = p1.getPunchtypeid();
		
        /* Insert Punch Into Database */
        
        int punchid = db.insertPunch(p1);
		
        /* Retrieve New Punch */
        
        Punch p2 = db.getPunch(punchid);
        
        /* Compare Punches */

        assertEquals(badgeid, p2.getBadgeid());
        assertEquals(originaltimestamp, (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(p2.getOriginaltimestamp().getTime()));
        assertEquals(terminalid, p2.getTerminalid());
        assertEquals(eventtypeid, p2.getPunchtypeid());
        
    }

}