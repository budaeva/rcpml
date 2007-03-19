package org.rcpml.core.internal.dom;

import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;

import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.dom.AbstractAttr;
import org.apache.batik.dom.AbstractAttrNS;
import org.apache.batik.dom.AbstractDOMImplementation;
import org.apache.batik.dom.AbstractElement;
import org.apache.batik.dom.AbstractNode;
import org.apache.batik.dom.AbstractStylableDocument;
import org.apache.batik.dom.GenericAttr;
import org.apache.batik.dom.GenericAttrNS;
import org.apache.batik.dom.GenericCDATASection;
import org.apache.batik.dom.GenericComment;
import org.apache.batik.dom.GenericDocumentFragment;
import org.apache.batik.dom.GenericElement;
import org.apache.batik.dom.GenericEntityReference;
import org.apache.batik.dom.GenericProcessingInstruction;
import org.apache.batik.dom.GenericText;
import org.apache.batik.dom.StyleSheetFactory;
import org.apache.batik.dom.events.EventSupport;
import org.apache.batik.dom.util.DOMUtilities;
import org.apache.batik.i18n.Localizable;
import org.apache.batik.i18n.LocalizableSupport;
import org.apache.batik.util.XMLConstants;
import org.rcpml.core.internal.IRCPMLConstants;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;

public class RCPOMDocument extends AbstractStylableDocument implements
		IRCPMLConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8235129805908200359L;

	/**
	 * The url of the document.
	 */
	protected URL url;

	/**
	 * Is this document immutable?
	 */
	protected transient boolean readonly;
	
	/**
	 * Style Maps hash.
	 * For support of dynamic css updating.
	 */
	
	HashMap fStyleMapHash = new HashMap();

	/**
	 * Creates a new uninitialized document.
	 */
	protected RCPOMDocument() {
	}

	/**
	 * Creates a new document.
	 */
	public RCPOMDocument(DocumentType dt, DOMImplementation impl) {
		super(dt, impl);
	}

	/**
	 * Implements {@link Localizable#setLocale(Locale)}.
	 */
	public void setLocale(Locale l) {
		super.setLocale(l);
		localizableSupport.setLocale(l);
	}

	/**
	 * Implements {@link Localizable#formatMessage(String,Object[])}.
	 */
	public String formatMessage(String key, Object[] args)
			throws MissingResourceException {
		try {
			return super.formatMessage(key, args);
		} catch (MissingResourceException e) {
			return localizableSupport.formatMessage(key, args);
		}
	}

	/**
	 * Returns the URI of the document.
	 */
	public URL getURLObject() {
		return url;
	}

	/**
	 * Sets the URI of the document.
	 */
	public void setURLObject(URL url) {
		this.url = url;
	}

	/**
	 * <b>DOM</b>: Implements {@link Document#createElement(String)}.
	 */
	public Element createElement(String tagName) throws DOMException {
		return new GenericElement(tagName.intern(), this);
	}

	/**
	 * <b>DOM</b>: Implements {@link Document#createDocumentFragment()}.
	 */
	public DocumentFragment createDocumentFragment() {
		return new GenericDocumentFragment(this);
	}

	/**
	 * <b>DOM</b>: Implements {@link Document#createTextNode(String)}.
	 */
	public Text createTextNode(String data) {
		return new GenericText(data, this);
	}

	/**
	 * <b>DOM</b>: Implements {@link Document#createComment(String)}.
	 */
	public Comment createComment(String data) {
		return new GenericComment(data, this);
	}

	/**
	 * <b>DOM</b>: Implements {@link Document#createCDATASection(String)}
	 */
	public CDATASection createCDATASection(String data) throws DOMException {
		return new GenericCDATASection(data, this);
	}

	/**
	 * <b>DOM</b>: Implements {@link
	 * Document#createProcessingInstruction(String,String)}.
	 * 
	 * @return a SVGStyleSheetProcessingInstruction if target is
	 *         "xml-stylesheet" or a GenericProcessingInstruction otherwise.
	 */
	public ProcessingInstruction createProcessingInstruction(String target,
			String data) throws DOMException {
		if ("xml-stylesheet".equals(target)) {
			return new RCPStyleSheetProcessingInstruction(data, this,
					(StyleSheetFactory) getImplementation());
		}
		return new GenericProcessingInstruction(target, data, this);
	}

	/**
	 * <b>DOM</b>: Implements {@link Document#createAttribute(String)}.
	 */
	public Attr createAttribute(String name) throws DOMException {
		return new GenericAttr(name.intern(), this);
	}

	/**
	 * <b>DOM</b>: Implements {@link Document#createEntityReference(String)}.
	 */
	public EntityReference createEntityReference(String name)
			throws DOMException {
		return new GenericEntityReference(name, this);
	}

	/**
	 * <b>DOM</b>: Implements {@link Document#createAttributeNS(String,String)}.
	 */
	public Attr createAttributeNS(String namespaceURI, String qualifiedName)
			throws DOMException {
		if (namespaceURI == null) {
			return new GenericAttr(qualifiedName.intern(), this);
		} else {
			return new GenericAttrNS(namespaceURI.intern(), qualifiedName
					.intern(), this);
		}
	}

	/**
	 * <b>DOM</b>: Implements {@link Document#createElementNS(String,String)}.
	 */
	public Element createElementNS(String namespaceURI, String qualifiedName)
			throws DOMException {
		RCPDOMImplementation impl = (RCPDOMImplementation) implementation;
		return impl.createElementNS(this, namespaceURI, qualifiedName);
	}

	/**
	 * Returns true if the given Attr node represents an 'id' for this document.
	 */
	public boolean isId(Attr node) {
		if (node.getNamespaceURI() != null)
			return false;
		return RCPML_ID_ATTRIBUTE.equals(node.getNodeName());
	}

	// AbstractDocument ///////////////////////////////////////////////

	/**
	 * Tests whether this node is readonly.
	 */
	public boolean isReadonly() {
		return readonly;
	}

	/**
	 * Sets this node readonly attribute.
	 */
	public void setReadonly(boolean v) {
		readonly = v;
	}

	/**
	 * Returns a new uninitialized instance of this object's class.
	 */
	protected Node newNode() {
		return new RCPOMDocument();
	}

	/**
	 * Copy the fields of the current node into the given node.
	 * 
	 * @param n
	 *            a node of the type of this.
	 */
	protected Node copyInto(Node n) {
		super.copyInto(n);
		RCPOMDocument sd = (RCPOMDocument) n;
		sd.localizableSupport = new LocalizableSupport(RESOURCES, getClass()
				.getClassLoader());
		sd.url = url;
		return n;
	}

	/**
	 * Deeply copy the fields of the current node into the given node.
	 * 
	 * @param n
	 *            a node of the type of this.
	 */
	protected Node deepCopyInto(Node n) {
		super.deepCopyInto(n);
		RCPOMDocument sd = (RCPOMDocument) n;
		sd.localizableSupport = new LocalizableSupport(RESOURCES, getClass()
				.getClassLoader());
		sd.url = url;
		return n;
	}	
	
	public StyleMap getStyleMapForElement( Element element ) {
		if( this.fStyleMapHash.containsKey(element)) {
			return (StyleMap)this.fStyleMapHash.get(element);
		}
		return null;
	}
	public void putStyleMapForElement( Element element, StyleMap styleMap ) {
		this.fStyleMapHash.put(element, styleMap);
	}
	public void clearStylesMap() {
		this.fStyleMapHash.clear();
	}
	
}
