package br.ufms.facom;

import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import br.ufms.facom.model.Attribute;
import br.ufms.facom.model.Class;
import br.ufms.facom.model.Entity;
import br.ufms.facom.model.Enumm;
import br.ufms.facom.model.Inheritance;
import br.ufms.facom.model.Invocation;
import br.ufms.facom.model.LocalVariable;
import br.ufms.facom.model.Method;
import br.ufms.facom.model.Namespace;
import br.ufms.facom.model.Parameter;
import br.ufms.facom.model.ParameterizableClass;
import br.ufms.facom.model.ParameterizedType;
import br.ufms.facom.model.PrimitiveType;
import br.ufms.facom.model.Type;


public class Manager {

	private ArrayList<Class> listClasses = new ArrayList<Class>();

		private void relationship(ArrayList<Method> listMethods, ArrayList<Invocation> listInvocations,
			ArrayList<Type> listTypes, ArrayList<Attribute> listAttributes,
			ArrayList<Enumm> listEnum, ArrayList<PrimitiveType> listPrimitive,
			ArrayList<ParameterizedType> listParameterized, ArrayList<LocalVariable> listLocalVariables, 
			ArrayList<Parameter> listParameters, ArrayList<Inheritance> listInheritance,
			ArrayList<Namespace> listNamespace, ArrayList<ParameterizableClass> listPClass){

		
		listParameters = putTypeOnParameters(listParameters, listClasses, listTypes,listEnum, listPrimitive, listParameterized);
		listLocalVariables = putTypeOnLocalVariables(listLocalVariables, listClasses, listTypes,listEnum, listPrimitive, listParameterized);
		listMethods = putParametersOnMethod(listMethods, listParameters);
		listMethods = putLocalVariablesOnMethod(listMethods, listLocalVariables);
				
		
		listMethods = relationBetweenMethodAndInvocation(listMethods, listInvocations);
		listClasses = relationBetweenClassAndAttribute(listAttributes, listClasses);
		listPClass = parameterizableClassAndAtribute(listPClass, listParameterized);
		listClasses = relationBetweenClassAndMethod(listMethods, listClasses);
		listClasses = recognizeTypesOfAttributes(listClasses, listTypes, listEnum, listPrimitive, listParameterized);
		listClasses = doInheritance(listClasses, listInheritance);
		listClasses = recongnizeInternalClasses(listClasses, listPClass, listMethods, listParameterized);
		listClasses = fullNameForClass(listNamespace, listClasses);
		listPClass = fullNameForParameterizableClass(listPClass, listNamespace);
		listPClass = fullNameForParameterizedType(listPClass, listParameterized);
		listClasses = fullNameForAttributes(listClasses, listAttributes);
		listClasses = fullNameForMethods(listClasses);
		listClasses = fullNameForInternalClass(listClasses,listPClass, listMethods, listAttributes, listParameterized);
		
		for(Class classe: listClasses){
			System.out.println(classe.getFullName());
		}
		for(ParameterizableClass pClass: listPClass){
			System.out.println(pClass.getFullName());
		}
		
	
	}
		
		
		private ArrayList<ParameterizableClass> parameterizableClassAndAtribute(
				ArrayList<ParameterizableClass> listPClass, ArrayList<ParameterizedType> listParameterized) {
			for(ParameterizableClass pClass: listPClass){
				ArrayList<ParameterizedType> listAux = pClass.getParameterizedAttributes();
				for(ParameterizedType pType: listParameterized){
					if(pClass.getId() == pType.getParameterizedClassID()){
						listAux.add(pType);
					}
				}
				pClass.setParameterizedAttributes(listAux);
			}
			return listPClass;
		}
		
