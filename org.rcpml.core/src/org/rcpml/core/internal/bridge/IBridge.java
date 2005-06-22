package org.rcpml.core.internal.bridge;

/**
 * A tagging interface that all bridges must implement. A bridge is
 * responsible on creating and maintaining an appropriate object
 * according to an Element.
 */
public interface IBridge {

    /**
     * Returns the namespace URI of the element this <tt>Bridge</tt> is
     * dedicated to.
     */
    String getNamespaceURI();

    /**
     * Returns the local name of the element this <tt>Bridge</tt> is dedicated
     * to.
     */
    String getLocalName();

    /**
     * Returns a new instance of this bridge.
     */
    IBridge getInstance();
}
