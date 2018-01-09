/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kh.or.nbc.mps.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.util.Assert;

/**
 * Bundles common core logic for the MyAdapter components.
 *
 * @author dhpark
 * @since 1.0
 *
 */
public class MyAdapterExecutor implements InitializingBean {

	private static final Log logger = LogFactory.getLog(MyAdapterExecutor.class);

	private volatile String  exampleProperty;

	/**
	 * Constructor.
	 */
	public MyAdapterExecutor() {}

	/**
	 * Verifies and sets the parameters. E.g. initializes the to be used
	 */
	public void afterPropertiesSet() {
		Assert.hasText(this.exampleProperty, "exampleProperty must not be empty.");
	}

	/**
	 * Executes the outbound MyAdapter Operation.
	 *
	 * @param message The Spring Integration input message
	 * @return The result message payload
	 *
	 */
	public Object executeOutboundOperation(final Message<?> message) {

		if (logger.isWarnEnabled()) {
			logger.warn("Logic not implemented, yet.");
		}

		return message.getPayload();

	}

	/**
	 * Execute the MyAdapter operation. Delegates to
	 * {@link MyAdapterExecutor#poll(Message)}.
	 *
	 * @return The payload object, which may be null.
	 */
	public Object poll() {
		return poll(null);
	}

	/**
	 * Execute a retrieving (polling) MyAdapter operation.
	 *
	 * @param requestMessage May be null.
	 * @return The payload object, which may be null.
	 */
	public Object poll(final Message<?> requestMessage) {

		if (logger.isWarnEnabled()) {
			logger.warn("Logic not implemented, yet.");
		}

		if (requestMessage == null) {
			return null;
		}

		return MessageBuilder.fromMessage(requestMessage).build();
	}

	/**
	 * Example property to illustrate usage of properties in Spring Integration
	 * components. Replace with your own logic.
	 *
	 * @param exampleProperty Must not be null
	 */
	public void setExampleProperty(String exampleProperty) {
		Assert.hasText(exampleProperty, "exampleProperty must be neither null nor empty");
		this.exampleProperty = exampleProperty;
	}

}