		private ArrayList<Class> recongnizeInternalClasses(ArrayList<Class> listClasses,
				ArrayList<ParameterizableClass> listPClass,	ArrayList<Method> listMethods, 
				ArrayList<ParameterizedType> listParameterized) {
			
			for(Class classe: listClasses){
				for(Class classe2: listClasses){
					if(classe.getContainer() == classe2.getId()){
						classe.setInternalClass(true);
					}
				}
			}	
			
			
			for(Class classe: listClasses){
				for(ParameterizableClass pClass: listPClass){
					if(classe.getContainer() == pClass.getId()){
						classe.setInternalClass(true);
					}
				}
			}
			
			for(Class classe: listClasses){
				for(Method method: listMethods){
					if(classe.getContainer() == method.getId()){
						classe.setInternalClass(true);
					}
				}
			}
			
			for(Class classe: listClasses){
				for(ParameterizedType pType: listParameterized){
					if(classe.getContainer() == pType.getId()){
						classe.setInternalClass(true);
					}
				}
			}
			
			for(ParameterizableClass pClass: listPClass){
				for(ParameterizableClass pClassInternal: listPClass){
					if(pClass.getContainer() == pClassInternal.getId()){
						pClass.setInternalClass(true);
					}
				}
			}
			
			return listClasses;
		}
				
		private ArrayList<Class> fullNameForClass(ArrayList<Namespace> listNamespace, ArrayList<Class> listClasses) {
			String fullName;
			for(Class classe: listClasses){
				int next = classe.getContainer();
				fullName = classe.getName();			
				
				
				if(classe.isInternalClass() == false){
					while(next != 0){
						for(Namespace namespace: listNamespace){
							if(namespace.getId() == next){
								fullName = namespace.getName() + "." + fullName;
								next = namespace.getParentScopeID();
								break;
							}			
							
						}
					}
				}
				classe.setFullName(fullName);
				fullName = "";
			}
				
			return listClasses;
		}
		
		private ArrayList<ParameterizableClass> fullNameForParameterizableClass(ArrayList<ParameterizableClass> listPClass, 
					ArrayList<Namespace> listNamespace){
			
			String fullName;
			for(ParameterizableClass pClass: listPClass){
				int next = pClass.getContainer();
				fullName = pClass.getName();				
				
				if(pClass.isInternalClass() == false){
					while(next != 0){
						for(Namespace namespace: listNamespace){
							if(namespace.getId() == next){
								fullName = namespace.getName() + "." + fullName;
								next = namespace.getParentScopeID();
								break;
							}			
							
						}
					}
				}
				pClass.setFullName(fullName);
				fullName = "";
			}
					
			return listPClass;
		}
		
		private ArrayList<Class> fullNameForMethods(ArrayList<Class> listClasses){
			
			for(Class classe: listClasses){
				for(Method method: classe.getMethods()){
					method.setFullName(classe.getFullName());
				}
			}
			return listClasses;
		}
		
		private ArrayList<ParameterizableClass> fullNameForParameterizedType (ArrayList<ParameterizableClass> listPClass,
					ArrayList<ParameterizedType> listParameterized){
			
			for(ParameterizableClass pClass: listPClass){
				for(ParameterizedType pType: listParameterized){
					pType.setFullName(pClass.getFullName());
				}
			}
			
			return listPClass;
		}
		
		private ArrayList<Class> fullNameForAttributes(ArrayList<Class> listClasses, ArrayList<Attribute> listAttributes){
			
			for(Class classe: listClasses){
				for(Attribute attribute : listAttributes){
					attribute.setFullName(classe.getFullName());
				}
			}
			
			
			return listClasses;
		}

