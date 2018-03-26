package br.ufms.facom;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import br.ufms.facom.model.AnnotationType;
import br.ufms.facom.model.Attribute;
import br.ufms.facom.model.Class;
import br.ufms.facom.model.Constructor;
import br.ufms.facom.model.Entity;
import br.ufms.facom.model.Enumm;
import br.ufms.facom.model.FamixEntity;
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


public class Model {

	private ArrayList<Class> listClasses = new ArrayList<Class>();
	private ArrayList<ParameterizableClass> listPClass = new ArrayList<ParameterizableClass>();
	private ArrayList<Method> listMethods;
	private ArrayList<Invocation> listInvocations;
	private String hash;

	private void relationship(ArrayList<Method> listMethods, ArrayList<Invocation> listInvocations,
			ArrayList<Type> listTypes, ArrayList<Attribute> listAttributes,
			ArrayList<Enumm> listEnum, ArrayList<PrimitiveType> listPrimitive,
			ArrayList<ParameterizedType> listPType, ArrayList<LocalVariable> listLocalVariables, 
			ArrayList<Parameter> listParameters, ArrayList<Inheritance> listInheritance,
			ArrayList<Namespace> listNamespace, ArrayList<ParameterizableClass> listPClass,
			ArrayList<AnnotationType> listAType){


		listParameters = putTypeOnParameters(listParameters, listClasses, listTypes,listEnum, listPrimitive, listPType, listPClass, listAType);
		listLocalVariables = putTypeOnLocalVariables(listLocalVariables, listClasses, listTypes,listEnum, listPrimitive, listPType, listPClass, listAType);
		listMethods = putParametersOnMethod(listMethods, listParameters);
		listMethods = putLocalVariablesOnMethod(listMethods, listLocalVariables);

		listMethods = relationBetweenMethodAndReturnType(listMethods, listClasses, listTypes, listPrimitive, listPType, listEnum, listPClass);
		listMethods = relationBetweenMethodAndInvocation(listMethods, listInvocations);
		listAttributes = recognizeTypesOfAttributes(listClasses, listTypes, listEnum, listPrimitive, listPType, listAttributes, listPClass);
		listClasses = relationBetweenClassAndAttribute(listAttributes, listClasses);
		listPClass = parameterizableClassAndAtribute(listPClass, listPType, listAttributes);
		listClasses = relationBetweenClassAndMethod(listMethods, listClasses);
		listClasses = doInheritance(listClasses, listInheritance, listPClass);
		listClasses = mountContainers(listClasses, listPClass, listMethods, listPType, listNamespace, 
				listTypes, listAttributes, listEnum, listAType);
		fullNameForEntities(listClasses, listTypes, listPClass, listPType, listMethods, listAttributes, listEnum, listParameters, listAType);
		listPType = relationBetweenParameterizedTypeAndArguments(listPType, listEnum, listClasses, listTypes);
		listClasses = recognizeInternalClass(listClasses, listPClass);
		listPType = recognizePClassForPType(listPClass, listPType);
		listInvocations = candidateOfInvocation(listMethods, listInvocations);
		fullNameForInvocations(listMethods);
		this.setPClasses(listPClass);
		this.setAllMethods(listMethods);
		this.setInvocations(listInvocations);

	}

	private ArrayList<Invocation> candidateOfInvocation(ArrayList<Method> listMethods, ArrayList<Invocation> listInvocations) {
		
		for(Invocation invocation: listInvocations){
			for(Method method: listMethods){
				if(invocation.getCandidateID() == method.getId()){
					invocation.setCandidate(method);
				}
			}
//			for(Attribute attribute: listAttributes){
//				if(invocation.getCandidateID() == attribute.getId()){
//					invocation.setCandidate(attribute);
//				}
//			}
//			for(Parameter parameter: listParameters){
//				if(invocation.getCandidateID() == parameter.getId()){
//					invocation.setCandidate(parameter);
//				}
//			}
//			for(Enumm enumm: listEnum){
//				if(invocation.getCandidateID() == enumm.getId()){
//					invocation.setCandidate(enumm);
//				}
//			}
//			for(Class classe: listClasses){
//				if(invocation.getCandidateID() == classe.getId()){
//					invocation.setCandidate(classe);
//				}
//			}
//			for(Type type: listTypes){
//				if(invocation.getCandidateID() == type.getId()){
//					invocation.setCandidate(type);
//				}
//			}
//			for(ParameterizedType pType: listPType){
//				if(invocation.getCandidateID() == pType.getId()){
//					invocation.setCandidate(pType);
//				}
//			}
//			for(ParameterizableClass pClass: listPClass){
//				if(invocation.getCandidateID() == pClass.getId()){
//					invocation.setCandidate(pClass);
//				}
//			}
		}
		return listInvocations;
	}

