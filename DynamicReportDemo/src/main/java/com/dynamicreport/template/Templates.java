package com.dynamicreport.template;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.tableOfContentsCustomizer;
import static net.sf.dynamicreports.report.builder.DynamicReports.template;

import java.awt.Color;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.dynamicreports.report.base.expression.AbstractValueFormatter;
import net.sf.dynamicreports.report.builder.ReportTemplateBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.datatype.BigDecimalType;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.tableofcontents.TableOfContentsCustomizerBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.constant.WhenNoDataType;
import net.sf.dynamicreports.report.definition.ReportParameters;



public class Templates {
	
	public static final StyleBuilder rootStyle;
	public static final StyleBuilder boldStyle;
	public static final StyleBuilder italicStyle;
	public static final StyleBuilder boldCenteredStyle;
	public static final StyleBuilder bold12CenteredStyle;
	public static final StyleBuilder bold18CenteredStyle;
	public static final StyleBuilder bold22CenteredStyle;
	public static final StyleBuilder columnStyle;
	public static final StyleBuilder columnTitleStyle;
	public static final StyleBuilder groupStyle;
	public static final StyleBuilder subtotalStyle;
	public static final StyleBuilder gridColumnStyle;

	public static final ReportTemplateBuilder reportTemplate;
	
	public static final CurrencyType currencyType;
	public static final ComponentBuilder<?, ?> dynamicReportsComponent;
	public static final ComponentBuilder<?, ?> footerComponent;

	static {
		rootStyle           = stl.style().setPadding(1);
		boldStyle           = stl.style(rootStyle).bold();
		italicStyle         = stl.style(rootStyle).italic();
		boldCenteredStyle   = stl.style(boldStyle).setAlignment(HorizontalAlignment.CENTER, VerticalAlignment.TOP);
		bold12CenteredStyle = stl.style(boldCenteredStyle).setFontSize(12);
		bold18CenteredStyle = stl.style(boldCenteredStyle).setFontSize(18);
		bold22CenteredStyle = stl.style(boldCenteredStyle).setFontSize(22);
		
		columnStyle         = stl.style(rootStyle).setVerticalAlignment(VerticalAlignment.MIDDLE).setBorder(stl.pen1Point());
		columnTitleStyle    = stl.style(columnStyle)
		                         .setHorizontalAlignment(HorizontalAlignment.CENTER)
		                         .setBackgroundColor(Color.LIGHT_GRAY)
		                         .bold();
		groupStyle          = stl.style(boldStyle).setHorizontalAlignment(HorizontalAlignment.LEFT);
		subtotalStyle       = stl.style(boldStyle).setTopBorder(stl.pen1Point());
		
		gridColumnStyle		= stl.style(Templates.columnStyle).setBorder(stl.pen1Point());

		StyleBuilder crosstabGroupStyle      = stl.style(columnTitleStyle);
		StyleBuilder crosstabGroupTotalStyle = stl.style(columnTitleStyle).setBackgroundColor(new Color(170, 170, 170));
		StyleBuilder crosstabGrandTotalStyle = stl.style(columnTitleStyle).setBackgroundColor(new Color(140, 140, 140));
		StyleBuilder crosstabCellStyle       = stl.style(columnStyle).setBorder(stl.pen1Point()).setBackgroundColor(Color.WHITE).setPadding(2);
		

		TableOfContentsCustomizerBuilder tableOfContentsCustomizer = tableOfContentsCustomizer()
				.setHeadingStyle(0, stl.style(rootStyle).bold());
		
		reportTemplate = template()
		                   .setLocale(Locale.ENGLISH)
		                   .setColumnStyle(columnStyle)
		                   .setColumnTitleStyle(columnTitleStyle)
		                   .setGroupStyle(groupStyle)
		                   .setGroupTitleStyle(groupStyle)
		                   .setSubtotalStyle(subtotalStyle)
		                   .highlightDetailEvenRows()
		                   .crosstabHighlightEvenRows()
		                   .setCrosstabGroupStyle(crosstabGroupStyle)
		                   .setCrosstabGroupTotalStyle(crosstabGroupTotalStyle)
		                   .setCrosstabGrandTotalStyle(crosstabGrandTotalStyle)
		                   .setCrosstabCellStyle(crosstabCellStyle)
		                   .setTableOfContentsCustomizer(tableOfContentsCustomizer)
		                   .setWhenNoDataType(WhenNoDataType.ALL_SECTIONS_NO_DETAIL);

		currencyType = new CurrencyType();

		dynamicReportsComponent =
				  cmp.horizontalList(					
				  	cmp.verticalList(
				  		cmp.text("Sample Report").setStyle(bold12CenteredStyle).setHorizontalAlignment(HorizontalAlignment.CENTER)
				  		));

				footerComponent =cmp.horizontalList(cmp.pageXofY()).setStyle(stl.style().setTopBorder(stl.pen1Point()));
	}

	
	/**
	 * Creates custom component which is possible to add to any report band component
	 */
	public static ComponentBuilder<?, ?> createTitleComponent(String label) {
		Date start = new Date();
		String DateToStr = DateFormat.getDateTimeInstance().format(start);
		
		return cmp.horizontalList()
		        .add(dynamicReportsComponent)
		        .newRow(0)
		        .add(cmp.text(label).setStyle(bold12CenteredStyle))
		        .newRow(0)
		        .add(cmp.text("as on"+" "+DateToStr).setStyle(boldCenteredStyle))		        
		        .newRow(0)
		        .add(cmp.line())
		        .newRow(0)
		        .add(cmp.verticalGap(10));
	}

	public static CurrencyValueFormatter createCurrencyValueFormatter(String label) {
		return new CurrencyValueFormatter(label);
	}

	public static class CurrencyType extends BigDecimalType {
		private static final long serialVersionUID = 1L;

		@Override
		public String getPattern() {
			return "$ #,###.00";
		}
	}

	private static class CurrencyValueFormatter extends AbstractValueFormatter<String, Number> {
		private static final long serialVersionUID = 1L;

		private String label;

		public CurrencyValueFormatter(String label) {
			this.label = label;
		}

		
		public String format(Number value, ReportParameters reportParameters) {
			return label + currencyType.valueToString(value, reportParameters.getLocale());
		}
	}
}