		private ArrayList<Class> fullNameForInternalClass(ArrayList<Class> listClasses, 
				ArrayList<ParameterizableClass> listPClass, ArrayList<Method> listMethods,
				ArrayList<Attribute> listAttributes, ArrayList<ParameterizedType> listParameterized) {
			
			for(Class internalClass: listClasses){
				for(Class classe: listClasses){
					if(internalClass.getContainer() == classe.getId() && internalClass.isInternalClass()){
						internalClass.setFullName(classe.getFullName() + "." + internalClass.getName());
					}
				}
				for(ParameterizableClass pClass: listPClass){
					if(internalClass.getContainer() == pClass.getId() && internalClass.isInternalClass()){
						internalClass.setFullName(pClass.getFullName() + "." + internalClass.getName());
					}
				}				
				
				for(Method method: listMethods){
					if(internalClass.getContainer() == method.getId() && internalClass.isInternalClass()){
						internalClass.setFullName(method.getFullName() + "." + internalClass.getName());
					}
				}
				
				for(Attribute attribute: listAttributes){
					if(internalClass.getContainer() == attribute.getId() && internalClass.isInternalClass()){
						internalClass.setFullName(attribute.getFullName() + "." + internalClass.getName());
					}
				}
				
				for(ParameterizedType parameterized: listParameterized){
					if(internalClass.getContainer() == parameterized.getId() && internalClass.isInternalClass()){
						internalClass.setFullName(parameterized.getFullName() + "." + internalClass.getName());
					}
				}
				
					
			}
			
			for(ParameterizableClass internalClass: listPClass){
				for(ParameterizableClass pClass: listPClass){
					if(internalClass.getContainer() == pClass.getId() && internalClass.isInternalClass()){
						internalClass.setFullName(pClass.getFullName() + "." + internalClass.getName());
					}
				}
				
				for(Class classe: listClasses){
					if(internalClass.getContainer() == classe.getId() && internalClass.isInternalClass()){
						internalClass.setFullName(classe.getFullName() + "." + internalClass.getName());
					}
				}				
				
				for(Method method: listMethods){
					if(internalClass.getContainer() == method.getId() && internalClass.isInternalClass()){
						internalClass.setFullName(method.getFullName() + "." + internalClass.getName());
					}
				}
				for(Attribute attribute: listAttributes){
					if(internalClass.getContainer() == attribute.getId() && internalClass.isInternalClass()){
						internalClass.setFullName(attribute.getFullName() + "." + internalClass.getName());
					}
				}
				
				for(ParameterizedType parameterized: listParameterized){
					if(internalClass.getContainer() == parameterized.getId() && internalClass.isInternalClass()){
						internalClass.setFullName(parameterized.getFullName() + "." + internalClass.getName());
					}
				}
					
			}
			
			return listClasses;
		}
				
		private ArrayList<LocalVariable> putTypeOnLocalVariables(ArrayList<LocalVariable> listLocalVariables,
				ArrayList<Class> listClasses, ArrayList<Type> listTypes, ArrayList<Enumm> listEnum,
				ArrayList<PrimitiveType> listPrimitive, ArrayList<ParameterizedType> listParameterized) {
			
			for(LocalVariable localVariable: listLocalVariables){
				for(Type type: listTypes){
					if(localVariable.getDeclaredTypeID() == type.getId()){
						localVariable.setType(type);
						break;
					}
				}

				for(Class classe: listClasses){
					if(localVariable.getDeclaredTypeID() == classe.getId()){
						localVariable.setType(classe);
						break;

					}
				}

				for(Enumm enumm: listEnum){
					if(localVariable.getDeclaredTypeID() == enumm.getId()){
						localVariable.setType(enumm);
						break;

					}
				}
				for(PrimitiveType primitive: listPrimitive){
					if(localVariable.getDeclaredTypeID() == primitive.getId()){
						localVariable.setType(primitive);
						break;
					}
				}
				for(ParameterizedType parameterized: listParameterized){
					if(localVariable.getDeclaredTypeID() == parameterized.getId()){
						localVariable.setType(parameterized);
						break;
					}
				}
			}

				return listLocalVariables;
		}
		
