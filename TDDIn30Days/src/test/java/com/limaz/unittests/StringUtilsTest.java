package com.limaz.unittests;

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
}