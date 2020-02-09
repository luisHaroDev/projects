// The Stock program is following the MVC design template and this is our controller object.
// The main functionality for buying and selling the stocks are in this controller object.
// This is the ONLY file you may edit

import java.util.LinkedList;
import java.util.Scanner;

public class Controller {
	
	public Controller() {
		LinkedList<Stock> googList = new LinkedList<Stock>();
		LinkedList<Stock> amazList = new LinkedList<Stock>();
		Scanner input = new Scanner(System.in);

		do{
                        input.reset();
       
			int stockSelect = 0;
                        Boolean T = true;
                        
                       try{
                        while(T){
                            T = true;
                            input.reset();
                            System.out.print("Enter the name of stock or type exit to quit: ");
                            String nameOfStock = input.nextLine();
                            nameOfStock = nameOfStock.toLowerCase();
                            //System.out.print(nameOfStock + " 1");
                            if(nameOfStock.equals("google")){
                                stockSelect = 1;  
                                //System.out.print(nameOfStock + " 2");
                                input.reset();
                                T = false;
                            }
                            else if (nameOfStock.equals("amazon")){
                                stockSelect = 2;
                                input.reset();
                                 T = false;
                            }
                            else if(nameOfStock.equals("exit")){
                                stockSelect = 3;
                                input.reset();
                            }
                            else{                            
                               System.out.print("You do not have this stock people try again:\n"); 
                               input.reset();
                            }                          
                        }
                       }catch(Exception e){
                           System.out.println("Something went wrong in the input.");
                       
                       }
                        input.reset();
			if(stockSelect == 3){
				break;
                        }

			System.out.print("Input 1 to buy, 2 to sell: ");
			int controlNum = input.nextInt();
			int quantity = 0;
                       
                        
			
			if(controlNum == 1) {
                                 System.out.print("How many stocks: ");
			         quantity = input.nextInt();
				System.out.print("At what price: ");
				double price = input.nextDouble();
				if(stockSelect == 1) {
					Controller.buyStock(googList, "Google", quantity, price);
				}
				else
					Controller.buyStock(amazList, "Amazon", quantity, price);
			}
			else {
			    do{
                                System.out.print("How many stocks: ");
			        quantity = input.nextInt();
                                int size = 0;
                                double totalStock = 0;
                                
                                if(stockSelect == 1) {
                                 size = googList.size();
                                 for (int i = 0 ; i < size ; i++){
                                 totalStock += googList.get(i).getQuantity();
                                    }
                                  if(totalStock >= quantity){
                                      break;
                                  }
                               }
                               if(stockSelect == 2){
                                  size = amazList.size();
                                  for (int i = 0 ; i < size ; i++){
                                      //System.out.println(" total " + googList.get(i).getQuantity());
                                      totalStock += amazList.get(i).getQuantity();   
                                      }
                                  if(totalStock >= quantity){
                                      break;
                                  }
                                }
                               System.out.print("not a valid quantity, please try again: ");
                                   

                        }while(true);
                            
                            
                                System.out.print("Press 1 for LIFO accounting, 2 for FIFO accounting: ");
				controlNum = input.nextInt();
                                System.out.print("At what price: ");
				double price = input.nextDouble();
				if(controlNum == 1) {
					if(stockSelect == 1)
						Controller.sellLIFO(googList, quantity, price);
					else
						Controller.sellLIFO(amazList, quantity, price);
				}
				else {
					if(stockSelect == 1)
						Controller.sellFIFO(googList, quantity, price);
					else
						Controller.sellFIFO(amazList, quantity, price);
				}
			}
			
		} while(true);
		input.close();
	}
			
	public static void buyStock(LinkedList<Stock> list, String name, int quantity, double price) {
		Stock temp = new Stock(name,quantity,price);
		list.add(temp);
		System.out.printf("You bought %d shares of %s stock at $%.2f per share %n", quantity, name, price);
	}
	
