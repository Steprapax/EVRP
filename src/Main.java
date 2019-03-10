import java.io.*;
import java.util.*;
import org.apache.commons.lang3.SerializationUtils;

public class Main {
	public static Depot depot= new Depot();
	public static ArrayList<Nodes> nodes= new ArrayList<Nodes>();
	public static ArrayList<Customer> customers= new ArrayList<Customer>();
	public static ArrayList<Recharger> rechargers= new ArrayList<Recharger>();
	public static ArrayList<Vehicle> vehicles= new ArrayList<Vehicle>();
	public static ArrayList<ArrayList<Float>> nodeToNode= new ArrayList<ArrayList<Float>>();
	public static Objective objective= new Objective();
	public static Recharger recharger= new Recharger();
	public static ArrayList<InfoForWriter> possibleSolution= new ArrayList<InfoForWriter>();
	public static ArrayList<Interval> interval= new ArrayList<Interval>();
	
	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		
		InputFileReader reader= new InputFileReader();
		reader.dataReader(args[0], depot, nodes, rechargers, customers, vehicles);
		nodeToNode();
		objective.setDistCost(vehicles.get(0).getDistCost());
		
		ArrayList<ArrayList<Customer>> grouped= new ArrayList<ArrayList<Customer>>();
		grouped=groupNode(grouped);
		
		for(int i=0;i<grouped.size();i++) {
			objective.objectives.add(Float.MAX_VALUE);
			objective.solution.add(new ArrayList<InfoForWriter>());
		}
		
		long limitTime= Integer.parseInt(args[1])*1000;
		long starTimer= System.currentTimeMillis();
		int cont=0;
		while(System.currentTimeMillis()<starTimer + limitTime) {		
			cont++;
			ArrayList<Vehicle> clonedVehicle= new ArrayList<Vehicle>();
			for(Vehicle v: vehicles) {
				clonedVehicle.add(SerializationUtils.clone(v));	
			}
			
			ArrayList<ArrayList<Customer>> clonedGroup= new ArrayList<ArrayList<Customer>>();
			for(int i1=0;i1<grouped.size();i1++) {
				ArrayList<Customer> temp= grouped.get(i1);
				ArrayList<Customer> cloned= new ArrayList<Customer>();

				for(int k=0;k<temp.size();k++) {
					cloned.add(SerializationUtils.clone(temp.get(k)));
				}
				clonedGroup.add(cloned);
			}
			
			int numVeh= clonedVehicle.size()/clonedGroup.size();
			int k=0;
		
			for(ArrayList<Customer> group: clonedGroup) {
			//for(int j=0;j<1;j++) {
				//ArrayList<Customer> group= grouped.get(0);
				customers= group;
				recharger= customers.get(0).getRecharger();
				float travelKM= 0;
				interval= new ArrayList<Interval>();
				possibleSolution= new ArrayList<InfoForWriter>();

				objective.setDistCost(clonedVehicle.get(0).getDistCost());

				for(int i=0;i<numVeh;i++) {
					int idVisitedNode= depot.getId();
					if(customers.isEmpty())
						break;
					do {
						idVisitedNode= routing(clonedVehicle.get(0));
					}while(idVisitedNode!=depot.getId());

					travelKM+=clonedVehicle.remove(0).getTravelKm();
					
					if(customers.isEmpty()) {
						if(objective.objectives.get(k).floatValue()>travelKM) { 
//							float time = threadMXBean.getCurrentThreadCpuTime();
							
							objective.objectives.set(k, travelKM);
							objective.setSolution(possibleSolution, k);
							objective.setCpuTime(System.currentTimeMillis()-starTimer);
							System.out.println("v: "+k + " "+ objective.objectives.get(k).floatValue()*objective.getDistCost() + " " + objective.getCpuTime());
						}
					}
				}
				k++;
			}
		}
		
