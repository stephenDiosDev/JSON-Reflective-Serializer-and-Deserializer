package program;

/**
 * Creates an object based off of user input and 5 premade objects. User can specify the type, amount and values
 * for any primitive in the premade object. Once the user is finished, they can specify to send the object
 * which will enable the sender and serializer functionality.
 */

//below are the 5 object types described on page 3 of the assignment description in order

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * A class with only primitives as its instance variables
  */
class AllPrimitive {
    private int a;
    private double b;
    private boolean c;

    AllPrimitive() {
        setA(5);
        setB(1.24);
        setC(false);
    }

    AllPrimitive(int i, double j, boolean k) {
        setA(i);
        setB(j);
        setC(k);
    }

    //copy constructor
    AllPrimitive(AllPrimitive object) {
        setA(object.getA());
        setB(object.getB());
        setC(object.isC());
    }

    public int getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public boolean isC() {
        return c;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setC(boolean c) {
        this.c = c;
    }

    public String toString() {
        String result = "[a: " + Integer.toString(getA()) + "] [b: " + Double.toString(getB()) + "] [c: " + Boolean.toString(isC()) + "]";
        return result;
    }
}

/**
 * A class that has instances of AllPrimitive and ArrayPrimitives
 */
class ComplexWithReferences {
    private AllPrimitive obj1;
    private AllPrimitive obj2;

    private ArrayPrimitives arr1;

    public ComplexWithReferences() {
        obj1 = new AllPrimitive(1,2,true);
        obj2 = new AllPrimitive(2228, 102947.9754, false);
        arr1 = new ArrayPrimitives(new int[] {9,8,7,6,5,4,3,2,1});
    }

    public ComplexWithReferences(AllPrimitive obj1, AllPrimitive obj2, ArrayPrimitives arr1) {
        this.setObj1(obj1);
        this.setObj2(obj2);
        this.setArr1(arr1);
    }

    public ComplexWithReferences(ComplexWithReferences object) {
        this.setObj1(object.getObj1());
        this.setObj2(object.getObj2());
        this.setArr1(object.getArr1());
    }

    public AllPrimitive getObj1() {
        return obj1;
    }

    public AllPrimitive getObj2() {
        return obj2;
    }

    public ArrayPrimitives getArr1() {
        return arr1;
    }

    public void setObj1(AllPrimitive obj1) {
        this.obj1 = obj1;
    }

    public void setObj2(AllPrimitive obj2) {
        this.obj2 = obj2;
    }

    public void setArr1(ArrayPrimitives arr1) {
        this.arr1 = arr1;
    }

    public String toString() {
        String result = getObj1().toString() + "\n" + getObj2().toString() + "\n" + getArr1().toString();
        return result;
    }
}

/**
 * A class with an int array
 */
class ArrayPrimitives {
    private int myArr[];

    public ArrayPrimitives() {
        setMyArr(new int[]{1, 2, 3, 4, 5, 6});
    }

    public ArrayPrimitives(int[] input) {
        setMyArr(input);
    }

    //copy constructor
    public ArrayPrimitives(ArrayPrimitives object) {
        setMyArr(object.getMyArr());
    }

    public int[] getMyArr() {
        return myArr;
    }

    public void setMyArr(int[] input) {
        myArr = new int[input.length];
        for(int i = 0; i < input.length; i++) {
            myArr[i] = input[i];
        }
    }

    public String toString() {
        String result = "\n";

        if(myArr.length == 0)
            return "No elements";

        for(int i = 0; i < myArr.length; i++) {
            result += "[Index: " + i + "]: " + Integer.toString(myArr[i]) + "\n";
        }

        return result;
    }
}

class ArrayReferences {
    private Object[] myArr;

    public ArrayReferences() {
        setMyArr(new Object[] {new AllPrimitive(1,2.2, true), new AllPrimitive(6, 7.89, false)});
    }

    public ArrayReferences(Object[] inputArr) {
        setMyArr(inputArr);
    }

    public ArrayReferences(ArrayReferences object) {
        this.setMyArr(object.getMyArr());
    }

