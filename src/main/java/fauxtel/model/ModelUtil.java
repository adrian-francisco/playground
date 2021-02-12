/*
 * Name: ModelUtil.java
 * Author: Adrian Francisco
 * Created: Oct 13, 2017
 */
package fauxtel.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Utilities for the model.
 *
 * @author Adrian Francisco
 */
public class ModelUtil {

    /**
     * Prints the array as a table.
     *
     * @param array the array
     * @return the table
     */
    public static String print(Object[][] array) {

        if (ArrayUtils.isEmpty(array) || ArrayUtils.isEmpty(array[0])) {
            return StringUtils.EMPTY;
        }

        // calculate column widths
        Map<Integer, Integer> widths = new HashMap<>();

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (!widths.containsKey(j)) {
                    widths.put(j, 0);
                }

                if (array[i][j] == null) {
                    continue;
                }

                int width = array[i][j].toString().length();
                if (width > widths.get(j)) {
                    widths.put(j, width);
                }
            }
        }

        // generate the table
        StringBuffer table = new StringBuffer();

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {

                if (array[i][j] == null) {
                    table.append(StringUtils.repeat(" ", widths.get(j)));
                }
                else {
                    table.append(StringUtils.rightPad(array[i][j].toString(), widths.get(j)));
                }
                table.append(" ");
            }
            table.append("\n");

            // print the table header boarder
            if (i == 0) {
                for (int j = 0; j < array[i].length; j++) {
                    table.append(StringUtils.repeat("=", widths.get(j)));
                    table.append(" ");
                }
                table.append("\n");
            }
        }

        return table.toString();
    }
}
