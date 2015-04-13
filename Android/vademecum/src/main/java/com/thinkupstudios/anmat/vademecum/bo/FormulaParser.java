package com.thinkupstudios.anmat.vademecum.bo;

/**
 * Created by dcamarro on 11/04/2015.
 * Parser de formula
 */
public class FormulaParser {
    public static Formula parse(String genericName) {
        Formula formula = new Formula();
        String[] parts = genericName.split("\\+");

        for (String part : parts)
        {
            if(part.trim().equals("")) {
                continue;
            }

            Component component = getComponent(part);

            formula.addComponent(component);
        }

        return formula;
    }

    private static Component getComponent(String part) {
        String activeComponent;
        String proportion = "";

        int proportionStartIndex = -1;
        boolean unitFound = false;
        boolean spaceFound = false;
        boolean slashFound = false;
        boolean proportionRead = false;

        char[] partChars = part.toCharArray();
        int i = partChars.length - 1;

        while (!proportionRead && i >= 0)
        {
            if (unitFound)
            {
                if (Character.isDigit(partChars[i]) ||
                        partChars[i] == '.' ||
                        partChars[i] == ',')
                {
                    if(spaceFound) {
                        proportionRead = true;
                        proportionStartIndex = i + 1;
                    }
                }
                else if(Character.isWhitespace(partChars[i])) {
                    spaceFound = true;
                }
                else if (partChars[i] == '/' && !slashFound)
                {
                    slashFound = true;
                    unitFound = false;
                    spaceFound = false;
                }
                else
                {
                    proportionRead = true;
                    proportionStartIndex = i + 1;
                }
            }
            else if (Character.isDigit(partChars[i]))
            {
                unitFound = true;
            }

            i--;
        }

        if (proportionStartIndex == -1)
        {
            activeComponent = part;
        }
        else
        {
            activeComponent = part.substring(0, proportionStartIndex).trim();
            proportion = part.substring(proportionStartIndex).trim();
        }

        return new Component(activeComponent, proportion);
    }

}