	private void fullNameForInvocations(ArrayList<Method> listMethods) {
		for(Method method: listMethods){
			for(Invocation invocation: method.getListInvocation()){
				if(invocation.getCandidate() != null){
//					if(invocation.getReciever() instanceof Attribute ){
//						Attribute temp = (Attribute)invocation.getReciever();
//						String fullName = "";
//						if(!(temp.getType() instanceof PrimitiveType)){
//							fullName = temp.getType().getFullName() + "." +StringUtils.remove(invocation.getSignature(), (temp.getName() + "."));
//						}
//						invocation.setFullName(fullName);
//					}
					
					invocation.setFullName(invocation.getCandidate().getFullName());

				}

			}
		}
	}		

	private ArrayList<ParameterizedType> recognizePClassForPType(ArrayList<ParameterizableClass> listPClass,
			ArrayList<ParameterizedType> listPType) {

		for(ParameterizedType pType: listPType){
			for(ParameterizableClass pClass: listPClass){
				if(pType.getParameterizableClassID() == pClass.getId()){
					pType.setpClass(pClass);
				}
			}
		}


		return listPType;
	}

	private void fullNameForEntities(ArrayList<Class> listClasses, ArrayList<Type> listTypes, 
			ArrayList<ParameterizableClass> listPClass, ArrayList<ParameterizedType> listPType,
			ArrayList<Method> listMethods, ArrayList<Attribute> listAttributes,
			ArrayList<Enumm> listEnum, ArrayList<Parameter> listParameters,
			ArrayList<AnnotationType> listAType){

		for(Class classe: listClasses){
			Entity nextEntity = classe.getContainer();
			String fullName = classe.getName();
			while(nextEntity != null){
				fullName = nextEntity.getName() + "." + fullName;
				nextEntity = nextEntity.getContainer();
			}
			classe.setFullName(fullName);

		}

		for(AnnotationType aType: listAType){
			Entity nextEntity = aType.getContainer();
			String fullName = aType.getName();
			while(nextEntity != null){
				fullName = nextEntity.getName() + "." + fullName;
				nextEntity = nextEntity.getContainer();
			}
			aType.setFullName(fullName);
		}

		for(Enumm enumm: listEnum){
			Entity nextEntity = enumm.getContainer();
			String fullName = enumm.getName();
			while(nextEntity != null){
				fullName = nextEntity.getName() + "." + fullName;
				nextEntity = nextEntity.getContainer();
			}
			enumm.setFullName(fullName);

		}

		for(Type type: listTypes){
			Entity nextEntity = type.getContainer();
			String fullName = type.getName();
			while(nextEntity != null){
				fullName = nextEntity.getName() + "." + fullName;
				nextEntity = nextEntity.getContainer();
			}
			type.setFullName(fullName);
		}

		for(ParameterizableClass pClass: listPClass){
			Entity nextEntity = pClass.getContainer();
			String fullName = pClass.getName();
			while(nextEntity != null){
				fullName = nextEntity.getName() + "." + fullName;
				nextEntity = nextEntity.getContainer();
			}
			pClass.setFullName(fullName);
		}
		for(ParameterizedType pType: listPType){
			Entity nextEntity = pType.getContainer();
			String fullName = pType.getName();
			while(nextEntity != null){
				fullName = nextEntity.getName() + "." + fullName;
				nextEntity = nextEntity.getContainer();
			}
			pType.setFullName(fullName);
		}
		for(Method method: listMethods){
			Entity nextEntity = method.getContainer();
			String fullName = method.getSignature();
			while(nextEntity != null){
				fullName = nextEntity.getName() + "." + fullName;
				nextEntity = nextEntity.getContainer();
			}

			method.setFullName(fullName);
		}
		for(Parameter parameter: listParameters){
			Entity nextEntity = parameter.getContainer();
			String fullName = parameter.getName();
			while(nextEntity != null){
				fullName = nextEntity.getName() + "." + fullName;
				nextEntity = nextEntity.getContainer();
			}

			parameter.setFullName(fullName);
		}
		for(Attribute attribute: listAttributes){
			Entity nextEntity = attribute.getContainer();
			String fullName = attribute.getName();
			while(nextEntity != null){
				fullName = nextEntity.getName() + "." + fullName;
				nextEntity = nextEntity.getContainer();
			}

			attribute.setFullName(fullName);
		}

	}

	private ArrayList<Class> recognizeInternalClass(ArrayList<Class> listClasses, 
			ArrayList<ParameterizableClass> listPClass){

		ArrayList<Entity> aux = new ArrayList<Entity>(); 

		for(Class classe: listClasses){

			aux = classe.getInternalClasses();

			for(Class classee: listClasses){

				if(classe.getId() == classee.getContainerID() ){
					aux.add(classee);
				}

			}
			for(ParameterizableClass pClass: listPClass){

				if(classe.getId() == pClass.getContainerID()){
					aux.add(pClass);
				}

			}

			classe.setInternalClasses(aux);
		}


		for(ParameterizableClass pClass: listPClass){

			aux = pClass.getInternalClasses();

			for(Class classee: listClasses){

				if(pClass.getId() == classee.getContainerID() ){
					aux.add(classee);
				}

			}
			for(ParameterizableClass pClasse: listPClass){

				if(pClass.getId() == pClasse.getContainerID()){
					aux.add(pClasse);
				}

			}

			pClass.setInternalClasses(aux);
		}

		return listClasses;
	}

