package mypack;
//每条订单信息的 独立模块类

public class ShoppingCartItem {
    Object item;//代表每条订单的对象
    int quantity;//对应购买的数量

    //构造方法实例化对象,传入购买的对象作为参数
    public ShoppingCartItem(Object anItem) {
        item = anItem;
        quantity = 1;//每次购买一件
    }

    public void incrementQuantity() {
        quantity++;//购买的数量+1
    }

    public void decrementQuantity() {
        quantity--;//购买的数量-1
    }

    public Object getItem() {
        return item;//返回订单对象
    }

    public int getQuantity() {
        return quantity;//得到此种书的总的购买的数量
    }
}