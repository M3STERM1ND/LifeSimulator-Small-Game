package lifesim;
import java.util.*;
import java.io.*;

class Player {
	private String name;
	private int hygiene;
	private int happiness;
	private int hunger;
	private BankAccount persona;
	private static int numPlayers=0;
	private String[] pantry= {"Apple", "Protein Bar", "Instant Ramen", "Oatmeal", "Brownies", "Pizza", "Curry", "Banana", "Chipotle Burrito", "Krispy Kreme Donut"};
	private int[] food_values={5, 7, 18, 17, 20, 30, 28, 5, 25, 10};
	
	public Player(String nombre) {
		name=nombre;
		hygiene=50;
		happiness=50;
		hunger=50;
		persona=new BankAccount();
		numPlayers++;
	}
	public double calculateScore() {
		double finalScore=persona.getBal()+hygiene+happiness+hunger+(persona.checkSecurity()*5);
		return finalScore;
	}
    public BankAccount getBankAccount() {
        return persona;  
    }
	public static int getNumPlayers() {
		return numPlayers;
	}
	public void buyRandomMoodBoost() {
		if (persona.getBal()<20) {
			System.out.println("Not enough funds for ts.");
			return;
		}
		else {
		    persona.subtractBalance(20);
			int choice=(int)(Math.random()*3)+1;
			 if (choice == 1) {  
			        hygiene += 25;
			        if (hygiene > 100) {
			            hygiene = 100;
			        }
			        System.out.println(name+"'s hygiene boosted to " + hygiene + "!");
			    }
			    else if (choice == 2) {  
			        happiness += 25;
			        if (happiness > 100) {
			            happiness = 100;
			        }
			        System.out.println(name+"'s happiness boosted to " + happiness + "!");
			    }
			    else if (choice == 3) {  
			        hunger += 25;
			        if (hunger > 100) {
			            hunger = 100;
			        }
			        System.out.println(name+"'s hunger boosted to " + hunger + "!");
			    }
		}
	}
	
