package com.limaz.futuremocking;

public class DependentClass {
	private DependencyClass dependencyClass;

	public DependencyClass getDependencyClass() {
		return dependencyClass;
	}

	public void setDependencyClass(DependencyClass dependencyClass) {
		this.dependencyClass = dependencyClass;
	}

	public DependentClass() {
		dependencyClass = new DependencyClass();
	}

	public int getValue() {
		return dependencyClass.getDependentValue();
	}
}