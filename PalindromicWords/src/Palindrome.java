import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Palindrome {

    public static void main(String[] args) {

        HashMap<String, ArrayList<String>> mapBagofTasks = new HashMap<String, ArrayList<String>>();
        try {
            // TODO code application logic here

            FileInputStream in = null;
            BufferedReader reader = null;

            in = new FileInputStream("C:\\Users\\Ayesha\\Downloads\\My Stuff\\Advanced Programming\\Assignments\\Assignment-2\\words.txt");

            reader = new BufferedReader(new InputStreamReader(in));

            //System.out.println("Reading File word by word using BufferedReader");

            String word = reader.readLine();

            while(word != null){
                //System.out.println(word);

                Integer wordLength = word.length(); //since maphas arraylist bags with the length of word as key //check

                if(mapBagofTasks.containsKey(wordLength.toString())) {

                    mapBagofTasks.get(wordLength.toString()).add(word); //if bag exists get it and add word to it
                } else {
                    ArrayList<String> arr = new ArrayList<String>();//if does not exist make new add word
                    arr.add(word);

                    mapBagofTasks.put(wordLength.toString(), arr); //and add it to all maps into the hashMap
                }

                word = reader.readLine(); //read next word
            }
        } catch (Exception ex) {
            Logger.getLogger(Palindrome.class.getName()).log(Level.SEVERE, null, ex);
        }


        //Spawn Worker Threads
        System.out.println("Enter the number of worker Threads: ");
        Scanner scanner = new Scanner(System.in);
        int w = scanner.nextInt();

        while(w < 1)
        {
            System.out.println("The Number of worker Threads cannot be less than 1!\nEnter Number again: ");
            w = scanner.nextInt();
        }

        //Shared ArrayList for Palindromes
        ArrayList<String > sharedArr = new ArrayList<String>();
        sharedArr.add(""); //0th index is the result Status string for all threads
        sharedArr.add("0"); //1st index will be the palindromeCount

        Thread Threads[] = new Thread[w];
        int size = mapBagofTasks.size();

        //System.out.println("Totals bags: " + size);

        Boolean TakenMaps[] = new Boolean[size];
        for(int i=0;i<size;i++)
            TakenMaps[i] = false;

        int allMapsaloted = 0;
        int alottedBags = 0;
        for(int i=0;i<w;i++)
        {
            System.out.println("\nCreating Thread " + (i+1));

            //System.out.println("How many Bags do you want to pass to this thread ");
            int Max = size;
            int Min = 1;
            Random ran = new Random(); //Choose The Number of bags to be chosen

            int NumberofBags = ran.nextInt((Max - Min) +1 ) + Min; //No need to take input choose randomly


            //System.out.println("number of bags : " + NumberofBags);

            if(i == w-1) // before making last thread make sure all the bags are selected jo reh gaye hain pass them to this thread
            {
                for(int k=0; k<size; k++) //if all are already aloted
                {
                    if(TakenMaps[k] == true)
                        alottedBags++;
                }

                //Make sure No bag is left
                if(alottedBags + NumberofBags < size) // = ho toh boht achi bat hai par agr > size ho b jaye toh khair hai kyunk Taken Maps check rakhta hai k har ma aik bar select ho aur agr zada bags hain toh free bags nahi milyn gy toh bbreak aur thread ko free ya koi b nhi milyga bag aur har bag aik he bar select hona chahiye
                {
                    NumberofBags = size - alottedBags;
                }
            }
            //get bags
            ArrayList<ArrayList<String>> bags = new ArrayList<ArrayList<String>>();

            for(int j=0;j<NumberofBags;j++){
                int max = size;
                int min = 0;
                Random rand = new Random(); //Choose randomly any map

                //nextInt is normally exclusive of the top value,
                // so add 1 to make it inclusive
                int index = rand.nextInt((max - min) ) + min;
                while(TakenMaps[index] == true) //if already given to any thread  choose again
                {
                    for(int k=0; k<size; k++) //if all are already aloted
                    {
                        if(TakenMaps[k] == true)
                            allMapsaloted++;
                    }
                    if(allMapsaloted == size){
                        break;
                    }
                    allMapsaloted = 0;
                    index = rand.nextInt((max - min) ) + min;
                }

                TakenMaps[index] = true;

                //System.out.println("index" + index);
                if(allMapsaloted == size){

                    //System.out.println("All the Maps are being alotted.\n");
                    break;
                }
                Iterator<String> iterator = mapBagofTasks.keySet().iterator();
                int count =0;
                while(iterator.hasNext()) {

                    String key = iterator.next();

                    if(count == index) {
                        ArrayList<String> arrayList = mapBagofTasks.get(key);
                        bags.add(arrayList);
                        break;
                    }
                    count++;
                }
            }

            PalindromeWorker pw = new PalindromeWorker(bags, sharedArr);
            Threads[i] = new Thread(pw, "Thread "+(i+1));
            Threads[i].start();
        }

        PalindromeWriter palindromeWriter = new PalindromeWriter(Threads,sharedArr);
        Thread thread = new Thread(palindromeWriter,"Writer Thread");
        thread.start();


       }
}