	private ArrayList<ParameterizedType> relationBetweenParameterizedTypeAndArguments(
			ArrayList<ParameterizedType> listPType, ArrayList<Enumm> listEnum,
			ArrayList<Class> listClasses, ArrayList<Type> listTypes){

		for(ParameterizedType pType: listPType){
			ArrayList<Entity> aux = new ArrayList<Entity>();
			if(pType.getArgumentsIDs() != null){
				for(Integer id: pType.getArgumentsIDs()){
					for(Enumm enumm: listEnum){
						if(enumm.getId()==id){
							aux.add(enumm);
						}
					}
					for(Class classe: listClasses){
						if(classe.getId() == id){
							aux.add(classe);
						}
					}
					for(Type type: listTypes){
						if(type.getId()==id){
							aux.add(type);
						}
					}
					for(ParameterizedType pTypee: listPType){
						if(pTypee.getId() == id){
							aux.add(pTypee);
						}
					}
				}
				pType.setArguments(aux);
			}
		}

		return listPType;

	}

	private ArrayList<Method> relationBetweenMethodAndReturnType(ArrayList<Method> listMethods,
			ArrayList<Class> listClasses, ArrayList<Type> listTypes, ArrayList<PrimitiveType> listPrimitive,
			ArrayList<ParameterizedType> listParameterized, ArrayList<Enumm> listEnum,
			ArrayList<ParameterizableClass> listPClass) {

		for(Method method: listMethods){
			if(method.getDeclaredTypeID() != 0){
				for(Class classe: listClasses){
					if(method.getDeclaredTypeID() == classe.getId()){
						method.setType(classe);
					}
				}
				for(Type type: listTypes){
					if(method.getDeclaredTypeID() == type.getId()){
						method.setType(type);
					}
				}
				for(PrimitiveType primitive: listPrimitive){
					if(method.getDeclaredTypeID() == primitive.getId()){
						method.setType(primitive);
					}
				}
				for(ParameterizedType pType: listParameterized){
					if(method.getDeclaredTypeID() == pType.getId()){
						method.setType(pType);
					}
				}
				for(Enumm enumm: listEnum){
					if(method.getDeclaredTypeID() == enumm.getId()){
						method.setType(enumm);
					}
				}
				for(ParameterizableClass pClass: listPClass){
					if(method.getDeclaredTypeID() == pClass.getId()){
						method.setType(pClass);
					}
				}
			}
			else{
				Constructor noType = new Constructor();
				noType.setId(0);
				noType.setName("Constructor");
				method.setType(noType);
			}
		}

		return listMethods;
	}

	private ArrayList<ParameterizableClass> parameterizableClassAndAtribute(
			ArrayList<ParameterizableClass> listPClass, ArrayList<ParameterizedType> listParameterized,
			ArrayList<Attribute> listAttributes) {
		for(ParameterizableClass pClass: listPClass){
			ArrayList<ParameterizedType> listAux = pClass.getParameterizedAttributes();
			for(ParameterizedType pType: listParameterized){
				if(pClass.getId() == pType.getParameterizableClassID()){
					listAux.add(pType);
				}
			}
			pClass.setParameterizedAttributes(listAux);

			ArrayList<Attribute> listTemp = pClass.getListAttributes();
			for(Attribute attribute: listAttributes){
				if(pClass.getId() == attribute.getParentID()){
					listTemp.add(attribute);
				}
			}
			pClass.setListAttributes(listTemp);
		}
		return listPClass;
	}

