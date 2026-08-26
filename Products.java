/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

/**
 *
 * @author MUDUMELA MASHAKA
 */
import java.util.Scanner;
public class Products {

    
    static Scanner inpt = new Scanner(System.in);
    static ReportData[] storeItems = new ReportData[10]; 
        
    public static int DisplayMenu(){
        System.out.println("Please select one of the following menu items:");
        System.out.println("(1) Capture a new product.");
        System.out.println("(2) Search for a product.");
        System.out.println("(3) Update a product.");
        System.out.println("(4) Delete a product.");
        System.out.println("(5) Print report.");
        System.out.println("(6) Exit Application.");
        int choice = 0;
        System.out.print("choice >> ");
        while(true){
            choice = inpt.nextInt();
            inpt.nextLine();
            if(!(choice > 0 && choice < 7)){
                System.out.print("Choice must be between 1 and 6"); 
                continue;
            }
            System.out.println("");
            return choice;
        }
    }
    
    public static void CaptureProduct(){
        // Using static index to check array capacity bounds smoothly
        if(ReportData.index >= storeItems.length){
            System.out.println("Max number of products has been reached");
            return;
        }
        else{
            System.out.println("CAPTURE A NEW PRODUCT");
            System.out.println("**************************");
            
            System.out.print("Enter the product code:");
            String prodCode = inpt.nextLine();
            for(int i=0;i<ReportData.index;i++){
                if(storeItems[i]!=null && storeItems[i].getProductCode().equalsIgnoreCase(prodCode)){
                    System.out.println("Product "+storeItems[i].getProductName()+" is already in the system");
                   
                    return;
                }
            }
            
            System.out.print("Enter the product name:");
            String prodName = inpt.nextLine();
            
            System.out.println("");
            System.out.println("Select the product category:");
            System.out.println("Desktop Computer - 1");
            System.out.println("Laptop - 2");
            System.out.println("Tablet - 3");
            System.out.println("Printer - 4");
            System.out.println("Gaming Console - 5");
            System.out.print("Product category >> ");
            int category;
            while(true){
                category = inpt.nextInt();
                inpt.nextLine();
                if(category < 1 || category > 5){
                    System.out.println("Category has to be between 1 and 5");
                    continue;
                }
                break;
            }
            System.out.println("");
            System.out.print("Indicate the product warranty. Enter (1) for 6 months or any other key for 2 years. ");
            String warranty = inpt.nextLine();
            double warrantyMonths;
            if("1".equals(warranty.trim())){
                warrantyMonths = 6.0;
            }
            else{
                warrantyMonths = 24.0;
            }
            System.out.println("");
            System.out.print("Enter the price for " + prodName + " >> ");
            double price;
            while(true){
                price = inpt.nextDouble();
                inpt.nextLine();
                if(price < 0){
                    System.out.println("Price cannot be negative,Enter price again:");
                    continue;
                }
                break;
            }
            
            int stock;
            while(true){
                System.out.print("Enter Stock level for " + prodName + " >> ");
                stock = inpt.nextInt();
                inpt.nextLine();
                if(stock < 0){
                    System.out.println("Stock cannot be negative");
                    continue;
                }
                break;
            }
            
            System.out.print("Enter the supplier for " + prodName + " >> ");
            String supplier = inpt.nextLine();
            
            ReportData i1 = new ReportData(prodCode.trim(), prodName.trim(), warrantyMonths, category, price, stock, supplier);
            SaveProduct(i1);
        }
    }
   
    public static void SaveProduct(ReportData i1){
        // Save at current active index tracking boundary position
        storeItems[ReportData.index] = i1;  
        ReportData.index++; // Counter ticks up exactly once per save
        System.out.println("Product details has been saved successfully!!!");
    }

