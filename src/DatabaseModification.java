import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.CharBuffer;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DatabaseModification extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        File GPAs = new File("GPAs.txt");
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
            }
        } catch(SQLException ex){
            System.out.println("SQLException: " + ex.getMessage());
            ex.printStackTrace();
        } catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
            ex.printStackTrace();
        }

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("GPA Pie Chart");
        Group root = new Group();
        Canvas canvas = new Canvas(900, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane pane = new StackPane();
        pane.getChildren().add(canvas);

        Statement stmt = conn.createStatement();
        ResultSet rset = stmt.executeQuery("SELECT * FROM student.classes");

        String GPA = "";

        while(rset.next()){
            GPA += rset.getString("GPA");
        }

        FileOutputStream outputStream = new FileOutputStream(GPAs);
        byte[] GPAinBytes = GPA.getBytes();
        outputStream.write(GPAinBytes);

        HistogramAlphaBet histogramAlphaBet = new HistogramAlphaBet();
        histogramAlphaBet.mapFromFile(GPAs);
        PieChart chart = new PieChart(canvas.getWidth()/2, canvas.getHeight()/2, canvas.getHeight(),
                canvas.getWidth(), histogramAlphaBet);
        chart.draw(gc, 6);

        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        //Close the database
        conn.close();
    }

    /**
     * Carries out various CRUD operations after
     * establishing the database connection.
     */
    public static void main (String args[]) {
        launch(args);
    }

    public static void populateTables(Connection conn){
        try {
            Random rand = new Random();
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
                classIDSeed = rset.getInt("classCode");
                PreparedStatement p = conn.prepareStatement("DELETE FROM `student`.`classes` WHERE " +
                        "(`classCode` = '" + classIDSeed + "');");
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
                        "('" + (courseIDSeed + n) + "', '" + physicsClasses[i] + "', 'Physics');");
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
                        "('" + (courseIDSeed + n) + "', '" + historyClasses[i] + "', 'History');");
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
                        "('" + (courseIDSeed + n) + "', '" + mathClasses[i] + "', 'Mathematics');");
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
                        "('" + (courseIDSeed + n) + "', '" + computerScienceClasses[i] + "', 'Computer Science');");
                n++;
                p.executeUpdate();
            }
            //add students
