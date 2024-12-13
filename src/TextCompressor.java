/******************************************************************************
 *  Compilation:  javac TextCompressor.java
 *  Execution:    java TextCompressor - < input.txt   (compress)
 *  Execution:    java TextCompressor + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   abra.txt
 *                jabberwocky.txt
 *                shakespeare.txt
 *                virus.txt
 *
 *  % java DumpBinary 0 < abra.txt
 *  136 bits
 *
 *  % java TextCompressor - < abra.txt | java DumpBinary 0
 *  104 bits    (when using 8-bit codes)
 *
 *  % java DumpBinary 0 < alice.txt
 *  1104064 bits
 *  % java TextCompressor - < alice.txt | java DumpBinary 0
 *  480760 bits
 *  = 43.54% compression ratio!
 ******************************************************************************/

/**
 *  The {@code TextCompressor} class provides static methods for compressing
 *  and expanding natural language through textfile input.
 *
 *  @author Zach Blick, Elijah Chandler
 */

public class TextCompressor {
    private final static int BIT_LENGTH = 12;
    private final static int MAX_CODES = 4096;
    private final static int EOF = 256;

    private static void compress() {
        // Read in the input String
        String line = BinaryStdIn.readString();
        // Create TST to store string and code pairs
        TST tst = new TST();
        int index = 0;
        int code = 257;
        // Fill the TST with single characters first
        for(int i =0; i < EOF; i++) {
            tst.insert("" + (char) i, i);
        }
        // Loop through the input string
        while(index < line.length()) {
            // Get the longest prefix of the current index
            String prefix = tst.getLongestPrefix(line,index);
            // Write the code for the prefix
            BinaryStdOut.write(tst.lookup(prefix), BIT_LENGTH);
            // If there is room in the TST and the prefix is not already in the TST add it
            if(index + prefix.length() < line.length() && code < MAX_CODES){
                tst.insert(prefix + line.charAt(prefix.length() + index), code++);
            }
            index += prefix.length();
        }
        BinaryStdOut.write(EOF, BIT_LENGTH);
        BinaryStdOut.close();
    }
    private static void expand() {
        // Create a dictionary to look up strings by their code
        String[] codes = new String[MAX_CODES];
        // Add single characters to the dictionary first
        for (int i = 0; i < EOF; i++) {
            codes[i] = "" + (char) i;
        }
        int eofCode = EOF;
        codes[EOF] = "";
        int currentMaxCode = 257;
        // Read in the first code
        int codeword = BinaryStdIn.readInt(BIT_LENGTH);
        // If the first code is the eof code, close the output and return
        if (codeword == eofCode) {
            BinaryStdOut.close();
            return;
        }
        String currentPrefix = codes[codeword];
        BinaryStdOut.write(currentPrefix);
        String nextPrefix = "";
        // Loop through the rest of the codes
        while (codeword != eofCode) {
            codeword = BinaryStdIn.readInt(BIT_LENGTH);
            // If the code is in the dictionary, get the next prefix
            if (codeword < currentMaxCode) {
                nextPrefix = codes[codeword];
            }
            // Special case where code is the max at this point so there is no prefix counterpart for this code
            else if (codeword == currentMaxCode) {
                // Make the next prefix based of the current prefix and the first character of the current prefix
                nextPrefix = currentPrefix + currentPrefix.charAt(0);
            }
            BinaryStdOut.write(nextPrefix);
            // Add the next prefix to the dictionary if there is room
            if (currentMaxCode < MAX_CODES) {
                codes[currentMaxCode++] = currentPrefix + nextPrefix.charAt(0);
            }
            currentPrefix = nextPrefix;
        }
        BinaryStdOut.close();
    }
    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
