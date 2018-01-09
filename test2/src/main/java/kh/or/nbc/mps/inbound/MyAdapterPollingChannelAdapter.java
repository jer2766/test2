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
package kh.or.nbc.mps.inbound;

import org.springframework.integration.Message;
import org.springframework.integration.context.IntegrationObjectSupport;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.util.Assert;

import kh.or.nbc.mps.core.MyAdapterExecutor;

/**
 * @author dhpark
 * @since 1.0
 *
 */
public class MyAdapterPollingChannelAdapter extends IntegrationObjectSupport implements MessageSource<Object>{

	private final MyAdapterExecutor myadapterExecutor;

	/**
	 * Constructor taking a {@link MyAdapterExecutor} that provide all required MyAdapter
	 * functionality.
	 *
	 * @param myadapterExecutor Must not be null.
	 */
	public MyAdapterPollingChannelAdapter(MyAdapterExecutor myadapterExecutor) {
		super();
		Assert.notNull(myadapterExecutor, "myadapterExecutor must not be null.");
		this.myadapterExecutor = myadapterExecutor;
	}

	/**
	 * Check for mandatory attributes
	 */
	@Override
	protected void onInit() throws Exception {
		 super.onInit();
	}

	/**
	 * Uses {@link MyAdapterExecutor#poll()} to executes the MyAdapter operation.
	 *
	 * If {@link MyAdapterExecutor#poll()} returns null, this method will return
	 * <code>null</code>. Otherwise, a new {@link Message} is constructed and returned.
	 */
	public Message<Object> receive() {

		final Object payload = myadapterExecutor.poll();

		if (payload == null) {
			return null;
		}

		return MessageBuilder.withPayload(payload).build();
	}

	@Override
	public String getComponentType(){
		return "myadapter:inbound-channel-adapter";
	}

}