	private ArrayList<Class> mountContainers(ArrayList<Class> listClasses,
			ArrayList<ParameterizableClass> listPClass,	ArrayList<Method> listMethods, 
			ArrayList<ParameterizedType> listParameterized, ArrayList<Namespace> listNamespace,
			ArrayList<Type> listTypes, ArrayList<Attribute> listAttributes,
			ArrayList<Enumm> listEnum, ArrayList<AnnotationType> listAType) {
		for(Enumm enumm: listEnum){
			for(Class classee: listClasses){
				if(enumm.getContainerID() == classee.getId()){
					enumm.setContainer(classee);
					break;
				}
			}
			for(AnnotationType aType: listAType){
				if(enumm.getContainerID() == aType.getId()){
					enumm.setContainer(aType);
					break;
				}
			}
			for(Type type: listTypes){
				if(enumm.getContainerID() == type.getId()){
					enumm.setContainer(type);
					break;
				}
			}
			for(ParameterizableClass pClass: listPClass){
				if(enumm.getContainerID() == pClass.getId()){
					enumm.setContainer(pClass);
					break;
				}
			}
			for(Method method: listMethods){
				if(enumm.getContainerID() == method.getId()){
					enumm.setContainer(method);
					break;
				}
			}
			for(ParameterizedType pType: listParameterized){
				if(enumm.getContainerID() == pType.getId()){
					enumm.setContainer(pType);
					break;
				}
			}
			for(Namespace namespace: listNamespace){
				if(enumm.getContainerID() == namespace.getId()){
					enumm.setContainer(namespace);
					break;
				}
			}
			for(Enumm enumm2: listEnum){
				if(enumm.getContainerID() == enumm2.getId()){
					enumm.setContainer(enumm);
					break;
				}
			}

		}			
		//Colocando container na classe	
		for(Class classe: listClasses){
			for(Class classee: listClasses){
				if(classe.getContainerID() == classee.getId()){
					classe.setContainer(classee);
					break;
				}
			}
			for(AnnotationType aType: listAType){
				if(classe.getContainerID() == aType.getId()){
					classe.setContainer(aType);
					break;
				}
			}
			for(Type type: listTypes){
				if(classe.getContainerID() == type.getId()){
					classe.setContainer(type);
					break;
				}
			}
			for(ParameterizableClass pClass: listPClass){
				if(classe.getContainerID() == pClass.getId()){
					classe.setContainer(pClass);
					break;
				}
			}
			for(Method method: listMethods){
				if(classe.getContainerID() == method.getId()){
					classe.setContainer(method);
					break;
				}
			}
			for(ParameterizedType pType: listParameterized){
				if(classe.getContainerID() == pType.getId()){
					classe.setContainer(pType);
					break;
				}
			}
			for(Namespace namespace: listNamespace){
				if(classe.getContainerID() == namespace.getId()){
					classe.setContainer(namespace);
					break;
				}
			}
			for(Enumm enumm: listEnum){
				if(classe.getContainerID() == enumm.getId()){
					classe.setContainer(enumm);
					break;
				}
			}

		}//END

		//Colocando container em cada Namespace
		for(Namespace namespace: listNamespace){
			for(Namespace namespacee: listNamespace){
				if(namespace.getParentScopeID() == namespacee.getId()){
					namespace.setContainer(namespacee);
					break;
				}
			}
			for(AnnotationType aType: listAType){
				if(namespace.getParentScopeID() == aType.getId()){
					namespace.setContainer(aType);
					break;
				}
			}
			for(Enumm enumm: listEnum){
				if(namespace.getParentScopeID() == enumm.getId()){
					namespace.setContainer(enumm);
					break;
				}
			}

		}//END

		//Colocando container em parameterizableClass	
		for(ParameterizableClass pClass: listPClass){
			for(Class classee: listClasses){
				if(pClass.getContainerID() == classee.getId()){
					pClass.setContainer(classee);
					break;
				}
			}
			for(AnnotationType aType: listAType){
				if(pClass.getContainerID() == aType.getId()){
					pClass.setContainer(aType);
					break;
				}
			}
			for(Type type: listTypes){
				if(pClass.getContainerID() == type.getId()){
					pClass.setContainer(type);
					break;
				}
			}
			for(ParameterizableClass pClasss: listPClass){
				if(pClass.getContainerID() == pClasss.getId()){
					pClass.setContainer(pClasss);
					break;
				}
			}
			for(Method method: listMethods){
				if(pClass.getContainerID() == method.getId()){
					pClass.setContainer(method);
					break;
				}
			}
			for(ParameterizedType pType: listParameterized){
				if(pClass.getContainerID() == pType.getId()){
					pClass.setContainer(pType);
					break;
				}
			}
			for(Namespace namespace: listNamespace){
				if(pClass.getContainerID() == namespace.getId()){
					pClass.setContainer(namespace);
					break;
				}
			}
			for(Enumm enumm: listEnum){
				if(pClass.getContainerID() == enumm.getId()){
					pClass.setContainer(enumm);
					break;
				}
			}

		}//END

		//Container no metodo
		for(Method method: listMethods){
			for(Class classe: listClasses){
				if(method.getParentID() == classe.getId()){
					method.setContainer(classe);
				}
			}

			for(AnnotationType aType: listAType){
				if(method.getParentID() == aType.getId()){
					method.setContainer(aType);
					break;
				}
			}
			for(ParameterizableClass pClass: listPClass){
				if(method.getParentID() == pClass.getId()){
					method.setContainer(pClass);
				}
			}
			for(Method method2: listMethods){
				if(method.getParentID() == method2.getId()){
					method.setContainer(method2);
				}
			}
			for(Type type: listTypes){
				if(method.getParentID() == type.getId()){
					method.setContainer(type);
					break;
				}
			}
			for(Enumm enumm: listEnum){
				if(method.getParentID() == enumm.getId()){
					method.setContainer(enumm);
					break;
				}
			}
		}//END

		//Colocando container no ParameterizedType
		for(ParameterizedType pType: listParameterized){
			for(Class classe: listClasses){
				if(pType.getContainerID() == classe.getId()){
					pType.setContainer(classe);
				}
			}
			for(ParameterizableClass pClass: listPClass){
				if(pType.getContainerID() == pClass.getId()){
					pType.setContainer(pClass);
				}
			}
			for(AnnotationType aType: listAType){
				if(pType.getContainerID() == aType.getId()){
					pType.setContainer(aType);
					break;
				}
			}
			for(Method method: listMethods){
				if(pType.getContainerID() == method.getId()){
					pType.setContainer(method);
				}
			}
			for(ParameterizedType pTypee: listParameterized){
				if(pType.getContainerID() == pTypee.getId()){
					pType.setContainer(pTypee);
				}
			}
			for(Namespace namespace: listNamespace){
				if(pType.getContainerID() == namespace.getId()){
					pType.setContainer(namespace);
				}
			}
			for(Type type: listTypes){
				if(pType.getContainerID() == type.getId()){
					pType.setContainer(type);
					break;
				}
			}
			for(Enumm enumm: listEnum){
				if(pType.getContainerID() == enumm.getId()){
					pType.setContainer(enumm);
					break;
				}
			}

		}//END

		//Colocanod container no type
		for(Type type: listTypes){
			for(Class classe: listClasses){
				if(type.getContainerID() == classe.getId()){
					type.setContainer(classe);
				}
			}
			for(ParameterizableClass pClass: listPClass){
				if(type.getContainerID() == pClass.getId()){
					type.setContainer(pClass);
				}
			}
			for(AnnotationType aType: listAType){
				if(type.getContainerID() == aType.getId()){
					type.setContainer(aType);
					break;
				}
			}
			for(Method method: listMethods){
				if(type.getContainerID() == method.getId()){
					type.setContainer(method);
				}
			}
			for(ParameterizedType pTypee: listParameterized){
				if(type.getContainerID() == pTypee.getId()){
					type.setContainer(pTypee);
				}
			}
			for(Namespace namespace: listNamespace){
				if(type.getContainerID() == namespace.getId()){
					type.setContainer(namespace);
				}
			}
			for(Type typee: listTypes){
				if(type.getContainerID() == typee.getId()){
					type.setContainer(typee);
					break;
				}
			}
			for(Enumm enumm: listEnum){
				if(type.getContainerID() == enumm.getId()){
					type.setContainer(enumm);
					break;
				}
			}
		}

		for(AnnotationType aType: listAType){
			for(Class classee: listClasses){
				if(aType.getContainerID() == classee.getId()){
					aType.setContainer(classee);
					break;
				}
			}
			for(AnnotationType aTypee: listAType){
				if(aType.getContainerID() == aTypee.getId()){
					aType.setContainer(aType);
					break;
				}
			}
			for(Type type: listTypes){
				if(aType.getContainerID() == type.getId()){
					aType.setContainer(type);
					break;
				}
			}
			for(ParameterizableClass pClasss: listPClass){
				if(aType.getContainerID() == pClasss.getId()){
					aType.setContainer(pClasss);
					break;
				}
			}
			for(Method method: listMethods){
				if(aType.getContainerID() == method.getId()){
					aType.setContainer(method);
					break;
				}
			}
			for(ParameterizedType pType: listParameterized){
				if(aType.getContainerID() == pType.getId()){
					aType.setContainer(pType);
					break;
				}
			}
			for(Namespace namespace: listNamespace){
				if(aType.getContainerID() == namespace.getId()){
					aType.setContainer(namespace);
					break;
				}
			}
			for(Enumm enumm: listEnum){
				if(aType.getContainerID() == enumm.getId()){
					aType.setContainer(enumm);
					break;
				}
			}

		}

		//Colocando container em attribute
		for(Attribute attribute: listAttributes){

			for(Class classe: listClasses){
				if(attribute.getParentID() == classe.getId()){
					attribute.setContainer(classe);
				}
			}
			for(ParameterizableClass pClass: listPClass){
				if(attribute.getParentID() == pClass.getId()){
					attribute.setContainer(pClass);
				}
			}
			for(AnnotationType aType: listAType){
				if(attribute.getParentID() == aType.getId()){
					attribute.setContainer(aType);
					break;
				}
			}
			for(Enumm enumm: listEnum){
				if(attribute.getParentID() == enumm.getId()){
					attribute.setContainer(enumm);
					break;
				}
			}
		}



		return listClasses;
	}

