import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FindSentences {
    public static void main(String[] args) {

        int min = 0;
        int max = 0;

        String Sentences = "";

        //Your program should handle both cases:
        if(args.length !=3)
        {
            if(args.length == 0) {
                //No arguments are given
                System.out.println("\nPrecondition: Min and Max will be positive integers less than 1000, and Min <= Max.");
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter min: ");
                min = scanner.nextInt();
                System.out.println("Enter max: ");
                max = scanner.nextInt();

                scanner.nextLine(); //Consume newline left-over

                System.out.println("Enter Text: ");
                Sentences = scanner.nextLine();

                while (min > max) {
                    System.out.println("min cannot be > max.\n Enter min again: ");
                    min = scanner.nextInt();
                }
                while (min < 0) {
                    System.out.println("min cannot be -ve.\n Enter min again: ");
                    min = scanner.nextInt();
                }
                while (max < 0) {
                    System.out.println("max cannot be -ve.\n Enter max again: ");
                    max = scanner.nextInt();
                }
                while (min > 1000) {
                    System.out.println("min cannot be > 1000.\n Enter min again: ");
                    min = scanner.nextInt();
                }
                while (max > 1000) {
                    System.out.println("max cannot be > 1000.\n Enter max again: ");
                    max = scanner.nextInt();
                }
            }
            if(args.length ==1)
            {
                //only min is entered
                min =Integer.parseInt(args[0]);
                System.out.println("\nPrecondition: Min and Max will be positive integers less than 1000, and Min <= Max.");
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter max: ");
                max = scanner.nextInt();

                scanner.nextLine(); //consume new0line leftover

                System.out.println("Enter Text: ");
                Sentences = scanner.nextLine();

                while (max < min) {
                    System.out.println("max cannot be < min.\n Enter max again: ");
                    max = scanner.nextInt();
                }
                while (max < 0) {
                    System.out.println("max cannot be -ve.\n Enter max again: ");
                    max = scanner.nextInt();
                }
                while (max > 1000) {
                    System.out.println("max cannot be > 1000.\n Enter max again: ");
                    max = scanner.nextInt();
                }
            }
            if(args.length ==2)
            {
                //Only String text is not being entered
                min = Integer.parseInt(args[0]);
                max = Integer.parseInt(args[1]);
                Scanner scanner = new Scanner(System.in);

                System.out.println("Enter Text: ");
                Sentences = scanner.nextLine();
            }
        }
        else
        {
            min =  Integer.parseInt(args[0]);
            max = Integer.parseInt(args[1]);

            Sentences = args[2];
        }

        System.out.println("min: " + min);
        System.out.println("max: " + max +"\n\nInput Text:\n");


        ArrayList<String> StringArray = new ArrayList<String>();
        //ArrayList<String> TempArray = new ArrayList<String>();

        for (String retval: Sentences.split("(?<=\\b[.?!])") ) {
            //remove leading and trailing white spaces and add one space at start for the EOL and grammer space
            //also it will count only one clank between sentences
            retval = retval.trim();
            //retval = " " + retval; // is ki zarurat nahi space nikal he di hy start main aur agy zarurat k moka pr insert kr di na b insert krty toh thekk tha
            StringArray.add(retval);
            //TempArray.add(retval);
            System.out.println(retval + " length: " + retval.length());
        }

        System.out.println("\n");
        ArrayList<String> resultArray = new ArrayList<String>();

        //Acha In my solution adding both individual and consecutive sentences to result set
        //The real work of finding sentences
        for(int i=0;i< StringArray.size()-1; i++)
        {
            System.out.println("StringArray: " + StringArray.get(i));
            int Length1 = StringArray.get(i).length();
            int Length2 = StringArray.get(i+1).length();

            //for Individual sentences
            if(min<=Length1 && Length1<= max) {

                if(!resultArray.contains(StringArray.get(i)))
                    resultArray.add(StringArray.get(i));
            }

            //for the last sentence
            if(min<=Length2 && Length2<= max) {

                if(!resultArray.contains(StringArray.get(i + 1)))
                    resultArray.add(StringArray.get(i + 1));
            }

            boolean add = false;
            //for consecutive sentences
            //check k ye current sentence sy agy consecutive sentence ki length agr required ban rhi hy toh add it to result
            //temp array is liye use ki taky agr koi change araha hy toh vo original array main an aye kyunk us sy phir consecutive check k
            //krty huyr lrngth kharab ho jayege, nahi is ky baghair b theek hy kyunk change toh usi index pr ayega na aur us par jitni marzi appended
            //strings hon fark nahi parta next index toh theek hy next iteration main wahan sy check.
            //aur yahan par hum aik he index par multiple sentences concate kr rhy  ye for loop isi aik index wali ko check krti next itertion main usii concatenated k agy sy
            for(int j= i+1; j< StringArray.size(); j++)
            {
                //int Length = TempArray.get(i).length() + StringArray.get(j).length();
                int Length = StringArray.get(i).length() + StringArray.get(j).length() + 1; // 1 for the space at start of sentence
                if(min <= Length && Length <= max)
                {
                    //TempArray.set(i,TempArray.get(i) + StringArray.get(j));
                    StringArray.set(i,StringArray.get(i) + " " + StringArray.get(j)); //ye space for the newly concatenated sentence
                    add = true;
                }
                else
                    break;
            }

            if(add == true)
            {
                //resultArray.add(TempArray.get(i));
                resultArray.add(StringArray.get(i));
            }

        }

        System.out.println("\nIndividual and Consecutive Sentences (where min<=length<=max):\n");
        for(int i=0; i<resultArray.size();i++)
        {
            System.out.println(resultArray.get(i));
        }
    }
}
