package hospital;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class Hospital implements java.io.Serializable {

	Care[] employees;
	Patient[] patients;

	public static int sizeof(Object obj) throws IOException {
		//Convert your object into a byte array using ObjectOutputStream and ByteArrayOutputStream:
	    ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
	    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);

	    objectOutputStream.writeObject(obj);
	    objectOutputStream.flush();
	    objectOutputStream.close();

	    return byteOutputStream.toByteArray().length;
	}
	
	public static void main(String[] args) {

		Hospital h = new Hospital();

		h.employees = new Care[3];
		h.patients = new Patient[2];

		h.employees[0] = new Doctor("Meredith", "Gray", "Neurosurgery", true);
		h.employees[1] = new Doctor("McSteamy", "Dr", "Plastic Surgery", true);
		h.employees[2] = new Nurse("Miss", "Ratchett", "RN", "day");
		h.patients[0] = new Patient("Homer", "Simpson", "O+");
		h.patients[0].illness = ((Doctor) h.employees[0]).diagnose(h.patients[0]);
		System.out.println(h.patients[0].illness);

		try {
			FileOutputStream fileOut = new FileOutputStream("hospital.data");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(h);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in hospital.data");
		} catch (IOException ex) {
			System.out.println(ex);
		}

		try {
			FileInputStream fileIn = new FileInputStream("hospital.data");
			ObjectInputStream in = new ObjectInputStream(fileIn);
//			int objSz = sizeof(in);
//			System.out.println("Size of object is " + objSz);
			h = (Hospital) in.readObject();
			in.close();
			fileIn.close();
			// System.out.println(h.firstName);
			Doctor d = (Doctor) h.employees[0];
			System.out.println("Doctor " + d.firstName + " " + d.lastName + " Specialty is " + d.specialty);
//			System.out.println(h.employees[0]);
		} catch (IOException ex) {
			System.out.println(ex);
		} catch (ClassNotFoundException ex) {
			System.out.println(ex);
		}
	}
}

class Person implements java.io.Serializable {
	String firstName;
	String lastName;
}

class Doctor extends Person implements Care {
	String specialty;
	Boolean resident;

	public Doctor(String fName, String lName, String specialty, Boolean resident) {
		this.firstName = fName;
		this.lastName = lName;
		this.specialty = specialty;
		this.resident = resident;
	}

	public String diagnose(Patient p) {
		String[] illness = { "Yellow Fever", "Zika", "Heart Disease", "Malaria", "Flu", "Pregnancy" };
		int idx = new Random().nextInt(illness.length);
		System.out.println("Index of illness is " + idx);
		return illness[idx];
	}

	public double takeTemperature(Patient p) {
		double temp = new Random().nextInt((110 - 95) + 1) + 95;
		return temp;
	}

	public String readChart(Patient p) {
		return "";
	}

	public double readBloodPressure(Patient p) {
	return 0.0;
	 }
}

class Patient extends Person {
	String bloodType;
	String illness;
	double temperature;
	double bloodPressure;

	public Patient(String fName, String lName, String bloodType) {
		this.firstName = fName;
		this.lastName = lName;
		this.bloodType = bloodType;
	}

}

class Nurse extends Person implements Care {
	String designation;
	String shift;

	public Nurse(String fName, String lName, String designation, String shift) {
		this.firstName = fName;
		this.lastName = lName;
		this.designation = designation;
		this.shift = shift;
	}

	public double takeTemperature(Patient p) {
		return 0.0;
	}

	public String readChart(Patient p) {
		return "";
	}

	public double readBloodPressure(Patient p) {
		return 0.0;
	}
}



interface Care  {
	double takeTemperature(Patient p);

	String readChart(Patient p);

	double readBloodPressure(Patient p);
}