	private ArrayList<LocalVariable> putTypeOnLocalVariables(ArrayList<LocalVariable> listLocalVariables,
			ArrayList<Class> listClasses, ArrayList<Type> listTypes, ArrayList<Enumm> listEnum,
			ArrayList<PrimitiveType> listPrimitive, ArrayList<ParameterizedType> listParameterized,
			ArrayList<ParameterizableClass> listPClass, ArrayList<AnnotationType> listAType) {

		for(LocalVariable localVariable: listLocalVariables){
			for(Type type: listTypes){
				if(localVariable.getDeclaredTypeID() == type.getId()){
					localVariable.setType(type);
					break;
				}
			}

			for(ParameterizableClass pClass: listPClass){
				if(localVariable.getDeclaredTypeID() == pClass.getId()){
					localVariable.setType(pClass);
					break;
				}
			}

			for(Class classe: listClasses){
				if(localVariable.getDeclaredTypeID() == classe.getId()){
					localVariable.setType(classe);
					break;

				}
			}
			
			for(AnnotationType aType: listAType){
				if(localVariable.getDeclaredTypeID() == aType.getId()){
					localVariable.setType(aType);
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
					localVariable.setContainer(method);
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
					parameter.setContainer(method);
				}
			}
			method.setParameters(listAux);
		}

		return listMethods;
	}

