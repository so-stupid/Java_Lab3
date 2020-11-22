package ru.rsatu.lab3;
import ru.rsatu.lab3.classloader.CustomClassLoaderFirst;
import ru.rsatu.lab3.classloader.CustomClassLoaderSecond;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;

public class App
{
    /**
     * Имена классов для трансляции их в путь.
     */
    private static final String FIRST_CLASS_NAME = "ru.rsatu.lab3.first.First";
    private static final String SECOND_CLASS_NAME = "ru.rsatu.lab3.second.Second";

    /**
     * Точка входа.
     */
    public static void main( String[] args ) {
        URL urlToApp = App.class.getClassLoader().getResource("ru/rsatu/lab3/App.class");
        CustomClassLoaderFirst firstClassLoader = new CustomClassLoaderFirst(urlToApp);
        CustomClassLoaderSecond secondClassLoader = new CustomClassLoaderSecond(urlToApp);
        try {
            workWithFirst(firstClassLoader);
            workWithSecond(secondClassLoader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Загрузка класса 1.
     */
    private static void workWithFirst(CustomClassLoaderFirst classLoader) throws Exception {
        System.out.println("===================== Работаем с классом First =====================");
        Class clazz1 = classLoader.loadClass(FIRST_CLASS_NAME);
        getClassInfo(clazz1);
        Object object1 = clazz1.newInstance();

        System.out.println("Попытка вызова метода getFirstString:");
        Class[] cArg1 = new Class[1];
        cArg1[0] = String.class;
        Method setMethod = object1.getClass().getDeclaredMethod("setFirstString", cArg1);

        System.out.println(setMethod.invoke(object1, "Да, это так."));
        System.out.println("Попытка вызова метода setFirstString с аргументом 'Да, это так.':");

        System.out.println("Попытка вызова метода getFirstString:");
        Method getMethod = object1.getClass().getDeclaredMethod("getFirstString");
        System.out.println(getMethod.invoke(object1));
        System.out.println();
    }

    /**
     * Загрузка класса 2.
     */
    private static void workWithSecond(CustomClassLoaderSecond classLoader) throws Exception {
        System.out.println("===================== Работаем с классом Second =====================");
        Class clazz2 = classLoader.loadClass(SECOND_CLASS_NAME);
        getClassInfo(clazz2);
        Object object2 = clazz2.newInstance();

        System.out.println("Попытка вызова метода getSecondString:");
        Method callClass1GetMethod = object2.getClass().getDeclaredMethod("getSecondString");
        System.out.println(callClass1GetMethod.invoke(object2));
        System.out.println("Попытка вызова метода сравнения:");
        Class[] cArg2 = new Class[1];
        cArg2[0] = clazz2.getDeclaredFields()[0].getType();
        Method isClass1ObjEqualsMethod = object2.getClass().getDeclaredMethod("isClass1ObjEquals", cArg2);
        System.out.println(isClass1ObjEqualsMethod.invoke(
                object2,
                classLoader.loadClass(FIRST_CLASS_NAME).newInstance()
        ));
    }

    /**
     * Вывод информации о переданном классе. Выводится на экран следующая информация:
     * 1. Класслоадер, которым был загружен класс.
     * 2. Список полей класса с указанием их типа.
     * 3. Список конструкторов с указанием типов принимаемых параметров.
     * 4. Список методов с указанием типа возвращаемого значения и типов принимаемых параметров.
     * 5. Иерархия родителей класса, начиная с него самого.
     */
    private static void getClassInfo(Class clazz) {
        System.out.print("При загрузке класса был использован загрузчик: ");
        System.out.println(clazz.getClassLoader());

        System.out.print("Поля класса: ");
        for (Field field : clazz.getDeclaredFields()) {
            System.out.println(field.getType().getSimpleName() + " " + field.getName());
        }

        System.out.println("Конструкторы класса: ");
        for (Constructor constructor : clazz.getConstructors()) {
            System.out.println(
                    "   "+ getMethodInfo(null, constructor.getName(), constructor.getParameterTypes())
            );
        }

        System.out.println("Методы класса: ");
        for (Method method : clazz.getDeclaredMethods()) {
            System.out.println(
                    "   "+ getMethodInfo(method.getReturnType().getSimpleName(), method.getName(), method.getParameterTypes())
            );
        }

        System.out.println("Иерархия классов: ");
        while (clazz != null) {
            System.out.println("  "+clazz.getName());
            clazz = clazz.getSuperclass();
        }
    }

    /**
     * Формирует строку, содержащую информацию о методе: тип возвращаемого значения, имя метода и список
     * его параметоров в скобках через запятую.
     * @param methodType - имя типа возвращаемого значения
     * @param methodName - имя метода
     * @param parameters - список параметров
     */
    private static StringBuilder getMethodInfo(String methodType, String methodName, Class[] parameters) {
        StringBuilder methodInfo = new StringBuilder( (methodType!=null) ? methodType+" " : "" );
        methodInfo.append(methodName).append(" ( ");
        for (int i=0; i<parameters.length; i++) {
            methodInfo.append(parameters[i].getSimpleName()).append(" value");
            if (i<parameters.length-1) {
                methodInfo.append(',');
            }
            methodInfo.append(' ');
        }
        methodInfo.append(')');
        return methodInfo;
    }
}
