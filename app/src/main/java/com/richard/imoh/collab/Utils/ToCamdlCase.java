package com.richard.imoh.collab.Utils;

public class ToCamdlCase {
  public   String camelCase(String str)
    {
        StringBuilder builder = new StringBuilder(str);
        // Flag to keep track if last visited character is a
        // white space or not
        boolean isLastSpace = true;

        // Iterate String from beginning to end.
        for(int i = 0; i < builder.length(); i++)
        {
            char ch = builder.charAt(i);

            if(isLastSpace && ch >= 'a' && ch <='z')
            {
                // Character need to be converted to uppercase
                builder.setCharAt(i, (char)(ch + ('A' - 'a') ));
                isLastSpace = false;
            }
            else if (ch != ' ')
                isLastSpace = false;
            else
                isLastSpace = true;
        }

        return builder.toString();
    }
}
