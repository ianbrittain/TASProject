
package tasproject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.util.GregorianCalendar;

/**
 *
 * @author Matthew & Maddie
 */
public class TASLogic {
    
    public static int calculateTotalMinutes(ArrayList<Punch> dailyPunchList, Shift shift){
        int totalMinutes = 0;
        int totalInMillis = 0;
        boolean insidePair;
        Punch p = new Punch();
        
        //ALGORITHM - Looping through the punch list and finding a "CLOCK IN" punch and a corresponding "CLOCK OUT" punch. Once we find those two we are
        //inside of a pair. Once inside a pair we calculate the minutes. After that we continue loop from the last "CLOCK OUT" punch. 
        
        int outFoundAt = 0;
        for(int i = outFoundAt; i < dailyPunchList.size();++i){
            //Getting a "CLOCK IN punch"
            if(dailyPunchList.get(i).getPunchtypeid() == 1){
                //boolean flag to flip once we find a "CLOCK OUT" punch.
                GregorianCalendar clockIn = dailyPunchList.get(i).getAdjustedTimeStamp();
                long inInMillis = clockIn.getTimeInMillis();
                insidePair = false;
               
                //Searching for a corresponding "CLOCK OUT" punch to pair the "CLOCK IN" with.
                for(int j = outFoundAt; j < dailyPunchList.size();++j){
                    if(dailyPunchList.get(j).getPunchtypeid() == 0){
                        GregorianCalendar clockOut = dailyPunchList.get(j).getAdjustedTimeStamp();
                        long outInMillis = clockOut.getTimeInMillis();
                        insidePair = true;
                        outFoundAt = j;
                        // We are inside a "CLOCK IN" and "CLOCK OUT" pair. Now we need to calculate minutes in between the two and convert to minutes.
                        if(insidePair){
                           int workedTimeInMins = p.millisToMinutes(outInMillis - inInMillis);
                        }
                    }
                    
                        
                }
            }
        }
      return totalMinutes; 
        

    }
    
    public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist) throws SQLException, InstantiationException, IllegalAccessException{
        TASDatabase db = new TASDatabase();
        
        //Create ArrayList Object
        ArrayList<HashMap<String, String>> jsonData = new ArrayList<>();

        //Create HashMap Object (one for every Punch!)
        HashMap<String, String>  punchData = new HashMap<>();
        
        
        //Loop through the punches and put their info into the appropriate places
        for(Punch punch: dailypunchlist){
              
              //Get the shift associated with this punch and adjust the punch
              Shift s = db.getShift(punch.getBadge());
              punch.adjust(s);
              
              //Add Punch Data to HashMap
              punchData.put("id", String.valueOf(punch.getId()));
              punchData.put("badgeid", String.valueOf(punch.getBadgeid()));
              punchData.put("terminalid", String.valueOf(punch.getTerminalid()));
              punchData.put("eventtypeid",String.valueOf(punch.getPunchtypeid()));
              punchData.put("eventdata",punch.getEventData());
              punchData.put("originaltimestamp",String.valueOf(punch.getOriginaltimestamp().getTimeInMillis()));
              punchData.put("adjustedtimestamp",String.valueOf(punch.getAdjustedTimeStamp().getTimeInMillis()));
        }
        
        //Append HashMap to ArrayList
        jsonData.add(punchData);
        
        String results = JSONValue.toJSONString(jsonData);
        
        //REMOVE LATER ... why?
        return results;
    }
    
}
