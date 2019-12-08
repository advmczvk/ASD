import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class s18730 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(args[0]));
        String line;
        Node root = new Node('+');
        while(sc.hasNextLine()){
            line = sc.nextLine();
            char[] arr = line.toCharArray();
            if(arr.length > 2){
                root.addNode(arr);
            }else if(arr.length == 1){
                root.setValue(arr[0]);
            }
        }
        root.getWord();
        System.out.println(max);
    }
    static String max = "";
}

class Node {
    char value;
    Node right, left, parent;
    boolean leafVisited;
    Node(char value){
        this.value = value;
        this.leafVisited = false;
    }

    void addNode(char[] arr){
        if(arr.length == 3){
            if(arr[2] == 'L') {
                if (left == null) left = new Node('/');
                left.setValue(arr[0]);
            }
            if(arr[2] == 'R') {
                if (right == null) right = new Node('\\');
                right.setValue(arr[0]);
            }
        }else if(arr.length > 3){
            char[] newArr = new char[arr.length - 1];
            newArr[0] = arr[0];
            newArr[1] = arr[1];
            for(int i = 2; i < newArr.length; i++){
                newArr[i] = arr[i + 1];
            }

            if(arr[2] == 'L') {
                if (left == null) left = new Node('/');
                left.addNode(newArr);
            }
            if(arr[2] == 'R') {
                if (right == null) right = new Node('\\');
                right.addNode(newArr);
            }
        }
    }
    void getWord(){
        if(left != null){
            left.setParent(this);
            left.getWord();
        }
        if(right != null) {
            right.setParent(this);
            right.getWord();
        }
        if(left == null && right == null) s18730.max = compare(buildWord(""), s18730.max);

    }
    String buildWord(String word){
        word += getValue();
        if(parent == null) return word;
        return parent.buildWord(word);
    }
    
    String compare(String wordLeft, String wordRight){
        if(wordLeft.compareTo(wordRight) > 0) return wordLeft;
        else if(wordLeft.compareTo(wordRight) < 0) return wordRight;
        else return wordLeft.length() > wordRight.length() ? wordLeft : wordRight;
    }
    char getValue(){
        return this.value;
    }
    void setValue(char value){
        this.value = value;
    }
    void setParent(Node parent){
        this.parent = parent;
    }
}