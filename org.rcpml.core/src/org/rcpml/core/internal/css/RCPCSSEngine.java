package org.rcpml.core.internal.css;

import java.net.URL;

import org.apache.batik.css.engine.CSSContext;
import org.apache.batik.css.engine.CSSEngine;
import org.apache.batik.css.engine.value.ShorthandManager;
import org.apache.batik.css.engine.value.ValueManager;
import org.apache.batik.css.parser.ExtendedParser;
import org.apache.batik.util.ParsedURL;
import org.rcpml.core.css.RCPCSSConstants;
import org.rcpml.core.internal.css.managers.BorderValueManager;
import org.rcpml.core.internal.css.managers.LayoutAlignValueManager;
import org.rcpml.core.internal.css.managers.LayoutDirectionValueManager;
import org.rcpml.core.internal.css.managers.LayoutValueManager;
import org.rcpml.core.internal.css.managers.NumberValueManager;
import org.rcpml.core.internal.css.managers.TrueFalseValueManager;
import org.w3c.dom.Document;

public class RCPCSSEngine extends CSSEngine {

	private final static ValueManager[] RCP_VALUE_MANAGERS = {
			new LayoutValueManager(),
			// this is layout columns manager
			new NumberValueManager(RCPCSSConstants.CSS_LAYOUT_COLUMNS_PROPERTY,
					1, false),
			new TrueFalseValueManager(RCPCSSConstants.CSS_LAYOUT_EQUALS_WIDTH_PROPERTY),
			new LayoutAlignValueManager(
					RCPCSSConstants.CSS_LAYOUT_ALIGN_PROPERTY,
					LayoutAlignValueManager.LEFT_VALUE),
			new LayoutAlignValueManager(
					RCPCSSConstants.CSS_LAYOUT_ALIGN_VERTICAL_PROPERTY,
					LayoutAlignValueManager.CENTER_VALUE),
			new LayoutDirectionValueManager(
					RCPCSSConstants.CSS_LAYOUT_FILL_PROPERTY),
			new LayoutDirectionValueManager(
					RCPCSSConstants.CSS_LAYOUT_GRAB_PROPERTY),

			new NumberValueManager(RCPCSSConstants.CSS_LAYOUT_COLSPAN_PROPERTY,
					1, false),
			new NumberValueManager(RCPCSSConstants.CSS_LAYOUT_ROWSPAN_PROPERTY,
					1, false),
			new TrueFalseValueManager(RCPCSSConstants.CSS_LAYOUT_WRAP_PROPERTY),
			new BorderValueManager(),
			new NumberValueManager(RCPCSSConstants.CSS_LAYOUT_WIDTH_PROPERTY,
					-1, false),
			new NumberValueManager(RCPCSSConstants.CSS_LAYOUT_HEIGHT_PROPERTY,
					-1, false),
			new NumberValueManager(
					RCPCSSConstants.CSS_LAYOUT_MARGIN_WIDTH_PROPERTY, 5, false),
			new NumberValueManager(
					RCPCSSConstants.CSS_LAYOUT_MARGIN_HEIGHT_PROPERTY, 5, false),
			new NumberValueManager(
					RCPCSSConstants.CSS_LAYOUT_MARGIN_LEFT_PROPERTY, 0, false),
			new NumberValueManager(
					RCPCSSConstants.CSS_LAYOUT_MARGIN_TOP_PROPERTY, 0, false),
			new NumberValueManager(
					RCPCSSConstants.CSS_LAYOUT_MARGIN_RIGHT_PROPERTY, 0, false),
			new NumberValueManager(
					RCPCSSConstants.CSS_LAYOUT_MARGIN_BUTTOM_PROPERTY, 0, false),
			new NumberValueManager(
					RCPCSSConstants.CSS_LAYOUT_SPACING_HORIZONTAL_PROPERTY, 5,
					false),
			new NumberValueManager(
					RCPCSSConstants.CSS_LAYOUT_SPACING_VERTICAL_PROPERTY, 5,
					false) };

	private final static ShorthandManager[] RCP_SHORTHAND_MANAGERS = {};

	private RCPErrorHandler fParserErrorHandler = new RCPErrorHandler();	

	/**
	 * Creates a new RCPCSSEngine.
	 * 
	 * @param doc
	 *            The associated document.
	 * @param uri
	 *            The document URI.
	 * @param p
	 *            The CSS parser to use.
	 * @param ctx
	 *            The CSS context.
	 */
	public RCPCSSEngine(Document doc, ParsedURL uri, ExtendedParser p, CSSContext ctx) {
		super(doc, uri, p, RCP_VALUE_MANAGERS, RCP_SHORTHAND_MANAGERS, null,
				null, "style", null, "class", true, null, ctx);
	}

	/**
	 * Creates a new RCPCSSEngine.
	 * 
	 * @param doc
	 *            The associated document.
	 * @param uri
	 *            The document URI.
	 * @param p
	 *            The CSS parser to use.
	 * @param vms
	 *            Extension value managers.
	 * @param sms
	 *            Extension shorthand managers.
	 * @param ctx
	 *            The CSS context.
	 */
	public RCPCSSEngine(Document doc, ParsedURL uri, ExtendedParser p,
			ValueManager[] vms, ShorthandManager[] sms, CSSContext ctx) {
		super(doc, uri, p, mergeArrays(RCP_VALUE_MANAGERS, vms), mergeArrays(
				RCP_SHORTHAND_MANAGERS, sms), null, null, "style", null,
				"class", true, null, ctx);

		p.setErrorHandler(this.fParserErrorHandler);
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
		ShorthandManager[] result = new ShorthandManager[a1.length + a2.length];
		System.arraycopy(a1, 0, result, 0, a1.length);
		System.arraycopy(a2, 0, result, a1.length, a2.length);
		return result;
	}

}
