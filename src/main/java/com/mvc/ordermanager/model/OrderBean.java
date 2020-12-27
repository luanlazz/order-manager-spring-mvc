package com.mvc.ordermanager.model;

import com.mvc.ordermanager.resource.avro.Order;
import com.mvc.ordermanager.resource.avro.OrderState;
import com.mvc.ordermanager.resource.avro.Product;

import java.util.Objects;

public class OrderBean {

    private String id;
    private long customerId;
    private OrderState state;
    private Product product;
    private int quantity;
    private double price;

    public OrderBean() { }

    public OrderBean(final String id, final long customerId, final OrderState state, final Product product,
                     final int quantity, final double price) {
        this.id = id;
        this.customerId = customerId;
        this.state = state;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public void setId(final String id) { this.id = id; }
    public String getId() {
        return id;
    }

    public void setCustomerId(final long customerId) { this.customerId = customerId; }

    public long getcustomerId() {
        return customerId;
    }

    public void setState(final OrderState state) {
        this.state = state;
    }

    public OrderState getState() {
        return state;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o){
            return true;
        } else if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        final OrderBean orderBean = (OrderBean) o;

        if (this.customerId != orderBean.customerId) {
            return false;
        }
        if (this.quantity != orderBean.quantity) {
            return false;
        }
        if (Double.compare(this.price, orderBean.price) != 0) {
            return false;
        }
        if (!Objects.equals(this.id, orderBean.id)) {
            return false;
        }
        if (this.state != orderBean.state) {
            return false;
        }
        return this.product == orderBean.product;
    }

    @Override
    public String toString() {
        return "OrderBean {" +
                "id='" + id + '\'' +
                ", customerId=" + customerId +
                ", state=" + state +
                ", product=" + product +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

    @Override
    public int hashCode() {
        int result;
        final long temp;
        result = this.id != null ? this.id.hashCode() : 0;
        result = 31 * result + (int) (this.customerId ^ this.customerId >>> 32);
        result = 31 * result + (this.state != null ? this.state.hashCode() : 0);
        result = 31 * result + (this.product != null ? this.product.hashCode() : 0);
        result = 31 * result + this.quantity;
        temp = Double.doubleToLongBits(this.price);
        result = 31 * result + (int) (temp ^ temp >>> 32);
        return result;
    }

    public static OrderBean toBean(final Order order) {
        return new OrderBean((String) order.getId(),
                order.getCustomerId(),
                order.getState(),
                order.getProduct(),
                order.getQuantity(),
                order.getPrice());
    }

    public static Order fromBean(final OrderBean order) {
        return new Order(order.getId(),
                order.getcustomerId(),
                order.getState(),
                order.getProduct(),
                order.getQuantity(),
                order.getPrice());
    }
}