	public void eat() {
		int choice=(int)(Math.random()*pantry.length);
		System.out.println(name+" ate "+pantry[choice]+" and gained +"+food_values[choice]+".");
		hunger+=food_values[choice];
		moderate();
	}
	public void moderate() {
	    if (hygiene > 100) {
	        hygiene = 100;
	    }
	    
	    if (happiness > 100) {
	        happiness = 100;
	    }
	    
	    if (hunger > 100) {
	        hunger = 100;
	    }
	    
	    if (hygiene < 0) {
	        hygiene = 0;
	    }
	    
	    if (happiness < 0) {
	        happiness = 0;
	    }
	    
	    if (hunger < 0) {
	        hunger = 0;
	    }
	}
	public void shower() {
		hygiene+=15;
		moderate();
		System.out.println(name+" successfully showered!");
	}
	public void watchTV() {
		happiness+=21;
		moderate();
		System.out.println(name+" had a fun time!");
	}
	public void dailyDuties() {
		hygiene-=(int)(Math.random()*25)+1;
		happiness-=(int)(Math.random()*25)+1;
		hunger-=(int)(Math.random()*25)+1;
		moderate();
	}
	public String getName() {
		return name;
	}
	public String getMoods() {
		return ("Moods:\n"+"Hygiene Level: "+hygiene+" ||Hunger Level: "+hunger+" ||Happiness Level: "+happiness);
	}
	public int getHygiene() {
		return hygiene;
	}
	public int getHappiness() {
		return happiness;
	}
	public int getHunger() {
		return hunger;
	}
	
}


 class BankAccount{
	private double balance;
	private int securityLevel;
	public BankAccount() {
		balance=50;
		securityLevel=1;
	}
	
	public void addBalance(double amount) {
        balance += amount;  
    }
	public void subtractBalance(double amount) {
        balance -= amount;  
        if (balance < 0) {
            balance = 0;
        }
    }
	public int checkSecurity() {
		return securityLevel;
	}
	public void upgradeSecurity() {
		if (securityLevel==1) {
			if (balance>=100) {
				securityLevel++;
				balance-=100;
				System.out.println("Successfully Upgraded!");
			}
			else {
				System.out.println("Not Enough funds to upgrade!");
			}
		}
		else if (securityLevel==2) {
			if (balance>=200) {
				securityLevel++;
				balance-=200;
				System.out.println("Sucessfully Upgraded!");
			}
			else {
				System.out.println("Not Enough funds to upgrade!");
			}
		}
		else {
			System.out.println("You are completely safe, you have our best security!");
		}
	}
	public void stolenFrom(Player theif) {
		int numero=(int)(Math.random()*3)+1;
		if (balance <=0) {
		    System.out.println("Ts guy too broke. No money to steal!");
		    return;
		}
		if (numero>securityLevel) {
			double amt=(Math.random()*balance);
			subtractBalance(amt);
			System.out.printf("%s drained $%.2f from another player! Get better security!%n",theif.getName(), amt);
			theif.getBankAccount().addBalance(amt);
		}
		else {
			System.out.println("A bum named "+theif.getName()+" tried to steal from another player and failed. Thankfully, security was good enough to stop them.");
		}
	}
	public void getStats() {
		System.out.printf("Balance: $%.2f%nSecurity Level: %d%n", balance, securityLevel);
	}
	public double getBal() {
		return balance;
	}
}

 class Arcade {
	public static void slots (Player p, double bet) {
		BankAccount b=p.getBankAccount();
		if (b.getBal()<bet) {
			System.out.println("Not enough funds :C");
			return;
		}
		b.subtractBalance(bet);
		String[] symbols= {"🕶","👑","🎓","👅","💪"};
		String result=symbols[(int)(Math.random()*5)] + " " + symbols[(int)(Math.random()*5)] + " " + symbols[(int)(Math.random()*5)];
		System.out.println("\n🎰 SLOTS 🎰");
        System.out.println("[ " + result + " ]");
        if (result.equals("🕶 🕶 🕶")||result.equals("👑 👑 👑")||result.equals("🎓 🎓 🎓")||result.equals("👅 👅 👅")||result.equals("💪 💪 💪")) {
        	System.out.println(p.getName()+" won $"+(bet*7));
        	p.watchTV();
        	b.addBalance(bet*7);
        }
        else {
        	System.out.println(p.getName()+" Lost D:!!");
        }
        
	}
	public static void blackjack(Player player, double bet) {
	    Scanner scanner = new Scanner(System.in);
	    BankAccount bank = player.getBankAccount();
	    
	    if (bank.getBal() < bet) {
	        System.out.println("Not enough money!");
	        return;
	    }
	    
	    bank.subtractBalance(bet);
	    
	    int playerHand = (int)(Math.random() * 11) + 10;  
	    int dealerHand = (int)(Math.random() * 11) + 10; 
	    
	    System.out.println("\n🃏 BLACKJACK 🃏");
	    System.out.println("Your hand: " + playerHand);
	    System.out.println("Dealer shows: " + (dealerHand - 5));
	    System.out.print("Hit or Stand? (H/S): ");
	    String choice = scanner.nextLine().toUpperCase();
	    
	    
	    if (choice.equals("H")) {
	        int newCard = (int)(Math.random() * 11) + 1;
	        playerHand += newCard;
	        System.out.println("You drew: " + newCard + " | Total: " + playerHand);
	    }
	    System.out.println("\nDealer hand: " + dealerHand);
	    
	    if (playerHand > 21) {
	        System.out.println("Bust! "+ player.getName() +" lost $" + bet);
	    } 
	    else if (dealerHand > 21 || playerHand > dealerHand) {
	        System.out.println(player.getName()+" won $" + (bet * 2) + "!");
	        bank.addBalance(bet * 2);
	    } 
	    else if (playerHand == dealerHand) {
	        System.out.println("Push! Money back.");
	        bank.addBalance(bet);
	    } 
	    else {
	        System.out.println("Dealer wins! "+player.getName()+ " lost $" + bet);
	    }
	}
	public static void diceRoll(Player player, double bet) {
		BankAccount bank = player.getBankAccount();
	    if (bank.getBal() < bet) {
	        System.out.println("Not enough money!");
	        return;
	    }
	    bank.subtractBalance(bet);
	    System.out.println("Total amount from rolls must be greater than or equal to 7 in order to win.");
	    int dice1=(int)(Math.random() * 6) + 1;
	    int dice2=(int)(Math.random() * 6) + 1;
	    System.out.println("Dice 1: "+dice1+" Dice 2: "+dice2);
	    if (dice1+dice2==7) {
	    	System.out.println("Congratulations "+player.getName()+"! You won $"+(bet*3));
	    	bank.addBalance(bet*3);
	    	player.watchTV();
	    }
	    else if (dice1+dice2>7) {
	    	System.out.println("Congratulations "+player.getName()+"! You won $"+(bet*2));
	    	bank.addBalance(bet*2);
	    	player.watchTV();
	    }
	    else {
	    	System.out.println(player.getName()+" lost $"+bet+" :C.");
	    }
	}
	public static void limbo(Player player, double bet) {
	    Scanner scanner = new Scanner(System.in);
	    BankAccount bank = player.getBankAccount();
	    if (bank.getBal() < bet) {
	        System.out.println("Not enough money!");
	        return;
	    }
	    bank.subtractBalance(bet);
	    System.out.print("Enter your limbo height (1.0-5.0): ");
	    double playerHeight = scanner.nextDouble();
	    scanner.nextLine();
	    
	    if (playerHeight < 1.0 || playerHeight > 5.0) {
	        System.out.println("Invalid height! Must be between 1.0 and 5.0");
	        bank.addBalance(bet); 
	        return;
	    }
	    double barHeight = 1.0 + (Math.random() * 4.0);
	    System.out.printf("\nThe bar is set at: %.1f\n", barHeight);
	    if (playerHeight<barHeight) {
	    	System.out.println("Congratulations "+player.getName()+"! You won $"+(bet*playerHeight)+"!");
	    	player.watchTV();
	    	bank.addBalance(bet*playerHeight);
	    }
	    else {
	    	System.out.println("Sadly you lost D:! Better luck next time!");
	    }
	}
}
class Driver {
	public static ArrayList<Player> playerList= new ArrayList<Player>();
	public static void displayStats() {
		System.out.println("Stats:");
		for (Player p: playerList) {
			p.dailyDuties();
			System.out.println("\n\n"+p.getName()+":");
			p.getBankAccount().getStats();
			System.out.println(p.getMoods()+"\n\n");
		}
	}
	public static String getWinner() {
		double max=0;
		String wName="";
		for (Player a: playerList) {
			if(a.calculateScore()>max) {
				max=a.calculateScore();
				wName=a.getName();
			}
		}
		return wName;
		
	}
	