		private ArrayList<Method> putLocalVariablesOnMethod(ArrayList<Method> listMethods,
			ArrayList<LocalVariable> listLocalVariables) {
		ArrayList<LocalVariable> listAux = new ArrayList<LocalVariable>();
		
		for(Method method: listMethods){
			listAux = method.getLocalVariables();
			for(LocalVariable localVariable: listLocalVariables){
				if(localVariable.getParentID() == method.getId()){
					listAux.add(localVariable);
				}
			}
			method.setLocalVariables(listAux);
		}
		
		return listMethods;
		}

		private ArrayList<Method> putParametersOnMethod(ArrayList<Method> listMethods,
			ArrayList<Parameter> listParameters) {
		
		ArrayList<Parameter> listAux = new ArrayList<Parameter>();
		
		for(Method method: listMethods){
			listAux = method.getParameters();
			for(Parameter parameter: listParameters){
				if(parameter.getParentID() == method.getId()){
					listAux.add(parameter);
				}
			}
			method.setParameters(listAux);
		}
		
		return listMethods;
		}
		
		private ArrayList<Parameter> putTypeOnParameters(ArrayList<Parameter> listParameters, ArrayList<Class> listClasses, ArrayList<Type> listTypes,
			ArrayList<Enumm> listEnum, ArrayList<PrimitiveType> listPrimitive,
			ArrayList<ParameterizedType> listParameterized) {

		for(Parameter parameter: listParameters){
			for(Type type: listTypes){
				if(parameter.getDeclaredTypeID() == type.getId()){
					parameter.setType(type);
					break;
				}
			}

			for(Class classe: listClasses){
				if(parameter.getDeclaredTypeID() == classe.getId()){
					parameter.setType(classe);
					break;

				}
			}

			for(Enumm enumm: listEnum){
				if(parameter.getDeclaredTypeID() == enumm.getId()){
					parameter.setType(enumm);
					break;

				}
			}
			for(PrimitiveType primitive: listPrimitive){
				if(parameter.getDeclaredTypeID() == primitive.getId()){
					parameter.setType(primitive);
					break;
				}
			}
			for(ParameterizedType parameterized: listParameterized){
				if(parameter.getDeclaredTypeID() == parameterized.getId()){
					parameter.setType(parameterized);
					break;
				}
			}
		}

			return listParameters;
		}
		
		private ArrayList<Class> doInheritance(ArrayList<Class> listClasses, ArrayList<Inheritance> listInheritance){

			for(Inheritance inheritance: listInheritance){
				for(Class classe: listClasses){
					if(classe.getId() == inheritance.getSubClassID()){
						for(Class classe2 : listClasses){
							if(classe2.getId() == inheritance.getSuperClassID()){
								classe.setSuperClass(classe2);
							}
						}
					}
				}

			}
			return listClasses;
		}
		
		private ArrayList<Class> recognizeTypesOfAttributes(ArrayList<Class> listClasses, ArrayList<Type> listTypes,
				ArrayList<Enumm> listEnum, ArrayList<PrimitiveType> listPrimitive,
				ArrayList<ParameterizedType> listParameterized) {
			//Focus a class
			for(Class classe: listClasses){
				//Get all attributes from that class to match all with his respective type/class/enum
				ArrayList<Attribute> listAuxAttributes = classe.getAttributes();
				//Take on attribute to find which is his kind
				if(listAuxAttributes.size() > 0){
					for(Attribute attribute: listAuxAttributes){
						for(Type type: listTypes){
							if(attribute.getDeclaredTypeID() == type.getId()){
								attribute.setType(type);
								break;
							}
						}

						for(Class classe2: listClasses){
							if(attribute.getDeclaredTypeID() == classe2.getId()){
								attribute.setType(classe2);
								break;

							}
						}

						for(Enumm enumm: listEnum){
							if(attribute.getDeclaredTypeID() == enumm.getId()){
								attribute.setType(enumm);
								break;

							}
						}
						for(PrimitiveType primitive: listPrimitive){
							if(attribute.getDeclaredTypeID() == primitive.getId()){
								attribute.setType(primitive);
								break;
							}
						}
						for(ParameterizedType parameterized: listParameterized){
							if(attribute.getDeclaredTypeID() == parameterized.getId()){
								attribute.setType(parameterized);
								break;
							}
						}
					}
				}
				classe.setAttributes(listAuxAttributes);
			}
			return listClasses;
		}
		
