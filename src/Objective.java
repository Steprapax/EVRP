import java.util.*;

public class Objective {
	public ArrayList<Float> objectives;
	public ArrayList<ArrayList<InfoForWriter>> solution;
	public float cpuTime;
	public int distCost;
	public float OBJ;
	
	public Objective() {		
		this.objectives= new ArrayList<Float>();
		this.solution= new ArrayList<ArrayList<InfoForWriter>>();
		this.cpuTime= 0;
		this.distCost= 0;
		this.OBJ= 0;
	}
	
	/**
	 * @return the oBJ
	 */
	public float getOBJ() {
		return OBJ;
	}

	/**
	 * @param oBJ the oBJ to set
	 */
	public void setOBJ(float oBJ) {
		OBJ = oBJ;
	}

	/**
	 * @return the distCost
	 */
	public int getDistCost() {
		return distCost;
	}

	/**
	 * @param distCost the distCost to set
	 */
	public void setDistCost(int distCost) {
		this.distCost = distCost;
	}

	/**
	 * @return the cpuTime
	 */
	public float getCpuTime() {
		return cpuTime;
	}

	/**
	 * @param cpuTime the cpuTime to set
	 */
	public void setCpuTime(float cpuTime) {
		this.cpuTime = cpuTime;
	}

	/**
	 * @return the objectives
	 */
	public ArrayList<Float> getObjectives() {
		return objectives;
	}

	/**
	 * @param objectives the objectives to set
	 */
	public void setObjectives(ArrayList<Float> objectives) {
		this.objectives = objectives;
	}

	/**
	 * @return the solution
	 */
	public ArrayList<InfoForWriter> getSolution(int k) {
		return solution.get(k);
	}

	/**
	 * @param solution the solution to set
	 */
	public void setSolution(ArrayList<InfoForWriter> solution, int k) {
		this.solution.set(k, solution);
	}

	public float sumTotalKm() {
		float sum=0;
		for(Float obj: objectives) {
			if(obj != Float.MAX_VALUE)
				sum+=obj;
		}
		System.out.println(sum*this.distCost);
		return sum*this.distCost;
	}

	public ArrayList<ArrayList<InfoForWriter>> getSolution() {
		return this.solution;
	}
}
