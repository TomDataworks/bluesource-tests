/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.testng.annotations.DataProvider;

public class BluesourceTestData
{
        @DataProvider(name="TestTotalTimeOffData")
        public static Object[][] TestTotalTimeOffData() {
            return new Object[][] { { "Kazirick Revele" }, {"Thomas Fields"}, {"Perscitia Laurence" } };
        }
    
        @DataProvider(name="TestUsedTimeOffData")
        public static Object[][] TestUsedTimeOffData() {
            return new Object[][] { { "Kazirick Revele" }, {"Thomas Fields"}, {"Perscitia Laurence" } };
        }
    
        @DataProvider(name="TestTimeOffData")
        public static Iterator<Object[]> TestTimeOffData() throws Exception {
            File csv = new File("C:\\Users\\brian.becker\\Git\\bluesource-tests\\BluesourceTests\\target\\test-classes\\com\\github\\arven\\bluesourcetests\\data\\bluesource-timeoff-test.csv");
            final CSVParser parser = CSVParser.parse(csv, StandardCharsets.US_ASCII, CSVFormat.EXCEL.withHeader());

            return new Iterator<Object[]>() {
                private final Iterator<CSVRecord> iter = parser.iterator();

                @Override
                public boolean hasNext() {
                    return iter.hasNext();
                }

                @Override
                public Object[] next() {
                    CSVRecord n = iter.next();
                    //SimpleDateFo//rmat df = new SimpleDateFormat("MM/dd/yyyy");
                    return new Object[] {
                        n.get("Name"),
                        n.get("Start"),
                        n.get("End"),
                        n.get("Type"),
                        n.get("Reason"),
                        Boolean.parseBoolean(n.get("Half-Day")),
                        Float.parseFloat(n.get("Days")),
                        Boolean.parseBoolean(n.get("Succeeds"))
                    };
                }

                @Override
                public void remove() {
                    // null operation
                    throw new UnsupportedOperationException("Read-only collection"); //To change body of generated methods, choose Tools | Templates.
                }
            };
        }
}