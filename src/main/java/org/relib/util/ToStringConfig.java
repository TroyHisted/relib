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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies configuration information for the {@link ToString} class.
 *
 * @author Troy Histed
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ToStringConfig {

	/**
	 * Configuration for the specific object type.
	 *
	 * @return the config value
	 */
	String value() default Strings.EMPTY;

	/**
	 * Indicates that the value of this property should be hidden from the generated toString.
	 *
	 * @return <code>true</code> if the field value should not be included in the toString.
	 */
	boolean hidden() default false;

}
