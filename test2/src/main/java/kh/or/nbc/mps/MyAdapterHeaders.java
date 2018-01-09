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

package kh.or.nbc.mps;

/**
 * MyAdapter adapter specific message headers.
 *
 * @author dhpark
 * @since 1.0
 */
public class MyAdapterHeaders {

	private static final String PREFIX = "myadapter_";

	public static final String EXAMPLE = PREFIX + "example_";

	/** Noninstantiable utility class */
	private MyAdapterHeaders() {
		throw new AssertionError();
	}

}
