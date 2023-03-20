package com.innowise;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Intervals {
    private static final List<Character> NOTES_LIST= Arrays.asList('C', '-', 'D', '-', 'E',  'F', '-','G', '-',  'A', '-', 'B', '-', 'C');
  private static final Map<String, Integer> INTERVALS = Stream.of(
                    new AbstractMap.SimpleEntry<>("m2", 1),
                    new AbstractMap.SimpleEntry<>("M2", 2),
                    new AbstractMap.SimpleEntry<>("m3", 3),
                    new AbstractMap.SimpleEntry<>("M3", 4),
                    new AbstractMap.SimpleEntry<>("P4", 5),
                    new AbstractMap.SimpleEntry<>("P5", 7),
                    new AbstractMap.SimpleEntry<>("m6", 8),
                    new AbstractMap.SimpleEntry<>("M6", 9),
                    new AbstractMap.SimpleEntry<>("m7", 10),
                    new AbstractMap.SimpleEntry<>("M7", 11),
                    new AbstractMap.SimpleEntry<>("P8", 12)
            )
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public static String intervalConstruction(String[] args) {
        validateLengthAndArgs(args);
        String intervalName=args[0];
        String startNoteName=args[1];
        String intervalType ="asc";
        if(args.length==3){
            intervalType=args[2];
        }
        int countOfSemitonesInInterval= INTERVALS.get(intervalName);
        int startNotePosition=getNotePositionInList(startNoteName);
        int secondNotePosition = 0;
        String secondNoteName;
        if(intervalType.equals("asc")){
            secondNotePosition=sumInCircle12(startNotePosition, countOfSemitonesInInterval);
        }
        if(intervalType.equals("dsc")){
            secondNotePosition=sumInCircle12(startNotePosition, -countOfSemitonesInInterval);
        }
        secondNoteName= String.valueOf(NOTES_LIST.get(secondNotePosition));
        if(secondNoteName.equals("-")){
           secondNotePosition--;
            secondNoteName= (NOTES_LIST.get(secondNotePosition))+"#";
        }
        return secondNoteName;
    }

    public static String intervalIdentification(String[] args) {
        validateLengthAndArgs(args);
        String firstNoteName=args[0];
        String secondNoteName=args[1];
        String intervalType ="asc";
        if(args.length==3){
            intervalType=args[2];
        }
        int firstNotePositionInList=getNotePositionInList(firstNoteName);
        int secondNotePositionInList=getNotePositionInList(secondNoteName);
        int countsOfSemitones=0;
        if(intervalType.equals("asc")){
            countsOfSemitones=sumInCircle12(secondNotePositionInList, - firstNotePositionInList);
        }
        if(intervalType.equals("dsc")){
            countsOfSemitones=sumInCircle12(firstNotePositionInList, -secondNotePositionInList);
        }
        return getIntervalNameByCountOfSemitones(countsOfSemitones);
    }

    private static int getNotePositionInList(String noteName) {
        int notePositionInList = NOTES_LIST.indexOf(noteName.charAt(0));
        if (noteName.length()>1) {
            for (int i = 0; i < 3; i++) {
                if(i==noteName.length())
                    break;
                if (noteName.substring(i, i + 1).equals("b")) {
                    notePositionInList--;
                }
                if (noteName.substring(i, i + 1).equals("#")) {
                    notePositionInList++;
                }
            }
        }
        if(notePositionInList>NOTES_LIST.size()){
            notePositionInList-=12;}
        if(notePositionInList<0){
            notePositionInList+=12;
        }
            return notePositionInList;
    }

    private static String getIntervalNameByCountOfSemitones(Integer countOfSemitones){
        return INTERVALS.entrySet().stream()
                .filter(entry -> countOfSemitones.equals(entry.getValue()))
                .findFirst().map(Map.Entry::getKey)
                .orElse(null);
    }

    private static int sumInCircle12(int firstNumber, int secondNumber){
        int result = firstNumber + secondNumber;
        if(result>12)
            result-=12;

        if(result<=0)
            result+=12;
        return result;
    }


    private static void validateLengthAndArgs(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException("Illegal number of elements in input array");
        }
        if (args.length < 2 || args.length > 3) {
            throw new IllegalArgumentException("Illegal number of elements in input array");
        }
        if (args[0] == null || args[1] == null || (args.length == 3 && args[2] == null)) {
            throw new IllegalArgumentException("Null input param");
        }

    }

}
