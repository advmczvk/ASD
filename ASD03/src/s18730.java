import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class s18730 {
    public static void main(String[] args) throws FileNotFoundException {
        Node root = new Node();
        root.isRoot = true;
        Scanner sc = new Scanner(new File(args[0]));
        int k = sc.nextInt();
        int p = 0;
        int index = 0;
        while(sc.hasNext()){
            root = root.insert(index++, sc.nextInt());
        }
        for (int i = 0; i < k; i++) {
                if(root.get(p).value % 2 != 0){
                root = root.insert(p + 1, root.get(p).value - 1);
                p += root.get(p).value;
            }else{
                int deletedValue = root.get(p+1).value;
                int deletedIndex = (p + 1) % root.size;
                root = root.remove(deletedIndex);
                if(root == null) return;
                if(p > deletedIndex) p--;
                p += deletedValue;
            }
            p = p >= root.size ? p % root.size : p;
        }
        root.printFromPoint(p);
    }
}
class Node {
    boolean isRoot;
    int value, height, size;
    public Node left, right;

    Node() {
        value = -5;
        height = 0;
        size = 0;
        left = null;
        right = null;
    }
    Node(int val) {
        value = val;
        height = 1;
        size = 1;
        left = null;
        right = null;
    }
    Node get(int index) {
        int leftSize = left == null ? 0 : left.size;
        index = index >= size ? index % size : index;
        if (index < leftSize) return left.get(index);
        else if (index > leftSize) return right.get(index - leftSize - 1);
        else return this;
    }

    Node insert(int index, int val) {
        if(this.left == null && this.right == null && this.isRoot) return new Node(val);
        int lWeight = left == null ? 0 : left.size;
        if(index <= lWeight) left = left != null ? left.insert(index, val) : new Node(val);
        else right = right != null ? right.insert(index - lWeight - 1, val) : new Node(val);
        check();
        return balance();
    }
    
    Node remove(int index) {
        int leftSize = left == null ? 0 : left.size;
        index = index >= size ? index % size : index;
        if (index < leftSize) left = left.remove(index);
        else if (index > leftSize) right = right.remove(index - leftSize - 1);
        else if (left == null && right == null) return null;
        else if (left != null && right == null) return left;
        else if (left == null && right != null) return right;
        else {
            Node temp = right;
            while (temp.left != null) temp = temp.left;
            value = temp.value;
            right = right.remove(0);
        }
        check();
        return balance();
    }

    Node balance() {
        Node result = this;
        if (getBalance() == -2) {
            if (left.getBalance() == 1) left = left.rotateLeft();
            result = rotateRight();
        } else if (getBalance() == 2) {
            if (right.getBalance() == -1) right = right.rotateRight();
            result = rotateLeft();
        }
        return result;
    }

    Node rotateLeft() {
        assert right != null;
        Node root = this.right;
        this.right = root.left;
        root.left = this;
        this.check();
        root.check();
        return root;
    }

    Node rotateRight() {
        assert left != null;
        Node root = this.left;
        this.left = root.right;
        root.right = this;
        this.check();
        root.check();
        return root;
    }

    void check() {
        int leftW, rightW, leftH, rightH;
        if(left == null){
            leftW = 0;
            leftH = 0;
        }else{
            leftW = left.size;
            leftH = left.height;
        }

        if(right == null){
            rightW = 0;
            rightH = 0;
        }else{
            rightW = right.size;
            rightH = right.height;
        }
        size = leftW + rightW + 1;
        if(leftH >= rightH){
            height = leftH+1;
        }else{
            height = rightH+1;
        }
    }

    int getBalance() {
        return left == null || right == null ? 0 : right.height - left.height;
    }

    void printFromPoint(int point){
        for (int i = point; i < size; i++) {
            System.out.print(get(i).value + " ");
        }
        for (int i = 0; i < point; i++) {
            System.out.print(get(i).value + " ");
        }
    }
}
