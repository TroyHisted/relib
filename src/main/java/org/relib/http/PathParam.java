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
package org.relib.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Designates an argument of a method as having a value from the Http Request path.
 *
 * <p>
 * Parameters are mapped using the path from the HandleRequest annotation.
 *
 * <pre>
 *  &#64;HandleRequest("/users/{userId}")
 * </pre>
 *
 * <p>
 * Parameters are annotated with PathParam and specify the specific path variable by using the same value as the
 * path.
 *
 * <pre>
 *  &#64;PathParam("{userId}")
 * </pre>
 *
 * @author Troy Histed
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface PathParam {

	/**
	 * @return the path variable name
	 */
	String value();

}
