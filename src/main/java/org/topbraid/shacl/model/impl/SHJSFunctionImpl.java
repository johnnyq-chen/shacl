/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  See the NOTICE file distributed with this work for additional
 *  information regarding copyright ownership.
 */
package org.topbraid.shacl.model.impl;

import org.apache.jena.enhanced.EnhGraph;
import org.apache.jena.graph.Node;
import org.topbraid.shacl.model.SHJSFunction;
import org.topbraid.shacl.vocabulary.SH;
import org.topbraid.spin.util.JenaUtil;

public class SHJSFunctionImpl extends SHParameterizableImpl implements SHJSFunction {
	
	public SHJSFunctionImpl(Node node, EnhGraph graph) {
		super(node, graph);
	}

	
	@Override
	public String getFunctionName() {
		String result = JenaUtil.getStringProperty(this, SH.jsFunctionName);
		if(result == null) {
			return getLocalName();
		}
		else {
			return result;
		}
	}
}