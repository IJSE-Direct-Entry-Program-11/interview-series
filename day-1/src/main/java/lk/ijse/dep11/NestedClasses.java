package lk.ijse.dep11;

import java.io.Serializable;

public class NestedClasses {
    static int myStaticField;           // Static Variable

    {
        new StaticNestedClasses();

        int x = 10;

        class MyLocalInnerClass{}

    }

    static class StaticNestedClasses{
    }

    {
        new StaticNestedClasses();
    }
    int myInstanceField;                // Instance Variable
    class RegularInnerClasses{

    }

    static {
        int myLocalVariable;            // Local Variable

        class LocalInnerClass{}

        new LocalInnerClass();
        new LocalInnerClass();
        new LocalInnerClass();
        new LocalInnerClass();
    }

    {
        int myLocalVariable;            // Local Variable

        class LocalInnerClass{}

//        class Customer extends Object{
//            void doSomething(){
//                System.out.println("Do Something");
//            }
//        }
//        new Customer().doSomething();

        new Object();
        new Object(){       // <= class Customer extends Object Anonymous Inner Class
            void doSomething(){
                System.out.println("Do Something");
            }
        }.doSomething();

        class Anon1 implements Serializable{}
        new Serializable(){

        };

        class Anon2 implements MyFun{

            @Override
            public void run() {

            }
        }
        Anon2 anon2 = new Anon2();

        // ===============================================

        MyFun anon2Clone = new MyFun(){

            @Override
            public void run() {
                System.out.println();
            }
        };

        MyFun anon2Clone2 = ()->{
            System.out.println();
        };

        MyFun anon2Clone22 = System.out::println;

        MyFun myFun = ()->{};
        doSomething(()->{});
    }

    static MyFun doSomething(MyFun fun){
        return ()->{};
    }

    @FunctionalInterface
    interface MyFun {
        void run();
    }

    // function MyFun(){}

}


