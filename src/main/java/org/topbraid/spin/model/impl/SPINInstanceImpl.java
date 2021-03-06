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

package org.topbraid.spin.model.impl;

import java.util.LinkedList;
import java.util.List;

import org.topbraid.spin.model.QueryOrTemplateCall;
import org.topbraid.spin.model.SPINInstance;
import org.topbraid.spin.util.JenaUtil;
import org.topbraid.spin.util.SPINUtil;

import org.apache.jena.enhanced.EnhGraph;
import org.apache.jena.graph.Node;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.ResourceImpl;


public class SPINInstanceImpl extends ResourceImpl implements SPINInstance {

	public SPINInstanceImpl(Node node, EnhGraph eg) {
		super(node, eg);
	}

	
	@Override
    public List<QueryOrTemplateCall> getQueriesAndTemplateCalls(Property predicate) {
		List<QueryOrTemplateCall> results = new LinkedList<QueryOrTemplateCall>();
		for(Resource cls : JenaUtil.getAllTypes(this)) {
			SPINUtil.addQueryOrTemplateCalls(cls, predicate, results);
		}
		return results;
	}
}
