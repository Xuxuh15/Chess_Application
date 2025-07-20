package interfaces;

import helpers.Pair;

public interface Publisher {
	
	/**
	 * Adds a new subscriber.
	 * @param subscriber subscriber to be added.
	 */
	public void subscribe(EventListener subscriber); 
	/**
	 * Unsubscribes the target. 
	 * @param targ the subscriber to remove.
	 */
	public void unsubscribe(EventListener targ); 
	
	/**
	 * Notifies subscribers of state change.
	 * @param initPos the initial square. 
	 * @param destination the destination square.
	 */
	public void notify(Pair initPos, Pair destination); 
	
	
	
	
	

}
