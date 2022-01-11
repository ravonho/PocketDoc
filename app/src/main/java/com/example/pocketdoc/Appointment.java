package com.example.pocketdoc;

public class Appointment {

        String appointment_date="", appointment_time="", appointment_hospital="", appointment_disease="";
        Appointment(String appointmentdate, String appointmenttime,String appointmenthospital, String appointmentdisease)
        {
            this.appointment_date=appointmentdate;
            this.appointment_time=appointmenttime;
            this.appointment_hospital=appointmenthospital;
            this.appointment_disease=appointmentdisease;

        }

        public String getappointmentdate() {
        return appointment_date;
    }

        public String getappointmenttime() { return appointment_time; }

        public String getappointmenthospital() {
            return appointment_hospital;
        }

        public String getappointmentdisease() {
            return appointment_disease;
        }


    }


