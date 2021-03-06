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

package org.topbraid.spin.util;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.topbraid.spin.constraints.ObjectPropertyPath;
import org.topbraid.spin.constraints.SimplePropertyPath;
import org.topbraid.spin.constraints.SubjectPropertyPath;
import org.topbraid.spin.model.Element;
import org.topbraid.spin.model.SPINFactory;
import org.topbraid.spin.model.TriplePattern;
import org.topbraid.spin.model.Variable;
import org.topbraid.spin.model.visitor.AbstractTriplesVisitor;
import org.topbraid.spin.vocabulary.SP;
import org.topbraid.spin.vocabulary.SPIN;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;


/**
 * A utility that can be used to find all SimplePropertyPaths encoded in a
 * SPIN element where either subject or object is ?this.
 * 
 * @author Holger Knublauch
 */
public class PropertyPathsGetter extends AbstractTriplesVisitor {
	
	private Resource localThis;
	
	private Set<SimplePropertyPath> results = new HashSet<SimplePropertyPath>();
	
	private Model targetModel;
	
	
	public PropertyPathsGetter(Element element, Map<Property,RDFNode> initialBindings) {
		super(element, initialBindings);
		this.targetModel = element.getModel();
		this.localThis = SPIN._this.inModel(targetModel);
	}
	
	
	public Set<SimplePropertyPath> getResults() {
		return results;
	}

	
	@Override
	protected void handleTriplePattern(TriplePattern triplePattern, Map<Property, RDFNode> bindings) {
		if(SPIN._this.equals(triplePattern.getSubject())) {
			Resource predicate = triplePattern.getPredicate();
			if(predicate != null && predicate.isURIResource()) {
				Variable variable = SPINFactory.asVariable(predicate);
				if(variable == null) {
					String uri = predicate.getURI();
					Property pred = targetModel.getProperty(uri);
					results.add(new ObjectPropertyPath(localThis, pred));
				}
				else if(bindings != null) {
					String varName = variable.getName();
					Property argProperty = targetModel.getProperty(SP.NS + varName);
					RDFNode b = bindings.get(argProperty);
					if(b != null && b.isURIResource()) {
						String uri = ((Resource)b).getURI();
						Property pred = targetModel.getProperty(uri);
						results.add(new ObjectPropertyPath(localThis, pred));
					}
				}
			}
		}
		if(SPIN._this.equals(triplePattern.getObject())) {
			Resource predicate = triplePattern.getPredicate();
			if(predicate != null && predicate.isURIResource()) {
				Variable variable = SPINFactory.asVariable(predicate);
				if(variable == null) {
					String uri = predicate.getURI();
					Property pred = targetModel.getProperty(uri);
					results.add(new SubjectPropertyPath(localThis, pred));
				}
				else if(bindings != null) {
					String varName = variable.getName();
					Property argProperty = targetModel.getProperty(SP.NS + varName);
					RDFNode b = bindings.get(argProperty);
					if(b != null && b.isURIResource()) {
						String uri = ((Resource)b).getURI();
						Property pred = targetModel.getProperty(uri);
						results.add(new SubjectPropertyPath(localThis, pred));
					}
				}
			}
		}
	}
}