    public Object[] getMyArr() {
        return myArr;
    }

    public void setMyArr(Object[] myArr) {
        this.myArr = myArr;
    }

    public String toString() {
        String result = "";

        /*
        The following if statement that checks if an array contains all null values
        was taken from the following link under "Check Array Null Using Java 8"
        https://www.delftstack.com/howto/java/how-to-check-whether-an-array-is-null-empty/#check-array-null-using-java-8
         */
        //checks if the entire array is null values
        if(Arrays.stream(getMyArr()).allMatch(Objects::isNull))
            return "All elements null";

        for(int i = 0; i < myArr.length; i++) {
            if(myArr[i] instanceof AllPrimitive) {
                AllPrimitive a = (AllPrimitive) myArr[i];
                result += a.toString() + "\n";
            }
            else if(myArr[i] instanceof ComplexWithReferences) {
                ComplexWithReferences a = (ComplexWithReferences) myArr[i];
                result += a.toString() + "\n";
            }
            else if(myArr[i] instanceof ArrayPrimitives) {
                ArrayPrimitives a = (ArrayPrimitives) myArr[i];
                result += a.toString() + "\n";
            }
            else if(myArr[i] instanceof ArrayReferences) {
                ArrayReferences a = (ArrayReferences) myArr[i];
                result += a.toString() + "\n";
            }
            else if(myArr[i] instanceof InstanceJavaCollection) {
                InstanceJavaCollection a = (InstanceJavaCollection) myArr[i];
                result += a.toString() + "\n";
            }
        }

        return result;
    }
}

class InstanceJavaCollection {
    private ArrayList<Object> list;

    private AllPrimitive a;
    private AllPrimitive b;
    private AllPrimitive c;
    private ArrayPrimitives arr;

    public InstanceJavaCollection() {
        setA(new AllPrimitive(1,2,true));
        setB(new AllPrimitive(-1, -2, false));
        setC(new AllPrimitive(3, 5069.8, true));
        setArr(new ArrayPrimitives(new int[] {1,3,5,7,11,13,17,19}));
        list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(arr);
    }

    public InstanceJavaCollection(AllPrimitive a, AllPrimitive b, AllPrimitive c, ArrayPrimitives arr) {
        setA(a);
        setB(b);
        setC(c);
        setArr(arr);
        list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(arr);
    }

    public AllPrimitive getA() {
        return a;
    }

    public AllPrimitive getB() {
        return b;
    }

    public AllPrimitive getC() {
        return c;
    }

    public ArrayPrimitives getArr() {
        return arr;
    }

    public ArrayList<Object> getList() {
        return list;
    }

    public void setA(AllPrimitive a) {
        this.a = a;
    }

    public void setB(AllPrimitive b) {
        this.b = b;
    }

    public void setC(AllPrimitive c) {
        this.c = c;
    }

    public void setArr(ArrayPrimitives arr) {
        this.arr = arr;
    }

    public void setList(ArrayList<Object> list) {
        this.list = list;
    }

    public String toString() {
        String result = "";
        for(Object ob : list) {
            if(ob instanceof AllPrimitive || ob instanceof ArrayPrimitives)
                result += ob.toString() + "\n";
        }
        return result;
    }
}

public class ObjectCreator {

    public AllPrimitive createAllPrimitive(int a, double b, boolean c) {
        return new AllPrimitive(a, b, c);
    }

    public ArrayPrimitives createArrayPrimitives(int[] inputArray) {
        return new ArrayPrimitives(inputArray);
    }

    public ComplexWithReferences createComplexWithReferences(AllPrimitive a, AllPrimitive b, ArrayPrimitives c) {
        return new ComplexWithReferences(a, b, c);
    }

    public ArrayReferences createArrayReferences(Object[] input) {
        return new ArrayReferences(input);
    }

    public InstanceJavaCollection createInstanceJavaCollection(AllPrimitive a, AllPrimitive b, AllPrimitive c, ArrayPrimitives arr) {
        return new InstanceJavaCollection(a,b,c,arr);
    }

}