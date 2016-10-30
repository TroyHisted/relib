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
 * Designates an argument as having a value provided by the request body.
 *
 * <p>
 * Parameters are annotated with RequestBean and may specify a prefix for the bean parameters.
 *
 * <pre>
 *  &#64;RequestBean("{parameterPrefix}")
 * </pre>
 *
 * <p>
 * This prefix will be used to filter the request attributes and then removed when setting the property value on
 * the bean.
 *
 * <p>
 * For example, given the request parameters <code>?type=1&amp;cat.name=Sam&amp;cat.age=3</code>, setting the parameter
 * prefix to "cat." will result in the <i>name</i> and <i>age</i> properties being set on the annotated object.
 *
 * @author Troy Histed
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestBean {

	/**
	 * @return the request parameter name prefix
	 */
	String value() default "";

}
