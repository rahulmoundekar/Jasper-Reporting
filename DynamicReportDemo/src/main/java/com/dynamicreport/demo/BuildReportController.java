package com.dynamicreport.demo;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dynamicreport.demo.dao.ReportStatisticsService;

@Controller
@RequestMapping("build")
public class BuildReportController implements ApplicationContextAware {

	@RequestMapping(value = "buildHtmlReport", method = RequestMethod.POST)
	public void buildReportPost(HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) ReportStatisticsService
					.generateReport("HTML");
			response.setContentLength(byteArrayOutputStream.size());
			response.getOutputStream().write(byteArrayOutputStream.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "buildPdfReport", method = RequestMethod.POST)
	public void buildPdfReportPost(HttpServletResponse response) {
		try {
			ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) ReportStatisticsService
					.generateReport("PDF");
			response.setContentType("application/pdf");

			String reportFileName = "MyFirstReport";
			response.setHeader("Content-Disposition", "attachment;filename=" + reportFileName + ".pdf");
			response.setContentLength(byteArrayOutputStream.size());
			response.getOutputStream().write(byteArrayOutputStream.toByteArray());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "buildExcelReport", method = RequestMethod.POST)
	public void buildExcelReporPostt(HttpServletResponse response) {

		try {
			ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) ReportStatisticsService
					.generateReport("EXCEL");
			response.setContentType("application/excel");

			String reportFileName = "MyFirstReport";
			response.setHeader("Content-Disposition", "attachment;filename=" + reportFileName + ".xlsx");
			response.setContentLength(byteArrayOutputStream.size());
			response.getOutputStream().write(byteArrayOutputStream.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

	}
}
