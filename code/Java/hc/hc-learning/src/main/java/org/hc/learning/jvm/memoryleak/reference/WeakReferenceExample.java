package org.hc.learning.jvm.memoryleak.reference;

import org.hc.learning.jvm.memoryleak.bean.Person;

import java.lang.ref.WeakReference;

/**
 * 弱引用示例
 */
public class WeakReferenceExample {
    public static void main(String[] args) {
        Person person = new Person("Hou", "Yamy", 23);
        // 当一个对象仅仅被weak reference指向, 而没有任何其他strong reference指向的时候, 如果GC运行, 那么这个对象就会被回收。
        WeakReference<Person> personWeakReference = new WeakReference<Person>(person);

        Person personClone = person;
        int count = 0;
        while (true) {
            if(personWeakReference.get() != null) {
                count++;
                System.out.println("person still be alive");
            } else {
                System.out.println("person was collected");
                break;
            }
        }
        // System.out.println(personClone.getName() + " is a cloneMan");
    }
}
