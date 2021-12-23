package com.example.pocketdoc;

public class Object {

    String Name="", Contact="", Location="", Doctor="", Start="", End="";
    Object(String Name,String Contact, String Location, String Doctor, String Start, String End)
    {
        this.Name=Name;
        this.Contact=Contact;
        this.Location=Location;
        this.Doctor=Doctor;
        this.Start=Start;
        this.End=End;
    }

    public String getName() {
        return Name;
    }

    public String getContact() {
        return Contact;
    }

    public String getLocation() {
        return Location;
    }

    public String getDoctor() {
        return Doctor;
    }

    public String getStart() {
        return Start;
    }

    public String getEnd() {
        return End;
    }
}