		private ArrayList<Class> relationBetweenClassAndAttribute(ArrayList<Attribute> listAttributes,
				ArrayList<Class> globalClasses) {

			ArrayList<Attribute> listAux = new ArrayList<Attribute>();
			for(Class classe: globalClasses){
				listAux = classe.getAttributes();
				for(Attribute attribute: listAttributes){
					if(attribute.getParent() == classe.getId()){
						listAux.add(attribute);
					}			
				}
				classe.setAttributes(listAux);
			}

//					//-----------Testando PRINT-------------------
//					for(Class classe: globalClasses){
//						System.out.println("Class name: " + classe.getName());
//						for(Attribute attribute: classe.getAttributes()){
//							System.out.println(attribute.getName());
//						}
//						System.out.printf("-------------- \n");
//					}
//					//--------------------------------------------
			return globalClasses;
		}
		
		private ArrayList<Method> relationBetweenMethodAndInvocation
		(ArrayList<Method> globalMethods, ArrayList<Invocation> globalInvocations){

			ArrayList<Invocation> listAux = new ArrayList<Invocation>();
			for(Method method: globalMethods){
				listAux = method.getListInvocation();
				for(Invocation invocation: globalInvocations){
					if(invocation.getSender() == method.getId()){
						listAux.add(invocation);
					}

				}
				method.setListInvocation(listAux);
			}

			//		//Testando PRINT
			//		for(Method method: globalMethods){
			//			System.out.println("Method name: " + method.getSignature());
			//			for(Invocation invocation: method.getListInvocation()){
			//				System.out.println(invocation.getSignature());
			//			}
			//			System.out.printf("-------------- \n");
			//		}

			return globalMethods;
		}
		
		private ArrayList<Class> relationBetweenClassAndMethod
		(ArrayList<Method> globalMethods, ArrayList<Class> globalClasses){

			ArrayList<Method> listAux = new ArrayList<Method>();
			for(Class classe: globalClasses){
				listAux = classe.getMethods();
				for(Method method: globalMethods){
					if(method.getParent() == classe.getId()){
						listAux.add(method);
					}

				}

				classe.setMethods(listAux);
			}

			//		//-----------Testando PRINT-------------------
			//		for(Class classe: globalClasses){
			//			System.out.println("Class name: " + classe.getName());
			//			for(Method method: classe.getListMethods()){
			//				System.out.println(method.getSignature());
			//			}
			//			System.out.printf("-------------- \n");
			//		}
			//		//--------------------------------------------
			return globalClasses;
		}
		
