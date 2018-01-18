# Jasper-Reporting

JasperReports is an open source java reporting engine. JasperReports is a Java class library, and it is meant for those Java developers who need to add reporting capabilities to their applications. This simple and user-friendly tutorial covers almost all the basics of JasperReports that a beginner should know.

JasperReports provides the necessary features to generate dynamic reports, including data retrieval using JDBC (Java Database Connectivity), as well as support for parameters, expressions, variables, and groups. JasperReports also includes advanced features, such as custom data sources, scriptlets, and subreports. All in all, JasperReports combines good features, maturity, community participation, and, best of all, it's free.

# Dynamic Jasper-Reporting

DynamicJasper (DJ) is an API that hides the complexity of JasperReports, it helps developers to save time when designing simple/medium complexity reports generating the layout of the report elements automatically. It creates reports dynamically, defining at run-time the columns, column width (auto width), groups, variables, fonts, charts, cross-tabs, sub reports (that can also be dynamic), page size and everything else that you can define at design time.

DJ keeps full compatibility with Jasper Reports since it’s a tool that helps create reports programmatically in a easy way (it only interferes with the creation of the report design doing the layout of the elements).

You can use the classic .jrxml files as templates while the content and layout of the report elements are handled by the DJ API.

# Features
Most of the features are provided directly by Jasper Reports (great tool guys!), nevertheless through the DJ API some of the results are achieved with really no effort.

	100% pure Java
	No need to use other tool than you favorite IDE.
	Friendly and intuitive API.
	Mature, robust and stable.

Dynamic column report: Columns can be defined at runtime, which means you also control (at runtime) the column positioning, width, title, etc.

Repeating groups / Breaking groups: Create repeating groups dynamically using simple expressions as criteria or complex custom expressions. Each repeating group may have a header and/or footer, which can have a variable showing the result of an operation (SUM, COUNT or any other provided by Jasper Reports).

Automatic report layout: Just define a minimum set of options and DJ will take care of the layout. It’s not an issue to generate the same report for different page sizes and orientation many more!

Dynamic Crosstabs: Jasper Report’s popular crosstabs can now be created dynamically in an easy and convenient way.
