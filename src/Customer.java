import java.io.Serializable;

public class Customer implements Serializable{
	public int id;
	public int demand;
	public float serviceTime;
	public float startTW;
	public float endTW;
	public Recharger recharger;
	
	public Customer(int id, int demand, float serviceTime, float startTW, float endTW) {
		this.id= id;
		this.demand= demand;
		this.serviceTime= serviceTime;
		this.startTW= startTW;
		this.endTW= endTW;
		this.recharger= new Recharger();
	}

	public Customer() {
	}

	/**
	 * @return the recharger
	 */
	public Recharger getRecharger() {
		return recharger;
	}

	/**
	 * @param recharger the recharger to set
	 */
	public void setRecharger(Recharger recharger) {
		this.recharger = recharger;
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
	 * @return the demand
	 */
	public int getDemand() {
		return demand;
	}

	/**
	 * @param demand the demand to set
	 */
	public void setDemand(int demand) {
		this.demand = demand;
	}

	/**
	 * @return the serviceTime
	 */
	public float getServiceTime() {
		return serviceTime;
	}

	/**
	 * @param serviceTime the serviceTime to set
	 */
	public void setServiceTime(float serviceTime) {
		this.serviceTime = serviceTime;
	}

	/**
	 * @return the startTW
	 */
	public float getStartTW() {
		return startTW;
	}

	/**
	 * @param startTW the startTW to set
	 */
	public void setStartTW(float startTW) {
		this.startTW = startTW;
	}

	/**
	 * @return the endTW
	 */
	public float getEndTW() {
		return endTW;
	}

	/**
	 * @param endTW the endTW to set
	 */
	public void setEndTW(float endTW) {
		this.endTW = endTW;
	}
}