		public void createObjects(Map<Integer, Entity> map){

			ArrayList<Class> listClasses = new ArrayList<Class>();
			ArrayList<ParameterizableClass> listPClass = new ArrayList<ParameterizableClass>();
			ArrayList<Method> listMethod = new ArrayList<Method>();
			ArrayList<Invocation> listInvocations = new ArrayList<Invocation>();
			ArrayList<Type> listType =  new ArrayList<Type>();
			ArrayList<Attribute> listAttribute = new ArrayList<Attribute>();
			ArrayList<Enumm> listEnum = new ArrayList<Enumm>();
			ArrayList<PrimitiveType> listPrimitive = new ArrayList<PrimitiveType>();
			ArrayList<ParameterizedType> listParameterized = new ArrayList<ParameterizedType>();
			ArrayList<LocalVariable> listLocalVariables = new ArrayList<LocalVariable>();
			ArrayList<Parameter> listParameters = new ArrayList<Parameter>();
			ArrayList<Inheritance> listInheritance = new ArrayList<Inheritance>();
			ArrayList<Namespace> listNamespace = new ArrayList<Namespace>();

			//entry.getkey() -> ID
			//entry.getValue() -> Entidade
			for (Map.Entry<Integer, Entity> entry : map.entrySet())
			{
				ArrayList<String[]> listAux;		
				if(StringUtils.contains(entry.getValue().getType(), "FAMIX.Method")){
					Method method = new Method();
					method.setId(entry.getKey());
					listAux = entry.getValue().getList();
					for (String[] array : listAux){
						if(StringUtils.contains(array[0], "signature")){
							method.setSignature(array[1]);
						}
						else if(StringUtils.contains(array[0], "parentType")){
							method.setParent(Integer.parseInt(array[2]));
						}

					}
					listMethod.add(method);
				}
				else if(StringUtils.contains(entry.getValue().getType(), "FAMIX.Class") ){
					Class classe = new Class();
					classe.setId(entry.getKey());
					listAux = entry.getValue().getList();
					for (String[] array : listAux){
						if(StringUtils.contains(array[0], "name")){
							if(array.length == 2){
								classe.setName(array[1]);
							}
							else{
								classe.setName("''");
							}
						}
						else if(StringUtils.contains(array[0], "container")){
							classe.setContainer(Integer.parseInt(array[2]));
						}
					}
					listClasses.add(classe);
				}
				
				else if(StringUtils.contains(entry.getValue().getType(), "FAMIX.ParameterizableClass") ){
					ParameterizableClass parameterizableClass = new ParameterizableClass();
					parameterizableClass.setId(entry.getKey());
					listAux = entry.getValue().getList();
					for (String[] array : listAux){
						if(StringUtils.contains(array[0], "name")){
							if(array.length == 2){
								parameterizableClass.setName(array[1]);
							}
							else{
								parameterizableClass.setName("''");
							}
						}
						else if(StringUtils.contains(array[0], "container")){
							parameterizableClass.setContainer(Integer.parseInt(array[2]));
						}
					}
					listPClass.add(parameterizableClass);
				}
				
				else if(StringUtils.contains(entry.getValue().getType(), "FAMIX.Invocation")){
					Invocation invocation = new Invocation();
					invocation.setId(entry.getKey());
					listAux = entry.getValue().getList();
					for (String[] array : listAux){
						if(StringUtils.contains(array[0], "signature")){
							invocation.setSignature((array[1]));
						}
						else if(StringUtils.contains(array[0], "sender")){
							invocation.setSender(Integer.parseInt(array[2]));
						}
					}
					listInvocations.add(invocation);
				}

				else if(StringUtils.contains(entry.getValue().getType(), "FAMIX.Type")){
					Type type = new Type();
					type.setId(entry.getKey());
					listAux = entry.getValue().getList();
					for (String[] array : listAux){
						if(StringUtils.contains(array[0], "name")){
							type.setName((array[1]));
						}
					}
					listType.add(type);
				}
				else if(StringUtils.contains(entry.getValue().getType(), "FAMIX.Attribute")){
					Attribute attribute = new Attribute();
					attribute.setId(entry.getKey());
					listAux = entry.getValue().getList();
					for (String[] array : listAux){
						if(StringUtils.contains(array[0], "name")){
							attribute.setName((array[1]));
						}
						else if(StringUtils.contains(array[0], "declaredType")){
							attribute.setDeclaredTypeID(Integer.parseInt(array[2]));
						}
						else if(StringUtils.contains(array[0], "parentType")){
							attribute.setParent(Integer.parseInt(array[2]));
						}
					}
					listAttribute.add(attribute);
				}
				else if(StringUtils.contains(entry.getValue().getType(), "FAMIX.Enum") &&
						!StringUtils.contains(entry.getValue().getType(), "FAMIX.EnumValue")){
					Enumm enumm = new Enumm();
					enumm.setId(entry.getKey());
					listAux = entry.getValue().getList();
					for (String[] array : listAux){
						if(StringUtils.contains(array[0], "name")){
							enumm.setName((array[1]));
						}
					}
					listEnum.add(enumm);
				}
				else if(StringUtils.contains(entry.getValue().getType(), "FAMIX.PrimitiveType")){
					PrimitiveType primitive = new PrimitiveType();
					primitive.setId(entry.getKey());
					listAux = entry.getValue().getList();
					for(String[] array : listAux){
						if(StringUtils.contains(array[0], "name")){
							primitive.setName(array[1]);
						}
					}
					listPrimitive.add(primitive);
				}
				else if(StringUtils.contains(entry.getValue().getType(), "FAMIX.ParameterizedType")){
					ParameterizedType parameterized = new ParameterizedType();
					parameterized.setId(entry.getKey());
					listAux = entry.getValue().getList();
					for(String[] array : listAux){
						if(StringUtils.contains(array[0], "name")){
							parameterized.setName(array[1]);
						}
						else if(StringUtils.contains(array[0], "parameterizableClass")){
							parameterized.setParameterizedClassID(Integer.parseInt(array[2]));
						}
					}
					listParameterized.add(parameterized);
				}
				else if(StringUtils.contains(entry.getValue().getType(), "FAMIX.LocalVariable")){
					LocalVariable localVariable = new LocalVariable();
					localVariable.setId(entry.getKey());
					listAux = entry.getValue().getList();
					for(String[] array : listAux){
						if(StringUtils.contains(array[0], "name")){
							localVariable.setName(array[1]);
						}
						else if(StringUtils.contains(array[0], "declaredType")){
							localVariable.setDeclaredTypeID(Integer.parseInt(array[2]));
						}
						else if(StringUtils.contains(array[0], "parentBehaviouralEntity")){
							localVariable.setParentID(Integer.parseInt(array[2]));
						}
					}
					listLocalVariables.add(localVariable);

				}
				else if(StringUtils.contains(entry.getValue().getType(), "FAMIX.Parameter") &&
						!StringUtils.contains(entry.getValue().getType(), "FAMIX.ParameterizableClass")){
					Parameter parameter = new Parameter();
					parameter.setId(entry.getKey());
					listAux = entry.getValue().getList();
					for(String[] array : listAux){
						if(StringUtils.contains(array[0], "name")){
							parameter.setName(array[1]);
						}
						else if(StringUtils.contains(array[0], "declaredType")){
							parameter.setDeclaredTypeID(Integer.parseInt(array[2]));
						}
						else if(StringUtils.contains(array[0], "parentBehaviouralEntity")){
							parameter.setParentID(Integer.parseInt(array[2]));
						}
					}

					listParameters.add(parameter);

				}
				else if(StringUtils.contains(entry.getValue().getType(), "FAMIX.Inheritance")){
					Inheritance inheritance = new Inheritance();
					inheritance.setId(entry.getKey());
					listAux = entry.getValue().getList();
					for(String[] array : listAux){
						if(StringUtils.contains(array[0], "subclass")){
							inheritance.setSubClassID(Integer.parseInt(array[2]));
						}
						else if(StringUtils.contains(array[0], "superclass")){
							inheritance.setSuperClassID(Integer.parseInt(array[2]));
						}

					}

					listInheritance.add(inheritance);

				}
				else if(StringUtils.contains(entry.getValue().getType(), "FAMIX.Namespace")){
					Namespace namespace = new Namespace();
					namespace.setId(entry.getKey());
					listAux = entry.getValue().getList();
					
					for(String[] array: listAux){
						if(StringUtils.contains(array[0], "name")){
							if(array.length == 2){
								namespace.setName(array[1]);
							}
							else{
								namespace.setName("''");
							}
						}
						else if(StringUtils.contains(array[0], "parentScope")){
							namespace.setParentScopeID(Integer.parseInt(array[2]));
						}
						
					}
					
					listNamespace.add(namespace);
				}


			}

			this.listClasses = listClasses;
			System.out.println("Lista de Metodos: " + listMethod.size());
			System.out.println("Lista de Tipos: " + listType.size());
			System.out.println("Lista de Atributos: " + listAttribute.size());
			System.out.println("Lista de Invocações: " + listInvocations.size());
			System.out.println("Lista de Classes: " + listClasses.size());
			System.out.println("Lista de Enum: " + listEnum.size());
			System.out.println("Lista de Primitivos: " + listPrimitive.size());
			System.out.println("Lista de Tipos Parametrizados: " + listParameterized.size());
			System.out.println("Lista de Variaveis Locais: " + listLocalVariables.size());
			System.out.println("Lista de Parametros: " + listParameters.size());
			System.out.println("Lista de Heranças: " + listInheritance.size());
			System.out.println("Lista de NameSpace: " + listNamespace.size());
			System.out.println("Lista de Classes parametrizadas: " + listPClass.size());
			
			relationship(listMethod, listInvocations, listType, listAttribute, listEnum, listPrimitive, listParameterized,
					listLocalVariables, listParameters, listInheritance, listNamespace, listPClass);

		}
		
