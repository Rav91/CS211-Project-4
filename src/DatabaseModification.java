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
                DatabaseModification.showColumns(conn);
                DatabaseModification.showValues(conn);
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
        try
        {
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT * FROM student.students");
            int n = 1;
            while(rset.next()){
                PreparedStatement p = conn.prepareStatement("DELETE FROM `student`.`students` WHERE " +
                        "(`studentID` = '" + n + "');");
                n++;
                p.executeUpdate();
            }
            for(int i=1; i<=30; i++){
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
                        "('" + firstName + "', '" + lastName + "', '" + i + "', '" + gender + "');");
                p.executeUpdate();
            }
        }catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    /**
     * Obtains and displays a ResultSet from the Student table.
     */
    public static void showValues(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("SELECT * FROM student.students");
            DatabaseModification.showResults("student.students", rset);
        }catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void showColumns(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("SHOW COLUMNS FROM Student.students");
            DatabaseModification.showResults("Student.students", rset);
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
                            "------------------------------------");
                }
            }
            System.out.println(resultString + '\n' +
                    "------------------------------------");
        } catch (SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
