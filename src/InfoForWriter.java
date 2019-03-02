
public class InfoForWriter {
	public int vehichle;
	public int orig;
	public int dest;
	public float dist;
	public float rel;
	public float deadline;
	public float servTime;
	public int demDest;
	public float arrTimeDest;
	public float arrBattDest;
	public float arrLoadDest;
	public float recTime;
	public int plugUsed;
	public float rechargingTime;
	
	public InfoForWriter(int vehichle, int orig, int dest, float dist, float rel, float deadline, float servTime, int demDest, float arrTimeDest, float arrBattDest, float arrLoadDest, float recTime, int plugUsed, float startChgtime) {
		this.vehichle= vehichle;
		this.orig= orig;
		this.dest= dest;
		this.dist= dist;
		this.rel= rel;
		this.deadline= deadline;
		this.servTime= servTime;
		this.demDest= demDest;
		this.arrTimeDest= arrTimeDest;
		this.arrBattDest= arrBattDest;
		this.arrLoadDest= arrLoadDest;
		this.recTime= recTime;
		this.plugUsed= plugUsed;
		this.rechargingTime= startChgtime;
	}

	/**
	 * @return the vehichle
	 */
	public int getVehichle() {
		return vehichle;
	}

	/**
	 * @param vehichle the vehichle to set
	 */
	public void setVehichle(int vehichle) {
		this.vehichle = vehichle;
	}

	/**
	 * @return the orig
	 */
	public int getOrig() {
		return orig;
	}

	/**
	 * @param orig the orig to set
	 */
	public void setOrig(int orig) {
		this.orig = orig;
	}

	/**
	 * @return the dest
	 */
	public int getDest() {
		return dest;
	}

	/**
	 * @param dest the dest to set
	 */
	public void setDest(int dest) {
		this.dest = dest;
	}

	/**
	 * @return the dist
	 */
	public float getDist() {
		return dist;
	}

	/**
	 * @param dist the dist to set
	 */
	public void setDist(float dist) {
		this.dist = dist;
	}

	/**
	 * @return the rel
	 */
	public float getRel() {
		return rel;
	}

	/**
	 * @param rel the rel to set
	 */
	public void setRel(float rel) {
		this.rel = rel;
	}

	/**
	 * @return the deadline
	 */
	public float getDeadline() {
		return deadline;
	}

	/**
	 * @param deadline the deadline to set
	 */
	public void setDeadline(float deadline) {
		this.deadline = deadline;
	}

	/**
	 * @return the servTime
	 */
	public float getServTime() {
		return servTime;
	}

	/**
	 * @param servTime the servTime to set
	 */
	public void setServTime(float servTime) {
		this.servTime = servTime;
	}

	/**
	 * @return the demDest
	 */
	public int getDemDest() {
		return demDest;
	}

	/**
	 * @param demDest the demDest to set
	 */
	public void setDemDest(int demDest) {
		this.demDest = demDest;
	}

	/**
	 * @return the arrTimeDest
	 */
	public float getArrTimeDest() {
		return arrTimeDest;
	}

	/**
	 * @param arrTimeDest the arrTimeDest to set
	 */
	public void setArrTimeDest(float arrTimeDest) {
		this.arrTimeDest = arrTimeDest;
	}

	/**
	 * @return the arrBattDest
	 */
	public float getArrBattDest() {
		return arrBattDest;
	}

	/**
	 * @param arrBattDest the arrBattDest to set
	 */
	public void setArrBattDest(float arrBattDest) {
		this.arrBattDest = arrBattDest;
	}

	/**
	 * @return the arrLoadDest
	 */
	public float getArrLoadDest() {
		return arrLoadDest;
	}

	/**
	 * @param arrLoadDest the arrLoadDest to set
	 */
	public void setArrLoadDest(float arrLoadDest) {
		this.arrLoadDest = arrLoadDest;
	}

	/**
	 * @return the recTime
	 */
	public float getRecTime() {
		return recTime;
	}

	/**
	 * @param recTime the recTime to set
	 */
	public void setRecTime(float recTime) {
		this.recTime = recTime;
	}

	/**
	 * @return the plugUsed
	 */
	public int getPlugUsed() {
		return plugUsed;
	}

	/**
	 * @param plugUsed the plugUsed to set
	 */
	public void setPlugUsed(int plugUsed) {
		this.plugUsed = plugUsed;
	}

	/**
	 * @return the rechargingTime
	 */
	public float getRechargingTime() {
		return rechargingTime;
	}

	/**
	 * @param rechargingTime the rechargingTime to set
	 */
	public void setRechargingTime(float rechargingTime) {
		this.rechargingTime = rechargingTime;
	}
}