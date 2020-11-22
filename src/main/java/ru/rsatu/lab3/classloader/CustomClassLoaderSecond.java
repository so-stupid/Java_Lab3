package ru.rsatu.lab3.classloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class CustomClassLoaderSecond extends ClassLoader {
    /**
     * Описание поля.
     */
    private URL appUrl;

    /**
     * Конструктор с параметром. Параметр содержит URL до main-класса приложения.
     */
    public CustomClassLoaderSecond(URL appUrl) {
        this.appUrl = appUrl;
    }

    /**
     * Метод для загрузки класса.
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if ((!"ru.rsatu.lab3.second.Second".equals(name))) {
            return super.loadClass(name);
        }

        try {
            String relativePath = "second/Second.class";
            URL myUrl = new URL(appUrl, relativePath);
            URLConnection connection = myUrl.openConnection();
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int data = input.read();
            while(data != -1){
                buffer.write(data);
                data = input.read();
            }
            input.close();

            byte[] classData = buffer.toByteArray();

            return defineClass("ru.rsatu.lab3.second.Second", classData, 0, classData.length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
