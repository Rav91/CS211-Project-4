import java.sql.*;
import java.util.Random;

public class DatabaseModification {
    /**
     * Carries out various CRUD operations after
     * establishing the database connection.
     */
    public static void main (String args[]) {
        Connection conn = null;
        try {
            //Loads the class object for the mysql driver into the DriverManager.

            Class.forName("com.mysql.cj.jdbc.Driver");

            //Attempt to establish a connection to the specified database via the DriverManager.
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/", "root", "@MySQL99");

            //Check the connection
            if(conn != null) {
                System.out.println("We have connected to our database!");
                //Create the table and show the table structure
                Statement stmt = conn.createStatement();

                DatabaseModification.populateTables(conn);
                DatabaseModification.showColumns(conn, "students");
                DatabaseModification.showColumns(conn, "courses");
                DatabaseModification.showColumns(conn, "classes");
                DatabaseModification.showValues(conn, "students");
                DatabaseModification.showValues(conn, "courses");
                DatabaseModification.showValues(conn, "classes");
                //Close the database
                conn.close();
            }
            } catch(SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        } catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void populateTables(Connection conn){
        String names[] = {"Owen", "Faye", "Colon", "Cesar", "Ruiz", "Norman", "Shane",
                "Francis", "Stanley", "Smith", "Adam", "Griffith", "Gladys", "Justice",
                "Alex", "Charlie", "Skyler", "Armani", "Salem", "Sidney", "Denver",
                "Robin", "Campbell", "Yael", "Ramsey", "Murphy", "Perry", "Hollis",
                "Jules", "Austin", "Dominique", "Reilly", "Kylar", "Austen", "Storm",
                "Ocean", "Summer", "Winter", "Spring", "Autumn", "Indiana", "Indianna",
                "Marlo", "Ridley", "Ryley", "Riley", "Jaden", "Jayden", "Jackie", "Taylor",
                "Taylen", "Lake", "Timber", "Cypress", "Jaziah", "Eastyn", "Easton",
                "Payson", "Kylin", "Hollis", "Holis", "Angel", "Blake", "Ruby", "Evan",
                "Frankie", "Jean", "Yang", "Sasha",  "Tristan", "Quinn", "Blair",
                "August", "May", "Parker", "Hayden", "Halo", "Rio", "Shuten"};
        Random rand = new Random();
        try {
            int studentIDSeed;
            int courseIDSeed;
            int classIDSeed;
            Statement stmt = conn.createStatement();
            //remove students
            ResultSet rset = stmt.executeQuery("SELECT * FROM student.students");
            while(rset.next()){
                studentIDSeed = rset.getInt("studentID");
                PreparedStatement p = conn.prepareStatement("DELETE FROM `student`.`students` WHERE " +
                        "(`studentID` = '" + studentIDSeed + "');");
                p.executeUpdate();
            }
            //remove courses
            rset = stmt.executeQuery("SELECT * FROM student.courses");
            while (rset.next()){
                courseIDSeed = rset.getInt("courseID");
                PreparedStatement p = conn.prepareStatement("DELETE FROM `student`.`courses` WHERE " +
                        "(`courseID` = '" + courseIDSeed + "');");
                p.executeUpdate();
            }
            //remove classes
            rset = stmt.executeQuery("SELECT * FROM student.classes");
            while (rset.next()){
                classIDSeed = rset.getInt("courseID");
                PreparedStatement p = conn.prepareStatement("DELETE FROM `student`.`classes` WHERE " +
                        "(`classID` = '" + classIDSeed + "');");
                p.executeUpdate();
            }
            //add physics courses
            courseIDSeed = (rand.nextInt(500) + 100) * 10;
            int n = 0;
            String[] physicsClasses = {"Phys 104", "Phys 105", "Phys 117", "Phys 254", "Phys 255", "Phys 289",
                                        "Phys 369", "Phys 352", "Phys 387", "Phys 444", "Phys 400", "Phys 418"};
            for (int i=0; i<physicsClasses.length; i++){
                PreparedStatement p = conn.prepareStatement("INSERT INTO student.courses " +
                        "(courseID, courseTitle, department) VALUES " +
                        "('" + courseIDSeed + n + "', '" + physicsClasses[i] + "', 'Physics');");
                n++;
                p.executeUpdate();
            }
            //add history courses
            courseIDSeed = (rand.nextInt(500) + 100) * 10;
            n = 0;
            String[] historyClasses = {"Hist 109", "Hist 106", "Hist 111", "Hist 229", "Hist 250", "Hist 200",
                    "Hist 382", "Hist 329", "Hist 390", "Hist 455", "Hist 404", "Hist 499"};
            for (int i=0; i<historyClasses.length; i++){
                PreparedStatement p = conn.prepareStatement("INSERT INTO student.courses " +
                        "(courseID, courseTitle, department) VALUES " +
                        "('" + courseIDSeed + n + "', '" + historyClasses[i] + "', 'History');");
                n++;
                p.executeUpdate();
            }
            //add math courses
            courseIDSeed = (rand.nextInt(500) + 100) * 10;
            n = 0;
            String[] mathClasses = {"Math 100", "Math 108", "Math 110", "Math 261", "Math 238", "Math 200",
                    "Math 300", "Math 360", "Math 365", "Math 421", "Math 494", "Math 408"};
            for (int i=0; i<mathClasses.length; i++){
                PreparedStatement p = conn.prepareStatement("INSERT INTO student.courses " +
                        "(courseID, courseTitle, department) VALUES " +
                        "('" + courseIDSeed + n + "', '" + mathClasses[i] + "', 'Mathematics');");
                n++;
                p.executeUpdate();
            }
            //add computer science courses
            courseIDSeed = (rand.nextInt(500) + 1000) * 10;
            n = 0;
            String[] computerScienceClasses = {"CSc 211", "CSc 113", "CSc 101", "CSc 103", "CSc 205", "CSc 216",
                    "CSc 341", "CSc 321", "CSc 367", "CSc 489", "CSc 436", "CSc 419"};
            for (int i=0; i<computerScienceClasses.length; i++){
                PreparedStatement p = conn.prepareStatement("INSERT INTO student.courses " +
                        "(courseID, courseTitle, department) VALUES " +
                        "('" + courseIDSeed + n + "', '" + computerScienceClasses[i] + "', 'Computer Science');");
                n++;
                p.executeUpdate();
            }
            //add students
            studentIDSeed = (rand.nextInt(5000) + 1000) * 10;
            for(int i=0; i<27; i++){
                String firstName = names[rand.nextInt(names.length)];
                String lastName = names[rand.nextInt(names.length)];
                char gender;
                int randGender = rand.nextInt(2);
                if(randGender%2==0){
                    gender = 'M';
                }else{
                    gender = 'F';
                }
                PreparedStatement p = conn.prepareStatement("INSERT INTO student.students " +
                        "(firstName, lastName, studentID, sex) VALUES " +
                        "('" + firstName + "', '" + lastName + "', '" + studentIDSeed + i + "', '" + gender + "');");
                p.executeUpdate();
            }
            PreparedStatement p = conn.prepareStatement("INSERT INTO student.students " +
                    "(firstName, lastName, studentID, sex) VALUES " +
                    "('Hashem', 'Auda', '" + studentIDSeed + 27 + "', 'M');");
            p.executeUpdate();
            p = conn.prepareStatement("INSERT INTO student.students " +
                    "(firstName, lastName, studentID, sex) VALUES " +
                    "('Ravid', 'Rahman', '" + studentIDSeed + 28 + "', 'M');");
            p.executeUpdate();
            p = conn.prepareStatement("INSERT INTO student.students " +
                    "(firstName, lastName, studentID, sex) VALUES " +
                    "('Kenneth', 'Feng', '" + studentIDSeed + 29 + "', 'M');");
            p.executeUpdate();
        }catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    /**
     * Obtains and displays a ResultSet from the Student table.
     */
    public static void showValues(Connection conn, String column) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT * FROM student." + column);
            DatabaseModification.showResults("student." + column, rset);
        }catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void showColumns(Connection conn, String column) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("SHOW COLUMNS FROM Student." + column);
            DatabaseModification.showResults("Student." + column, rset);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void showResults(String tableName, ResultSet rSet) {
        try {
            ResultSetMetaData rsmd = rSet.getMetaData();
            int numColumns = rsmd.getColumnCount();
            String resultString = null;
            if (numColumns > 0) {
                resultString = "\nTable: " + tableName + "\n" +
                        "==================================================\n";
                for (int colNum = 1; colNum <= numColumns; colNum++) {
                    resultString += rsmd.getColumnLabel(colNum) + " ";
                }
                System.out.println(resultString);
                System.out.println(
                        "==================================================");
                while (rSet.next()) {
                    resultString = " ";
                    for (int colNum = 1; colNum <= numColumns; colNum++) {
                        String column = rSet.getString(colNum);
                        if (column != null)
                            resultString += column + " ";
                    }
                    System.out.println(resultString + '\n' +
                            "--------------------------------------------------");
                }
            }
        } catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
