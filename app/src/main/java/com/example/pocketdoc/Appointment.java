package com.example.pocketdoc;

public class Appointment {

        String appointmentdate="", appointmenttime="", appointmenthospital="", appointmentdisease="";
        Appointment(String appointmentdate, String appointmenttime,String appointmenthospital, String appointmentdisease)
        {
            this.appointmentdate=appointmentdate;
            this.appointmenttime=appointmenttime;
            this.appointmenthospital=appointmenthospital;
            this.appointmentdisease=appointmentdisease;

        }

        public String getappointmentdate() {
        return appointmentdate;
    }

        public String getappointmenttime() { return appointmenttime; }

        public String getappointmenthospital() {
            return appointmenthospital;
        }

        public String getappointmentdisease() {
            return appointmentdisease;
        }


    }