	private ArrayList<Parameter> putTypeOnParameters(ArrayList<Parameter> listParameters, ArrayList<Class> listClasses, ArrayList<Type> listTypes,
			ArrayList<Enumm> listEnum, ArrayList<PrimitiveType> listPrimitive,
			ArrayList<ParameterizedType> listParameterized, ArrayList<ParameterizableClass> listPClass,
			ArrayList<AnnotationType> listAType) {

		for(Parameter parameter: listParameters){
			for(Type type: listTypes){
				if(parameter.getDeclaredTypeID() == type.getId()){
					parameter.setType(type);
					break;
				}
			}

			for(ParameterizableClass pClass: listPClass){
				if(parameter.getDeclaredTypeID() == pClass.getId()){
					parameter.setType(pClass);
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
			
			for(AnnotationType aType: listAType){
				if(parameter.getDeclaredTypeID() == aType.getId()){
					parameter.setType(aType);
					break;
				}
			}
		}

		return listParameters;
	}

	private ArrayList<Class> doInheritance(ArrayList<Class> listClasses, ArrayList<Inheritance> listInheritance,
			ArrayList<ParameterizableClass> listPClass){
		
		ArrayList<Entity> temp = new ArrayList<Entity>();

		for(Inheritance inheritance: listInheritance){
			for(Class classe: listClasses){
				if(classe.getId() == inheritance.getSubClassID()){
					for(Class classe2 : listClasses){
						if(classe2.getId() == inheritance.getSuperClassID()){
							temp = classe.getSuperClass();
							temp.add(classe2);
							classe.setSuperClass(temp);
						}
					}
					for(ParameterizableClass pClass: listPClass){
						if(pClass.getId() == inheritance.getSuperClassID()){
							temp = classe.getSuperClass();
							temp.add(pClass);
							classe.setSuperClass(temp);
						}
					}
				}
			}



			for(ParameterizableClass pClass: listPClass){
				if(pClass.getId() == inheritance.getSubClassID()){
					for(Class classe2 : listClasses){
						if(classe2.getId() == inheritance.getSuperClassID()){
							temp = pClass.getSuperClass();
							temp.add(classe2);
							pClass.setSuperClass(temp);
						}
					}
					for(ParameterizableClass pClasse: listPClass){
						if(pClasse.getId() == inheritance.getSuperClassID()){
							temp = pClass.getSuperClass();
							temp.add(pClasse);
							pClass.setSuperClass(temp);
						}
					}
				}
			}

		}
		return listClasses;
	}

	private ArrayList<Attribute> recognizeTypesOfAttributes(ArrayList<Class> listClasses, ArrayList<Type> listTypes,
			ArrayList<Enumm> listEnum, ArrayList<PrimitiveType> listPrimitive,
			ArrayList<ParameterizedType> listParameterized, ArrayList<Attribute> listAttributes,
			ArrayList<ParameterizableClass> listPClass) {

		for(Attribute attribute: listAttributes){
			for(Type type: listTypes){
				if(attribute.getDeclaredTypeID() == type.getId()){
					attribute.setType(type);
					break;
				}
			}

			for(ParameterizableClass pClass: listPClass){
				if(attribute.getDeclaredTypeID() == pClass.getId()){
					attribute.setType(pClass);
					break;
				}
			}

			for(Class classe2:	 listClasses){
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

		return listAttributes;
	}

	private ArrayList<Class> relationBetweenClassAndAttribute(ArrayList<Attribute> listAttributes,
			ArrayList<Class> globalClasses) {

		ArrayList<Attribute> listAux = new ArrayList<Attribute>();
		for(Class classe: globalClasses){
			listAux = classe.getAttributes();
			for(Attribute attribute: listAttributes){
				if(attribute.getParentID() == classe.getId()){
					listAux.add(attribute);
					attribute.setContainer(classe);
				}			
			}
			classe.setAttributes(listAux);
		}

		return globalClasses;
	}

	private ArrayList<Method> relationBetweenMethodAndInvocation
	(ArrayList<Method> globalMethods, ArrayList<Invocation> globalInvocations){

		ArrayList<Invocation> listAux = new ArrayList<Invocation>();
		for(Method method: globalMethods){
			listAux = method.getListInvocation();
			for(Invocation invocation: globalInvocations){
				if(invocation.getSenderID() == method.getId()){
					listAux.add(invocation);
					invocation.setContainer(method);
				}

			}
			method.setListInvocation(listAux);
		}

		return globalMethods;
	}

	private ArrayList<Class> relationBetweenClassAndMethod
	(ArrayList<Method> globalMethods, ArrayList<Class> globalClasses){

		ArrayList<Method> listAux = new ArrayList<Method>();
		for(Class classe: globalClasses){
			listAux = classe.getMethods();
			for(Method method: globalMethods){
				if(method.getParentID() == classe.getId()){
					listAux.add(method);
					method.setContainer(classe);
				}

			}

			classe.setMethods(listAux);
		}

		return globalClasses;
	}

	public void createObjects(Map<Integer, FamixEntity> map){

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
		ArrayList<AnnotationType> listAType = new ArrayList<AnnotationType>();

		//entry.getkey() -> ID
		//entry.getValue() -> Entidade
		for (Map.Entry<Integer, FamixEntity> entry : map.entrySet())
		{
			ArrayList<String[]> listAux;		
			if(StringUtils.contains(entry.getValue().getType(), "FAMIX.Method")){
				Method method = new Method();
				method.setId(entry.getKey());
				listAux = entry.getValue().getList();
				for (String[] array : listAux){
					if(StringUtils.contains(array[0], "name")){
						if(array.length == 2){
							method.setName(array[1]);
						}
						else{
							method.setName("''");
						}
					}
					else if(StringUtils.contains(array[0], "signature")){
						method.setSignature(array[1]);
					}
					else if(StringUtils.contains(array[0], "parentType")){
						method.setParentID(Integer.parseInt(array[2]));
					}
					else if(StringUtils.contains(array[0], "declaredType")){
						method.setDeclaredTypeID(Integer.parseInt(array[2]));
					}
					else if(StringUtils.contains(array[0], "modifiers")){
						method.setModifier(array[1]);
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
						classe.setContainerID(Integer.parseInt(array[2]));
					}
					else if(StringUtils.contains(array[0], "modifiers")){
						classe.setModifier(array[1]);
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
						parameterizableClass.setContainerID(Integer.parseInt(array[2]));
					}
					else if(StringUtils.contains(array[0], "modifiers")){
						parameterizableClass.setModifier(array[1]);
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
						invocation.setSenderID(Integer.parseInt(array[2]));
					}
					else if(StringUtils.contains(array[0], "candidates")){
						invocation.setCandidateID(Integer.parseInt(array[2]));
					}
				}
				listInvocations.add(invocation);
			}

			else if(StringUtils.contains(entry.getValue().getType(), "FAMIX.AnnotationType")){
				AnnotationType aType = new AnnotationType();
				aType.setId(entry.getKey());
				listAux = entry.getValue().getList();
				for (String[] array : listAux){
					if(StringUtils.contains(array[0], "name")){
						aType.setName(array[1]);
					}
					else if(StringUtils.contains(array[0], "container")){
						aType.setContainerID(Integer.parseInt(array[2]));
					}
				}
				listAType.add(aType);
			}

			else if(StringUtils.contains(entry.getValue().getType(), "FAMIX.Type")){
				Type type = new Type();
				type.setId(entry.getKey());
				listAux = entry.getValue().getList();
				for (String[] array : listAux){
					if(StringUtils.contains(array[0], "name")){
						type.setName((array[1]));
					}
					else if(StringUtils.contains(array[0], "container")){
						type.setContainerID(Integer.parseInt(array[2]));
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
						attribute.setParentID(Integer.parseInt(array[2]));
					}
					else if(StringUtils.contains(array[0], "modifiers")){
						attribute.setModifier(array[1]);
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
					else if(StringUtils.contains(array[0], "modifiers")){
						enumm.setModifier(array[1]);
					}
					else if(StringUtils.contains(array[0], "container")){
						enumm.setContainerID(Integer.parseInt(array[2]));
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
					else if(StringUtils.contains(array[0], "modifiers")){
						primitive.setModifier(array[1]);
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
						if(array.length == 2){
							parameterized.setName(array[1]);
						}
						else{
							String aux = array[1];
							for(int i=2; i < array.length; i++){
								aux = aux + " "  + array[i];
							}
						}
					}
					else if(StringUtils.contains(array[0], "parameterizableClass")){
						parameterized.setParameterizableClassID(Integer.parseInt(array[2]));
					}
					else if(StringUtils.contains(array[0], "modifiers")){
						parameterized.setModifier(array[1]);
					}
					else if(StringUtils.contains(array[0], "container")){
						parameterized.setContainerID(Integer.parseInt(array[2]));
					}
					else if(StringUtils.contains(array[0], "arguments")){
						ArrayList<Integer> argumentsIDs = new ArrayList<Integer>();
						for(int i =0; i< array.length; i++){
							if(StringUtils.contains(array[i], "ref")){
								argumentsIDs.add(Integer.parseInt(array[i+1]));
							}
						}
						parameterized.setArgumentsIDs(argumentsIDs);
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
					!StringUtils.contains(entry.getValue().getType(), "FAMIX.ParameterizableClass") &&
					!StringUtils.contains(entry.getValue().getType(), "FAMIX.ParameterizedType")) {
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
		//			System.out.println("Lista de Metodos: " + listMethod.size());
		//			System.out.println("Lista de Tipos: " + listType.size());
		//			System.out.println("Lista de Atributos: " + listAttribute.size());
		//			System.out.println("Lista de Invocações: " + listInvocations.size());
		//			System.out.println("Lista de Classes: " + listClasses.size());
		//			System.out.println("Lista de Enum: " + listEnum.size());
		//			System.out.println("Lista de Primitivos: " + listPrimitive.size());
		//			System.out.println("Lista de Tipos Parametrizados: " + listParameterized.size());
		//			System.out.println("Lista de Variaveis Locais: " + listLocalVariables.size());
		//			System.out.println("Lista de Parametros: " + listParameters.size());
		//			System.out.println("Lista de Heranças: " + listInheritance.size());
		//			System.out.println("Lista de NameSpace: " + listNamespace.size());
		//			System.out.println("Lista de Classes parametrizadas: " + listPClass.size());

		relationship(listMethod, listInvocations, listType, listAttribute, listEnum, listPrimitive, listParameterized,
				listLocalVariables, listParameters, listInheritance, listNamespace, listPClass, listAType);

	}

	public ArrayList<Method> findMethods(String classFullName){
		for(Class classe: listClasses){
			if(classe.getFullName().equals(classFullName)){
				return classe.getMethods();
			}
		}
		return null;
	}

	public ArrayList<Attribute> findAttributes(String classFullName){
		for(Class classe: listClasses){
			if(classe.getFullName().equals(classFullName)){
				return classe.getAttributes();
			}
		}
		return null;
	}

	public Attribute findAttribute(String attributeFullName){

		String temp[] = StringUtils.split(attributeFullName, ".");
		String className = temp[temp.length - 2];

		for(Class classe: listClasses){
			if(classe.getName().equals(className)){
				for(Attribute attribute: classe.getAttributes()){
					if(attribute.getFullName().equals(attributeFullName)){
						return attribute;
					}
				}
			}
		}
		return null;
	}

	public Method findMethod(String methodFullName){	
		for(Method method: listMethods){
			if(method.getFullName().equals(methodFullName)){
				return method;
			}
		}			
		return null;
	}		

	public Class findClass(String classFullName){
		for(Class classe: listClasses){
			if(classe.getFullName().equals(classFullName)){
				return classe;
			}
		}
		return null;
	}

	public ParameterizableClass findParameterizableClass(String classFullName){
		for(ParameterizableClass pClass: listPClass){
			if(pClass.getFullName().equals(classFullName)){
				return pClass;
			}
		}
		return null;
	}

	public Set<Entity> findCBO(String classFullName){
		for(Class classe: listClasses){
			if(classe.getFullName().equals(classFullName)){
				return classe.calculateCBO();
			}
		}
		return null;
	}

	public Invocation findInvocation(String invocationFullName){
		for(Invocation invocation: listInvocations){
			if(invocation.getFullName() != null){
				if(invocation.getFullName().equals(invocationFullName)){
					return invocation;
				}
			}
		}		
		return null;
	}

	public ArrayList<Class> getClasses() {
		return listClasses;
	}

	public void setClasses(ArrayList<Class> listClasses) {
		this.listClasses = listClasses;
	}

	public ArrayList<ParameterizableClass> getPClasses() {
		return listPClass;
	}

	public void setPClasses(ArrayList<ParameterizableClass> listPClass) {
		this.listPClass = listPClass;
	}

		public ArrayList<Method> getAllMethods() {
			return listMethods;
		}

		public void setAllMethods(ArrayList<Method> listMethods) {
			this.listMethods = listMethods;
		}

		public ArrayList<Invocation> getInvocations() {
			return listInvocations;
		}

		public void setInvocations(ArrayList<Invocation> listInvocations) {
			this.listInvocations = listInvocations;
		}

		public String getHash() {
			return hash;
		}

		public void setHash(String hash) {
			this.hash = hash;
		}

	}
