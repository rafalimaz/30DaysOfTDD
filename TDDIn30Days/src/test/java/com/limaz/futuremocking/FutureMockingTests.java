package com.limaz.futuremocking;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

public class FutureMockingTests {
	@Test
    public void FutureTest() throws Exception
    {
		//Arrange
        DependencyClass expectedDependency = Mockito.mock(DependencyClass.class);
        
        Mockito.when(expectedDependency.getDependentValue()).thenReturn(5);
        
        //Act
        DependentClass classUnderTest = new DependentClass();
        
        //Need to add the setDepencencyClass in order to work with mock
        classUnderTest.setDependencyClass(expectedDependency);

        //Assert
        assertEquals(5, classUnderTest.getValue());
        Mockito.verify(expectedDependency, Mockito.times(1)).getDependentValue();
    }
}
