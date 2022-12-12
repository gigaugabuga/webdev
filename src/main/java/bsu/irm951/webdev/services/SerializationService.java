package bsu.irm951.webdev.services;

import bsu.irm951.webdev.models.Book;
import org.springframework.stereotype.Service;

import java.io.*;


@Service
public class SerializationService {

    public String serialize() throws IOException {
        Book book = new Book();
        String fullPath = "C:\\Users\\yard7\\IdeaProjects\\webdev\\src\\main\\resources\\Uploads\\serializeto.txt";
        File file = new File(fullPath);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(book);
        objectOutputStream.flush();
        objectOutputStream.close();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String st;
        String result = "";
        while ((st = bufferedReader.readLine()) != null){
            result += st;
        }

        return result;
    }

    public String deserialize() throws IOException, ClassNotFoundException {
        Book book = null;
        String fullPath = "C:\\Users\\yard7\\IdeaProjects\\webdev\\src\\main\\resources\\Uploads\\serializeto.txt";
        FileInputStream fileInputStream = new FileInputStream(fullPath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        book = (Book) objectInputStream.readObject();
        objectInputStream.close();

        return book.title;
    }

}
