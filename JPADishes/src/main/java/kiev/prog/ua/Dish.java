package kiev.prog.ua;

import javax.persistence.*;
//Колонки:
//название блюда,
//id,
//его стоимость,
//вес,
//наличие скидки
@Entity
@Table(name="Dishes")
public class Dish implements Comparable<Dish>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="price", nullable = false)
    private Double price;

    @Column(name="weight", nullable = false)
    private Double weight;

    @Column(name="discount")
    private Integer disсount;

    public Dish() {}

    public Dish(String name, Double price, Double weight, Integer disсount) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.disсount = disсount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() { return price; }

    public void setPrice(Double price) { this.price = price; }

    public Double getWeight() { return weight; }

    public void setWeight(Double weight) { this.weight = weight; }

    public Integer getDisсount() { return disсount;}

    public void setDisсount(Integer disсount) { this.disсount = disсount; }

    @Override
    public String toString() {
        String nullDiscount = "0";
        if (disсount != null){
            nullDiscount = ""+disсount;
        }
        return "Dish{" +
                "name = '" + name + '\'' +
                ", price = " + price +
                ", weight = " + weight +
                "kilo, disсount = " + nullDiscount +
                "%}";
    }

    @Override
    public int compareTo(Dish o) {
        if(weight > o.getWeight()){
            return 1;
        }
        if(weight < o.getWeight()){
            return -1;
        }
        return 0;
    }
}
