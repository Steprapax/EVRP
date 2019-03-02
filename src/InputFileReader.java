import java.io.*;
import java.util.*;

public class InputFileReader {
	public void dataReader(String txtFile, Depot depot, ArrayList<Nodes> nodes, ArrayList<Recharger> rechargers, ArrayList<Customer> customers, ArrayList<Vehicle> vehicles) throws IOException {
		FileReader file= new FileReader(txtFile);
		Scanner scanner= new Scanner(file);
		scanner.useLocale(Locale.ENGLISH);
		scanner.nextLine(); 
		scanner.nextLine(); 
		
		String data= scanner.next();
		while(!data.equals("RECHARGERS")){
			int id= Integer.parseInt(data);
			float x= scanner.nextFloat();
			float y= scanner.nextFloat();
			String type= scanner.next();
			
			Nodes node= new Nodes(id, x, y, type);
			nodes.add(node);
			
			data= scanner.next();
		}
		
		depot= new Depot(nodes.get(0).id, nodes.get(0).x, nodes.get(0).x, "D");

		scanner.nextLine();
		scanner.nextLine();
		scanner.nextLine();

		data= scanner.next();
		while(!data.equals("CUSTOMERS")){
			int id= Integer.parseInt(data);
			int numRec= scanner.nextInt();

			Recharger recharger= new Recharger(id, numRec);
			rechargers.add(recharger);
			
			data= scanner.next();
		}
		scanner.nextLine();
		scanner.nextLine();
		
		data= scanner.next();
		while(!data.equals("INSTDATA")){
			int id= Integer.parseInt(data);
			int demand= scanner.nextInt();
			float serviceTime= scanner.nextFloat();
			float startTW= scanner.nextFloat();
			float endTW= scanner.nextFloat();
			
			Customer customer= new Customer(id, demand, serviceTime, startTW, endTW);
			customers.add(customer);
			
			data= scanner.next();
		}
		
		scanner.next();
		scanner.nextLine();
		
		int Tmax= scanner.nextInt();
		int numV= scanner.nextInt();
		int capacity=  scanner.nextInt();
		int speed= scanner.nextInt();
		int distCost= scanner.nextInt();
		int battery= scanner.nextInt();
		float ro= scanner.nextFloat();
		float alpha= scanner.nextFloat();

		for(int id=1;id<=numV;id++){
			Vehicle vehicle= new Vehicle(id, Tmax, capacity, speed, distCost, battery , ro, alpha);
			vehicles.add(vehicle);
		}
		
		scanner.close();
		file.close();
	}
}	
