package com.example.exam.Model;

import com.example.exam.Singleton.SingletonConnexionDb;

import java.sql.Connection;

public class Console {
    public static void main(String[] args) {
        Connection con = SingletonConnexionDb.getConnexion();
    }
}