//            String names[] = {"Owen", "Faye", "Colon", "Cesar", "Ruiz", "Norman", "Shane",
//                    "Francis", "Stanley", "Smith", "Adam", "Griffith", "Gladys", "Justice",
//                    "Alex", "Charlie", "Skyler", "Armani", "Salem", "Sidney", "Denver",
//                    "Robin", "Campbell", "Yael", "Ramsey", "Murphy", "Perry", "Hollis",
//                    "Jules", "Austin", "Dominique", "Reilly", "Kylar", "Austen", "Storm",
//                    "Ocean", "Summer", "Winter", "Spring", "Autumn", "Indiana", "Nano",
//                    "Marlo", "Ridley", "Ryley", "Riley", "Jaden", "Jayden", "Jackie", "Taylor",
//                    "Taylen", "Lake", "Timber", "Cypress", "Jaziah", "Eastyn", "Easton",
//                    "Payson", "Kylin", "Hollis", "Holis", "Angel", "Blake", "Ruby", "Evan",
//                    "Frankie", "Jean", "Yang", "Sasha",  "Tristan", "Quinn", "Blair",
//                    "August", "May", "Parker", "Hayden", "Halo", "Rio", "Shuten"};
            ArrayList<String> names = new ArrayList<String>();
            File getNames = new File("names.txt");
            Scanner scanner = new Scanner(getNames);
            while (scanner.hasNext()){
                names.add(scanner.nextLine());
            }
            studentIDSeed = (rand.nextInt(5000) + 1000) * 10;
            PreparedStatement p = conn.prepareStatement("INSERT INTO student.students " +
                    "(firstName, lastName, studentID, sex) VALUES " +
                    "('Hashem', 'Auda', '" + studentIDSeed + "', 'M');");
            p.executeUpdate();
            p = conn.prepareStatement("INSERT INTO student.students " +
                    "(firstName, lastName, studentID, sex) VALUES " +
                    "('Ravid', 'Rahman', '" + (studentIDSeed + 1) + "', 'M');");
            p.executeUpdate();
            p = conn.prepareStatement("INSERT INTO student.students " +
                    "(firstName, lastName, studentID, sex) VALUES " +
                    "('Kenneth', 'Feng', '" + (studentIDSeed + 2) + "', 'M');");
            p.executeUpdate();
            for(int i=3; i<500; i++){
                String firstName = names.get(rand.nextInt(names.size()));
                String lastName = names.get(rand.nextInt(names.size()));
                char gender;
                int randGender = rand.nextInt(2);
                if(randGender%2==0){
                    gender = 'M';
                }else{
                    gender = 'F';
                }
                p = conn.prepareStatement("INSERT INTO student.students " +
                        "(firstName, lastName, studentID, sex) VALUES " +
                        "('" + firstName + "', '" + lastName + "', '" + (studentIDSeed + i) + "', '" + gender + "');");
                p.executeUpdate();
            }
            //get all courseIDs to populate classes and get class code for 211
            ArrayList<Integer> courseIDs = new ArrayList<Integer>();
            int CSc211ClassCode = 0;
            rset = stmt.executeQuery("SELECT * FROM student.courses");
            while (rset.next()){
                courseIDs.add(rset.getInt("courseID"));
                String courseName = rset.getString("courseTitle");
                if(courseName.equals("CSc 211")){
                    CSc211ClassCode = rset.getInt("courseID");
                }
            }
            //add classes
            classIDSeed = (rand.nextInt(50) + 10) * 10;
            p = conn.prepareStatement("INSERT INTO student.classes " +
                    "(classCode, courseID, studentID, year, semester, GPA) VALUES " +
                    "('" + classIDSeed + "', '" + CSc211ClassCode + "', '" + studentIDSeed + "', '"
                    + 2019 + "', 'Fall', '" + DatabaseModification.getRandGPA() + "');");
            p.executeUpdate();
            p = conn.prepareStatement("INSERT INTO student.classes " +
                    "(classCode, courseID, studentID, year, semester, GPA) VALUES " +
                    "('" + (classIDSeed + 1) + "', '" + CSc211ClassCode + "', '" + (studentIDSeed + 1) + "', '"
                    + 2019 + "', 'Fall', '" + DatabaseModification.getRandGPA() + "');");
            p.executeUpdate();
            p = conn.prepareStatement("INSERT INTO student.classes " +
                    "(classCode, courseID, studentID, year, semester, GPA) VALUES " +
                    "('" + (classIDSeed + 2) + "', '" + CSc211ClassCode + "', '" + (studentIDSeed + 2) + "', '"
                    + 2019 + "', 'Fall', '" + DatabaseModification.getRandGPA() + "');");
            p.executeUpdate();
            for(int i=3; i<500; i++){
                char GPA = DatabaseModification.getRandGPA();
                p = conn.prepareStatement("INSERT INTO student.classes " +
                        "(classCode, courseID, studentID, year, semester, GPA) VALUES " +
                        "('" + (classIDSeed + i) + "', '" + courseIDs.get(rand.nextInt(courseIDs.size())).intValue() + "', '" +
                        (studentIDSeed + i) + "', '" + 2019 + "', 'Fall', '" + GPA + "');");
                p.executeUpdate();
            }

            int tally211 = 0;
            String GPAs211 = "";
            rset = stmt.executeQuery("SELECT * FROM student.classes");
            while (rset.next()){
                int codeToCheck = rset.getInt("courseID");
                int yearToCheck = rset.getInt("year");
                String semesterToCheck = rset.getString("semester");
                if(codeToCheck == CSc211ClassCode && yearToCheck == 2019 && semesterToCheck.equals("Fall")){
                    tally211++;
                    GPAs211 +=  " " + rset.getString("GPA");
                }
            }
            System.out.println("There are " + tally211 + " students enrolled in CSc211");
            System.out.println("GPA of students enrolled in the Fall 2019 semester of CSc 211: " + GPAs211);

        }catch(SQLException | FileNotFoundException ex) {
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
                            resultString += column + "\t";
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

    public static char getRandGPA(){
        Random rand = new Random();
        int randGPA = rand.nextInt(65) + 40;
        //105-40
        if(randGPA > 100){
            //105-101
            return 'W';
        }else if(randGPA >= 95){
            //100-95
            return 'A';
        }else if(randGPA >= 80){
            //94-80
            return 'B';
        }else if(randGPA >= 60){
            //79-60
            return 'C';
        }else if(randGPA >= 45){
            //59-45
            return 'D';
        }else{
            //44-40
            return 'F';
        }
    }
}