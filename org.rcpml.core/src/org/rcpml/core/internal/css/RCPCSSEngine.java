package org.rcpml.core.internal.css;

import java.net.URL;

import org.apache.batik.css.engine.CSSContext;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.value.ShorthandManager;
import org.apache.batik.css.engine.value.ValueManager;
import org.apache.batik.css.parser.ExtendedParser;
import org.w3c.dom.Document;

public class RCPCSSEngine extends CSSEngine {

	private final static ValueManager[] RCP_VALUE_MANAGERS = {};
	private final static ShorthandManager[] RCP_SHORTHAND_MANAGERS = {};
	
    /**
     * Creates a new RCPCSSEngine.
     * @param doc The associated document.
     * @param uri The document URI.
     * @param p The CSS parser to use.
     * @param ctx The CSS context.
     */
    public RCPCSSEngine(Document doc,
                        URL uri,
                        ExtendedParser p,
                        CSSContext ctx) {
        super(doc, uri, p,
              RCP_VALUE_MANAGERS,
              RCP_SHORTHAND_MANAGERS,
              null,
              null,
              "style",
              null,
              "class",
              true,
              null,
              ctx);
    }
    
    /**
     * Creates a new RCPCSSEngine.
     * @param doc The associated document.
     * @param uri The document URI.
     * @param p The CSS parser to use.
     * @param vms Extension value managers.
     * @param sms Extension shorthand managers.
     * @param ctx The CSS context.
     */
    public RCPCSSEngine(Document doc,
                        URL uri,
                        ExtendedParser p,
                        ValueManager[] vms,
                        ShorthandManager[] sms,
                        CSSContext ctx) {
        super(doc, uri, p,
              mergeArrays(RCP_VALUE_MANAGERS, vms),
              mergeArrays(RCP_SHORTHAND_MANAGERS, sms),
              null,
              null,
              "style",
              null,
              "class",
              true,
              null,
              ctx);
    }

    /**
     * Merges the given arrays.
     */
    protected static ValueManager[] mergeArrays(ValueManager[] a1,
                                              ValueManager[] a2) {
        ValueManager[] result = new ValueManager[a1.length + a2.length];
        System.arraycopy(a1, 0, result, 0, a1.length);
        System.arraycopy(a2, 0, result, a1.length, a2.length);
        return result;
    }

    /**
     * Merges the given arrays.
     */
    protected static ShorthandManager[] mergeArrays(ShorthandManager[] a1,
                                                  ShorthandManager[] a2) {
        ShorthandManager[] result =
            new ShorthandManager[a1.length + a2.length];
        System.arraycopy(a1, 0, result, 0, a1.length);
        System.arraycopy(a2, 0, result, a1.length, a2.length);
        return result;
    }
    
}
