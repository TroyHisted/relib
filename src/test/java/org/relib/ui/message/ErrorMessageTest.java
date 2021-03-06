/**
 * Copyright 2015 Troy Histed
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.relib.ui.message;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests the ErrorMessage implementation of the {@link Message} interface.
 *
 * @author Troy Histed
 */
public class ErrorMessageTest extends AbstractMessageTest {

	/**
	 * Constructs an instance of a Message.
	 *
	 * @param message
	 *            the message text
	 * @param args
	 *            any message variables
	 * @return a new Message instance
	 */
	@Override
	public Message construct(String message, String... args) {
		return new ErrorMessage(message, args);
	}

	/**
	 * Ensure the message level for an error is 40.
	 */
	@Test
	public void testMessageLevel() {
		final Message message = this.construct("");
		Assert.assertEquals(40, message.getLevel());
	}

}
