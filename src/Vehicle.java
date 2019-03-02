import java.io.Serializable;

public class Vehicle implements Serializable{
	public int id;
	public int tMax;
	public int capacity;
	public int speed;
	public int distCost;
	public float currentBattery;
	public float ro;
	public float alpha;
	public float travelTime;
	public int currentNodeId;
	public boolean goToDepot;
	public float maxBattery;
	public float travelKm;
	
	public Vehicle(int id, int Tmax, int capacity, int speed, int distCost, float currentBattery, float ro, float alpha) {
		this.id= id;
		this.tMax= Tmax;
		this.capacity= capacity;
		this.speed= speed;
		this.distCost= distCost;
		this.currentBattery= currentBattery;
		this.ro= ro;
		this.alpha= alpha;
		this.travelTime= 0;
		this.currentNodeId= 0;
		this.goToDepot= false;
		this.maxBattery = currentBattery;
		this.travelKm= 0; 
	}

	public Vehicle() {
	}

	/**
	 * @return the travelKm
	 */
	public float getTravelKm() {
		return travelKm;
	}

	/**
	 * @param travelKm the travelKm to set
	 */
	public void setTravelKm(float travelKm) {
		this.travelKm = travelKm;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the tMax
	 */
	public int gettMax() {
		return tMax;
	}

	/**
	 * @param tMax the tMax to set
	 */
	public void settMax(int tMax) {
		this.tMax = tMax;
	}

	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
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
	 * @return the currentBattery
	 */
	public float getCurrentBattery() {
		return currentBattery;
	}

	/**
	 * @param currentBattery the currentBattery to set
	 */
	public void setCurrentBattery(float currentBattery) {
		this.currentBattery = currentBattery;
	}

	/**
	 * @return the ro
	 */
	public float getRo() {
		return ro;
	}

	/**
	 * @param ro the ro to set
	 */
	public void setRo(float ro) {
		this.ro = ro;
	}

	/**
	 * @return the alpha
	 */
	public float getAlpha() {
		return alpha;
	}

	/**
	 * @param alpha the alpha to set
	 */
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	/**
	 * @return the travelTime
	 */
	public float getTravelTime() {
		return travelTime;
	}

	/**
	 * @param travelTime the travelTime to set
	 */
	public void setTravelTime(float travelTime) {
		this.travelTime = travelTime;
	}

	/**
	 * @return the currentNodeId
	 */
	public int getCurrentNodeId() {
		return currentNodeId;
	}

	/**
	 * @param currentNodeId the currentNodeId to set
	 */
	public void setCurrentNodeId(int currentNodeId) {
		this.currentNodeId = currentNodeId;
	}

	/**
	 * @return the goToDepot
	 */
	public boolean isGoToDepot() {
		return goToDepot;
	}

	/**
	 * @param goToDepot the goToDepot to set
	 */
	public void setGoToDepot(boolean goToDepot) {
		this.goToDepot = goToDepot;
	}

	/**
	 * @return the maxBattery
	 */
	public float getMaxBattery() {
		return maxBattery;
	}

	/**
	 * @param maxBattery the maxBattery to set
	 */
	public void setMaxBattery(float maxBattery) {
		this.maxBattery = maxBattery;
	}
}
