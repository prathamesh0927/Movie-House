import java.io.*;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Scanner;


public class Client {
    static void showw(char[][] seats){
            char charac='A';

        for(int i=1;i<=9;i++)
        {
            System.out.print("    "+i);
        }
        System.out.println();
        for(int i=0;i<8;i++)
        {
            System.out.println();
            System.out.print(charac++);
            for(int j=0;j<9;j++)
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

    
     
     
    public static void main(String[] args){
        Scanner scan =new Scanner(System.in);
        try{
            int m=0;
        
        Socket s = new Socket("localhost", 7979);
        System.out.println("Connected");
        DataInputStream din =new DataInputStream(s.getInputStream());
       
        DataOutputStream dout=new DataOutputStream(s.getOutputStream());
   
        
        
        System.out.println("ALL set");
              

        int ch3;
        String line;
        
        
        do{
            System.out.println("You want to login as \n1)admin\n2)customer");
            int ch,ch1,ch2;
            ch=scan.nextInt();
            dout.writeInt(ch);

            switch (ch)
            {
                case 1:
                {
                    System.out.println("Enter Admin password");
                    String j;
                    scan.nextLine();
                    j=scan.next();
                    dout.writeUTF(j);
                    if(!j.equals("admin")){
                    	System.out.println(j+"! This is wrong password.");
                        break;
                    }
                    System.out.println("Enter you choice\n1)Add Movie\n2)delete\n3)box office\n4)clear seats booking");
                    ch1=scan.nextInt();
                    dout.writeInt(ch1);
                    switch (ch1)
                    {
                        case 1:
                        {
                            
                            String name;
                            int gold,silver;
                            System.out.println("Enter Movie name:");
                            scan.nextLine();
                            name=scan.nextLine();
                            dout.writeUTF(name);
                            System.out.println("Enter silver seat price followed by gold seat price");
                            silver=scan.nextInt();
                            dout.writeInt(silver);
                            gold=scan.nextInt();
                            dout.writeInt(gold);
                            float rating=0f;
                            System.out.println("Enter rating of Movie");
                            rating=scan.nextFloat();
                            dout.writeFloat(rating);
                        }    
                            
                            break;
                        case 2:
                        {
                            System.out.println("which Movie you want to delete");
                            String k=din.readUTF();
                            while(!k.equals("null"))
                            {
                            	System.out.println(k);
                            	k=din.readUTF();
                            }
                             
                            
                            int del=scan.nextInt();
                            
                            dout.writeInt(del);
                         }
                         break;  
                            
                        case 3:
                        {
                            System.out.println("Box office of which Movie.");
                            String k=din.readUTF();
                            while(!k.equals("null"))
                            {
                            	System.out.println(k);
                            	k=din.readUTF();
                            }
                            scan.nextLine();
                            int box=scan.nextInt();
                            box--;
                            dout.writeInt(box);
                            System.out.println(din.readUTF());
                        }
                            break;
                        case 4:
                        {
                            System.out.println("Which seat need to be reset.");
                            String k=din.readUTF();
                            while(!k.equals("null"))
                            {
                            	System.out.println(k);
                            	k=din.readUTF();
                            }
                            scan.nextLine();
                            int res=scan.nextInt();
                            
                            dout.writeInt(res);
                        }    
                            break;
                        default:
                            System.out.println("Please enter correct choice");
                    }
                    break;
                }

                case 2:
                {
                    int interest;
                    System.out.println("Which Movie you are interested in.");
                    String k=din.readUTF();
                            while(!k.equals("null"))
                            {
                            	System.out.println(k);
                            	k=din.readUTF();
                            }
                    scan.nextLine();
                    interest=scan.nextInt();
        
                    dout.writeInt(interest);

                    System.out.println("Enter your choice \n1]book\n2]pay\n3]cancel seat ");
                    ch2=scan.nextInt();
                    dout.writeInt(ch2);
                    switch (ch2)
                    {
                        case 1:
                        {
                            System.out.println("Enter username to continue!!");
                            scan.nextLine();
                            String username=scan.nextLine();
                            System.out.println("username entered is ="+username);
                            dout.writeUTF(username);
                            System.out.println("Enter your Password");
                            String password = scan.nextLine();
                            System.out.println("password entered is ="+password);
                            dout.writeUTF(password);

                         	char [][] seats =new char[8][9];
                            for(int i=0;i<8;i++)
                            {
                                for(int j=0;j<9;j++)
                                {
                                    seats[i][j]=' ';
                                }
                            }
                            String seat=din.readUTF();
                            for(int i = 0; i < seat.length(); i++) {
                                int row,col;
                                row=Integer.parseInt(String.valueOf(seat.charAt(i)));
                                ++i;
                                ++i;
                                col=Integer.parseInt(String.valueOf(seat.charAt(i)));
                                ++i;
                                seats[row][col]='B';
                            }
					        showw(seats);
					        
					        
                            
                            System.out.println("how many seat you want to book");
                            int n=scan.nextInt();
                            dout.writeInt(n);


                            for(int kk=0;kk<n;kk++)
                            {
                                int col,rows;
                                char row;
                                System.out.println("Enter row(A-H) and then enter column (1-10)");
                                scan.nextLine();
                                row=scan.next().charAt(0);
                                scan.nextLine();
                                col=scan.nextInt();
                                row=Character.toLowerCase(row);
                                rows=(int)row-'a';
                                System.out.println("row entered="+rows+"column entered="+col);
                                dout.writeInt(rows);
                                dout.writeInt(col);
                                seat=din.readUTF();

                                for(int i = 0; i < seat.length(); i++) {
                                    int rowss,colss;
                                    rowss=Integer.parseInt(String.valueOf(seat.charAt(i)));
                                    ++i;
                                    ++i;
                                    colss=Integer.parseInt(String.valueOf(seat.charAt(i)));
                                    ++i;
                                    seats[rowss][colss]='B';
                                }
                                showw(seats);
                            }
                        }

                            break;
                        case 2:
                        {
                            System.out.println("Enter username to continue!!");
                            scan.nextLine();
                            String username=scan.nextLine();
                            dout.writeUTF(username);
                            System.out.println("Enter your Password");
                            String password = scan.nextLine();
                            dout.writeUTF(password);
                            System.out.println(din.readUTF());
                            

                        }
                            break;
                        case 3:
                        {
                            System.out.println("Enter username to continue!!");
                            scan.nextLine();
                            String username=scan.nextLine();
                            dout.writeUTF(username);
                            System.out.println("Enter your Password");
                            String password = scan.nextLine();
                            dout.writeUTF(password);
                            String ss;
                            ss=din.readUTF();
                            

                            if(ss.equals("passed"))
                            {
								char [][] seats =new char[8][9];
								for(int i=0;i<8;i++)
								{
									for(int j=0;j<9;j++)
									{
										seats[i][j]=' ';
									}
								}
								String seat=din.readUTF();
								for(int i = 0; i < seat.length(); i++) 
								{
									int row,col;
									row=Integer.parseInt(String.valueOf(seat.charAt(i)));
									++i;
									++i;
									col=Integer.parseInt(String.valueOf(seat.charAt(i)));
									++i;
									seats[row][col]='B';
								}
								showw(seats);
								
								int col,rows;
								char row;
								System.out.println("Enter row(A-H) and then enter column (1-10)");
								row = scan.next().charAt(0);
								col = scan.nextInt();
								row = Character.toLowerCase(row);
								rows=(int)row-'a';
								dout.writeInt(rows);
								dout.writeInt(col);
								ss=din.readUTF();
								if(ss.equals("passed")){
								    System.out.println("Deleted Successfully");
								}
								else
								{
								System.out.println(din.readUTF());
                                }
                            }
                            else
                            {
                                System.out.println(din.readUTF());
                            }

                        }    
                          break;

                        default:
                            System.out.println("please enter correct choice."); //case 22
                    }


                   
                }
                break;
                default:
               System.out.println("Please enter correct choice");
            }

            System.out.println("Do you want to continue? Or press 0 to exit..");
            ch3=scan.nextInt();
            dout.writeInt(ch3);
        }while(ch3!=0);

        s.close();
        
        
        // ois1.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        
    }

}

