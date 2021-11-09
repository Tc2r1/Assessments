package com.tc2r1.fiskerassessment

class Sequence {

    /* Write an algorithm that takes as input a string and returns
    *  the substring that represents the longest sequential chain.  Sequences stop at ‘z’ and do not roll over.  So, for instance, ‘xyzab’ is 2 sequences ‘xyz’ and ‘ab.’ If there are more than one occurrence with longest chain return the first occurrence. Only lowercase letters are considered.
    */

    private fun _getLongestSeq(input : String): String {
        val longestSeq: String

        val list = mutableListOf<String>()
        var startInt = 0
        if(!input.contains("z")) {
            return input
        }
        for(i in input.indices) {
            if(input[i] == 'z') {
                list.add(input.substring(startInt, i + 1))
                startInt = i + 1
            } else if(i == input.length - 1) {
                list.add(input.substring(startInt, input.length))
            }
        }
        longestSeq = list.maxByOrNull { it.length }!!

        return longestSeq
    }

    fun getLongestSeqOld(input: String): String {
        return _getLongestSeq(input)
    }
}