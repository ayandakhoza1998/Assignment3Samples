/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sample;

/**
 *
 * @author Ayanda  Phumzile Khoza
 * Student Number 218057172
 */
public class MyProgram {
    public static void main(String[] args)
    {
        Test program = new Test();
        program.openFile();
        program.readFile();
        program.openCustomers();
        program.writeCustomer();
        program.openSuppliers();
        program.writeSuppliers();
    }
}
