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

/**
 * A debug message.
 *
 * @author Troy Histed
 */
public class DebugMessage extends AbstractMessage {

	/**
	 * Constructs a message with a level of {@link Message#DEBUG_LEVEL}.
	 *
	 * <p>
	 * See {@link AbstractMessage#AbstractMessage(String, String...)}.
	 *
	 * @param aMessage
	 *            the message text
	 * @param args
	 *            message variables
	 */
	public DebugMessage(String aMessage, String... args) {
		super(aMessage, args);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getLevel() {
		return Message.DEBUG_LEVEL;
	};
}
