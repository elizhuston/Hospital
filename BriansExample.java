package hello;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class Hello {

    public static void main(String[] args) {

        Employee e = new Employee();
        e.name = "Brian Will";
        e.address = "nowhere";
        e.SSN = 11122333;
        e.number = 101;
        e.cat = new Cat[2];

        Cat c = new Cat();
        c.name = "Oscar";
        Cat c2 = new Cat();
        c2.name = "Cuddy";

        e.cat[0] = c;
        e.cat[1] = c2;


        try {
            FileOutputStream fileOut = new FileOutputStream("employee.data");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(e);
//            out.writeObject(c);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in employee.data");
        } catch (IOException ex) {
            System.out.println(ex);
        }


        try {
            FileInputStream fileIn = new FileInputStream("employee.data");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (Employee) in.readObject();
            //c = (Cat) in.readObject();
            in.close();
            fileIn.close();
            System.out.println(e.name);
            System.out.println(e.cat[0].name);
            System.out.println(e.cat[1].name);
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
             System.out.println(ex);
        }
    }
}

class Employee implements java.io.Serializable {
    public String name;
    public String address;
    public int SSN;
    public int number;
    public Cat[] cat;
}

class Cat implements java.io.Serializable {
    public String name;
}
