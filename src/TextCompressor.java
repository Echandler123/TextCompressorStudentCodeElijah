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

    private static void compress() {
        String line = BinaryStdIn.readString();
        TST tst = new TST();
        int index = 0;
        int code = 0;
        for(int i =0; i < 256; i++) {
            tst.insert("" + (char) i, code++);
        }
        while(index < line.length()) {
            String prefix = tst.getLongestPrefix(line,index);
            BinaryStdOut.write(tst.lookup(prefix), 12);
            if(index + prefix.length() < line.length() && code < 4096){
                tst.insert(prefix + line.charAt(prefix.length()), code++);
            }
            index += prefix.length();
        }
        BinaryStdOut.write(256, 12);
        BinaryStdOut.close();
    }
    private static void expand() {
        String[] codeDictionary = new String[4096];
        for(int i = 0; i < 256; i++) {
            codeDictionary[i] = "" + (char) i;
        }
        codeDictionary[256] = "";
        int currentMaxCode = 257;
        int codeword = BinaryStdIn.readInt(12);
        if(codeword == 256) {
            BinaryStdOut.close();
            return;
        }
        String currentString = codeDictionary[codeword];
        BinaryStdOut.write(currentString);
        String nextString = "";
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
