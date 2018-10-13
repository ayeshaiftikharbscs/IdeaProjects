/* Header
* Name: Ayesha Iftikhar
* Email ID: l144130@lhr.nu.edu.pk
* Section: A
* Purpose: This program creates a Student class and test it.
* */

import java.util.Random;

public class Student {

    //Define a Student class with instance variables name, id, midterm1, midterm2 and final. Name is a string whereas others
    // are all integers`. Also, add a static variable nextId, which is an integer and statically initialized to 1
    String Name;
    int id;
    int midTerm1;
    int midTerm2;
    int Final;
    static int nextId = 1;

    //getters and setters
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMidTerm1() {
        return midTerm1;
    }

    public void setMidTerm1(int midTerm1) {
        this.midTerm1 = midTerm1;
    }

    public int getMidTerm2() {
        return midTerm2;
    }

    public void setMidTerm2(int midTerm2) {
        this.midTerm2 = midTerm2;
    }

    public int getFinal() {
        return Final;
    }

    public void setFinal(int aFinal) {
        Final = aFinal;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        Student.nextId = nextId;
    }

    //The default constructor should set the name of the student object to “StudentX” where X is the next id.
    Student() {
        id = nextId;
        Name = "Student" + nextId;
        nextId++;
    }

    //Overloaded constructor, the id is assigned to the next available id given by nextId.
    Student(String _Name, int _midTerm1,int _midTerm2, int _Final)
    {
        Name = _Name;
        id = nextId;
        midTerm1 = _midTerm1;
        midTerm2 = _midTerm2;
        Final = _Final;
        nextId++;
    }

    //Add a calculateGrade() method which returns a string for the letter grade of the student,
    // like “A”, “B”, “C”, “D” or “F”, based on the overall score.
    public String CalculateGrade() {
        //Overall score should be calculated as (30% of midterm1 + 30% of midterm2 + 40% of final). The letter grade should be
        // calculated according to the absolute grading in FAST.
        int score = 30 * midTerm1/100 + 30*midTerm2/100 + 40*Final/100 ;

        if(score >=85)
            return "A+";
        else if(score >= 80)
            return "A";
        else if(score >=77 )
            return "A-";
        else if(score >= 75)
            return "B+";
        else if(score >= 70)
            return "B";
        else if(score >=67)
            return "B-";
        else if(score >= 64)
            return "C+";
        else if(score >=62)
            return "C";
        else if(score >= 58)
            return "C-";
        else if(score >= 54)
            return "D+";
        else if(score >= 50)
            return "D";
        else if(score < 50)
            return "F";
        return "";
    }

    //Student information provided by toString() should include name, midterm1, midterm2, final and the letter grade
    // given by the calculateGrade() method.
    public String toString()
    {
        return "Name: " + Name + "\nMidterm1: " + midTerm1 + "\nMidterm2: " + midTerm2
                + "\nFinal: " + Final+ "\nGrade: " + CalculateGrade();
    }

}


class Homework1 //Your test class, to be named as Homework1
{
    public static void main(String[] args) {

        //Legend
        System.out.print("This program creates a Student Class and test it.\n");

        //Testing Student Class

        //This test class should create 25 student objects with default constructor and invoke the setter methods for
        // midterm1, midterm2 and final with random numbers ranging from 50 to 100 inclusive.

        Student[] StudentArray = new Student[25];

        int max= 100;
        int min = 50;
        Random rand = new Random();

        for(int i= 0; i<25; i++)
        {
            StudentArray[i] = new Student();//Creating 25 student objects with default Constructors

            //invoking setters

            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            StudentArray[i].setMidTerm1(rand.nextInt((max - min) + 1) + min);
            StudentArray[i].setMidTerm2(rand.nextInt((max - min) + 1) + min);
            StudentArray[i].setFinal(rand.nextInt((max - min) + 1) + min);

            //print the student information via the toString() method.
            System.out.println("\nInformation of Student No "+ (i+1) +":\n"  + StudentArray[i]);
        }

    }
}
/*Test Cases
*  This program randomly generates marks of students, computes their score and prints out the Students Information.
* */
