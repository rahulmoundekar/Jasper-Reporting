package com.dynamicreport.demo.dao;

import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.constant.PageOrientation;
import net.sf.dynamicreports.report.constant.PageType;

import org.slf4j.LoggerFactory;

import com.dynamicreport.template.Templates;

public class ReportStatisticsService {
	private static String DB_DRIVER = null;
	private static String DB_CONNECTION = null;
	private static String DB_USER = null;
	private static String DB_PASSWORD = null;

	public static void getProperties() throws Exception {

		DB_DRIVER = "com.mysql.jdbc.Driver";
		DB_CONNECTION = "jdbc:mysql://localhost:3306/jasperspring";
		DB_USER = "root";
		DB_PASSWORD = "root";
	}

	private static Connection getDBConnection() throws Exception {
		Connection dbConnection = null;
		try {
			getProperties();
			Class.forName(DB_DRIVER);
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
					DB_PASSWORD);
			return dbConnection;
		} catch (ClassNotFoundException e) {
			LoggerFactory.getLogger(ReportStatisticsService.class).error(
					e.getMessage());
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			LoggerFactory.getLogger(ReportStatisticsService.class).error(
					e.getMessage());
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

	public static OutputStream generateReport(String formatType)
			throws Exception {

		Connection dbConnection = null;
		Statement st = null;
		OutputStream reportStream = new ByteArrayOutputStream();
		String query = "SELECT * FROM sales";

		try {
			dbConnection = getDBConnection();
			st = dbConnection.createStatement();
			st.execute("DROP TABLE IF EXISTS sales");
			st.execute("CREATE TABLE sales (item VARCHAR(50), quantity INTEGER, unitprice DECIMAL)");
			st.execute("INSERT INTO sales VALUES ('Book', 5, 100)");
			st.execute("INSERT INTO sales VALUES ('DVD', 2, 200)");
			st.execute("INSERT INTO sales VALUES ('Hard-Disk', 1, 500)");
			st.execute("INSERT INTO sales VALUES ('CPU', 1, 7500)");
			st.execute("INSERT INTO sales VALUES ('Mouse', 5, 1700)");

			JasperReportBuilder reportBuilder = report()
					.setTemplate(Templates.reportTemplate)
					.columns(
							col.column("Item", "item", type.stringType()),
							col.column("Quantity", "quantity",
									type.integerType()),
							col.column("Unit price", "unitprice",
									type.bigDecimalType()))
					.title(Templates.createTitleComponent("My First Report"))
					.pageFooter(Templates.footerComponent)
					.setDataSource(query, dbConnection);

			if (formatType == "PDF") {
				reportBuilder.toPdf(reportStream);
			} else if (formatType == "HTML") {
				FontBuilder boldFont = stl.font().setFontSize(16);
				reportBuilder
						.ignorePagination()
						.setDefaultFont(boldFont)
						.setPageFormat(PageType.LEDGER,
								PageOrientation.LANDSCAPE).toHtml(reportStream);
			} else if (formatType == "EXCEL") {
				reportBuilder.ignorePagination().ignorePageWidth()
						.toXlsx(reportStream);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (st != null) {
				st.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return reportStream;
	}
}
