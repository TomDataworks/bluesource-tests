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

            return new IteratorMap<CSVRecord, Object[]>(parser) {
                @Override
                public Object[] map(CSVRecord in) {
                    return new Object[] {
                        in.get("Name"),
                        in.get("Start"),
                        in.get("End"),
                        in.get("Type"),
                        in.get("Reason"),
                        Boolean.parseBoolean(in.get("Half-Day")),
                        Float.parseFloat(in.get("Days")),
                        Boolean.parseBoolean(in.get("Succeeds"))
                    };
                }
            };
        }
}