	public static void sellLIFO(LinkedList<Stock> list, int numToSell, double price) {
	    
            double total = 0; // this variable will store the total after the sale
	    double profit = 0; // the price paid minus the sale price, negative # means a loss
            int k = 0;
            double p = 0;
            int i = 1;
            int shareLeft = 0; // stock left per object
            
             while(true){
                if(shareLeft < 0 )break; 
                k = list.get(list.size() - i).getQuantity(); //total quantity of stock per obj
                p = list.get(list.size() - i).getPrice();    //total price per stock per obj
                shareLeft = numToSell - k;    // # you want to sell minus quantity in object
                
                //if number to sell less that stock in last object, get total you paid for it and break loop
                if(numToSell < list.get(list.size()-1).getQuantity()){
                    total = numToSell * p;
                    list.get(list.size()-1).setQuantity(list.get(list.size() - 1).getQuantity() - numToSell);//set new quantity to last stock object
                    break;
                }
                //otherwise get the total and keep going
                total += k * p; 
               
                // if number to sell equal to number of stock in last object break loop
                if(shareLeft == 0) break;
                
                //if no cases met enter loop for other objects in list
                while(true){
                    if(shareLeft > 0){
                        i++;
                        //System.out.print("i : " + i);
                        k = list.get(list.size() - i).getQuantity(); //total quantity of stock per obj
                        p = list.get(list.size() - i).getPrice();    //total price per stock per obj
                        int Left =  shareLeft - k;
                        
                        if(Left > 0){
                            total += p * k;
                            list.get(list.size()-i).setQuantity(0);
                            shareLeft = Left;
                        }
                        else{
                            total += p * shareLeft;
                            shareLeft = Left;
                            list.get(list.size()-i).setQuantity(list.get(list.size()-i).getQuantity() - shareLeft);
                            //break;
                        }
                    }
                    else
                        break;
                    
                   //break;
                }
               
               break;
           } 
            
            //total nimus the number to sell and the price you are selling them, give the profit
            profit = (price*numToSell)- total;
            
	    System.out.printf("You sold %d shares of %s stock at %.2f per share %n", numToSell, list.element().getName(), price);
	    System.out.printf("You made $%.2f on the sale %n", profit);
	
	}
	
	public static void sellFIFO(LinkedList<Stock> list, int numToSell, double price) {
	    
	    double total = 0; // this variable will store the total after the sale
	    double profit = 0; // the price paid minus the sale price, negative # means a loss
            int k = 0;
            double p = 0;
            int i = 0;
            int shareLeft = 0;
            
            while(true){
                if(shareLeft < 0 )break;
                
                if(numToSell < list.get(0).getQuantity()){
                    p = list.get(0).getPrice();
                    total = numToSell * p;
                    list.get(0).setQuantity(k - numToSell);
                    break;
                }
                
                k = list.get(i).getQuantity();
                p = list.get(i).getPrice();
                shareLeft = numToSell - k;

                total += k * p;
               
                if(shareLeft == 0) break;
                
                while(true){
                    if(shareLeft > 0){
                        i++;
                        //System.out.print("i : " + i);
                        k = list.get(i).getQuantity();
                        p = list.get(i).getPrice();
                        int Left =  shareLeft - k;
                        
                        if(Left > 0){
                            total += p * k;
                            list.get(i).setQuantity(0);
                            shareLeft = Left;
                        }
                        else{
                            total += p * shareLeft;
                            shareLeft = Left;
                            list.get(i).setQuantity(list.get(i).getQuantity() - shareLeft);
                            //break;
                        }
                    }
                    else
                        break;
                    
                   //break;
                }
               
               break;
           } 
            
            profit = (price*numToSell)- total;
            
	    System.out.printf("You sold %d shares of %s stock at %.2f per share %n", numToSell, list.element().getName(), price);
	    System.out.printf("You made $%.2f on the sale %n", profit);
	}
		
}