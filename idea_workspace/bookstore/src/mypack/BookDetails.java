package mypack;

//书对象,作为宾语,宾语要有一个javabean类,即此类包含了宾语对象 即:书的各种私有信息和提供给外界访问私有信息的公有方法
public class BookDetails implements Comparable {
    private String bookId = null;
    private String title = null;
    private String name = null;
    private float price = 0.0F;
    private int year = 0;
    private String description = null;
    private int saleAmount = 0;

    //构造方法初始化 由实例化对象传进来的参数信息
    public BookDetails(String bookId, String title, String name, float price, int year, String description,
            int saleAmount) {
        this.bookId = bookId;
        this.title = title;
        this.name = name;
        this.price = price;
        this.year = year;
        this.description = description;
        this.saleAmount = saleAmount;
    }

    public String getTitle() {
        return this.title;
    }

    public float getPrice() {
        return this.price;
    }

    public int getYear() {
        return this.year;
    }

    public String getDescription() {
        return this.description;
    }

    public String getBookId() {
        return this.bookId;
    }

    public String getName() {
        return this.name;
    }

    public int getSaleAmount() {
        return this.saleAmount;
    }

    //对实现的接口类的方法的实现
    public int compareTo(Object o) {     //由于Object类在java中是所有类的父类
        BookDetails n = (BookDetails) o;//存在继承关系的对象的转换,向下转换
        int lastCmp = title.compareTo(n.title);//参数返回结果,0等于,-1是小于,1是大于
        return lastCmp;
    }

}