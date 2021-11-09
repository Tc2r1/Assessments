package com.tc2r1.fiskerassessment

import org.junit.Test

import org.junit.Assert.*

class SequenceTest {
    //Sequences stop at ‘z’ and do not roll over.  So, for instance, ‘xyzab’ is 2 sequences ‘xyz’ and ‘ab.’ If there are more than one occurrence with longest chain return the first occurrence. Only lowercase letters are considered.
    private val listOfStrings = mutableListOf<String>("aa", "abbabcabc", "xyzabxyz", "abcdefxyzabcdef", "zzzzzzzzzzzzz", "catZzaa")

    @Test
    fun getLongestSeq_returnsEntireStringNoOccurancesOf_Z() {
        val sequence = Sequence()

        var result = sequence.getLongestSeqOld(listOfStrings[0])

        assertEquals(listOfStrings[0], result)

        result = sequence.getLongestSeqOld(listOfStrings[1])

        assertEquals(listOfStrings[1], result)
    }

    @Test
    fun getLongestSeq_returnsLongestSequence() {
        val sequence = Sequence()


        var result = sequence.getLongestSeqOld(listOfStrings[2])


        assertEquals("abxyz", result)

        result = sequence.getLongestSeqOld(listOfStrings[3])


        assertEquals("abcdefxyz", result)
    }

    @Test
    fun getLongestSeq_returnsFirst_IfMoreThanOne() {
        val sequence = Sequence()


        val result = sequence.getLongestSeqOld("catzdogzpizzpiez")

        assertEquals("catz", result)
    }

    @Test
    fun getLongestSeq_returnsEntireStringIf_NoZ() {
        val sequence = Sequence()
        var uselessString = "Iliketogotothepark"

        val result = sequence.getLongestSeqOld(uselessString)

        assertEquals(uselessString, result)
    }

    @Test
    fun getLongestSeq_returns_OnlyLowerCase() {
        val sequence = Sequence()

        val result = sequence.getLongestSeqOld(listOfStrings[5])

        assertEquals("catZz", result)
    }

    @Test
    fun getLongestSeq_returns_handlesRepeated_z() {
        val sequence = Sequence()

        val result = sequence.getLongestSeqOld(listOfStrings[4])

        assertEquals("z", result)
    }
}