import java.util.ArrayList;

public class PalindromeWorker implements Runnable {

    private final ArrayList<String> SharedArr;
    private final ArrayList<ArrayList<String>> bags;  //These bags are not shared every Thread has unique bags

    public PalindromeWorker( ArrayList<ArrayList<String>> bags, ArrayList<String> SharedArr){

        this.bags = bags;
        this.SharedArr = SharedArr;
    }
    @Override
    public void run() {
        
        //Find palindromes in all the bags
        //iterate List to get a list and then iterate it
        ArrayList<String> arrayList = new ArrayList<String>();
        int count = 0;

        for(int i=0;i<bags.size();i++){

            ArrayList<String> bag = bags.get(i);

            for(int j=0;j < bag.size() ; j++){

                String word = bag.get(j);
                String reverse = new StringBuffer(word).reverse().toString();
                //check if reverse exists in the map (obvio same length map hy aurreverse ki length b same hogi toh aik he for loop main check
                if(bag.contains(reverse)){
                    arrayList.add(word);
                    count++;
                }
            }
        }
        try {
            AddtoSharedarr(arrayList,count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    private void AddtoSharedarr(ArrayList<String> arrayList,int count) throws InterruptedException
    {
        // synchronously add them to Shared array list

        synchronized (SharedArr){

            for(int i=0;i<arrayList.size(); i++){

                SharedArr.add(arrayList.get(i));
            }

            String result = SharedArr.get(0);
            result = result + " Worker name: " + Thread.currentThread().getName() + ",  palindrome_count: " +  count;
            SharedArr.set(0,result);

            Integer PalindromeCount = Integer.parseInt(SharedArr.get(1)) ;
            PalindromeCount = PalindromeCount +count;

            SharedArr.set(1,PalindromeCount.toString());

        }
    }
}
