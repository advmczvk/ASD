import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class s18730 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(args[0]));
        PQueue pq = new PQueue();
        while(scanner.hasNextLine()){
            pq.insert(new Vertex(scanner.next(), scanner.nextInt()));
        }
        Vertex tmp1, tmp2, root = null;
        System.out.println("------Laczenie slow------");
        while(pq.count > 1){
            Vertex con = new Vertex();
            tmp1 = pq.pop();
            tmp2 = pq.pop();
            con.connect(tmp1, tmp2);
            root = con;
            pq.insert(con);
            System.out.println("V1 " + tmp1.symbol + " " + tmp1.freq + " | V2 " + tmp2.symbol + " " + tmp2.freq );
            System.out.println("CON " + con.symbol + " " + con.freq);
        }
        System.out.println("-----PreOrder-----");
        root.preOrder("");
    }

}

class Vertex {
    int freq;
    String symbol;
    Vertex left, right;

    public Vertex(){
        this.freq = 0;
        this.symbol = "-";
        this.left = null;
        this.right= null;
    }
    public Vertex(String symbol, int freq){
        this.freq = freq;
        this.symbol = symbol;
        this.left = null;
        this.right= null;
    }
    void connect(Vertex v1, Vertex v2){
        this.symbol = v1.symbol + v2.symbol;
        this.freq = v1.freq + v2.freq;
        this.left = v1;
        this.right = v2;
    }

    void preOrder(String path){
        if(this.left == null && right == null) System.out.println(this.symbol + " " + path);
        if(this.left != null) this.left.preOrder(path + 0);
        if(this.right!= null) this.right.preOrder(path + 1);
    }
}

class PQueue {
    Vertex[] array;
    int count;

    public PQueue(){
        this.array = new Vertex[1];
        this.count = 0;
    }

    void insert(Vertex v){
        if(count >= array.length){
            Vertex[] tmp = new Vertex[count+1];
            for (int i = 0; i < array.length; i++) tmp[i] = array[i];
            array = tmp;
        }
        int i;
        if (count == 0) array[count++] = v;
        else {
            for (i = count - 1; i >= 0; i--) {
                if (v.freq > array[i].freq || (v.freq == array[i].freq && v.symbol.compareTo(array[i].symbol) > 0)) array[i+1] = array[i]; //jesli nowy input wystepowal wiecej razy lub jest wyzej w alfabecie, przesuwamy istniejacy element w prawo
                else break;
            }
            array[i+1] = v;
            count++;
            for (int j = 0; j < array.length; j++) System.out.print(array[j].symbol);
            System.out.println();
        }
    }

    Vertex pop(){
        Vertex v = array[--count];
        if(count < array.length){
            Vertex[] tmp = new Vertex[count];
            for (int i = 0; i < tmp.length; i++) tmp[i] = array[i];
            array = tmp;
        }
        return v;
    }
}