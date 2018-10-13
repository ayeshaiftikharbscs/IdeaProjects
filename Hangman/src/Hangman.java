import sun.plugin2.applet.context.NoopExecutionContext;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hangman{
    public static void main(String[] args) {

        System.out.println("Welcome to Hangman!");
        HangmanLexicon HL = new HangmanLexicon();

        final int NoOfGuesses = 8;
        int wordCount = HL.getWordCount();

        int max = wordCount;
        int min = 0 ;
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        //int index = rand.nextInt((max - min) ) + min;
        int index = 30;

        String GuessingWord = HL.getWord(index);

        GuessingWord = GuessingWord.toUpperCase(); //taky jis b case main ho guess aur word dono same case main hon aur index get ho jaye word he guess krna hai upper ho ya lower

        int NoOfLeftGuesses = NoOfGuesses;

        String guess;

        StringBuffer tobeGuessedWord = new StringBuffer(GuessingWord);
        for(int i=0;i< tobeGuessedWord.length(); i++)
            tobeGuessedWord.replace(i,i+1,"-");

        Scanner scanner = new Scanner(System.in);

        System.out.println(GuessingWord);
         while(NoOfLeftGuesses > 0) {

            System.out.println("\nThe word now looks like " + tobeGuessedWord);

            System.out.println("You have " + NoOfLeftGuesses + " guesses left.");


            System.out.println("Your guess: ");
            guess = scanner.next();

            if(guess.length()>1) {
                while(guess.length() > 1)
                {
                    System.out.println("Illegal guess!");
                    System.out.println("Enter Your guess again: ");
                    guess = scanner.next();
                }
            }

            guess = guess.toUpperCase();

            int indx = GuessingWord.indexOf(guess);
             System.out.println("index: " + indx);
            if(indx == -1)
            {
                System.out.println("There are no "+ guess + "\'s in the word.");
                NoOfLeftGuesses--;
            }
            else
            {
                //check k is ki agr multiple guesses hain aur
                //tobeGuessedWord main sy get array of incies of this character
                //pehle compare agr array ka size one toh matlab aik
                //he occurence hai nahi toh one by one compare both index arrays
                //aur jahan tobeGuessedWord array khatam us sy aglay index ko idhr pass
                //tobeGuessedWord wali toh aik kam he hogi size main kyunk iss main toh abi ahi dala na
                //character toh agr uska size he pas kr du toh vo next index ko point kr rhi hogi

                //agar dash main aik bar b nahi aya toh dusri index array ka[0] nahi toh dash index array +1 jo k
                //dusri array ka hoga aur agla index return karega

                ArrayList<Integer> IndexListofGuesssingword = new ArrayList<Integer>();
                ArrayList<Integer> IndexListoftobeGuessedWord = new ArrayList<Integer>();

                for (int ind = GuessingWord.indexOf(guess);
                     ind >= 0;
                     ind = GuessingWord.indexOf(guess, ind + 1))
                {
                    IndexListofGuesssingword.add(ind);
                }
                for (int ind = tobeGuessedWord.indexOf(guess);
                     ind >= 0;
                     ind = tobeGuessedWord.indexOf(guess, ind + 1))
                {
                    IndexListoftobeGuessedWord.add(ind);
                }

                if(IndexListoftobeGuessedWord.size() < IndexListofGuesssingword.size())
                {
                    indx = IndexListofGuesssingword.get(IndexListoftobeGuessedWord.size());
                }
                tobeGuessedWord.replace(indx,indx+1,guess);

            }
            if(GuessingWord.compareTo(tobeGuessedWord.toString())==0)
                break;
        }

        if(GuessingWord.compareTo(tobeGuessedWord.toString()) !=0)
        {
            System.out.println("\nYou're completely hung.");
            System.out.println("The word was: " + GuessingWord);
            System.out.println("You Lose.");
        }
        else
        {
            System.out.println("\nThat guess is correct.");
            System.out.println("You guessed the word: " + GuessingWord);
            System.out.println("You win.");
        }

    }
}
