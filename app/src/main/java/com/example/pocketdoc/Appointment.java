package com.example.pocketdoc;

public class Appointment {

        String appointmenttime="", appointmentdate="", appointmenthospital="", appointmentdisease="";
        Appointment(String appointmenttime,String appointmentdate, String appointmenthospital, String appointmentdisease)
        {
            this.appointmenttime=appointmenttime;
            this.appointmentdate=appointmentdate;
            this.appointmenthospital=appointmenthospital;
            this.appointmentdisease=appointmentdisease;

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