	public static void main(String[]args) {
		Scanner sc=new Scanner(System.in);
		System.out.print("How many players: ");
		int numPlayers = sc.nextInt();
		sc.nextLine();
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter name for player " + (i+1) + ": ");
            String name = sc.nextLine();  
            playerList.add(new Player(name));
        }
        System.out.println("Player count: "+Player.getNumPlayers());
		for (int j=0;j<10;j++) {
			for (int k=0;k<playerList.size();k++) {
				System.out.println("\n" + playerList.get(k).getName() + "'s turn!");
	            System.out.println("1. Slots");
	            System.out.println("2. Blackjack");
	            System.out.println("3. Dice");
	            System.out.println("4. Limbo");
	            System.out.println("5. Shower");
	            System.out.println("6. Entertain");
	            System.out.println("7. Eat");
	            System.out.println("8. Random Boost ($20)");
	            System.out.println("9. Upgrade Security");
	            System.out.println("10. Steal from random player");
	            System.out.println(playerList.get(k).getName()+"'s choice:");
	            int choice=sc.nextInt();
	            sc.nextLine();
	            if (choice==1) {
	            	System.out.println("How much do you want to put for slots:");
	            	double bet=sc.nextDouble();
	            	sc.nextLine();
	            	Arcade.slots(playerList.get(k), bet);
	            }
	            else if (choice==2) {
	            	System.out.println("How much do you want to put for bj:");
	            	double bet=sc.nextDouble();
	            	sc.nextLine();
	            	Arcade.blackjack(playerList.get(k), bet);
	            }
	            else if (choice==3) {
	            	System.out.println("How much do you want to put for dice:");
	            	double bet=sc.nextDouble();
	            	sc.nextLine();
	            	Arcade.diceRoll(playerList.get(k), bet);
	            }
	            else if (choice==4) {
	            	System.out.println("How much do you want to put for limbo:");
	            	double bet=sc.nextDouble();
	            	sc.nextLine();
	            	Arcade.limbo(playerList.get(k), bet);
	            }
	            else if (choice==5) {
	            	playerList.get(k).shower();
	            }
	            else if (choice==6) {
	            	playerList.get(k).watchTV();
	            }
	            else if (choice==7) {
	            	playerList.get(k).eat();
	            }
	            else if (choice==8) {
	            	playerList.get(k).buyRandomMoodBoost();
	            }
	            else if (choice==9) {
	            	playerList.get(k).getBankAccount().upgradeSecurity();
	            }
	            else if (choice==10) {
	            	int random=(int)(Math.random()*playerList.size());
	            	while (random==k) {
	            		random=(int)(Math.random()*playerList.size());
	            	}
	            	playerList.get(random).getBankAccount().stolenFrom(playerList.get(k));	
	            	System.out.println(playerList.get(k).getName()+" was targetting "+playerList.get(random).getName()+"!");
	            }
	            else {
	            	System.out.println("Please enter a valid choice, you just wasted a turn D:.");
	            }
			}
			System.out.println("After a day of hard work.....\n");
			displayStats();
		}
		sc.close();
		System.out.println("Scores:");
		for (Player a: playerList) {
			System.out.printf("%s's Score: %.2f%n", a.getName(), a.calculateScore());
		}
		System.out.println("\nWinner: "+ getWinner());
	}
}
