import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.lang.*;
import java.sql.*;


public class Server  
{ 
    public static void main(String[] args) throws IOException  
    { 
        
        ServerSocket ss = new ServerSocket(7979); 
          
        
        while (true)  
        { 
            Socket s = null; 
              
            try 
            { 
                // socket object to receive incoming client requests 
                s = ss.accept(); 
                  
                System.out.println("A new client is connected : " + s); 
                  
                // obtaining input and out streams 
                DataInputStream dis = new DataInputStream(s.getInputStream()); 
                DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
                  
                System.out.println("Assigning new thread for this client"); 
  
                // create a new thread object 
                Thread t = new ClientHandler(s, dis, dos); 
  
                // Invoking the start() method 
                t.start(); 
                  
            } 
            catch (Exception e){ 
                s.close(); 
                e.printStackTrace(); 
            } 
        } 
    } 
} 


class DB
{
    static Connection con=null;
    public static Connection getConnection()
    {
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/movie","root","root");
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            catch(ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            return con;
    }
    public static void closeConnection()
    {
            try
            {
                con.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
    }
}
class ClientHandler extends Thread {

	final DataInputStream din; 
    final DataOutputStream dout; 
    final Socket s; 
      
  
    // Constructor 
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)  
    { 
        this.s = s; 
        this.din = dis; 
        this.dout = dos; 
    } 
    @Override
    public void run()  
    {
        
        

        try
        {
            DB d=new DB();
            Connection con=null;
    
            int m=0;

        System.out.println("ALL set");
        


        int ch3=0;
        
        do {
            int ch, ch1, ch2;
            ch = din.readInt();
            switch (ch) {
                case 1:
                    String j;
                    j=din.readUTF();
                    if(!j.equals("admin")){
                        break;
                    }
                    ch1 = din.readInt();
                    switch (ch1) {
                        case 1:
                        {
                            String name;
                            int gold,silver;
                            float rating;
                            name=din.readUTF();
                            silver=din.readInt();
                            gold=din.readInt();
                            rating=din.readFloat();
                            con=d.getConnection();
                            PreparedStatement ps=con.prepareStatement("insert into cinema(movie_name,rating,silver,gold,box,seats) values(?,?,?,?,?,?);");
                            ps.setString(1,name);
                            ps.setFloat(2,rating);
                            ps.setInt(3,silver);    
                            ps.setInt(4,gold);
                            ps.setInt(5,0);    
                            ps.setString(6,"");
                            int rows=ps.executeUpdate();
                            d.closeConnection();
                        }
                            break;
                        case 2:
                        {
                            con=d.getConnection();
                            Statement stmt=con.createStatement();  
                            ResultSet rs=stmt.executeQuery("select movie_id,movie_name,rating from cinema;");

                            while(rs.next()) 
                            { 
                                dout.writeUTF(rs.getInt(1)+" ) "+rs.getString(2)+"    "+rs.getFloat(3));
                            }
                            dout.writeUTF("null");
                            
                            int del = din.readInt();

                            try{
                            	PreparedStatement ps2=con.prepareStatement("delect from users where movie_id=?;");
	                            ps2.setInt(1,del);
	                            int rows=ps2.executeUpdate();
                            }
                            catch(Exception ee)
                            {
                            	System.out.println("okk");
                            }

                            PreparedStatement ps1=con.prepareStatement("delete from cinema where movie_id=?;");
                            ps1.setInt(1,del);
                            int rows=ps1.executeUpdate();
                            
                            
                            d.closeConnection();
                        }
                            break;
                        case 3:
                        {
                            con=d.getConnection();
                            Statement stmt=con.createStatement();  
                            ResultSet rs=stmt.executeQuery("select movie_id,movie_name,rating from cinema;");

                            while(rs.next()) 
                            { 
                                dout.writeUTF(rs.getInt(1)+" ) "+rs.getString(2)+"    "+rs.getFloat(3));
                            }
                            dout.writeUTF("null");

                            int box = din.readInt();
                            PreparedStatement ps3=con.prepareStatement("Select box from cinema where movie_id=?;");
                            ps3.setInt(1,box);
                            ResultSet res=ps3.executeQuery();
                            if(res.next())
                            {
                            	dout.writeUTF("Total boxoffice: "+res.getInt(1));
                            }
                            else
                            {
                            	dout.writeUTF("sorry incorrect choice");
                            }
                            
                            d.closeConnection();
                        }
                            break;
                        case 4:
                        {
                            con=d.getConnection();
                            Statement stmt=con.createStatement();  
                            ResultSet rs=stmt.executeQuery("select movie_id,movie_name,rating from cinema;");

                            while(rs.next()) 
                            { 
                                dout.writeUTF(rs.getInt(1)+" ) "+rs.getString(2)+"    "+rs.getFloat(3));
                            }
                            dout.writeUTF("null");

                            int resi = din.readInt();
                            
                            PreparedStatement ps31=con.prepareStatement("UPDATE cinema SET seats='' WHERE movie_id =?;");
                            ps31.setInt(1,resi);
                            int rows=ps31.executeUpdate();
                            try{
                            	PreparedStatement ps41=con.prepareStatement("delect from users where movie_id=?;");
	                            ps41.setInt(1,resi);
	                            rows=ps41.executeUpdate();

                            }
                            catch(Exception ee)
                            {
                            	System.out.println("okk in clear seat!");
                            }
                            

                            d.closeConnection();
                        }
                            break;
                        
                    }
                    break;

                case 2:
                {
                    int interest;
                    con=d.getConnection();

                    Statement stmt=con.createStatement();  
                    ResultSet rs=stmt.executeQuery("select movie_id,movie_name,rating from cinema");
                    while(rs.next()) 
                    { 
                        dout.writeUTF(rs.getInt(1)+" ) "+rs.getString(2)+"    "+rs.getFloat(3));
                    }
                    dout.writeUTF("null");
                    d.closeConnection();
                    interest = din.readInt();


                    ch2 = din.readInt();
                    switch (ch2) {
                        case 1:
                        {
                            String username = din.readUTF();
                            String password = din.readUTF();

                           
                            con=d.getConnection();
                            PreparedStatement ps9=con.prepareStatement("select seats from cinema where movie_id =? ;");
                            ps9.setInt(1,interest);
                            ResultSet rs9=ps9.executeQuery();
                            rs9.next();
                            dout.writeUTF(rs9.getString(1));
                            d.closeConnection();
                            
                            int n = din.readInt();
                            for (int i = 0; i < n; i++) {
                                int col, rows;
                                con=d.getConnection();
                                rows = din.readInt();
                                col = din.readInt();
                                
                                con=d.getConnection();
                                

                                PreparedStatement ps21=con.prepareStatement("SELECT seats FROM users WHERE movie_id=? and username=? and password=?; ");
                                ps21.setInt(1,interest);
                                ps21.setString(2,username);
                                ps21.setString(3,password);
                                ResultSet rs21=ps21.executeQuery();
                                if(!rs21.next())
                                {
                                    System.out.println("This is new user behave properly or else we will be srewed!!");
                                    PreparedStatement ps1=con.prepareStatement(" insert into users(username,password,bill,movie_id,seats) values(?,?,?,?,?); ");
                                    ps1.setString(1,username);
                                    ps1.setString(2,password);
                                    ps1.setInt(3,0);
                                    ps1.setInt(4,interest);
                                    ps1.setString(5,rows+"."+col+" ");
                                    int row=ps1.executeUpdate();
                                    PreparedStatement ps3=con.prepareStatement("select seats from cinema where movie_id=?;");
                                    ps3.setInt(1,interest);
                                    ResultSet rs1=ps3.executeQuery();
                                    rs1.next();
                                    String seatsuser=rs1.getString(1);
                                    seatsuser=seatsuser+row+"."+col+" ";
                                    PreparedStatement ps4=con.prepareStatement("UPDATE cinema SET seats=? WHERE movie_id=? ;");
                                    ps4.setString(1,seatsuser);
                                    ps4.setInt(2,interest);
                                    row=ps4.executeUpdate();
                            


                                }
                                else{
                                    System.out.println("This is old user!!");
                                    String seatsuser=rs21.getString(1);
                                    seatsuser=seatsuser+rows+"."+col+" ";
                                    PreparedStatement ps12=con.prepareStatement("UPDATE users SET seats=? WHERE movie_id=? and username=? And password=?;");
                                    ps12.setString(1,seatsuser);
                                    ps12.setInt(2,interest);
                                    ps12.setString(3,username);
                                    ps12.setString(4,password);
                                    int row=ps12.executeUpdate();
                                    PreparedStatement ps31=con.prepareStatement("select seats from cinema where movie_id=?;");
                                    ps31.setInt(1,interest);
                                    ResultSet rs31=ps31.executeQuery();
                                    rs31.next();
                                    seatsuser=rs31.getString(1);
                                    String mod=rows+"."+col+" ";
                                    seatsuser=seatsuser+mod;
                                    PreparedStatement ps4=con.prepareStatement("UPDATE cinema SET seats=? WHERE movie_id=? ;");
                                    ps4.setString(1,seatsuser);
                                    ps4.setInt(2,interest);
                                    rows=ps4.executeUpdate();

                                }
                                
                                con=d.getConnection();
                                PreparedStatement ps921=con.prepareStatement("select seats from cinema where movie_id= ?; ");
                                ps921.setInt(1,interest);
                                ResultSet rs921=ps921.executeQuery();
                                rs921.next() ;
                                dout.writeUTF(rs921.getString(1));
                                d.closeConnection();
                                
                            }
                        }
                        break;
                        case 2:
                        {
                            String username = din.readUTF();
                            String password = din.readUTF();
                            
                            

                            con=d.getConnection();
                            PreparedStatement ps22=con.prepareStatement("SELECT bill FROM users WHERE movie_id=? and username=? And password=? ; ");
                            ps22.setInt(1,interest);
                            ps22.setString(2,username);
                            ps22.setString(3,password);
                            ResultSet rs22=ps22.executeQuery();
                            if(rs22.next())
                            {
                                int bill=rs22.getInt(1);
                                if(bill>0)
                                    dout.writeUTF("Bill to be paid = "+bill+"\nThanks for paying the bill !");
                                else
                                    dout.writeUTF("refund is in progress \nRefund amount= "+bill);
                            }
                            else{
                                dout.writeUTF("This username and password does not match!!...");
                            }
                            d.closeConnection();

                        }
                            break;
                        case 3:
                        {
                            String username = din.readUTF();
                            String password = din.readUTF();
                            

                            con=d.getConnection();

                            PreparedStatement psd=con.prepareStatement("SELECT seats FROM users WHERE movie_id=? and username=? And password=? ; ");
                            psd.setInt(1,interest);
                            psd.setString(2,username);
                            psd.setString(3,password);
                            ResultSet rsd=psd.executeQuery();
                            if(rsd.next())
                            {
                                dout.writeUTF("passed");
                                PreparedStatement ps9=con.prepareStatement("select seats from cinema where movie_id =? ;");
	                            ps9.setInt(1,interest);
	                            ResultSet rs9=ps9.executeQuery();
	                            if(rs9.next())
	                            {
	                            	dout.writeUTF(rs9.getString(1));
	                            	System.out.println("no problem");
	                            }
	                            else
	                            {
	                            	System.out.println("problem");
	                            }
	                            
                             
                                int col, rows;
                                rows = din.readInt();
                                col = din.readInt();
                                String userseat=rsd.getString(1);
                                String sub=rows+"."+col+" ";

                                if(userseat.contains(sub))
                                {
                                    dout.writeUTF("passed");
                                    userseat=userseat.replace(sub,"");
                                    PreparedStatement ps1=con.prepareStatement("UPDATE users SET seats= ? WHERE movie_id=? and username=? And password=? ; ");
                                    ps1.setString(1,userseat);
                                    ps1.setInt(2,interest);
                                    ps1.setString(3,username);
                                    ps1.setString(4,password);
                                    rows=ps1.executeUpdate();
                                    System.out.println("okk in updatexd in user ");

                                    PreparedStatement ps3=con.prepareStatement("select seats from cinema where movie_id=?;");
                                    ps3.setInt(1,interest);
                                    ResultSet rs2=ps3.executeQuery();
                                    rs2.next();
                                    String seatsuser=rs2.getString(1);
                                    seatsuser=seatsuser.replace(sub,"");
                                    System.out.println("okk in selected in cinema");

                                    PreparedStatement ps4=con.prepareStatement("UPDATE cinema SET seats=? WHERE movie_id=? ;");
                                    ps4.setString(1,seatsuser);
                                    ps4.setInt(2,interest);
                                    rows=ps4.executeUpdate();
                                    System.out.println("okk in updated in cinema in cinema");
                                }
                                else
                                {
                                    dout.writeUTF("npassed \nthis seat is not book by you!!");
                                }

                            }
                            else
                            {
                                dout.writeUTF("npassed \nThis username and password does not match!!...");
                            }

                            

                            d.closeConnection();
                        }
                            break;

                    }
                     
                }
                break;   
            }

            ch3 = din.readInt();
        } while (ch3 != 0);

        s.close();
        
        con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    
    }
}

