
import java.util.*;

public class CSPZebra extends CSP {

    static Set<Object> varCol = new HashSet<Object>(
            Arrays.asList(new String[] { "blue", "green", "ivory", "red", "yellow" }));
    static Set<Object> varDri = new HashSet<Object>(
            Arrays.asList(new String[] { "coffee", "milk", "orange-juice", "tea", "water" }));
    static Set<Object> varNat = new HashSet<Object>(
            Arrays.asList(new String[] { "englishman", "japanese", "norwegian", "spaniard", "ukrainian" }));
    static Set<Object> varPet = new HashSet<Object>(
            Arrays.asList(new String[] { "dog", "fox", "horse", "snails", "zebra" }));
    static Set<Object> varCig = new HashSet<Object>(
            Arrays.asList(new String[] { "chesterfield", "kools", "lucky-strike", "old-gold", "parliament" }));

    public boolean isGood(Object X, Object Y, Object x, Object y) {
        Integer xVal = (Integer) x;
        Integer yVal = (Integer) y;
        //if X is not even mentioned in by the constraints, just return true
        //as nothing can be violated
        if (!C.containsKey(X))
            return true;

        //check to see if there is an arc between X and Y
        //if there isn't an arc, then no constraint, i.e. it is good
        if (!C.get(X).contains(Y))
            return true;

        //Uniqueness constraints
        if (varCol.contains(X) && varCol.contains(Y) && !X.equals(Y) && x.equals(y))
            return false;
        if (varDri.contains(X) && varDri.contains(Y) && !X.equals(Y) && x.equals(y))
            return false;
        if (varNat.contains(X) && varNat.contains(Y) && !X.equals(Y) && x.equals(y))
            return false;
        if (varPet.contains(X) && varPet.contains(Y) && !X.equals(Y) && x.equals(y))
            return false;
        if (varCig.contains(X) && varCig.contains(Y) && !X.equals(Y) && x.equals(y))
            return false;

        //Englishman lives in red house
        if (X.equals("englishman") && Y.equals("red") && !x.equals(y))
            return false;
        //Spaniard owns a dog
        if (X.equals("spaniard") && Y.equals("dog") && !x.equals(y))
            return false;
        //coffee drunk in green house
        if (X.equals("coffee") && Y.equals("green") && !x.equals(y))
            return false;
        //ukrainian drinks tea
        if (X.equals("ukrainian") && Y.equals("tea") && !x.equals(y))
            return false;
        //green house to right of ivory house
        if (X.equals("green") && Y.equals("ivory") && xVal != yVal + 1)
            return false;
        //old-gold smoker owns snails
        if (X.equals("old-gold") && Y.equals("snails") && !x.equals(y))
            return false;
        //kools smoked in yellow house
        if (X.equals("kools") && Y.equals("yellow") && !x.equals(y))
            return false;

        //Milk is drunk in middle house
        //Norwegian lives in first house

        //Chesterfield smoker lives next to fox owner
        if (X.equals("chesterfield") && Y.equals("fox") && xVal != yVal + 1 && xVal != yVal - 1)
            return false;
        //Kools smoker next to horse house
        if (X.equals("kools") && Y.equals("horse") && xVal != yVal + 1 && xVal != yVal - 1)
            return false;
        //Lucky-Strike smoker drinks orange juice
        if (X.equals("lucky-strike") && Y.equals("orange-juice") && !x.equals(y))
            return false;
        //Japanese smokes parliament
        if (X.equals("japanese") && Y.equals("parliament") && !x.equals(y))
            return false;
        //Norwegian lives next to blue house
        if (X.equals("norwegian") && Y.equals("blue") && xVal != yVal + 1 && xVal != yVal - 1)
            return false;
        return true;
    }

    public static void main(String[] args) throws Exception {

        CSPZebra csp = new CSPZebra();
        Integer[] houses = { 1, 2, 3, 4, 5 }; //left most house is 1, then 2, then 3...

        for (Object X : varCol)
            csp.addDomain(X, houses);
        for (Object X : varDri)
            csp.addDomain(X, houses);
        for (Object X : varNat)
            csp.addDomain(X, houses);
        for (Object X : varPet)
            csp.addDomain(X, houses);
        for (Object X : varCig)
            csp.addDomain(X, houses);

        //uniqueness constraints
        for (Object X : varCol)
            for (Object Y : varCol)
                csp.addBidirectionalArc(X, Y);
        for (Object X : varDri)
            for (Object Y : varDri)
                csp.addBidirectionalArc(X, Y);
        for (Object X : varNat)
            for (Object Y : varNat)
                csp.addBidirectionalArc(X, Y);
        for (Object X : varPet)
            for (Object Y : varPet)
                csp.addBidirectionalArc(X, Y);
        for (Object X : varCig)
            for (Object Y : varCig)
                csp.addBidirectionalArc(X, Y);

        //unary constraints
        //Milk is drunk in middle house
        Integer[] house3 = { 3 };
        csp.addDomain("milk", house3);
        //Norwegian lives in first house
        Integer[] house1 = { 1 };
        csp.addDomain("norwegian", house1);

        //binary constraints
        //The Englishman lives in the red house.
        csp.addBidirectionalArc("englishman", "red");
        //The Spaniard owns a dog
        csp.addBidirectionalArc("spaniard", "dog");
        //Coffee is drunk in green house
        csp.addBidirectionalArc("coffee", "green");
        //Ukrainian drinks tea
        csp.addBidirectionalArc("ukrainian", "tea");
        //green house is directly to the right of ivory house
        csp.addBidirectionalArc("green", "ivory");
        //Old-Gold smoker owns snail
        csp.addBidirectionalArc("old-gold", "snails");
        //Kools are smoked in yellow house
        csp.addBidirectionalArc("kools", "yellow");
        //Chesterfield smoker lives next to fox owner
        csp.addBidirectionalArc("chesterfield", "fox");
        //Kools are smoked in house next to horse house
        csp.addBidirectionalArc("kools", "horse");
        //Lucky-Strike smoker drinks orange juice
        csp.addBidirectionalArc("lucky-strike", "orange-juice");
        //Japanese smokes Parliament
        csp.addBidirectionalArc("japanese", "parliament");
        //Norwegian lives next to blue house
        csp.addBidirectionalArc("norwegian", "blue");

        Search search = new Search(csp);
        System.out.println(search.BacktrackingSearch());
    }
}
