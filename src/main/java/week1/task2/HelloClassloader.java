package week1.task2;

public class HelloClassloader extends ClassLoader{

    public static void main(String[] args) {
        String filePath = HelloClassloader.class.getResource("/").getPath();
        System.out.println(filePath);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        throw new ClassNotFoundException(name);
    }

}
