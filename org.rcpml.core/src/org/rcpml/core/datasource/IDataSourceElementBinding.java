package org.rcpml.core.datasource;

public interface IDataSourceElementBinding extends IDataSourceElementListener {
	
	/**
	 * The value type of this observable value, or <code>null</code> if this
	 * observable value is untyped.
	 * 
	 * @return the value type, or <code>null</null>
	 */
	public Object getValueType();

	/**
	 * @return the current value
	 * @TrackedGetter
	 */
	public Object getValue();

	/**
	 * @param value
	 *            the value to set
	 * @throws UnsupportedOperationException
	 *             if this observable value cannot be set.
	 */
	public void setValue(Object value);

	/**
	 * 
	 * @param listener
	 */
	public void addValueChangeListener(IDataSourceElementListener listener);

	/**
	 * @param listener
	 */
	public void removeValueChangeListener(IDataSourceElementListener listener);
}
