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
package org.relib.http.request;


/**
 * Handles creating an argument value for a request.
 *
 * @author Troy Histed
 */
interface ArgumentGenerator {

	/**
	 * Uses the given request information to generate the appropriate argument value.
	 *
	 * @param requestInfo the parsed request information
	 * @return the argument value to use
	 */
	Object generateArgument(RequestInfo requestInfo);

}
