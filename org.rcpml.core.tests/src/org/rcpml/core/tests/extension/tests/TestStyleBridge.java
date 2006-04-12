package org.rcpml.core.tests.extension.tests;

import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.CSSStylableElement;
import org.apache.batik.css.engine.StyleMap;
import org.apache.batik.css.engine.value.Value;
import org.rcpml.core.IController;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.AbstractBridge;
import org.rcpml.core.css.RCPCSSConstants;
import org.rcpml.core.dom.RCPStylableElement;
import org.w3c.dom.Node;

public class TestStyleBridge extends AbstractBridge {

	protected TestStyleBridge(Node node, IController controller) {
		super(node, controller, true);
		if( !( node instanceof CSSStylableElement ) ) {
			throw new RCPMLException("Element not stylable");
		}
		if( !( node instanceof RCPStylableElement ) ) {
			throw new RCPMLException("Element not stylable");
		}
		//CSSStylableElement stylable = (CSSStylableElement)node;
		RCPStylableElement stylable = (RCPStylableElement)node;
		CSSEngine engine = stylable.getCSSEngine();
		//engine.getComputedStyle((CSSStylableElement)node, )
		StyleMap styleMap = engine.getCascadedStyleMap((CSSStylableElement)node, null );
		System.out.println("@");
		System.out.println(styleMap.toString(engine));		
		System.out.println("Cool");
		
		Value value = engine.getComputedStyle((CSSStylableElement)node, null, RCPCSSConstants.LAYOUT_INDEX );		
		System.out.println("layout value:" + value.getStringValue() );
		
		value = engine.getComputedStyle((CSSStylableElement)node, null, RCPCSSConstants.LAYOUT_ALIGN_INDEX );		
		System.out.println("layout align value:" + value.getStringValue() );
		
		value = engine.getComputedStyle((CSSStylableElement)node, null, RCPCSSConstants.LAYOUT_COLUMNS_INDEX );		
		System.out.println("layout columns value:" + value.getFloatValue() );
		
		value = engine.getComputedStyle((CSSStylableElement)node, null, RCPCSSConstants.LAYOUT_FILL_INDEX );		
		System.out.println("layout fill value:" + value.getStringValue() );
		
		value = engine.getComputedStyle((CSSStylableElement)node, null, RCPCSSConstants.LAYOUT_GRAB_INDEX );		
		System.out.println("layout grab value:" + value.getStringValue() );
		
		value = engine.getComputedStyle((CSSStylableElement)node, null, RCPCSSConstants.LAYOUT_COLSPAN_INDEX );		
		System.out.println("layout colspan value:" + (int)value.getFloatValue() );
	}

	public Object getPresentation() {		
		return null;
	}	
}
