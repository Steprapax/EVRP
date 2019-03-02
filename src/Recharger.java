import java.io.Serializable;

public class Recharger implements Serializable{
	public int id;
	public int numRec;
	public boolean busy;
	
	public Recharger(int id, int numRec) {
		this.id= id;
		this.numRec= numRec;
		this.busy= false;
	}
	
	public Recharger() {
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
	 * @return the numRec
	 */
	public int getNumRec() {
		return numRec;
	}

	/**
	 * @param numRec the numRec to set
	 */
	public void setNumRec(int numRec) {
		this.numRec = numRec;
	}

	/**
	 * @return the busy
	 */
	public boolean isBusy() {
		return busy;
	}

	/**
	 * @param busy the busy to set
	 */
	public void setBusy(boolean busy) {
		this.busy = busy;
	}
}
