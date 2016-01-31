package com.limaz.unittest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.limaz.utils.StringUtils;

public class StringUtilsTest
{
    @Test
    public void shouldBeAbleToCountNumberOfLettersInSimpleSentence()
    {
        String sentenceToScan = "TDD is awesome!";
        String characterToScanFor = "e";
        int expectedResult = 2;
        StringUtils stringUtils = new StringUtils();

        int result = stringUtils.FindNumberOfOccurences(sentenceToScan, characterToScanFor);

        assertEquals(expectedResult, result);
    }
    
    @Test
    public void ShouldBeAbleToCountNumberOfLettersInAComplexSentence()
    {
        String sentenceToScan = "Once is unique, twice is a coincidence, three times is a pattern.";
        String characterToScanFor = "n";
        int expectedResult = 5;
        StringUtils stringUtils = new StringUtils();

        int result = stringUtils.FindNumberOfOccurences(sentenceToScan, characterToScanFor);

        assertEquals(expectedResult, result);
    }
}