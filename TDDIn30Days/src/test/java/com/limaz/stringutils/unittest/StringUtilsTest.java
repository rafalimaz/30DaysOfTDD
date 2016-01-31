package com.limaz.stringutils.unittest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.limaz.stringutils.StringUtils;

public class StringUtilsTest
{
	private StringUtils stringUtils;
	
	@Before
	public void setupStringUtils() {
		stringUtils = new StringUtils();
	}
	
    @Test
    public void shouldBeAbleToCountNumberOfLettersInSimpleSentence() {
        assertEquals(2, stringUtils.FindNumberOfOccurences("TDD is awesome!", "e"));
    }
    
    @Test
    public void shouldBeAbleToCountNumberOfLettersInAComplexSentence() {
        assertEquals(5, stringUtils.FindNumberOfOccurences("Once is unique, twice is a coincidence, three times is a pattern.", "n"));
    }
    
    
    @Test (expected = IllegalArgumentException.class)
    public void shouldGetAnArgumentExceptionWhenCharacterToScanForIsLargerThanOneCharacter() {
    	stringUtils.FindNumberOfOccurences("This test should throw an exception", "xx");
    }
}