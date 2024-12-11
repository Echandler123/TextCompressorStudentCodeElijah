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
        // TODO: Complete the compress() method
        String line =   BinaryStdIn.readString();
        TST tst = new TST();
        int code = 0;
        while(!BinaryStdIn.isEmpty()){
            String prefix = tst.getLongestPrefix(line);
            BinaryStdOut.write(tst.lookup(prefix), 8);
            tst.insert(prefix + line.charAt(prefix.length()), code++);
            line = line.substring(prefix.length());
        }
        BinaryStdOut.close();

//        read data into String text
//                index = 0
//        while index < text.length:
//        prefix = longest coded word that matches text @ index
//        write out that code
//        if possible, look ahead to the next character
//        append that character to prefix
//        associate prefix with the next code (if available)
//        index += prefix.length
//        write out EOF and close


    }

    private static void expand() {
        // TODO: Complete the expand() method







        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
