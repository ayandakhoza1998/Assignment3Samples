/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sample;

/**
 *
 * @author Ayanda Khoza
 * Student Number 218057172
 */

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
        
     
public class Test{
    private ObjectInputStream objectInputStream;
    private ArrayList <Customer> customerList = new ArrayList<Customer>();
    private ArrayList <Supplier> supplierList = new ArrayList<Supplier>();
    private BufferedWriter writerObj;

    public void openFile()
    {
        try{
            objectInputStream = new ObjectInputStream(new FileInputStream("stakeholder.ser"));
            System.out.println("file opened without any problems");
        }
        catch (IOException e)
        {
            System.out.println("error opening stakeholder.ser file: " + e.getMessage());
            System.exit(1);
        }
    }
    
    public void readFile()
    {
        try{
            for(int i = 0; i < 11; ++i)
            {
                Object readObject = objectInputStream.readObject();
                if ( readObject instanceof  Customer)
                {
                    customerList.add((Customer)readObject);
                }
                else{
                    supplierList.add((Supplier)readObject);
                }
            }
        }catch(IOException e){
            System.out.println("error reading stakeholder.ser file: " + e.getMessage());
            System.exit(1);
        }catch(ClassNotFoundException a){
            System.out.println("error reading stakeholder.ser file: " + a.getMessage());
            System.exit(1);
        }finally{
            try{
                objectInputStream.close();
            }catch (IOException g){
                System.out.println("error closing file: " + g.getMessage());
                System.exit(1);
            }
        }
    }
    
    public void openCustomers()
    {
        try{
            writerObj = new BufferedWriter(new FileWriter("customerOutFile.txt"));
        }catch(IOException e)
        {
            System.out.println("Failed to create file "  + e.getMessage());
        }
    }
    
    public void openSuppliers()
    {
        try{
            writerObj = new BufferedWriter(new FileWriter("supplierOutFile.txt"));
        }catch(IOException e)
        {
            System.out.println("Failed to create file "  + e.getMessage());
        }
    }
    
    public void writeCustomer()
    {
        int ableToRenter = 0;
        int unableToRent = 0;
        sortCustomer();
        try{
            writerObj.write("======================== CUSTOMERS =============================\n" +
"ID   	Name      	Surname   	Date of birth  	Age  \n" +
"================================================================\n");
            for(int i = 0; i < customerList.size(); ++i)
            {
                writerObj.write(String.format("%-5s\t%-10s\t%-10s\t%-15s\t%-10s\n",
                    customerList.get(i).getStHolderId() ,
                    customerList.get(i).getFirstName() ,
                    customerList.get(i).getSurName() ,
                    DOBFormat(customerList.get(i)).toString() ,
                    getCustomerAge(customerList.get(i))
                ));
                
                if(customerList.get(i).getCanRent())
                {
                    ableToRenter += 1;
                }else{
                    unableToRent += 1;
                }
            }   
            
            writerObj.write("\n\nNumber of customers who can rent: " + ableToRenter);
            writerObj.write("\nNumber of customers who cannot rent: " + unableToRent);
        }catch(IOException e){
             System.out.println("error writing to file: " + e.getMessage());
        }finally{
            try{
                writerObj.close();
            }catch (IOException g){
                System.out.println("error closing file: " + g.getMessage());
                System.exit(1);
            }
        }
    }
    
    public void writeSuppliers()
    {
        sortSupplier();
        try{
             writerObj.write("======================= SUPPLIERS ==========================\n" +
"ID   	Name                	Prod Type	Description    \n" +
"============================================================\n");
            for(int i = 0; i < supplierList.size(); ++i)
            {
                writerObj.write(String.format("%-5s\t%-20s\t%-10s\t%-15s\n",
                    supplierList.get(i).getStHolderId(),
                    supplierList.get(i).getName() ,
                    supplierList.get(i).getProductType() ,
                     supplierList.get(i).getProductDescription()
                   
                ));
                              
            }   
        }catch(IOException e){
             System.out.println("error writing to file: " + e.getMessage());
        }finally{
            try{
                writerObj.close();
            }catch (IOException g){
                System.out.println("error closing file: " + g.getMessage());
                System.exit(1);
            }
        }
    }
    
    public void sortCustomer()
    {
        Customer custTemp;
        int size = customerList.size();
        Customer[] customers = new Customer[size];
        for(int n = 0; n < size; ++n )
        {
            customers[n] = customerList.get(n);
        }
        for (int i = 0; i < customers.length - 1; i++)
        {
            for (int j = i + 1; j < customers.length; j++)
            {
                if (customers[i].getStHolderId().
                    compareTo(customers[j].getStHolderId()) > 0)
                {
                    custTemp = customers[i];
                    customers[i] = customers[j];
                    customers[j] = custTemp;
                }
            }
        }
        customerList.clear();
        customerList.addAll(Arrays.asList(customers));
    }
    
    public void sortSupplier()
    {
        Supplier custTemp;
        int size = supplierList.size();
        Supplier[] suppliers = new Supplier[size];
        for(int n = 0; n < size; ++n )
        {
            suppliers[n] = supplierList.get(n);
        }
        for (int i = 0; i < suppliers.length - 1; i++)
        {
            for (int j = i + 1; j < suppliers.length; j++)
            {
                if (suppliers[i].getName().
                    compareTo(suppliers[j].getName()) > 0)
                {
                    custTemp = suppliers[i];
                    suppliers[i] = suppliers[j];
                    suppliers[j] = custTemp;
                }
            }
        }
        supplierList.clear();
        supplierList.addAll(Arrays.asList(suppliers));
    }
    
    public int getCustomerAge(Customer object)
    {
        LocalDate dateOB = LocalDate.parse(object.getDateOfBirth());
        LocalDate todayDate = LocalDate.now();
        int objectAge = Period.between(dateOB, todayDate).getYears();
        return objectAge;
    }

    public String DOBFormat(Customer dob)
    {
        LocalDate dobFormat = LocalDate.parse(dob.getDateOfBirth());
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String dateOB = dobFormat.format(format); 
        return dateOB;
    }
    
}

