import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PalindromeWriter implements Runnable {

    private final ArrayList<String> sharedArr;
    private final Thread Threads[];

    public PalindromeWriter(Thread Threads[], ArrayList<String> sharedArr) {
        this.Threads = Threads;
        this.sharedArr = sharedArr;
    }

    @Override
    public void run() {


        //join call for all the threads then output the result
        for (Thread thread : Threads) {
            try {
                //System.out.println("Writer Thread is waiting for Worker " + thread.getName());
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            OutputResults();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void OutputResults() throws InterruptedException {

        synchronized (sharedArr)
        {
            PrintWriter outputStream = null;

            //0th and 1st index are specified

            try {
                outputStream = new PrintWriter(new FileWriter("C:\\Users\\Ayesha\\Desktop\\results.txt"));

                for(int i = 2; i< sharedArr.size() ;i++) {
                    String word = sharedArr.get(i);
                    outputStream.println(word);
                }
                outputStream.println(sharedArr.get(0));
                outputStream.println("Total count for Palindromes = " + sharedArr.get(1));


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        }

    }
}