		System.out.println(cont);
		Result result= new Result();
		result.outputFile(args[0], args[1], objective);
	}
	//////////////Ricerca del percorso//////////////
	private static int routing(Vehicle vehicle) {
		
		if(vehicle.currentNodeId==0 ){
			Random rand= new Random();
			int r= rand.nextInt(customers.size()+1);{
				if(r==customers.size())
					return goRecharger(vehicle);
			}
		}
		if(customers.size()==0) {
			evaluateHomePath(vehicle);
		}
		if(vehicle.goToDepot==true) {
			return goDepot(vehicle);
		}
				
		ArrayList<Customer> candidateList = new ArrayList<Customer>();
		ArrayList<Customer> restrictedCandidateList = new ArrayList<Customer>();

		for(Customer customer: customers) {
			int customerId= customer.getId();
			float customerDist= nodeDistance(vehicle.getCurrentNodeId(), customerId);
			float rechargerDist= nodeDistance(customerId, recharger.getId());
			
			if (customerDist+rechargerDist<=vehicle.getCurrentBattery()/vehicle.getRo()){
				candidateList.add(customer);	
			}
		}

		if(!candidateList.isEmpty()) {
			for(Customer candidate: candidateList) {
				int customerId= candidate.getId();
				float customerDist= nodeDistance(vehicle.getCurrentNodeId(), customerId);
				float totalTime = customerDist/vehicle.getSpeed();

				if (vehicle.getTravelTime() + totalTime <= candidate.getEndTW()){
					restrictedCandidateList.add(candidate);	
				}
			}
			Random random= new Random();
			Customer selectedCustomer= new Customer();
			if(!restrictedCandidateList.isEmpty()) {
				selectedCustomer= restrictedCandidateList.get(random.nextInt(restrictedCandidateList.size()));
				
				if(evaluateLimitTime(vehicle, selectedCustomer) && meetingDemand(vehicle, selectedCustomer)) {					
					float customerDistance= nodeDistance(vehicle.getCurrentNodeId(), selectedCustomer.getId());
					
					if(vehicle.getTravelTime()+customerDistance/vehicle.getSpeed() <= selectedCustomer.getStartTW()){
						vehicle.setTravelTime(selectedCustomer.getServiceTime() + selectedCustomer.getStartTW());
					}
					else{
						vehicle.setTravelTime(vehicle.getTravelTime()+customerDistance/vehicle.getSpeed()+selectedCustomer.getServiceTime());
					}
					
					vehicle.setCapacity(vehicle.getCapacity()-selectedCustomer.getDemand());
					vehicle.setCurrentBattery(vehicle.getCurrentBattery() - customerDistance*vehicle.ro);
					vehicle.setTravelKm(vehicle.getTravelKm()+customerDistance);
					customers.remove(selectedCustomer);
					
					possibleSolution.add(new InfoForWriter(vehicle.getId(), vehicle.getCurrentNodeId(), selectedCustomer.getId(), customerDistance, selectedCustomer.getStartTW(), selectedCustomer.getEndTW(), selectedCustomer.getServiceTime(), selectedCustomer.getDemand(), vehicle.getTravelTime()-selectedCustomer.serviceTime, vehicle.getCurrentBattery(), vehicle.getCapacity(), 0, 0, 0));
				
					vehicle.setCurrentNodeId(selectedCustomer.getId());
					
					return vehicle.getCurrentNodeId();
				}
				
				else 
					evaluateHomePath(vehicle);
			}
			else
				evaluateHomePath(vehicle);
		}
		else 
			return goRecharger(vehicle);
		
		return vehicle.getCurrentNodeId();
	}

	//////////Soddisfacimento limite TMAX///////////////
	private static boolean evaluateLimitTime(Vehicle vehicle, Customer customer) {
		int maxLimitTime= vehicle.gettMax();
		float visitingNodeTime=vehicle.getTravelTime()+nodeDistance(vehicle.getCurrentNodeId(), customer.getId())/vehicle.getSpeed()+customer.getServiceTime();
		float depotDistTime= nodeDistance(customer.getId(), depot.getId())/vehicle.getSpeed();
		
		return visitingNodeTime+depotDistTime <= maxLimitTime;
	}
	
	/////////Soddisfacimento della domanda////////////////
	private static boolean meetingDemand(Vehicle vehicle, Customer selectedCustomer) {
		return vehicle.getCapacity() - selectedCustomer.demand>0;
	}
	
	/////////////Funzione per valutare il ritorno al depot////////////
	private static int evaluateHomePath(Vehicle vehicle) {
		
		float depotDist= nodeDistance(depot.getId(), vehicle.getCurrentNodeId());
		if(depotDist*vehicle.getRo()<=vehicle.getCurrentBattery()) {
			return goDepot(vehicle);
		}
		else {
			vehicle.setGoToDepot(true);
			return goRecharger(vehicle);
		}
	}
	
	//////////////Funzione per ricaricare i veicoli/////////////////////
	private static int goRecharger(Vehicle vehicle) {
		
		float rechargerDist= nodeDistance(recharger.getId(), vehicle.getCurrentNodeId());
				
		vehicle.setCurrentBattery(vehicle.getCurrentBattery() - rechargerDist*vehicle.getRo());
		
		float kilowatt= 0;
		if(vehicle.isGoToDepot()) {
			float var= nodeDistance(depot.getId(), recharger.getId()); 
			kilowatt = var*vehicle.getRo();	
			kilowatt= kilowatt-vehicle.getCurrentBattery();			
		}
		else {
//			float max=Float.MIN_VALUE;
//			for(Customer cust: this.customers) {
//				float nodeDist= nodeDistance(cust.getId(), recharger.getId());
//					if(nodeDist>max) {
//						max= nodeDist;
//					}
//			}

//			if(2*max*vehicle.getRo()+vehicle.getCurrentBattery()>=14)
//				kilowatt= vehicle.getMaxBattery()-vehicle.getCurrentBattery();
//			else {
				//kilowatt = 2*max*vehicle.getRo();
			float min=Float.MAX_VALUE;
			for(Customer cust: customers){
				float nodeDist= nodeDistance(cust.getId(), recharger.getId());
					if(nodeDist<min) {
						min= nodeDist;
					}
			}
				Random rand = new Random();
				float filling= (float)(vehicle.getCurrentBattery()/(vehicle.getMaxBattery()-0.000001) + rand.nextFloat() * (1 - vehicle.getCurrentBattery()/(vehicle.getMaxBattery()-0.000001)));
				kilowatt= vehicle.getMaxBattery()*filling - vehicle.getCurrentBattery();
//			}
		}
		vehicle.setTravelTime(vehicle.getTravelTime() + rechargerDist/vehicle.getSpeed());
		float initialArrivalTime= vehicle.getTravelTime();
		
		if(vehicle.getId()==1 || vehicle.getId()==3 ||vehicle.getId()==5) {
			if(vehicle.getCurrentNodeId()==recharger.id){
				Interval interv= interval.get(interval.size()-1);

				Interval intervalSet= new Interval();
				intervalSet.start= interv.start;
				
				float battery = vehicle.getCurrentBattery();
				vehicle.setCurrentBattery(vehicle.getCurrentBattery() + kilowatt);
				
				possibleSolution.add(new InfoForWriter(vehicle.getId(), vehicle.getCurrentNodeId(), recharger.getId(), rechargerDist, 0, 0, 0, 0, vehicle.getTravelTime(), battery, vehicle.getCapacity(), kilowatt/vehicle.getAlpha(), 1, vehicle.getTravelTime()));
	
				vehicle.setTravelTime(vehicle.getTravelTime() + kilowatt/vehicle.getAlpha());
				
				intervalSet.setEnd(vehicle.getTravelTime());
				
				vehicle.setTravelKm(vehicle.getTravelKm() + rechargerDist);
				vehicle.setCurrentNodeId(recharger.getId());
				interval.remove(interval.size()-1);
				interval.add(intervalSet);
	
				return vehicle.getCurrentNodeId();
			}
			else {
				Interval intervalSet= new Interval();
				intervalSet.start= vehicle.getTravelTime();
				
				float battery = vehicle.getCurrentBattery();
				vehicle.setCurrentBattery(vehicle.getCurrentBattery() + kilowatt);
				
				possibleSolution.add(new InfoForWriter(vehicle.getId(), vehicle.getCurrentNodeId(), recharger.getId(), rechargerDist, 0, 0, 0, 0, vehicle.getTravelTime(), battery, vehicle.getCapacity(), kilowatt/vehicle.getAlpha(), 1, vehicle.getTravelTime()));
	
				vehicle.setTravelTime(vehicle.getTravelTime() + kilowatt/vehicle.getAlpha());
				
				intervalSet.setEnd(vehicle.getTravelTime());
				
				vehicle.setTravelKm(vehicle.getTravelKm() + rechargerDist);
				vehicle.setCurrentNodeId(recharger.getId());
				
				interval.add(intervalSet);
	
				return vehicle.getCurrentNodeId();
			}
		}
		else {
			float battery = vehicle.getCurrentBattery();
			float time= vehicle.getTravelTime();
			
			for(Interval takeInterval: interval) {
				if(((time>=takeInterval.getStart() && time<takeInterval.getEnd()) || (time<takeInterval.getStart() && time+ kilowatt/vehicle.getAlpha()>takeInterval.getStart()) || (time<takeInterval.getEnd() && time+ kilowatt/vehicle.getAlpha()>takeInterval.getEnd())) ){
					vehicle.setTravelTime(takeInterval.getEnd());
				}
				if(((vehicle.getTravelTime()>=takeInterval.getStart() && vehicle.getTravelTime()<takeInterval.getEnd()) || (vehicle.getTravelTime()<takeInterval.getStart() && vehicle.getTravelTime()+ kilowatt/vehicle.getAlpha()>takeInterval.getStart()) || (vehicle.getTravelTime()<takeInterval.getEnd() && vehicle.getTravelTime()+ kilowatt/vehicle.getAlpha()>takeInterval.getEnd())) ){
					vehicle.setTravelTime(takeInterval.getEnd());
				}
			}
	
			vehicle.setCurrentBattery(vehicle.getCurrentBattery() + kilowatt);
			
			possibleSolution.add(new InfoForWriter(vehicle.getId(), vehicle.getCurrentNodeId(), recharger.getId(), rechargerDist, 0, 0, 0, 0, initialArrivalTime, battery, vehicle.getCapacity(), kilowatt/vehicle.getAlpha(), 1, vehicle.getTravelTime()));

			vehicle.setTravelTime(vehicle.getTravelTime() + kilowatt/vehicle.getAlpha());
			vehicle.setTravelKm(vehicle.getTravelKm() + rechargerDist);
			vehicle.setCurrentNodeId(recharger.getId());

			return vehicle.getCurrentNodeId();		
		}
	}
	
	///////////Funzione per raggiungere il depot///////////////////
	private static int goDepot(Vehicle vehicle) {
		
		float depotDist= nodeDistance(depot.getId(), vehicle.getCurrentNodeId());
		
		vehicle.setTravelTime(vehicle.getTravelTime() + depotDist/vehicle.getSpeed());
		vehicle.setCurrentBattery(vehicle.getCurrentBattery() - depotDist*vehicle.getRo());	
		vehicle.setTravelKm(vehicle.getTravelKm() + depotDist);
		
		possibleSolution.add(new InfoForWriter(vehicle.getId(), vehicle.getCurrentNodeId(), depot.getId(), depotDist, 0, 0, 0, 0, vehicle.getTravelTime(), vehicle.getCurrentBattery(), vehicle.getCapacity(), 0, 0, 0));
		
		vehicle.setCurrentNodeId(depot.getId());

		return vehicle.getCurrentNodeId();
	}

	private static float nodeDistance(int idNode1, int idNode2) {
		return nodeToNode.get(idNode1).get(idNode2);
	}
	
	//////////Funzione per calcolo distanze (NODO X NODO)/////////////////
	public static void nodeToNode(){		
		for(Nodes nodeX: nodes){
			ArrayList<Float> row = new ArrayList<Float>();		
			for(Nodes nodeY: nodes){
				float distance= (float) Math.sqrt(Math.pow((nodeX.getX() - nodeY.getX()), 2) + Math.pow((nodeX.getY() - nodeY.getY()), 2));
				row.add(distance);	
			}
			nodeToNode.add(row);
		}
	}
	///////////////Funzione di raggruppamento dei customers//////////////
	private static ArrayList<ArrayList<Customer>> groupNode(ArrayList<ArrayList<Customer>> grouped) {
		for(Recharger recharger: rechargers) {
			ArrayList<Customer> group= new ArrayList<Customer>();
			
			for(Customer customer: customers) {
				if(customer.id==0)
					continue;
				if(customers.size()>40) {
					if(nodeToNode.get(customer.getId()).get(recharger.getId()) <= 15) {
						customer.setRecharger(recharger);
						group.add(customer);
					}
				}
				else{
					if(nodeToNode.get(customer.getId()).get(recharger.getId()) <= 20) {
						customer.setRecharger(recharger);
						group.add(customer);
					}
				}
			}
			grouped.add(group);
		}
		return grouped;
	}
}
