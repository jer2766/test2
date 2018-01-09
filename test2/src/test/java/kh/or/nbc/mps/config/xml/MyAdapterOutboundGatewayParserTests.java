/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package kh.or.nbc.mps.config.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.integration.test.util.TestUtils;

import kh.or.nbc.mps.core.MyAdapterExecutor;
import kh.or.nbc.mps.outbound.MyAdapterOutboundGateway;

/**
 * @author dhpark
 * @since 1.0
 *
 */
public class MyAdapterOutboundGatewayParserTests {

	private ConfigurableApplicationContext context;

	private EventDrivenConsumer consumer;

	@Test
	public void testOutboundGatewayParser() throws Exception {
		setUp("MyAdapterOutboundGatewayParserTests.xml", getClass(), "myadapterOutboundGateway");


		final AbstractMessageChannel inputChannel = TestUtils.getPropertyValue(this.consumer, "inputChannel", AbstractMessageChannel.class);

		assertEquals("in", inputChannel.getComponentName());

		final MyAdapterOutboundGateway myadapterOutboundGateway = TestUtils.getPropertyValue(this.consumer, "handler", MyAdapterOutboundGateway.class);

		long sendTimeout = TestUtils.getPropertyValue(myadapterOutboundGateway, "messagingTemplate.sendTimeout", Long.class);

		assertEquals(100, sendTimeout);

		final MyAdapterExecutor myadapterExecutor = TestUtils.getPropertyValue(this.consumer, "handler.myadapterExecutor", MyAdapterExecutor.class);

		assertNotNull(myadapterExecutor);

		final String exsampleProperty = TestUtils.getPropertyValue(myadapterExecutor, "exampleProperty", String.class);

		assertEquals("I am a sample property", exsampleProperty);


	}

	@Test
	public void testExecutorBeanIdNaming() throws Exception {

		this.context = new ClassPathXmlApplicationContext("MyAdapterOutboundGatewayParserTests.xml", getClass());
		assertNotNull(context.getBean("myadapterOutboundGateway.myadapterExecutor", MyAdapterExecutor.class));

	}

	@After
	public void tearDown() {
		if (context != null) {
			context.close();
		}
	}

	public void setUp(String name, Class<?> cls, String gatewayId) {
		context    = new ClassPathXmlApplicationContext(name, cls);
		consumer   = this.context.getBean(gatewayId, EventDrivenConsumer.class);
	}

}
