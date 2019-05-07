/**
 * A class of Voorbeeld involving a value, a ... (alle belangrijke variables hier)
 * 
 * @invar  The value of each Voorbeeld must be a valid value for any Voorbeeld.
 *       | isValidValue(getValue)
 *           
 * @version 1.0
 * @author Brecht Evens
 */
public class Voorbeeld {
	
	/**
	 * Initialize this new Voorbeeld with given value.
	 *
	 * @param  value
	 *         The value for this new Voorbeeld.
	 * @effect The value of this Voorbeeld is set to value.
	 * 		 | this.setValue(value)
	 */
	Voorbeeld(double value) throws IllegalArgumentException {
		this.setValue(value);
	}
	
	/**
	 * Check whether the given value is a valid value for any Voorbeeld.
	 *  
	 * @param  value
	 *         The given value to check.
	 * @return 
	 *       | result == (value > 0);
	*/
	public static boolean isValidValue(double value) { //indien je voor zoiets een niet static methode gebruikt, moet het canHaveAsValue(value) heten
		return value > 0;
	}
	
	/**
	 * Set the value of this Voorbeeld to the given value.
	 * 
	 * @pre    The input has to be larger than zero. //NOMINAAL
	 * 		 | input > 0
	 * @throws IllegalArgumentException //DEFENSIEF
	 * 		   The input is not larger than zero.
	 *		 | input <= 0
	 * @post   The value of this Voorbeeld is equal to value.
	 * 		 | new.value == value
	 */
	@Basic
	public void setValue(double value) throws IllegalArgumentException {
		assert(isValidValue(value));
		if (!isValidValue(value)) {
			throw new IllegalArgumentException();
		}
		this.value = value;
	}
	
	/**
	 *@return Return the value of this Voorbeeld.
	 *		| result == this.value
	 */
	@Basic
	public double getValue() {
		return this.value;
	}
	
	/**
	 * Variable registering the value of this Voorbeeld.
	 */
	private double value;
}
