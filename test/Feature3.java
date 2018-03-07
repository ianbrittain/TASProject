import org.junit.*;
import static org.junit.Assert.*;
import tasproject.Punch;
import tasproject.Shift;

public class Feature3 {
    
    private TASDatabase db;
    
    @Before
    public void setup() {
        db = new TASDatabase();
    }
    
    @Test
    public void testAdjustPunchesShift1Weekday() {
		
        /* Get Shift Ruleset and Punch Data */
        
        Shift s1 = db.getShift(1);

        Punch p1 = db.getPunch(3634);
        Punch p2 = db.getPunch(3687);
        Punch p3 = db.getPunch(3688);
        Punch p4 = db.getPunch(3716);
		
        /* Adjust Punches According to Shift Rulesets */
        
        p1.adjust(s1);
        p2.adjust(s1);
        p3.adjust(s1);
        p4.adjust(s1);
		
        /* Compare Adjusted Timestamps to Expected Values */
        
        assertEquals("#28DC3FB8 CLOCKED IN: FRI 09/08/2017 06:50:35", p1.printOriginalTimestamp());
        assertEquals("#28DC3FB8 CLOCKED IN: FRI 09/08/2017 07:00:00 (Shift Start)", p1.printAdjustedTimestamp());
        
        assertEquals("#28DC3FB8 CLOCKED OUT: FRI 09/08/2017 12:03:54", p2.printOriginalTimestamp());
        assertEquals("#28DC3FB8 CLOCKED OUT: FRI 09/08/2017 12:00:00 (Lunch Start)", p2.printAdjustedTimestamp());
        
        assertEquals("#28DC3FB8 CLOCKED IN: FRI 09/08/2017 12:23:41", p3.printOriginalTimestamp());
        assertEquals("#28DC3FB8 CLOCKED IN: FRI 09/08/2017 12:30:00 (Lunch Stop)", p3.printAdjustedTimestamp());

        assertEquals("#28DC3FB8 CLOCKED OUT: FRI 09/08/2017 15:34:13", p4.printOriginalTimestamp());
        assertEquals("#28DC3FB8 CLOCKED OUT: FRI 09/08/2017 15:30:00 (Shift Stop)", p4.printAdjustedTimestamp());
        
    }

    @Test
    public void testAdjustPunchesShift1Weekend() {
		
        /* Get Shift Ruleset and Punch Data */
        
        Shift s1 = db.getShift(1);

        Punch p1 = db.getPunch(1087);
        Punch p2 = db.getPunch(1162);
		
        /* Adjust Punches According to Shift Rulesets */
        
        p1.adjust(s1);
        p2.adjust(s1);
		
        /* Compare Adjusted Timestamps to Expected Values */

        assertEquals("#F1EE0555 CLOCKED IN: SAT 08/12/2017 05:54:58", p1.printOriginalTimestamp());
        assertEquals("#F1EE0555 CLOCKED IN: SAT 08/12/2017 06:00:00 (Interval Round)", p1.printAdjustedTimestamp());
        
        assertEquals("#F1EE0555 CLOCKED OUT: SAT 08/12/2017 12:04:02", p2.printOriginalTimestamp());
        assertEquals("#F1EE0555 CLOCKED OUT: SAT 08/12/2017 12:00:00 (Interval Round)", p2.printAdjustedTimestamp());
        
    }
    
    @Test
    public void testAdjustPunchesShift2Weekday() {
		
        /* Get Shift Ruleset and Punch Data */
        
        Shift s2 = db.getShift(2);

        Punch p1 = db.getPunch(4943);
        Punch p2 = db.getPunch(5004);
		
        /* Adjust Punches According to Shift Rulesets */
        
        p1.adjust(s2);
        p2.adjust(s2);
        
        /* Compare Adjusted Timestamps to Expected Values */

        assertEquals("#08D01475 CLOCKED IN: TUE 09/19/2017 11:59:33", p1.printOriginalTimestamp());
        assertEquals("#08D01475 CLOCKED IN: TUE 09/19/2017 12:00:00 (Shift Start)", p1.printAdjustedTimestamp());
        
        assertEquals("#08D01475 CLOCKED OUT: TUE 09/19/2017 21:30:27", p2.printOriginalTimestamp());
        assertEquals("#08D01475 CLOCKED OUT: TUE 09/19/2017 21:30:27 (None)", p2.printAdjustedTimestamp());
        
    }
    
