<h1>Hacktoberfest 2019</h1>
<br>
<br>
my name is krish
<br>
I am in class 10
<br>
import java.sql.*;
import java.util.Scanner;

public class Employee {
    private static final String url = "jdbc:mysql://localhost:3306/rdbms";
    private static final String usern = "root";
    private static final String passwrd = "123456789";

    private Connection con = null;

    public void connect() {
        try {
            con = DriverManager.getConnection(url, usern, passwrd);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            con.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean checkLogin(int emp_id, String pass)
    {
        //Returns true if correct login credentials, else false
        try {
            Statement st = con.createStatement();

            String q = "SELECT * FROM Employee WHERE emp_id = "+emp_id+" AND pass = '"+pass.trim()+"'";
            ResultSet rs = st.executeQuery(q);
            boolean b =rs.next();
            st.close();
            return b;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public void viewUnresolvedComplaints()
    {
        //View all Unresolved Complaints
        
        System.out.println("These are all the Unresolved Complaints");
        System.out.println("Complaint id\tComplaint Text\tFamily ID\n");
        try {
            Statement st = con.createStatement();

            String q = "SELECT comp_id,comp_text,fam_id FROM Complaint WHERE com_status = FALSE";
            ResultSet rs = st.executeQuery(q);

            while (rs.next())
            {
                System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3));
            }

            st.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean viewComplaint(int comp_id)
    {
        //View any individual complaint. Return true if complaint exists

        System.out.println("\nViewing complaint with ID "+comp_id);
        try {
            Statement st = con.createStatement();

            String q = "SELECT * FROM Complaint WHERE comp_id = "+comp_id;
            ResultSet rs = st.executeQuery(q);
            boolean b = rs.next();
            if (!b)
            {
                System.out.println("The given complaint ID does not exist");
            }
            else
            {
                System.out.println("The family ID is "+rs.getInt(3));
                System.out.println("Complaint text - "+rs.getString(2));
                if (rs.getInt(4) == 1)
                System.out.println("Complaint is resolved");
                else
                System.out.println("Complaint is not resolved");
                if (rs.getString(5) != null)
                System.out.println("Employee response - "+rs.getString(5));
                if (rs.getInt(6) != 0)
                System.out.println("Employee ID is "+rs.getInt(6));
            }

            st.close();
            return b;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateComplaint(int emp_id, int comp_id)
    {
        //Update any individual complaint. Return true if complaint exists

        Scanner sc=new Scanner(System.in);
        boolean b = false;
        try {
            b = viewComplaint(comp_id);
            if (b)
            {
                Statement st = con.createStatement();

                System.out.println("\nEnter your response");
                String resp = sc.nextLine().trim();
            
                String q = "UPDATE Complaint SET emp_response = '"+resp+"' , emp_id = "+emp_id+" ,com_status = TRUE WHERE comp_id = "+comp_id;
                st.executeUpdate(q);
                st.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        sc.close();
        return b;
    }











    //TO BE USED IN FAMILY

    
    public void addNewComplaint(int fam_id)
    {
        //For Family to add new Complaint

        try {
            Statement st = con.createStatement();
            Scanner sc=new Scanner(System.in);
            System.out.println("\nEnter your complaint");
            String comp = sc.nextLine().trim();
            String q = "INSERT INTO Complaint(comp_text,fam_id,com_status) VALUES ('"+comp+"', "+fam_id+" , FALSE)";
            st.executeUpdate(q);

            q = "SELECT LAST_INSERT_ID()";
            ResultSet rs = st.executeQuery(q);
            rs.next();
            System.out.println("Your complaint ID is "+rs.getInt(1));

            st.close();
            sc.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewComplaints(int fam_id)
    {
        //View all complaints associated with a family ID
        System.out.println("\nViewing all complaints");
        try {
            Statement st = con.createStatement();

            String q = "SELECT * FROM Complaint WHERE fam_id = "+fam_id;

            ResultSet rs = st.executeQuery(q);
            while (rs.next())
            {
                System.out.println("The family ID is "+rs.getInt(3));
                System.out.println("Complaint text - "+rs.getString(2));
                if (rs.getInt(4) == 1)
                System.out.println("Complaint is resolved");
                else
                System.out.println("Complaint is not resolved");
                if (rs.getString(5) != null)
                System.out.println("Employee response - "+rs.getString(5));
                if (rs.getInt(6) != 0)
                System.out.println("Employee ID is "+rs.getInt(6));
            }
            st.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }  
}
