

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.mysql.cj.Session;


@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		
		
		try {
			String url ="jdbc:mysql://localhost:3306/schooldb";
			String uname= "root";
			String pass = "root";
			
		    Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url,uname,pass);
			
			
			
			// Creating table for subjects
            
           String  sql = "CREATE TABLE IF NOT EXISTS subjects(id INT NOT NULL AUTO_INCREMENT, subjectname VARCHAR(255),classid INT,PRIMARY KEY (id),FOREIGN KEY (classid) REFERENCES classes(id));";
            
             Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            
         // creating table for teachers
            
            sql = "CREATE TABLE IF NOT EXISTS teachers(id INT NOT NULL AUTO_INCREMENT,teachername VARCHAR(255),PRIMARY KEY (id))";
           
            stmt.executeUpdate(sql);
            
         // creating table for classes
			
            sql = "CREATE TABLE IF NOT EXISTS classes(id INT NOT NULL AUTO_INCREMENT,classname VARCHAR(255),PRIMARY KEY (id));";

           
           stmt.executeUpdate(sql);  
            
         // Assigning subjects to classes
            
            sql = "INSERT INTO subjects (subjectname, classid) VALUES (?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);
            
            pstmt.setString(1, "Social");
            pstmt.setInt(2, 4);
            pstmt.executeUpdate();
            
         // creating a table to map and Assign teachers to subjects
            
                sql= "CREATE TABLE IF NOT EXISTS class_subject_teacher (classid INT,subjectid INT,teacherid INT,FOREIGN KEY (classid) REFERENCES classes(id),FOREIGN KEY (subjectid) REFERENCES subjects(id),FOREIGN KEY (teacherid) REFERENCES teachers(id))";
                stmt.executeUpdate(sql);
                
                sql = "INSERT INTO class_subject_teacher (classid, subjectid, teacherid) VALUES (?,?,?)";
                pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, 5);
                pstmt.setInt(2, 1);
                pstmt.setInt(3, 3);
                pstmt.executeUpdate();
                
             // Creating a table for students
                
                sql = "CREATE TABLE IF NOT EXISTS students (id INT NOT NULL AUTO_INCREMENT,studentname VARCHAR(255),classid INT,PRIMARY KEY (id),FOREIGN KEY (classid) REFERENCES classes(id))";
                stmt.executeUpdate(sql);
                
             // Fetching class report
                
                sql= "SELECT DISTINCT classes.id as class_id,classes.classname as class_name,subjects.subjectname as subjects_taught,teachers.teachername as teachers_assigned, students.studentname as students FROM classes INNER JOIN subjects ON classes.id = subjects.classid INNER JOIN class_subject_teacher ON subjects.id = class_subject_teacher.subjectid INNER JOIN teachers ON class_subject_teacher.teacherid = teachers.id INNER JOIN students ON classes.id";
                ResultSet rs= ((Statement) stmt).executeQuery(sql);
                
               
                out.println("<h1>CLASS REPORT</h1><br>");
                
                String data="";
                
               while( rs.next()) {
                
                 data =  rs. getInt(1)+ " : " + rs. getString(2)+ " : " + rs. getString(3)+ " : "+ rs. getString(4)+ " : "+ rs. getString(5)+ "<br>";
                
                out.println(data);
               }
                
               
           }
		 catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
			
		out.println("<body>");
		out.println("<html>");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
	}

}
