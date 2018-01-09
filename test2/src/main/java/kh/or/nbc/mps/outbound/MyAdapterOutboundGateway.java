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

package kh.or.nbc.mps.outbound;

import org.springframework.integration.Message;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.util.Assert;

import kh.or.nbc.mps.core.MyAdapterExecutor;

/**
 *
 * @author dhpark
 * @since 1.0
 *
 */
public class MyAdapterOutboundGateway extends AbstractReplyProducingMessageHandler {

	private final MyAdapterExecutor   myadapterExecutor;
	private boolean producesReply = true;	//false for outbound-channel-adapter, true for outbound-gateway

	/**
	 * Constructor taking an {@link MyAdapterExecutor} that wraps common
	 * MyAdapter Operations.
	 *
	 * @param myadapterExecutor Must not be null
	 *
	 */
	public MyAdapterOutboundGateway(MyAdapterExecutor myadapterExecutor) {
		Assert.notNull(myadapterExecutor, "myadapterExecutor must not be null.");
		this.myadapterExecutor = myadapterExecutor;
	}

	/**
	 *
	 */
	@Override
	protected void onInit() {
		super.onInit();
	}

	@Override
	protected Object handleRequestMessage(Message<?> requestMessage) {

		final Object result;

		result = this.myadapterExecutor.executeOutboundOperation(requestMessage);

		if (result == null || !producesReply) {
			return null;
		}

		return MessageBuilder.withPayload(result).copyHeaders(requestMessage.getHeaders()).build();

	}

	/**
	 * If set to 'false', this component will act as an Outbound Channel Adapter.
	 * If not explicitly set this property will default to 'true'.
	 *
	 * @param producesReply Defaults to 'true'.
	 *
	 */
	public void setProducesReply(boolean producesReply) {
		this.producesReply = producesReply;
	}

}
