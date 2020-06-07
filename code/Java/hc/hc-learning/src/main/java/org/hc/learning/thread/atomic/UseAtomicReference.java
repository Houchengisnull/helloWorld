package org.hc.learning.thread.atomic;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

public class UseAtomicReference {

    static AtomicReference<UserInfo> atomicUserRef;
    public static void main(String[] args) {
        UserInfo houc = new UserInfo("Houc", 24);
        atomicUserRef = new AtomicReference<>(houc);
        // 20200607 与阿龟在星巴克喝咖啡
        UserInfo yang = new UserInfo("Yang", 25);
        atomicUserRef.compareAndSet(houc, yang);
        System.out.println(atomicUserRef.get());
        // 不改变原对象
        System.out.println(houc);

        atomicUserRef.updateAndGet(new UnaryOperator<UserInfo>() {
                @Override
                public UserInfo apply(UserInfo userInfo) {
                   return new UserInfo("H", 12);
                }
        });
        System.out.println(atomicUserRef);
    }

    static class UserInfo {
        private String name;
        private int age;
        public UserInfo(String name, int age){
            this.name = name;
            this.age = age;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}

