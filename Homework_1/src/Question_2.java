/* Header
* Name: Ayesha Iftikhar
* Email ID: l144130@lhr.nu.edu.pk
* Section: A
* Purpose: This program demostrates the use of StringBuffer class
* */

import java.util.Scanner;

public class Question_2 {
    public static void main(String[] args) {

        //Legend
        System.out.print("This program demonstrates the use of the StringBuffer class.\n\n");

        //1.	The user needs to enter two strings: one long string (say, 10 or so characters at a minimum)
        // and a shorter string that is contained within the longer string.  This input should be obtained via
        // thenextLine() method, as using the next() method will not read in a string that contains spaces
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a long string:\n");
        String long_string = scanner.nextLine();

        System.out.println("\nYou Entered:\t" + long_string + "\n");

        if(long_string.length() < 10) {
            System.out.println("long string should be of length >=10.\nEnter a long String again:\n");
            long_string = scanner.nextLine();
            System.out.println("\nYou Entered:\t" + long_string + "\n");
        }

        System.out.print("Enter a short string within the first string:\n");
        String short_string = scanner.nextLine();

        System.out.println("\nYou Entered:\t" + short_string);

        //2.	Create a StringBuffer object from the longer string -- this is the StringBuffer that you
        // will manipulate for the rest of the homework.  There are two ways to do this: create a default
        // constructred StringBuffer, and append() the long string to that, or use the StringBuffer with the
        // appropriate specific constructor.
        StringBuffer sbuf = new StringBuffer();
        sbuf.append(long_string);

        //3.	Include, as a comment in your program, the code for creating the StringBuffer in the other way from step 2.

        //StringBuffer sbuf = new StringBuffer(long_string);

        //4.	Find the position of the small string within the StringBuffer, and save that position
        int position = sbuf.indexOf(short_string);

        if(position == -1)
        {
            System.out.println("short string does not exist in long string.");
            System.exit(0);//return
        }

        //5.	Delete the small string from the StringBuffer, and print out the result
        System.out.println("\nDelete the small string from the StringBuffer: \n" + sbuf.delete(position,(position+short_string.length())));

        //6.	Insert "CS433" into the position of the StringBuffer where the small
        // string was originally found (from step 3), and print out the result.
        System.out.println("\nInsert \"CS433\" into the position of the StringBuffer where the small string was originally found: \n" + sbuf.insert(position,"CS433"));

        //7.	Remove the last word from the string. You can assume that everything from
        // the last space (found via lastIndexOf()) to the end of the String is the last word. Print out the result.
        System.out.println("\nRemove the last word from the string:\n" + sbuf.delete(sbuf.lastIndexOf(" ")+1,sbuf.length()));

        //8.	Append " rocks" to the end of the StringBuffer, and print out the result. Note that there
        // is a space before the work 'rocks'.
        System.out.println("\nAppend \" rocks\" to the end of the StringBuffer:\n" + sbuf.append("rocks"));

        //9.	Delete the character at position n/2, where n is the length of the StringBuffer.  Print out the result
        System.out.println("\nDelete the character at position n/2:\n" + sbuf.deleteCharAt(sbuf.length()/2));

        //10.	Reverse the StringBuffer, and print out the result.
        System.out.println("\nReverse the StringBuffer:\n" + sbuf.reverse());
    }
}
/*Test Cases:
* 1-  I think that this course really rules
* 2-  This is a long string
*
* The asked steps are performed and the result is printed out
*/
