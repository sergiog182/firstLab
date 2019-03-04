package com.router;

import org.apache.camel.builder.RouteBuilder;

public class MainRouteBuilder extends RouteBuilder{
	final String XPATH_ORDERID = "/order/id/text()";
	final String XPATH_VENDOR_NAME = "/order/@provider";
	final String XPATH_TEST = "/order/@test";
	
	@Override
	public void configure() throws Exception {
		from("file:files/incoming?include=order.*xml")
		.setHeader("orderId", xpath(XPATH_ORDERID))
		.setHeader("vendorName", xpath(XPATH_VENDOR_NAME))
		.setHeader("test", xpath(XPATH_TEST))
		.process(new com.processors.TestProcessor())
		.log("${headers.vendorName}")
		.filter(simple("${headers.skipOrder} != 'Y'"))
			.doTry()
				.choice()
					.when(simple("${headers.vendorName} == 'DHL'"))
					.to("file:files/outgoing/DHL?fileExist=Fail")
					.when(simple("${headers.vendorName} == 'COORDINADORA'"))
					.to("file:files/outgoing/Coordinadora?fileExist=Fail")
					.when(simple("${headers.vendorName} == 'SERVIENTREGA'"))
					.to("file:files/outgoing/Servientrega?fileExist=Fail")
					.otherwise()
					.to("file:files/outgoing/Other?fileExist=Fail")
				.endChoice()
			.endDoTry()
			.doCatch(Exception.class)
				.log("Error al guardar el archivo: " + exceptionMessage())
				.to("file:files/outgoing/Errors?fileExist=Append")
			.end()
		.end();
	}

}
