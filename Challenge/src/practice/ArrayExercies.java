package practice;

import java.util.Arrays;
import java.util.Comparator;

public class ArrayExercies {
    public static void main(String[] args) {
        Book[] books = new Book[4];
        books[0] = new Book("红楼梦",100);
        books[1] = new Book("金瓶梅新",90);
        books[2] = new Book("青年文稿20年",5);
        books[3] = new Book("java从入门到放弃",300);
        System.out.println("从大到小=======");
        Arrays.sort(books, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Book book1 = (Book)o1;
                Book book2 = (Book)o2;

                return book2.getPrice()-book1.getPrice();
            }
        }
        );
        for (int i = 0; i < books.length; i++) {
            System.out.println(books[i].toString());
        }
        System.out.println("从小到大=======");

        Arrays.sort(books, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Book book1 = (Book)o1;
                        Book book2 = (Book)o2;

                        return book1.getPrice()-book2.getPrice();
                    }
                }
        );
        for (int i = 0; i < books.length; i++) {
            System.out.println(books[i].toString());
        }

        System.out.println("从名字大到小=======");

        Arrays.sort(books, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Book book1 = (Book)o1;
                        Book book2 = (Book)o2;

                        return book2.name.length()-book1.name.length();
                    }
                }
        );
        for (int i = 0; i < books.length; i++) {
            System.out.println(books[i].toString());
        }


    }

}

class Book{
    String name;
    int price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Book(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