    public static void SearchProduct(){
        System.out.print("Please enter the product code to search: ");
        String prodCode = inpt.nextLine();
        
        for(int i = 0; i < ReportData.index; i++){
            if(storeItems[i] != null && storeItems[i].getProductCode().equals(prodCode)){
                System.out.println("*************************************************");
                System.out.println("PRODUCT SEARCH RESULTS");
                System.out.println("*************************************************");
                System.out.println("PRODUCT CODE: " + storeItems[i].getProductCode());
                System.out.println("PRODUCT NAME: " + storeItems[i].getProductName());
                System.out.println("PRODUCT WARRANTY: " + (storeItems[i].getWarranty() / 12) + " years");
                System.out.println("PRODUCT CATEGORY: " + CategName(storeItems[i].getCategory()));
                System.out.println("PRODUCT PRICE: R" + storeItems[i].getPrice());
                System.out.println("PRODUCT STOCK LEVELS: " + storeItems[i].getstockLevels());
                System.out.println("PRODUCT SUPPLIER: " + storeItems[i].getSupplier());
                return;
            }   
        }
        System.out.println("The product cannot be located. Invalid Product");
        return;
    }
    
    public static void DeleteProduct(){
        System.out.print("Please enter the product code to delete: ");
        String prodCode = inpt.nextLine();
        boolean found = false;
        
        for(int i = 0; i < ReportData.index; i++){//loop condition is designed to avoid going out of bounds
            if(storeItems[i] != null && storeItems[i].getProductCode().equals(prodCode)){//check if the index we on is not null helps avoid outOfBounds error
                found = true;
                System.out.println("Are you sure that you want to delete? (y) for yes, any other key to cancel");
                String confirm = inpt.nextLine();
                if("y".equalsIgnoreCase(confirm.trim())){
                    
                    //loop for shifting things left 
                    for (int j = i; j < ReportData.index - 1; j++) {//we minusing coz sure, < will stop at the last index but we wanna delete and leave last index empty,hence this
                        storeItems[j] = storeItems[j + 1];//replaces item on the current index with item from next index
                    }
                    //deleting the last index duplicate so it can be reused for another product
                    storeItems[ReportData.index - 1] = null;
                    
                    ReportData.index--; // Static variable for tracking number of objects in ReportData class is reduced by 1
                    System.out.println("Deletion was successful");
                    break;
                }
                else{//this block will be executed when any other key is entered
                    System.out.println("Cancellation successful");
                }
            } 
        } 
        if(!found){//will only be true  if variable found is still false meaning when product was not found
            System.out.println("The product was not found in the system");
        }
        
    }
    public static void UpdateProduct(){
    System.out.print("Please enter the product code to update: ");
    String prodCod = inpt.nextLine().trim();
    boolean code = false;
    
    for(int i = 0; i < ReportData.index; i++){
        if(storeItems[i] != null && storeItems[i].getProductCode().equals(prodCod)){
            code = true;
            
            // updating the product name section
            System.out.print("Update the warranty? (y) Yes, (n) No ");
            while(true){
                String warrInput = inpt.nextLine().trim().toLowerCase();
                if(warrInput.isEmpty()){
                    System.out.print("Please enter 'y' or 'n': ");
                    continue;
                }
                char warr = warrInput.charAt(0);
                
                if(warr == 'y'){
                    System.out.print("Indicate the new product warranty. Enter (1) for 6 months or any other key for 2 years. ");
                    String Warrant = inpt.nextLine();
                    if("1".equals(Warrant.trim())){
                        storeItems[i].setWarranty(6);
                    }
                    else{
                        storeItems[i].setWarranty(24);
                    }
                    break;
                }
                else if(warr == 'n'){
                    System.out.println("It won't be changed then");//change the message to be more user friendly
                    break;
                }
                else{
                    System.out.print("Invalid character entered! Try again (y/n): ");
                }
            }
            
            // updating the price section
            System.out.print("Update the price? (y) Yes, (n) No ");
            while(true){
                String costInput = inpt.nextLine().trim().toLowerCase();
                if(costInput.isEmpty()){
                    System.out.print("Please enter 'y' or 'n': ");
                    continue;
                }
                char cost = costInput.charAt(0);
                
                if(cost == 'y'){
                    while(true){
                        System.out.print("Enter the new price for >> " + storeItems[i].getProductName() + " ");
                        double p = inpt.nextDouble();
                        inpt.nextLine();
                        storeItems[i].setPrice(p);
                        break;
                    } 
                    break;
                }
                else if(cost == 'n'){
                    System.out.println("Okay, Moving on");//change the message to be more user friendly
                    break;
                }
                else{
                    System.out.print("Invalid character entered! Try again (y/n): ");
                }
            }
            
            // updating the stock level section
            System.out.print("Update the stock level? (y) Yes, (n) No ");
            while(true){
                String choiceInput = inpt.nextLine().trim().toLowerCase();
                
                if (choiceInput.isEmpty()) {
                    System.out.print("Please enter 'y' or 'n': ");
                    continue;
                }
                choiceInput = choiceInput.toLowerCase();//making sure
                char stok = choiceInput.charAt(0);
                
                if(stok == 'y'){
                    while(true){
                        System.out.print("Enter the new stock level for " + storeItems[i].getProductName() + " >> ");
                        int st = inpt.nextInt();
                        inpt.nextLine();
                        
                        if(st >= 0){
                            storeItems[i].setStockLevels(st);//changing stock level
                            break; 
                        }
                        else{
                            System.out.println("Stock cannot be negative.");
                        }
                    }
                    break; 
                }
                else if(stok == 'n'){
                    System.out.println("Stock level left unchanged.");
                    break; 
                }
                else{
                    System.out.print("Invalid character entered! Enter (y) for Yes or (n) for No: ");
                } 
            }
            
            // Successful completion path inside the match block
            System.out.println("Product details updated successfully.");
            
            return; 
        }
    } 
    
    
    if(!code){//message to be displayed if the loop doesn't find any products in the system
        System.out.println("Product not found");//message to the user
        
    }
}
    public static void PrintProductReport(){
        System.out.println("PRODUCT REPORT");
        System.out.println("=====================================================================================");
        
        if(ReportData.index == 0){//this if statement is to ensure that the array isn't empty,if condition true then we return nothing
            System.out.println("No products available.");
            System.out.println("");
            
            return;
        }
        double total = 0;
        for(int i = 0; i < ReportData.index; i++){
            total += storeItems[i].getPrice() * storeItems[i].getstockLevels();
            System.out.println("PRODUCT " + (i + 1));
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("PRODUCT CODE >> " + storeItems[i].getProductCode());
            System.out.println("PRODUCT NAME >> " + storeItems[i].getProductName());
            double warranty = storeItems[i].getWarranty() / 12;
            System.out.println("PRODUCT WARRANTY >> " + warranty+ " years");
            System.out.println("PRODUCT CATEGORY >> " + CategName(storeItems[i].getCategory()));
            System.out.println("PRODUCT PRICE >> " + storeItems[i].getPrice());
            System.out.println("PRODUCT STOCK LEVELS >> " + storeItems[i].getstockLevels());
            System.out.println("PRODUCT SUPPLIER >> " + storeItems[i].getSupplier());
            System.out.println("-------------------------------------------------------------------------------------");  
        }
        System.out.println("==================================================================================");
        System.out.println("TOTAL PRODUCT COUNT: " + ReportData.index);
        System.out.println("TOTAL PRODUCT VALUE: R " + total);
        double average = total / ReportData.index;
        System.out.println("AVERAGE PRODUCT VALUE: R " + average);
        System.out.println("==================================================================================");
        
    }
    public static String CategName(int category){
        switch (category) {
            case 1:
                return "Desktop Computer";
            case 2:
                return "Laptop";
            case 3:
                return "Tablet";
            case 4:
                return "Printer";
            case 5:
                return "Gaming Console";
            default:
                break;
        }
        return "";
    }
    public static void ExitApplication(){
        System.exit(0);
    }
}
    



