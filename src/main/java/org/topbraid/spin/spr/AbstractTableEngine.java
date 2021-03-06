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
package org.topbraid.spin.spr;

import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;


/**
 * Convenience base class for TableEngine implementations.
 * 
 * @author Holger Knublauch
 */
public abstract class AbstractTableEngine implements TableEngine {

	
	protected void addVarNames(String ns, Resource table, List<String> varNames) {
		Model model = table.getModel();
		for(int col = 0; col < varNames.size(); col++) {
			String varName = varNames.get(col);
			Property varNameProperty = getVarNameProperty(ns, col);
			table.addProperty(varNameProperty, model.createTypedLiteral(varName));
		}
	}
	
	
	protected Property getColCountProperty(String ns) {
		return ResourceFactory.createProperty(ns + "colCount");
	}
	
	
	protected Property getRowCountProperty(String ns) {
		return ResourceFactory.createProperty(ns + "rowCount");
	}
	
	
	protected Property getValueProperty(String ns) {
		return ResourceFactory.createProperty(ns + "value");
	}

	
	protected Property getVarNameProperty(String ns, int colIndex) {
		return ResourceFactory.createProperty(ns + "colName" + colIndex);
	}
}
