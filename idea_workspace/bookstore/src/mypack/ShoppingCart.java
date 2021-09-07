package mypack;
//本购物车类模块是由代码复用的角度出发,将此模块类独立出来,(利用哈希表Items存储订单对象,故哈希表items对应购物车)
import java.util.*;

public class ShoppingCart {
    HashMap items = null;//声明哈希表变量,哈希表对应购物车
    int numberOfItems = 0;//代表购物车中订单对象的数量

    public ShoppingCart() {
        items = new HashMap();//实例化哈希表对象
    }

    //增加一条订单信息,即索引(书号)和索引对应的对象(订单对象),故函数需带有两个形参,即 增加购物车中一条订单
    public synchronized void add(String bookId, BookDetails book) {
        if (items.containsKey(bookId)) {//索引在哈希表中已存在
            ShoppingCartItem scitem = (ShoppingCartItem) items.get(bookId);//通过索引得到订单对象
            scitem.incrementQuantity();//订单对象的购买数量属性+1
        } else {//索引在哈希表中不存在
            ShoppingCartItem newItem = new ShoppingCartItem(book);//实例化新的订单对象
            items.put(bookId, newItem);//给哈希表增加新的记录
        }
        numberOfItems++;//哈希表购物车的总的订单对象数量+1
    }

    //删除一条订单信息,哈希表中已经存在订单,故函数只需带一个索引形参
    public synchronized void remove(String bookId) {
        if (items.containsKey(bookId)) {
            ShoppingCartItem scitem = (ShoppingCartItem) items.get(bookId);//通过索引得到订单记录对象
            scitem.decrementQuantity();//书本订单购买的数量-1
            if (scitem.getQuantity() <= 0) {//当书本购买的订单数<=0时
                //调用 i.remove() 删除一个哈希表记录。
                items.remove(bookId);//删除哈希表购物车中的一条关于这个书的记录
            }
            numberOfItems--;//购物车的订单对象数-1
        }
    }

    public synchronized Collection getItems() {
        //由于哈希表包括key索引和value对象,利用values获取索引表中的所有值对象,即所有订单对象
        return items.values();
    }

    protected void finalize() throws Throwable {
        //清空哈希表购物车
        items.clear();
    }
    //返回购物车中的总的订单对象数目
    public synchronized int getNumberOfItems() {
        return numberOfItems;
    }

    //计算购物车的总金额
    public synchronized double getTotal() {
        double amount = 0.0;
        //遍历购物车来计算总金额
        for (Iterator i = getItems().iterator(); i.hasNext();) {
            //遍历,得到每个订单对象
            ShoppingCartItem item = (ShoppingCartItem) i.next();//由于i.next() 会返回迭代器的下一个元素，并且更新迭代器的状态,故这里for循环不需要++
            BookDetails bookDetails = (BookDetails) item.getItem();//由订单对象得到书本对象
            amount += item.getQuantity() * bookDetails.getPrice();//累计计算总金额
        }
        //返回金额的四舍五入结果
        return roundOff(amount);
    }
    //进行数字的四舍五入结果
    private double roundOff(double x) {
        long val = Math.round(x * 100);
        return val / 100.0;
    }

    //清空购物车
    public synchronized void clear() {
        items.clear();//清空哈希表购物车
        numberOfItems = 0;//更改购物车的订单对象数为0
    }
}