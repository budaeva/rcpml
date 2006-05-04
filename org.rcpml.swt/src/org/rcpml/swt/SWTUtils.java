package org.rcpml.swt;

import org.apache.batik.css.engine.value.Value;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.rcpml.core.css.RCPCSSConstants;
import org.rcpml.core.dom.RCPStylableElement;

public class SWTUtils {
	public static Layout constructLayout(RCPStylableElement stylable) {
		Layout layout = null;
		Value layoutValue = stylable
				.getComputedValue(RCPCSSConstants.LAYOUT_INDEX);
		Value columnsValue = stylable
				.getComputedValue(RCPCSSConstants.LAYOUT_COLUMNS_INDEX);

		String layoutString = layoutValue.getStringValue();
		int columns = (int) columnsValue.getFloatValue();

		if (layoutString.equals(RCPCSSConstants.LAYOUT_GRID_VALUE)) {
			GridLayout lo = new GridLayout();
			lo.numColumns = columns;
			layout = lo;
		}
		return layout;
	}
	public static int getGridAlignmentH(String align, int left, int center,
			int right) {

		return getAlignValue(align, left, left, center, right);
	}

	public static int getGridAlignmentV(String align, int left, int center,
			int right) {
		return getAlignValue(align, center, left, center, right);
	}

	public static int getAlignValue(String align, int alignValue, int left,
			int center, int right) {
		if (align.equals(RCPCSSConstants.LAYOUT_ALIGN_LEFT_VALUE)) {
			alignValue = left;
		}
		if (align.equals(RCPCSSConstants.LAYOUT_ALIGN_CENTER_VALUE)) {
			alignValue = center;
		}
		if (align.equals(RCPCSSConstants.LAYOUT_ALIGN_RIGHT_VALUE)) {
			alignValue = right;
		}
		return alignValue;
	}

	public static Object constructLayoutData(RCPStylableElement stylable,
			Composite parent) {
		Value alignValue = stylable
				.getComputedValue(RCPCSSConstants.LAYOUT_ALIGN_INDEX);
		Value alignVerticalValue = stylable
				.getComputedValue(RCPCSSConstants.LAYOUT_ALIGN_VERTICAL_INDEX);
		Value fillValue = stylable
				.getComputedValue(RCPCSSConstants.LAYOUT_FILL_INDEX);
		Value grabValue = stylable
				.getComputedValue(RCPCSSConstants.LAYOUT_GRAB_INDEX);

		Value colspanValue = stylable
				.getComputedValue(RCPCSSConstants.LAYOUT_COLSPAN_INDEX);
		Value rowspanValue = stylable
				.getComputedValue(RCPCSSConstants.LAYOUT_ROWSPAN_INDEX);

		String align = alignValue.getStringValue();
		String alignVertical = alignVerticalValue.getStringValue();
		String fill = fillValue.getStringValue();
		String grab = grabValue.getStringValue();

		int colspan = (int) colspanValue.getFloatValue();
		int rowspan = (int) rowspanValue.getFloatValue();

		if (parent.getLayout() instanceof GridLayout) {
			GridData gd = new GridData();

			gd.horizontalAlignment = getGridAlignmentH(align,
					GridData.BEGINNING, GridData.CENTER, GridData.END);
			gd.verticalAlignment = getGridAlignmentV(alignVertical,
					GridData.BEGINNING, GridData.CENTER, GridData.END);

			if (fill.equals(RCPCSSConstants.LAYOUT_HORIZONTAL_VALUE)
					|| fill.equals(RCPCSSConstants.LAYOUT_BOTH_VALUE)) {
				gd.horizontalAlignment = SWT.FILL;
			}
			if (fill.equals(RCPCSSConstants.LAYOUT_VERTICAL_VALUE)
					|| fill.equals(RCPCSSConstants.LAYOUT_BOTH_VALUE)) {
				gd.verticalAlignment = SWT.FILL;
			}

			if (grab.equals(RCPCSSConstants.LAYOUT_HORIZONTAL_VALUE)
					|| grab.equals(RCPCSSConstants.LAYOUT_BOTH_VALUE)) {
				gd.grabExcessHorizontalSpace = true;
			}
			if (grab.equals(RCPCSSConstants.LAYOUT_VERTICAL_VALUE)
					|| grab.equals(RCPCSSConstants.LAYOUT_BOTH_VALUE)) {
				gd.grabExcessVerticalSpace = true;
			}
			gd.horizontalSpan = colspan;
			gd.verticalSpan = rowspan;
			return gd;
		}
		return null;
	}
}