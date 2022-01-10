package com.example.pocketdoc;

public class Appointment {

        String appointmentdisease="", appointmenttime="", appointmentdate="", appointmenthospital="";
        Appointment(String appointmentdisease, String appointmenttime,String appointmentdate, String appointmenthospital)
        {
            this.appointmentdisease=appointmentdisease;
            this.appointmenttime=appointmenttime;
            this.appointmentdate=appointmentdate;
            this.appointmenthospital=appointmenthospital;


        }

        public String getappointmenttime() { return appointmenttime; }

        public String getappointmentdate() {
            return appointmentdate;
        }

        public String getappointmenthospital() {
            return appointmenthospital;
        }

        public String getappointmentdisease() {
            return appointmentdisease;
        }


    }


