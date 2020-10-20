package org.pahappa.systems;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    public static void main(String[] args) {
        List<String> customVariableList = new ArrayList<String>();
        String message = "Hello {name} how are you {name}";
        customVariableList.add("\\{name\\}");
        customVariableList.add("\\{amount\\}");
        customVariableList.add("\\{requisition\\}");

        for (String customVariable : customVariableList) {
            switch (customVariable) {
                case "\\{name\\}":
                    message = message.replaceAll(customVariable, "requisition.getUser().getFullName()");
                    break;
                case "\\{amount\\}":
                    message = message.replaceAll(customVariable, "requisition.getAmountRequested()");
                    break;
                case "\\{requisition\\}":
                    message = message.replaceAll(customVariable, "requisition.getRequisitionNumber()");
                    break;
            }
        }

        System.out.println("--------FINAL MESSAGE" + message);
    }
}
