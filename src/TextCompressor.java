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
        String text = BinaryStdIn.readString();
        int length = text.length();
        String word = "";
        int code = 0;
        String [] dictionary = new String[256];
        for(int i = 0; i < length; i++){
            char c = text.charAt(i);
            if(c != ' '){
                word += c;
            }
            else{
                word = "";
            }
        }
        BinaryStdOut.write(word.length(), 8);
        for(int i =0; i < word.length();i++){
            char c = word.charAt(i);
            code += (int) c;
        }
        BinaryStdOut.write(code, 8);
        BinaryStdOut.close();
    }

    private static void expand() {

        // TODO: Complete the expand() method
        String text = BinaryStdIn.readString();
        int length = text.length();







        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
