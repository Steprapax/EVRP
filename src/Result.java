import java.io.*;
import java.util.*;

public class Result {

	public void outputFile(String instance, String time, Objective objective) throws IOException {

		ArrayList<ArrayList<InfoForWriter>> sol= objective.getSolution();
		
		float cpuTime= objective.getCpuTime()/1000;
		float totalKM= objective.sumTotalKm();
		instance = instance.replaceAll(".txt", "");
		PrintWriter printWriter = new PrintWriter(instance+"_Result.csv");
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("Obj" + ';');
		stringBuilder.append(String.format(Locale.ITALIAN, "%f", totalKM) + '\n');		
		stringBuilder.append("Time" + ';');
		stringBuilder.append(String.format(Locale.ITALIAN, "%f", cpuTime) + '\n');
		
		String[] text = {"Vehicle", "Orig","Dest","Dist","Rel","Dead","ServTime","DemDest","ArrTimeDest","ArrBattDest","ArrLoadDest", "RecTime", "PlugUsed", "StartRecharging" }; 
		for(String title : text){
			stringBuilder.append(title + ';');
		}
		stringBuilder.append('\n');
		
		for(int k=0;k<sol.size();k++){
			bugControl(sol.get(k));

			ArrayList<InfoForWriter> solution= sol.get(k);
			for(int i=0;i<solution.size();i++) {
				InfoForWriter info= solution.get(i);
				int temp=info.getVehichle();
				if(sol.size()==3 && k==1) {
					temp= info.getVehichle()-1;
				}
				if(sol.size()==3 && k==2) {
					temp= info.getVehichle()-2;
				}
				
				stringBuilder.append(temp);
				stringBuilder.append(';');
				stringBuilder.append(info.getOrig());
				stringBuilder.append(';');
				stringBuilder.append(info.getDest());
				stringBuilder.append(';');
				stringBuilder.append(String.format(Locale.ITALIAN, "%f", info.getDist()));
				stringBuilder.append(';');
				stringBuilder.append(String.format(Locale.ITALIAN, "%f", info.getRel()));
				stringBuilder.append(';');
				stringBuilder.append(String.format(Locale.ITALIAN, "%f", info.getDeadline()));
				stringBuilder.append(';');
				stringBuilder.append(String.format(Locale.ITALIAN, "%f", info.getServTime()));
				stringBuilder.append(';');
				stringBuilder.append(info.getDemDest());
				stringBuilder.append(';');
				stringBuilder.append(String.format(Locale.ITALIAN, "%f", info.getArrTimeDest()));
				stringBuilder.append(';');
				stringBuilder.append(String.format(Locale.ITALIAN, "%f", info.getArrBattDest()));
				stringBuilder.append(';');
				stringBuilder.append((int)info.getArrLoadDest());
				stringBuilder.append(';');
				stringBuilder.append(String.format(Locale.ITALIAN, "%f", info.getRecTime()));
				stringBuilder.append(';');
				stringBuilder.append(info.getPlugUsed());
				stringBuilder.append(';');
				stringBuilder.append(String.format(Locale.ITALIAN, "%f", info.getRechargingTime()));	
				stringBuilder.append('\n');
			}
		}
		printWriter.write(stringBuilder.toString());
		printWriter.close();
	
		PrintWriter printWriter2 = new PrintWriter(new FileWriter("output.csv", true));
		StringBuilder stringBuilder2 = new StringBuilder();
		stringBuilder2.append(instance);
		stringBuilder2.append(';');
		stringBuilder2.append(Integer.parseInt(time));
		stringBuilder2.append(';');
		stringBuilder2.append(cpuTime);
		stringBuilder2.append(';');
		stringBuilder2.append(String.format(Locale.ITALIAN, "%f", totalKM));
			
		printWriter2.write(stringBuilder2.toString());
		printWriter2.println();
		printWriter2.close();
	}

	private void bugControl(ArrayList<InfoForWriter> solution) {
		for(int i=0;i<solution.size();i++) {
			if(solution.get(i).getOrig()==solution.get(i).getDest()) {
				InfoForWriter info= solution.get(i-1);
				InfoForWriter infoSucc= solution.get(i);
				info.setRecTime(info.getRecTime()+infoSucc.getRecTime());
				solution.remove(i);
			}
		}
		
	}
}
