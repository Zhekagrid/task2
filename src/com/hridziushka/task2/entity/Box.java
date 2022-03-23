package com.hridziushka.task2.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Box {
    static Logger logger = LogManager.getLogger();
    private BoxType boxType;
    private int count;
    private double price;

    public Box(BoxType boxType, int count, double price) {
        this.boxType = boxType;
        this.count = count;
        this.price = price;
        logger.log(Level.INFO, "Box was created");
    }

    public Box() {
        logger.log(Level.INFO, "Box was created");

    }

    public BoxType getBoxType() {
        return boxType;
    }

    public void setBoxType(BoxType boxType) {
        this.boxType = boxType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Box{");
        sb.append("boxType=").append(boxType);
        sb.append(", count=").append(count);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Box)) return false;

        Box box = (Box) o;

        if (getCount() != box.getCount()) return false;
        if (Double.compare(box.getPrice(), getPrice()) != 0) return false;
        return getBoxType() == box.getBoxType();
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getBoxType() != null ? getBoxType().hashCode() : 0;
        result = 31 * result + getCount();
        temp = Double.doubleToLongBits(getPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
