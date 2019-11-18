import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class s18730 {
    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(args[0]));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int current = 0, previous, equalCount = 0;
        int[][] results = new int[2][2];
        results[1][0] = 1;
        boolean isAsc = false, isFirst = true;

        while(scanner.hasNext()){
            if(!isFirst){
                //Updating previous and current numbers
                previous = current;
                current = scanner.nextInt();

                if(previous == current){
                    equalCount++;
                    results[1][0]++;
                    results[1][1] += current;
                }else if(previous < current){ //Ascending subsequence
                    if(!isAsc){               //If previously the subsequence was descending, we need to update our results
                        results[1][0] = 1;
                        results[1][1] = 0;
                        if(equalCount > 0) {  //Acknowledging previous appearance of steady subsequence
                            results[1][1] += equalCount * previous + previous; //Getting value from steady subsequence
                            results[1][0] += equalCount;                       //Updating how many equal numbers we encountered
                        }
                        isAsc = true;
                    }
                    if(results[1][1] == 0) results[1][1] = previous + current;
                    else results[1][1] += current;
                    results[1][0]++;
                }else if(previous > current){   //Descending
                    if(isAsc){
                        results[1][0] = 1;
                        results[1][1] = 0;
                        if(equalCount > 0) {
                            results[1][1] += equalCount*previous + previous;
                            results[1][0] += equalCount;
                        }
                        isAsc = false;
                    }
                    if(results[1][1] == 0) results[1][1] = previous + current;
                    else results[1][1] += current;
                    results[1][0]++;
                }

                //Updating highest monotonic string
                if(results[0][0] < results[1][0]){
                    results[0][0] = results[1][0];
                    results[0][1] = results[1][1];
                }

            if(previous != current) equalCount = 0; //If the numbers are different, we need to reset the amount of encountered equal numbers
            }else{
                previous = scanner.nextInt(); //Getting first argument in case it is the last

                if(!scanner.hasNext()) {    //If it's the last, we shut it down and display subsequence of length 1 and value of the current (previous) number
                    results[0][0] = 1;
                    results[0][1] = previous;
                }else{                      //If it's not last, we need to determine whether it's ascending or descending
                    current = scanner.nextInt();
                    results[1][0] = 2;
                    results[1][1] = previous + current;
                    if(previous < current) isAsc = true;
                }
                isFirst = false;
            }
        }
        System.out.println(results[0][0] + " " + results[0][1]);
        scanner.close();
    }
}
