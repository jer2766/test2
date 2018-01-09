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
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.config.xml.AbstractOutboundChannelAdapterParser;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.w3c.dom.Element;

import kh.or.nbc.mps.outbound.MyAdapterOutboundGateway;

/**
 * The parser for the MyAdapter Outbound Channel Adapter.
 *
 * @author dhpark
 * @since 1.0
 *
 */
public class MyAdapterOutboundChannelAdapterParser extends AbstractOutboundChannelAdapterParser {

	@Override
	protected boolean shouldGenerateId() {
		return false;
	}

	@Override
	protected boolean shouldGenerateIdAsFallback() {
		return true;
	}

	@Override
	protected AbstractBeanDefinition parseConsumer(Element element, ParserContext parserContext) {

		final BeanDefinitionBuilder myadapterOutboundChannelAdapterBuilder = BeanDefinitionBuilder.genericBeanDefinition(MyAdapterOutboundGateway.class);
		final BeanDefinitionBuilder myadapterExecutorBuilder = MyAdapterParserUtils.getMyAdapterExecutorBuilder(element, parserContext);

		IntegrationNamespaceUtils.setValueIfAttributeDefined(myadapterExecutorBuilder, element, "example-property");

		final BeanDefinition myadapterExecutorBuilderBeanDefinition = myadapterExecutorBuilder.getBeanDefinition();
		final String channelAdapterId = this.resolveId(element, myadapterOutboundChannelAdapterBuilder.getRawBeanDefinition(), parserContext);
		final String myadapterExecutorBeanName = channelAdapterId + ".myadapterExecutor";

		parserContext.registerBeanComponent(new BeanComponentDefinition(myadapterExecutorBuilderBeanDefinition, myadapterExecutorBeanName));

		myadapterOutboundChannelAdapterBuilder.addConstructorArgReference(myadapterExecutorBeanName);
		myadapterOutboundChannelAdapterBuilder.addPropertyValue("producesReply", Boolean.FALSE);

		return myadapterOutboundChannelAdapterBuilder.getBeanDefinition();

	}

}
