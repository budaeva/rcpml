package org.rcpml.core.css;

/**
 * This interface defines constants for CSS. Important: Constants must not
 * contain uppercase characters.
 */
public interface RCPCSSConstants {
	//
	// The CSS mime-type string.
	//
	String CSS_MIME_TYPE = "text/css";

	//
	// The CSS property names.
	//	
	String CSS_LAYOUT_PROPERTY = "rcpml-layout";

	String CSS_LAYOUT_COLUMNS_PROPERTY = "rcpml-layout-columns";

	String CSS_LAYOUT_ALIGN_PROPERTY = "rcpml-align";

	String CSS_LAYOUT_ALIGN_VERTICAL_PROPERTY = "rcpml-align-vertical";

	String CSS_LAYOUT_FILL_PROPERTY = "rcpml-fill";

	String CSS_LAYOUT_GRAB_PROPERTY = "rcpml-grab";

	String CSS_LAYOUT_COLSPAN_PROPERTY = "rcpml-colspan";

	String CSS_LAYOUT_ROWSPAN_PROPERTY = "rcpml-rowspan";

	String CSS_LAYOUT_WRAP_PROPERTY = "rcpml-wrap";

	String CSS_LAYOUT_BORDER_PROPERTY = "rcpml-layout-border";

	String CSS_LAYOUT_WIDTH_PROPERTY = "rcpml-layout-width";

	String CSS_LAYOUT_HEIGHT_PROPERTY = "rcpml-layout-height";

	String CSS_LAYOUT_MARGIN_WIDTH_PROPERTY = "rcpml-layout-margin-width";

	String CSS_LAYOUT_MARGIN_HEIGHT_PROPERTY = "rcpml-layout-margin-height";

	String CSS_LAYOUT_MARGIN_LEFT_PROPERTY = "rcpml-layout-margin-left";

	String CSS_LAYOUT_MARGIN_RIGHT_PROPERTY = "rcpml-layout-margin-right";

	String CSS_LAYOUT_MARGIN_TOP_PROPERTY = "rcpml-layout-margin-top";

	String CSS_LAYOUT_MARGIN_BUTTOM_PROPERTY = "rcpml-layout-margin-buttom";

	String CSS_LAYOUT_SPACING_HORIZONTAL_PROPERTY = "rcpml-layout-spacing-horizontal";

	String CSS_LAYOUT_SPACING_VERTICAL_PROPERTY = "rcpml-layout-spacing-vertical";
	
	// The CSS property values

	String NONE_VALUE = "none";

	String LAYOUT_GRID_VALUE = "grid";

	String LAYOUT_ROW_VALUE = "row";

	String LAYOUT_FILL_VALUE = "fill";

	String LAYOUT_WRAP_VALUE = "wrap";

	String LAYOUT_COLUMN_VALUE = "column";

	String LAYOUT_ALIGN_LEFT_VALUE = "left";

	String LAYOUT_ALIGN_CENTER_VALUE = "center";

	String LAYOUT_ALIGN_RIGHT_VALUE = "right";

	String LAYOUT_ALIGN_FILL_VALUE = "fill";

	String LAYOUT_HORIZONTAL_VALUE = "horizontal";

	String LAYOUT_VERTICAL_VALUE = "vertical";

	String LAYOUT_BOTH_VALUE = "both";

	String LAYOUT_NONE_VALUE = NONE_VALUE;

	String TRUE_VALUE = "true";

	String FALSE_VALUE = "false";

	String LAYOUT_BORDER_SINGLE_VALUE = "single";

	String LAYOUT_BORDER_DOUBLE_VALUE = "double";

	String LAYOUT_BORDER_VALUE = "border";

	String LAYOUT_BORDER_NONE_VALUE = "none";

	int LAYOUT_WH_DEFAULT_VALUE = -1;

	// Value Indexes
	/**
	 * Value indexes.
	 */
	public final static int LAYOUT_INDEX = 0;

	public final static int LAYOUT_COLUMNS_INDEX = 1;

	public final static int LAYOUT_ALIGN_INDEX = 2;

	public final static int LAYOUT_ALIGN_VERTICAL_INDEX = 3;

	public final static int LAYOUT_FILL_INDEX = 4;

	public final static int LAYOUT_GRAB_INDEX = 5;

	public final static int LAYOUT_COLSPAN_INDEX = 6;

	public final static int LAYOUT_ROWSPAN_INDEX = 7;

	public final static int LAYOUT_WRAP_INDEX = 8;

	public final static int LAYOUT_BORDER_INDEX = 9;

	public final static int LAYOUT_WIDTH_INDEX = 10;

	public final static int LAYOUT_HEIGHT_INDEX = 11;

	public final static int LAYOUT_MARGIN_WIDTH = 12;

	public final static int LAYOUT_MARGIN_HEIGHT = 13;

	public final static int LAYOUT_MARGIN_LEFT = 14;

	public final static int LAYOUT_MARGIN_TOP = 15;

	public final static int LAYOUT_MARGIN_RIGHT = 16;

	public final static int LAYOUT_MARGIN_BUTTOM = 17;
	
	public final static int LAYOUT_SPACING_HORIZONTAL = 18;
	
	public final static int LAYOUT_SPACING_VERTICAL = 19;
}
