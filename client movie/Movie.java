import java.util.*;
import java.io.*;

 

public class Movie implements Serializable{
	
   
    char [][] seats =new char[8][10];
    String name;
    int gold,silver,box,bill;
    float rating;

    Movie(){
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<10;j++)
            {
                seats[i][j]=' ';
            }
        }
        box=0;
        rating=0.0f;
    }
    

    void addmovie(String name, int gold, int silver, float rating){
        this.name=name;
        this.gold=gold;
        this.silver=silver;
        this.rating=rating;
    }
    String show_stat(){
        return name+"    Rating:"+rating;
    }
    String show_box(){
        return ("Movie name: "+name+"\nTotal boxoffice: "+box);

    }

    void show_over(){
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<10;j++)
            {
                seats[i][j]=' ';
            }
        }
    }



    void show(){
        char charac='A';

        for(int i=1;i<=10;i++)
        {
            System.out.print("    "+i);
        }
        System.out.println();
        for(int i=0;i<8;i++)
        {
            System.out.println();
            System.out.print(charac++);
            for(int j=0;j<10;j++)
            {
                System.out.print("  ("+seats[i][j]+")");
            }
            System.out.println();

        }
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("----------------------------------------------------");
        System.out.println("                      Screen");

    }
    HashMap<String, String> login = new HashMap<>();

    HashMap<String, Set<ArrayList<Integer> > > profile= new HashMap<>();

    String hash_put(String username,int row,int col)
    {
        ArrayList<Integer> a= new ArrayList<>();
        a.add(row);
        a.add(col);
        Set<ArrayList<Integer>> s=new HashSet<>();
        s.add(a);
        profile.put(username,s);
        seats[row][col]='B';
        
        if(row<3){
            bill+=gold;
        }
        else{
            bill+=silver;
        }
        return ("OK seat booked");
    }
    String book(int row,int col,String username) {

        if (seats[row][col] == 'B') {
            return ("This seat is already book..Sorry!!");

        }
        else {
            ArrayList<Integer> a= new ArrayList<>();
            a.add(row);
            a.add(col);
            Set<ArrayList<Integer>> s=new HashSet<>(profile.get(username));
            s.add(a);


            profile.put(username,s);
            seats[row][col]='B';
            
            if(row<3){
                bill+=gold;
            }
            else{
                bill+=silver;
            }
            return ("OK seat booked");
        }

    }
    String pay(){
        if(bill==0)
        {
            return ("No bill to be paid.");
        }
        else if(bill<0)
        {
            return ("Refund will process within 3 working days");
        }
        else
        {
            box=box+bill;
            int bills=bill;
            bill=0;
            return ("The bill amount is "+bills+"\nPayment successful!!");
            
        }
    }

    String delete(int row,int col,String username){

        show();
        if(seats[row][col]!='B'){
            return ("The seat is not book!");
        }
        else
        {
            ArrayList<Integer> a= new ArrayList<>();
            a.add(row);
            a.add(col);

            if(profile.get(username).contains(a)){
                profile.get(username).remove(a);
                seats[row][col]=' ';
                show();
                if(row<3)
                {
                    box-=gold;
                    bill-=gold;
                }
                else
                {
                    box-=silver;
                    bill-=silver;
                }
                return ("Seat has been cancled");
            }
            else{
                return ("This seat is not booked by You!");
            }


        }
        
        
    }
}