		public int findMethods(int id){

			ArrayList<Method> listForReturn = new ArrayList<Method>();
			for(Class classe: listClasses){
				if(id == classe.getId()){
					listForReturn = classe.getMethods();
					System.out.println("A classe " + classe.getName() + " possui " + classe.getMethods().size() + " Métodos");
					for(Method method: classe.getMethods()){
						System.out.println(method.getSignature());
					}
				}
			}
			return listForReturn.size();
		}
		public int findMethods(String signature){

			ArrayList<Method> listForReturn = new ArrayList<Method>();
			for(Class classe: listClasses){
				if(signature.equals(classe.getName())){
					listForReturn = classe.getMethods();
					System.out.println("A classe " + classe.getName() + " possui " + classe.getMethods().size() + " Métodos");
					for(Method method: classe.getMethods()){
						System.out.println(method.getSignature());
					}
				}
			}
			return listForReturn.size();
		}
		public int findAttributes(int id){

			ArrayList<Attribute> listForReturn = new ArrayList<Attribute>();
			for(Class classe: listClasses){
				if(id == classe.getId()){
					listForReturn = classe.getAttributes();
					System.out.println("A classe " + classe.getName() + " possui " + classe.getAttributes().size() + " Atributos");
					for(Attribute attributes: classe.getAttributes()){
						System.out.println(attributes.getName());
					}
				}
			}
			return listForReturn.size();
		}
		public int findAttributes(String signature){


			ArrayList<Attribute> listForReturn = new ArrayList<Attribute>();
			for(Class classe: listClasses){
				if(signature.equals(classe.getName())){
					listForReturn = classe.getAttributes();
					System.out.println("A classe " + classe.getName() + " possui " + classe.getAttributes().size() + " Atributos");
					for(Attribute attributes: classe.getAttributes()){
						System.out.println(attributes.getName());
					}
				}
			}
			return listForReturn.size();
		}
		public int findInvocations(int id){

			ArrayList<Invocation> listForReturn = new ArrayList<Invocation>();
			for(Class classe: listClasses){
				for(Method method: classe.getMethods()){
					if(method.getId() == id){
						listForReturn = method.getListInvocation();
						System.out.println("O Metodo " + method.getSignature() + " possui " + listForReturn.size() + " Invocações");
						for(Invocation invocations: listForReturn){
							System.out.println(invocations.getSignature());
						}
					}
				}

			}
			return listForReturn.size();

		}
		public int findCBO(int id){
			for(Class classe: listClasses){
				if(id == classe.getId()){
					return classe.calculateCBO();
				}
			}
			return -1;
		}	
		public ArrayList<Class> getClasses() {
			return listClasses;
		}
		public void setClasses(ArrayList<Class> listClasses) {
			this.listClasses = listClasses;
		}

	}
