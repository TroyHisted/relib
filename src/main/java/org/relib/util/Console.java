/**
 * Copyright 2016 Troy Histed
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
package org.relib.util;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility method for writing messages to the system out.
 *
 * <p>
 * Adds information about the location of the log call and returns the object being logged to make it more
 * convenient to log return values.
 *
 * @author Troy Histed
 */
public class Console {

	private static final String TIME_FORMAT = "HH:MM:ss.SSS";

	/**
	 * Writes the object to the System.err output.
	 *
	 * @param object
	 *            the object to log
	 * @param <T>
	 *            the object type to log and return
	 * @return the passed object
	 */
	public static <T> T err(T object) {
		return writeMessage(System.err, object, null);
	}

	/**
	 * Writes the object to the System.err output.
	 *
	 * @param object
	 *            the primary object to log
	 * @param objects
	 *            any additional objects to log
	 */
	public static void err(String object, Object... objects) {
		writeMessage(System.err, object, objects);
	}

	/**
	 * Writes the object to the System.out output.
	 *
	 * @param object
	 *            the object to log
	 * @param <T>
	 *            the object type to log and return
	 * @return the passed object
	 */
	public static <T> T log(T object) {
		return writeMessage(System.out, object, null);
	}

	/**
	 * Writes the message and the objects to the System.out output.
	 *
	 * @param object
	 *            the primary object to log
	 * @param objects
	 *            any additional objects to log
	 */
	public static void log(String object, Object... objects) {
		writeMessage(System.out, object, objects);
	}

	/**
	 * Constructs the console messages as a String.
	 *
	 * @param printStream
	 *            the stream to write to
	 * @param object
	 *            the primary object to log
	 * @param objects
	 *            any additional objects to log
	 * @return the passed object
	 */
	private static <T> T writeMessage(PrintStream printStream, T object, Object[] objects) {
		printStream.print(generateThreadInfo());
		printStream.print(ToString.of(object));
		if (objects != null && objects.length > 0) {
			printStream.print(" ");
			printStream.println(ToString.of(objects));
		} else {
			printStream.println("");
		}
		return object;
	}

	/**
	 * Returns a string containing information about the current thread.
	 *
	 * @return the description
	 */
	private static String generateThreadInfo() {
		final String time = (new SimpleDateFormat(TIME_FORMAT)).format(new Date());
		final StackTraceElement method = Thread.currentThread().getStackTrace()[4];
		final String link = "(" + method.getFileName() + ":" + method.getLineNumber() + ")";
		return time + " " + link + ":";
	}

}
