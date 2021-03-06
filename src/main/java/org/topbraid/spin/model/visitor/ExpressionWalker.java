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

package org.topbraid.spin.model.visitor;

import java.util.List;

import org.topbraid.spin.model.Aggregation;
import org.topbraid.spin.model.FunctionCall;
import org.topbraid.spin.model.Variable;

import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;


/**
 * An ExpressionVisitor that recursively visits all expressions under
 * a given root.
 * 
 * @author Holger Knublauch
 */
public class ExpressionWalker implements ExpressionVisitor {

	private ExpressionVisitor visitor;
	
	
	public ExpressionWalker(ExpressionVisitor visitor) {
		this.visitor = visitor;
	}

	
	@Override
    public void visit(Aggregation aggregation) {
		visitor.visit(aggregation);
		Variable as = aggregation.getAs();
		if(as != null) {
			visitor.visit(as);
		}
		Resource expr = aggregation.getExpression();
		if(expr != null) {
			ExpressionVisitors.visit(expr, this);
		}
	}


	@Override
    public void visit(FunctionCall functionCall) {
		visitor.visit(functionCall);
		List<RDFNode> args = functionCall.getArguments();
		for(RDFNode arg : args) {
			ExpressionVisitors.visit(arg, this);
		}
	}

	
	@Override
    public void visit(RDFNode node) {
		visitor.visit(node);
	}

	
	@Override
    public void visit(Variable variable) {
		visitor.visit(variable);
	}
}
