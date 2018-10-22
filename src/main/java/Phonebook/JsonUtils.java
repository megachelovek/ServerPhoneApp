package Phonebook;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonUtils {

  /**
   * Метод для получения данных по указанной ссылке
   *
   * @param url - ссылка в виде объекта URL (Uniform Resource Locator)
   * @return содержимое страницы на указанной ссылке в @param url
   */
  public static String parseUrl(URL url) {
    if (url == null) {
      return "";
    }
    StringBuilder stringBuilder = new StringBuilder();
    // открываем соедиение к указанному URL
    // помощью конструкции try-with-resources
    try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {

      String inputLine;
      // построчно считываем результат в объект StringBuilder
      while ((inputLine = in.readLine()) != null) {
        stringBuilder.append(inputLine);
        System.out.println(inputLine);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stringBuilder.toString();
  }

  // создаем объект URL из указанной в параметре строки
  public static URL createUrl(String link) {
    try {
      return new URL(link);
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return null;
    }
  }
}
