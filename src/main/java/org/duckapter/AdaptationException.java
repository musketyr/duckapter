package org.duckapter;

/**
 * {@link AdaptationException} is thrown when the adaptation to the specified
 * duck interface failed. This usually happens in
 * {@link Duck#type(Class, Class)} or {@link Duck#type(Object, Class)} method.
 * 
 * @author Vladimir Orany
 * 
 */
public final class AdaptationException extends RuntimeException {

	/**
	 * Generated UID.
	 */
	private static final long serialVersionUID = -1880673867743378698L;

	/**
	 * Adapted which caused this exception to be thrown.
	 */
	private final AdaptedClass<?, ?> adapted;

	/**
	 * Creates new instance of this exception with specified adapted as its
	 * cause.
	 * 
	 * @param theAdapted
	 *            adapted which caused this exception to be thrown
	 */
	public AdaptationException(final AdaptedClass<?, ?> theAdapted) {
		this.adapted = theAdapted;
	}

	/**
	 * @return adapted which causes this exception to be thrown
	 */
	public AdaptedClass<?, ?> getAdapted() {
		return adapted;
	}

	@Override
	public String getMessage() {
		return "Adaptation fail! (Original class: "
				+ adapted.getOriginalClass()
				+ ", duck interface: "
				+ adapted.getDuckInterface()
				+ ")Unimplemented class methods: "
				+ adapted.getUnimplementedForClass()
				+ ", Unimplemented methods for instance: "
				+ adapted.getUnimplementedForInstance();
	}

}
