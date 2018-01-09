/* Copyright 2002-2013 the original author or authors.
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.integration.endpoint.SourcePollingChannelAdapter;
import org.springframework.integration.test.util.TestUtils;

import kh.or.nbc.mps.core.MyAdapterExecutor;

/**
 * @author dhpark
 * @since 1.0
 *
 */
public class MyAdapterInboundChannelAdapterParserTests {

	private ConfigurableApplicationContext context;

	private SourcePollingChannelAdapter consumer;

	@Test
	public void testInboundChannelAdapterParser() throws Exception {

		setUp("MyAdapterInboundChannelAdapterParserTests.xml", getClass(), "myadapterInboundChannelAdapter");

		final AbstractMessageChannel outputChannel = TestUtils.getPropertyValue(this.consumer, "outputChannel", AbstractMessageChannel.class);

		assertEquals("out", outputChannel.getComponentName());

		final MyAdapterExecutor myadapterExecutor = TestUtils.getPropertyValue(this.consumer, "source.myadapterExecutor", MyAdapterExecutor.class);

		assertNotNull(myadapterExecutor);

		final String exsampleProperty = TestUtils.getPropertyValue(myadapterExecutor, "exampleProperty", String.class);

		assertEquals("I am a sample property", exsampleProperty);

	}

	@Test
	public void testLifeCycleAttributes() throws Exception {

		setUp("MyAdapterInboundChannelAdapterParserTests.xml", getClass(), "myadapterInboundChannelAdapter");

		assertFalse(this.consumer.isAutoStartup());
		assertEquals(Integer.valueOf(Integer.MAX_VALUE), Integer.valueOf(this.consumer.getPhase()));

	}

	@Test
	public void testLifeCycleAttributesStarted() throws Exception {
		setUp("MyAdapterInboundChannelAdapterParserTestsStopped.xml", getClass(), "myadapterInboundChannelAdapter");
		assertTrue(this.consumer.isAutoStartup());
	}

	@Test
	public void testExecutorBeanIdNaming() throws Exception {

		this.context = new ClassPathXmlApplicationContext("MyAdapterInboundChannelAdapterParserTests.xml", getClass());
		assertNotNull(context.getBean("myadapterInboundChannelAdapter.myadapterExecutor", MyAdapterExecutor.class));

	}

	@After
	public void tearDown(){
		if(context != null){
			context.close();
		}
	}

	public void setUp(String name, Class<?> cls, String consumerId){
		context    = new ClassPathXmlApplicationContext(name, cls);
		consumer   = this.context.getBean(consumerId, SourcePollingChannelAdapter.class);
	}

}
