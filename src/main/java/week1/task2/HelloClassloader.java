package week1.task2;

import java.io.*;
import java.lang.reflect.Method;

public class HelloClassloader extends ClassLoader {

    public static void main(String[] args) {
        try {
            String className = "Hello";
            String methodName = "hello";
            ClassLoader classLoader = new HelloClassloader();
            Class<?> clazz = classLoader.loadClass(className);
            Object obj = clazz.getDeclaredConstructor().newInstance();
            Method method = clazz.getMethod(methodName);
            method.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("name: " + name);
        InputStream inputStream = null;
        try {
            String filePath = HelloClassloader.class.getResource("").getPath();
            filePath = filePath + "Hello.xlass";
            File file = new File(filePath);
            inputStream = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            byte[] bytes = decode(baos.toByteArray());
            return defineClass(name, bytes, 0, bytes.length);
        } catch (FileNotFoundException e) {
            throw new ClassNotFoundException(name, e);
        } catch (IOException e) {
            throw new ClassNotFoundException(name, e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new ClassNotFoundException(name, e);
            }
        }
    }

    private static byte[] decode(byte[] bytes) {
        byte[] bts = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            bts[i] = (byte)(255 - bytes[i]);
        }
        return bts;
    }

}
