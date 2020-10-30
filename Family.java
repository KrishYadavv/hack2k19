import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Family {
	private String address;
	private int familyID;
	private static int counter=1;
    private static int day=0;
    private static Records RecObj=new Records();
    private ArrayList<Resident> res=new ArrayList<Resident>();
    private String password;
    private Bill b=new Bill();
    Complaint com=new Complaint("");

    private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

    public Family(String a, String n, String em, String p, long phn){
    	address=a;
    	familyID = counter++;
    	password=p;
    	Resident obj = new Resident(n,phn,em);
    	res.add(obj);
    	for(int i=0;i<30;i++)
    		passToRecord();

    	try (
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbms_project?useSSL=false","diya","Password123#@!");
			statement = connect.createStatement();
			) {
    		resultSet = statement.executeQuery("insert into Family values (%d, %s, %s)", familyID, address, password);
    	} catch(SQLException ex) {
    		ex.printStackTrack();
    	}
    }

    public void setAddress(String a){
    	address = a;
    	try (
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbms_project?useSSL=false","diya","Password123#@!");
			statement = connect.createStatement();
			) {
    		int countUpdated = statement.executeUpdate("update Family set address = %s where familyID = %d", address, counter);
    		//resultSet = statement.executeQuery("update Family set address = %s where familyID = %d", address, counter);
    	} catch(SQLException ex) {
    		ex.printStackTrack();
    }
}

    public String getAddress() {
    	try (
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbms_project?useSSL=false","diya","Password123#@!");
			statement = connect.createStatement();
			) {
    		resultSet = statement.executeQuery("select address from Family where familyID = %d", counter);

    		String add = null;
    		if(resultSet.next()){
    			add = resultSet.getString(1);
    		}
    		return add;
    	} catch(SQLException ex) {
    		ex.printStackTrack();
    	}
    }

        public void setFamilyID(int f)
    {
        familyID=f;
        try (
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbms_project?useSSL=false","diya","Password123#@!");
			statement = connect.createStatement();
			) {
    		statement.executeUpdate("update Family set familyID = %d where address = %s", familyID, address);
    	} catch(SQLException ex) {
    		ex.printStackTrack();
    	}
    }
    public int getFamilyID()
    {
    	try (
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbms_project?useSSL=false","diya","Password123#@!");
			statement = connect.createStatement();
			) {
    		resultSet = statement.executeQuery("select familyID from Family where address = %s", address);

    		int id = null;
    		if(resultSet.next()){
    			id = resultSet.getInt(1);
    		}
    		return id;
    	} catch(SQLException ex) {
    		ex.printStackTrack();
    	}
        }
    public String getPass()
    {
        try (
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbms_project?useSSL=false","diya","Password123#@!");
			statement = connect.createStatement();
			) {
    		resultSet = statement.executeQuery("select password from Family where familyID = %d", familyID);

    		String pass = null;
    		if(resultSet.next()){
    			pass = resultSet.getString(1);
    		}
    		return pass;
    	} catch(SQLException ex) {
    		ex.printStackTrack();
    	}
    }
    public void print()
    {
        System.out.println("The family ID is "+familyID);
        System.out.println("The address is "+address);
        for (int i=0;i<res.size();i++)
        {
            System.out.println("For resident "+(i+1));
            System.out.println("Name is "+res.get(i).getName());
            System.out.println("Phone number is "+res.get(i).getPhoneNo());
            System.out.println("Email is "+res.get(i).getEmail());
        }
    }
    public void new_Resident(String n,long phn,String em)
    {
        Resident obj=new Resident(n,phn,em);
        res.add(obj);
    }
    public void seeRecord()
    {
        RecObj.printData(familyID);
    }
    public void passToRecord()
    {
        Random r=new Random();
        RecObj.addData(familyID,day,(10+Math.abs(r.nextInt()%9)));
        day=(day+1)%30;
            }
    public void Complaint_Add(String s)
    {
        com=new Complaint(s);
        System.out.println("The complaint ID is "+com.getID());
    }
    public void check_Complaint()
    {
        com.print();
    }
    public void see_Bill()
    {
        System.out.println("The bill is "+b.calculateBill(familyID));
    }
    public void pay_Bill()
    {
        b.payBill(getFamilyID());
    }
    public int noOfResidents()
    {
        return res.size();
    }
    public String getResidentName(int i) {
        return res.get(i).getName();
    } 
    public void updateRes(int i,String n,long p,String e)
    {
        res.get(i).update(n, p, e);
    }

    }
    
