package com.limaz.strictmocking.unittests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.limaz.strictmocking.IMockMe;

public class StrinctAndLooseMocking {
	@Test
    public void LooseMocking()
    {
        IMockMe mockedInterface = Mockito.mock(IMockMe.class, Mockito.RETURNS_SMART_NULLS);
        Mockito.stub(mockedInterface.foo(1, 2)).toReturn("called!");
        
        //TODO: Study strict mocking on Mockito. Not worked yet
        Mockito.verifyNoMoreInteractions(Mockito.ignoreStubs(mockedInterface));
        assertEquals(0, mockedInterface.getBar());
    }
}

