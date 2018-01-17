package com.jasper.controller;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.jasper.helper.JasperHelper;
import com.jasper.model.Sales;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Rahul Moundekar
 */
public class DatabaseDatasourceReport {
	private Connection connection;
	JasperPrint jasperPrint;

	public DatabaseDatasourceReport() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jasperspring", "root", "root");
			// createTable();
			// build();
			List listOfUser = new ArrayList();

			Statement statement = connection.createStatement();
			String str = "SELECT * FROM jasperspring.sales s";
			ResultSet rs = statement.executeQuery(str);
			while (rs.next()) {
				Sales sales = new Sales();
				sales.setItem(rs.getString(1));
				sales.setQuantity(rs.getDouble(2));
				sales.setUnitprice(rs.getDouble(3));
				listOfUser.add(sales);
			}
			try {
				JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listOfUser);
				jasperPrint = JasperFillManager.fillReport("C:\\Users\\ramki\\Desktop\\report.jasper", new HashMap(),
						beanCollectionDataSource);

			} catch (JRException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void build() {
		try {
			report().setTemplate(JasperHelper.reportTemplate)
					.columns(col.column("Item", "item", type.stringType()),
							col.column("Quantity", "quantity", type.integerType()),
							col.column("Unit price", "unitprice", type.bigDecimalType()))
					.title(JasperHelper.createTitleComponent("DatabaseDatasource"))
					.pageFooter(JasperHelper.footerComponent).setDataSource("SELECT * FROM sales", connection).show();
		} catch (DRException e) {
			e.printStackTrace();
		}
	}

	private void createTable() throws SQLException {
		Statement st = connection.createStatement();
		st.execute("CREATE TABLE sales (item VARCHAR(50), quantity INTEGER, unitprice DECIMAL)");
		st.execute("INSERT INTO sales VALUES ('Book1', 5, 100)");
		st.execute("INSERT INTO sales VALUES ('Book2', 5, 100)");
		st.execute("INSERT INTO sales VALUES ('Book3', 5, 100)");
		st.execute("INSERT INTO sales VALUES ('Book4', 5, 100)");
		st.execute("INSERT INTO sales VALUES ('Book5', 5, 100)");
		st.execute("INSERT INTO sales VALUES ('Book6', 5, 100)");
	}

	public static void main(String[] args) throws SQLException {
		new DatabaseDatasourceReport();
	}
}