/* Header
* Name: Ayesha Iftikhar
* Email ID: l144130@lhr.nu.edu.pk
* Section: A
* Purpose: This program experiments String and StringBuffer classes
* */

public class Homework_1 { //Have a class named Homework_1 with a main function.
    public static void main(String[] args) {

        //Legend
        System.out.print("\n\nThis program experiments String and StringBuffer classes.\n\n");

        //The main function should expect exactly two command-line arguments. If the number of command-line arguments
        // is not two, the main function should return immediately. Otherwise, it should print out the command-line arguments
        if(args.length == 0)
        {
            System.out.println("No command line arguments given");
            System.exit(0);//return
        }

        //Check if Command Line arguments are entered correctly
        if(args.length >2 || args.length <2)
        {
            System.out.println("Please give only two command line arguments");
            return;
        }
        else
            System.out.println("The Two Command Line arguments are:\n" + args[0] + "\n" + args[1]);


        //----------------------------- Q1 part 1 ----------------------------
        //Experimenting String class

        // Create two String objects, say s1 and s2, initializing them with the first and the second command-line arguments
        String s1= args[0];
        String s2 = args[1];

        System.out.println("\n------------------ Experimenting String ------------------------");
        System.out.println("String1: " + s1);
        System.out.println("String2: " + s2);

        //s1.length()
        System.out.println("\ns1.length:\n"+s1.length());

        //s1.charAt(i)
        System.out.println("\ns1.charAt(i):");
        for(int i=0; i< s1.length();i++)
            System.out.println(s1.charAt(i));

        //s1.equals(s2),
        System.out.println("\ns1.equals(s2):");
        if(s1.equals(s2))
            System.out.println("Both strings are equal");
        else
            System.out.println("s1 is not equal to s2");

        // s1.equalsIgnoreCase(s2),
        if(s1.equalsIgnoreCase(s2))
            System.out.println("\nBoth strings are equal ignoring the case");

        // s1.compareTo(s2)
        System.out.println("\ns1.compareTo(s2):\t" + s1.compareTo(s2));
        if(s1.compareTo(s2)==0)
            System.out.println("s1 is equal to s2");
        else if(s1.compareTo(s2) <0)
            System.out.println("s1 is less than s2");
        else if(s1.compareTo(s2) > 0)
            System.out.println("s1 is greater than s2");


        // s1.regionMatches(int toffset,  s2, int offset, int len) for some offset and len
        System.out.println("\ns1.regionMatches:");
        if(s1.regionMatches(0,s2,0,9)) //give Tutorials and Tutorialspoint as two args
            System.out.println("Found!");
        else
            System.out.println("Not Found!");


        //s1.regionMatches(boolean ignoreCase,  int toffset,  s2, int offset, int len) for some offset and len
        System.out.println("\ns1.regionMatches(ignoring the case):");
        if(s1.regionMatches(true,0,s2,0,9)) //give Tutorials and Tutorialspoint as two args
            System.out.println("Found!(ignoring the case");
        else
            System.out.println("Not Found!");


        // s1.indexOf(c, i) for some c and i,
        System.out.println("\ns1 Index of:\n"+s1.indexOf(s1.charAt(1)));
        // s1.concat(s2),
        System.out.println("\ns1.concat(s2):\n"+ s1.concat(s2));
        // s1.replace(c1, c2),
        System.out.println("\nreplace char at index 0 with 2:\n"+s1.replace(s1.charAt(0),s1.charAt(2)));
        // s1.upperCase(),
        System.out.println("\nUpperCase:\n"+s1.toUpperCase());
        // s1.lowerCase().
        System.out.println("\nLowerCase:\n"+s1.toLowerCase());



        //--------------------------------------Q1 part 2-----------------------------------
        //Experimenting StringBuffer class

        StringBuffer sbuf1 = new StringBuffer(args[0]);
        StringBuffer sbuf2 = new StringBuffer(args[1]);

        System.out.println("\n------------------- Experimenting StringBuffer -----------------------\n");
        System.out.println("StringBuffer1: " + sbuf1);
        System.out.println("StringBuffer2: " + sbuf2);

        //sbuf1.length(),
        System.out.println("\nsbuf1.length():\n" + sbuf1.length());
        // sbuf1.delete(int start, int end),
        System.out.println("\nsbuf1 delete from index 2 to 4:\n" + sbuf1.delete(2,4));
        // sbuf1.deleteCharAt(int index),
        System.out.println("\ndelete char at index 2:\n" + sbuf1.deleteCharAt(2));
        // sbuf1.reverse() methods.
        System.out.println("\nsbuf1.reverse():\n" + sbuf1.reverse());
        // Invoke sbuf1.replace() with proper arguments.
        System.out.println("\nsbuf1.replace:\n" + sbuf1.replace(2,4,"New"));


        // Call the sbuf1.append().append() methods in a chain of method calls by
        // passing primitive data types and sbuf2 as the parameters.
        boolean b = true;
        char c = 'c';
        char[] cstr = new char[]{' ','c','h','a','r',' ','a','r','r','a','y'};
        CharSequence s =" CharSequence";
        double d = 3.144;
        float f=  3.14f;
        int i = 0;
        long l=1;
        Object object= " object"; String str = " string";

        System.out.println("\nAppending primitive data ypes:\n" + sbuf1.append(b).append(c).append(cstr).append(s)
                .append(d).append(f).append(i).append(l).append(object).append(str).append(sbuf2));


        // Additionally, introduce a new class named MyClass in the same file
        // but outside of StringTest class. MyClass should define nothing
        // Invoke sbuf1.append() with an object of MyClass.
        final MyClass myObj = new MyClass();
        System.out.println("\nAppend MyClass Obj:\n" + sbuf1.append(myObj));


        // Invoke sbuf1.insert() method just like you did with the append().
        System.out.println("\nInserting primitive data types to sbuf1:\n" + sbuf1.insert(3,b).insert(3,c).insert(3,cstr).insert(3,d)
                .insert(3,f).insert(3,i).insert(3,l).insert(3,object).insert(3,str)
                .insert(3,cstr,0,2).insert(3,s).insert(3,s,0,5));

        System.out.println("\nInsert MyClass Obj:\n" + sbuf1.insert(3,myObj));

    }
}
class MyClass{ //here  if public error IDE asks to make this class in ther file
    public String toString(){
        return "This is my object";
    }

}

/*Test Cases
* Following two arguments are given
* 1-  String1, String2
* 2-  StringBuffer1, StringBuffer2
* 3-  Tutorials, Tutorialspoint
*
* different methods of string and StringBuffer are invoked and the result is printed out
*/


//works both ways agr public static class bnau inside homework tab b same kam ho jata
//also it asks to be static wrna error
//mgr sir asks to do it separately
//tostring bcoz evry class extends from Object jis main ye method hota hy toh i guess ye override sort of thing ho gai
