package org.rcpml.forms.internal;

import org.apache.batik.css.engine.value.Value;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.rcpml.core.RCPMLException;
import org.rcpml.core.bridge.IBridge;
import org.rcpml.core.css.RCPCSSConstants;
import org.rcpml.core.dom.RCPStylableElement;
import org.rcpml.swt.ICompositeHolder;
import org.rcpml.swt.SWTUtils;

public class EclipseFormsUtil {
	public static FormToolkit constructFormToolkit(IBridge parentBridge ) {
		FormToolkit formToolkit = null;
		if (parentBridge != null) {
			if (parentBridge instanceof ITookitHolder) {
				formToolkit = ((ITookitHolder) parentBridge)
						.getFormToolkit();
			}
			Object presentation = parentBridge.getPresentation();
			if (presentation != null) {
				Composite composite = null;
				if (presentation instanceof Composite) {
					composite = (Composite) presentation;
				} else if (presentation instanceof ICompositeHolder) {
					composite = ((ICompositeHolder) presentation)
							.getComposite();
				}
				if (composite != null) {
					if ( formToolkit == null) {
						formToolkit = new FormToolkit(new FormColors(
								composite.getDisplay()));
					}
					if (formToolkit == null) {
						throw new RCPMLException(
								"Failed to create Eclipse form toolkit");
					}
				} else {
					throw new RCPMLException("Composite can't be null");
				}
			} else {
				throw new RCPMLException("Parent can't be null");
			}
		}
		return formToolkit;
	}
	public static Object constructWrapLayout(RCPStylableElement stylable, Object layout) {
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
		
		Object layoutData = null;

		if (layout instanceof TableWrapLayout) {
			TableWrapData twd = new TableWrapData();

			twd.align = SWTUtils.getGridAlignmentH(align, TableWrapData.LEFT,
					TableWrapData.CENTER, TableWrapData.RIGHT);
			twd.valign = SWTUtils.getGridAlignmentV(alignVertical, TableWrapData.TOP,
					TableWrapData.CENTER, TableWrapData.BOTTOM);

			if (fill.equals(RCPCSSConstants.LAYOUT_HORIZONTAL_VALUE)
					|| fill.equals(RCPCSSConstants.LAYOUT_BOTH_VALUE)) {
				twd.align = TableWrapData.FILL;
			}
			if (fill.equals(RCPCSSConstants.LAYOUT_VERTICAL_VALUE)
					|| fill.equals(RCPCSSConstants.LAYOUT_BOTH_VALUE)) {
				twd.valign = TableWrapData.FILL;
			}

			if (grab.equals(RCPCSSConstants.LAYOUT_HORIZONTAL_VALUE)
					|| grab.equals(RCPCSSConstants.LAYOUT_BOTH_VALUE)) {
				twd.grabHorizontal = true;
			}
			if (grab.equals(RCPCSSConstants.LAYOUT_VERTICAL_VALUE)
					|| grab.equals(RCPCSSConstants.LAYOUT_BOTH_VALUE)) {
				twd.grabVertical = true;
			}

			twd.colspan = colspan;
			twd.rowspan = rowspan;
			layoutData = twd;			
		}
		return layoutData;
	}
	public static Layout constructLayout(RCPStylableElement stylable ) {
		Layout layout = null;
		Value layoutValue = stylable.getComputedValue( RCPCSSConstants.LAYOUT_INDEX );
		Value columnsValue = stylable.getComputedValue( RCPCSSConstants.LAYOUT_COLUMNS_INDEX );
		
		String layoutString = layoutValue.getStringValue();
		int columns = (int)columnsValue.getFloatValue();
						
		if( layoutString.equals( RCPCSSConstants.LAYOUT_COLUMN_VALUE )) {
			ColumnLayout cl = new ColumnLayout();
			cl.maxNumColumns = columns;
			layout = cl;
		}
		else if( layoutString.equals( RCPCSSConstants.LAYOUT_WRAP_VALUE )) {
			TableWrapLayout twl = new TableWrapLayout();
			twl.numColumns = columns;				
			layout = twl;
		}
		if( layout == null ) {
			return SWTUtils.constructLayout(stylable);
		}
		return layout;
	}
}