    @Test
    public void testAdjustPunchesShift2Weekend() {
		
        /* Get Shift Ruleset and Punch Data */
        
        Shift s2 = db.getShift(2);

        Punch p1 = db.getPunch(5463);
        Punch p2 = db.getPunch(5541);
		
        /* Adjust Punches According to Shift Rulesets */
        
        p1.adjust(s2);
        p2.adjust(s2);
		
        /* Compare Adjusted Timestamps to Expected Values */

        assertEquals("#08D01475 CLOCKED IN: SAT 09/23/2017 05:49:00", p1.printOriginalTimestamp());
        assertEquals("#08D01475 CLOCKED IN: SAT 09/23/2017 05:45:00 (Interval Round)", p1.printAdjustedTimestamp());
        
        assertEquals("#08D01475 CLOCKED OUT: SAT 09/23/2017 12:04:15", p2.printOriginalTimestamp());
        assertEquals("#08D01475 CLOCKED OUT: SAT 09/23/2017 12:00:00 (Interval Round)", p2.printAdjustedTimestamp());
        
    }
    
    @Test
    public void testAdjustPunchesShift1SpecialCases() {
		
        /* Get Shift Ruleset and Punch Data */
        
        Shift s1 = db.getShift(1);

        Punch p1 = db.getPunch(151);  // Interval Adjustment Before Shift (In)
        Punch p2 = db.getPunch(2439); // Grace Period (In)
        Punch p3 = db.getPunch(2693); // Shift Dock (In)
        Punch p4 = db.getPunch(3953); // Interval Round During Shift (Out)
        Punch p5 = db.getPunch(2079); // Grace Period (Out)
        Punch p6 = db.getPunch(1358); // Shift Dock (Out)
        Punch p7 = db.getPunch(4119); // Interval Adjustment After Shift (Out)
        
        /* Adjust Punches According to Shift Ruleset */
        
        p1.adjust(s1);
        p2.adjust(s1);
        p3.adjust(s1);
        p4.adjust(s1);
        p5.adjust(s1);
        p6.adjust(s1);
        p7.adjust(s1);
        
        /* Compare Adjusted Timestamps to Expected Values */

        assertEquals("#BE51FA92 CLOCKED IN: WED 08/02/2017 06:48:20", p1.printOriginalTimestamp());
        assertEquals("#BE51FA92 CLOCKED IN: WED 08/02/2017 07:00:00 (Shift Start)", p1.printAdjustedTimestamp());
        
        assertEquals("#3DA8B226 CLOCKED IN: FRI 08/25/2017 07:02:23", p2.printOriginalTimestamp());
        assertEquals("#3DA8B226 CLOCKED IN: FRI 08/25/2017 07:00:00 (Shift Start)", p2.printAdjustedTimestamp());

        assertEquals("#8E5F0240 CLOCKED IN: MON 08/28/2017 07:08:57", p3.printOriginalTimestamp());
        assertEquals("#8E5F0240 CLOCKED IN: MON 08/28/2017 07:15:00 (Shift Dock)", p3.printAdjustedTimestamp());
        
        assertEquals("#D2C39273 CLOCKED OUT: MON 09/11/2017 15:07:52", p4.printOriginalTimestamp());
        assertEquals("#D2C39273 CLOCKED OUT: MON 09/11/2017 15:15:00 (Interval Round)", p4.printAdjustedTimestamp());

        assertEquals("#408B195F CLOCKED OUT: TUE 08/22/2017 15:28:13", p5.printOriginalTimestamp());
        assertEquals("#408B195F CLOCKED OUT: TUE 08/22/2017 15:30:00 (Shift Stop)", p5.printAdjustedTimestamp());

        assertEquals("#1B2052DE CLOCKED OUT: TUE 08/15/2017 15:15:00", p6.printOriginalTimestamp());
        assertEquals("#1B2052DE CLOCKED OUT: TUE 08/15/2017 15:15:00 (Shift Dock)", p6.printAdjustedTimestamp());

        assertEquals("#ADD650A8 CLOCKED OUT: TUE 09/12/2017 15:37:12", p7.printOriginalTimestamp());
        assertEquals("#ADD650A8 CLOCKED OUT: TUE 09/12/2017 15:30:00 (Shift Stop)", p7.printAdjustedTimestamp());
        
    }
    
}

