package kiev.prog.ua;

import javax.persistence.*;

//Колонки:
//id
//Откуда,
//Куда,
//Соотношение1,
//Соотношение2,

@Entity
@Table(name="ExchangeRates")
public class ExchangeRate{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    private String first;

    private String second;

    @Column(name="relation", nullable = false)
    private Double relation;

    public ExchangeRate() {}

    public ExchangeRate(String first, String second, Double relation) {
        this.first = first;
        this.second = second;
        this.relation = relation;
    }

    public Double calculate(Double Money) throws NullPointerException{
        if(Money == null){
            throw new NullPointerException();
        }
        if (Money <= 0.0){
            return 0.0;
        }
        Money *= relation;
        return (double)Math.round(Money * 100d) / 100d;
    }

    public long getId() {
        return id;
    }

    public void setId(long er_name) {
        this.id = er_name;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String er_first) {
        this.first = er_first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String er_second) {
        this.second = er_second;
    }

    public Double getRelation() {
        return relation;
    }

    public void setRelation(Double er_relation) {
        this.relation = er_relation;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "er_name='" + id + '\'' +
                ", er_first='" + first + '\'' +
                ", er_second='" + second + '\'' +
                ", er_relation=" + relation +
                '}';
    }
}
