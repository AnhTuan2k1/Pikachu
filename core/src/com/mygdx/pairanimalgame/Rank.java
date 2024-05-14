package com.mygdx.pairanimalgame;

import java.util.Objects;

public class Rank {
    static class Rank1{
        static final String name = "CLASSIC";
        static final int maxLevel = 3;
        static final int seconds = 240;
    }
    static class Rank2{
        static final String name = "HIGH TEMPLAR";
        static final int maxLevel = 7;
        static final int seconds = 300;
    }
    static class Rank3{
        static final String name = "CHALLENGE";
        static final int maxLevel = 13;
        static final int seconds = 360;
    }
    static class Rank4{
        static final String name = "ADVANCED";
        static final int maxLevel = 17;
        static final int seconds = 420;
    }

    static int remainSeconds(String rankName){
        if(Objects.equals(rankName, Rank.Rank1.name))
            return Rank.Rank1.seconds;
        else if(Objects.equals(rankName, Rank.Rank2.name))
            return Rank.Rank2.seconds;
        else if(Objects.equals(rankName, Rank.Rank3.name))
            return Rank.Rank3.seconds;
        else if(Objects.equals(rankName, Rank.Rank4.name))
            return Rank.Rank4.seconds;
        return 10;
    }

    static int maxLevel(String rankName){
        if(Objects.equals(rankName, Rank.Rank1.name))
            return Rank1.maxLevel;
        else if(Objects.equals(rankName, Rank.Rank2.name))
            return Rank2.maxLevel;
        else if(Objects.equals(rankName, Rank.Rank3.name))
            return Rank3.maxLevel;
        else if(Objects.equals(rankName, Rank.Rank4.name))
            return Rank4.maxLevel;
        return 10;
    }

    static int rankName(String rankName) {
        if(Objects.equals(rankName, Rank.Rank1.name))
            return 1;
        else if(Objects.equals(rankName, Rank.Rank2.name))
            return 3;
        else if(Objects.equals(rankName, Rank.Rank3.name))
            return 5;
        else if(Objects.equals(rankName, Rank.Rank4.name))
            return 7;
        return 0;
    }

    static String getNextRank(String rankName) {
        if(Objects.equals(rankName, Rank.Rank1.name))
            return Rank.Rank2.name;
        else if(Objects.equals(rankName, Rank.Rank2.name))
            return Rank.Rank3.name;
        else if(Objects.equals(rankName, Rank.Rank3.name))
            return Rank.Rank4.name;
        else if(Objects.equals(rankName, Rank.Rank4.name))
            return Rank.Rank4.name;
        return Rank.Rank1.name;
    }
}
