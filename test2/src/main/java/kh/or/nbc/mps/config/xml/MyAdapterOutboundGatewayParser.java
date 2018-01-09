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
package kh.or.nbc.mps.config.xml;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractConsumerEndpointParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import kh.or.nbc.mps.outbound.MyAdapterOutboundGateway;

/**
 * The Parser for MyAdapter Outbound Gateway.
 *
 * @author dhpark
 * @since 1.0
 *
 */
public class MyAdapterOutboundGatewayParser extends AbstractConsumerEndpointParser  {

	@Override
	protected BeanDefinitionBuilder parseHandler(Element gatewayElement, ParserContext parserContext) {

		final BeanDefinitionBuilder myadapterOutboundGatewayBuilder = BeanDefinitionBuilder
				.genericBeanDefinition(MyAdapterOutboundGateway.class);

		IntegrationNamespaceUtils.setValueIfAttributeDefined(myadapterOutboundGatewayBuilder, gatewayElement, "reply-timeout", "sendTimeout");

		final String replyChannel = gatewayElement.getAttribute("reply-channel");

		if (StringUtils.hasText(replyChannel)) {
			myadapterOutboundGatewayBuilder.addPropertyReference("outputChannel", replyChannel);
		}

		final BeanDefinitionBuilder myadapterExecutorBuilder = MyAdapterParserUtils.getMyAdapterExecutorBuilder(gatewayElement, parserContext);

		IntegrationNamespaceUtils.setValueIfAttributeDefined(myadapterExecutorBuilder, gatewayElement, "example-property");

		final BeanDefinition myadapterExecutorBuilderBeanDefinition = myadapterExecutorBuilder.getBeanDefinition();
		final String gatewayId = this.resolveId(gatewayElement, myadapterOutboundGatewayBuilder.getRawBeanDefinition(), parserContext);
		final String myadapterExecutorBeanName = gatewayId + ".myadapterExecutor";

		parserContext.registerBeanComponent(new BeanComponentDefinition(myadapterExecutorBuilderBeanDefinition, myadapterExecutorBeanName));

		myadapterOutboundGatewayBuilder.addConstructorArgReference(myadapterExecutorBeanName);

		return myadapterOutboundGatewayBuilder;

	}

	@Override
	protected String getInputChannelAttributeName() {
		return "request-channel";
	}

}
