package kiev.prog.ua;

import javax.persistence.*;
import java.util.Date;

//Колонки:
//id,
//id_Пользователь
//Номер счета1
//Номер счета2
//id_Курса валют
//Время транзакции
@Entity
@Table(name="Transactions")
public class Transaction {
    //Связаны с юзерами

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    //Связано с колонкой names в cliente
    @ManyToOne
    @JoinColumn(name = "clients_id")
    private Client client;

    @OneToOne
    @JoinColumn(name = "accounts_id")
    private Account fAccount;

    @OneToOne
    @JoinColumn(name = "accounts_id", insertable= false, updatable= false)
    private Account sAccount;

    @OneToOne
    @JoinColumn(name = "exchangeRates_id")
    private ExchangeRate exchangeRate;

    private Double money;

    private Boolean withdrow;

    private Date time;

    public Transaction() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Account getfEr_fAccount() {
        return fAccount;
    }

    public void setfEr_fAccount(Account fAccount) {
        this.fAccount = fAccount;
    }

    public Account getsEr_sAccount() {
        return sAccount;
    }

    public void setsEr_sAccount(Account sAccount) {
        this.sAccount = sAccount;
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Boolean getWithdrow() {
        return withdrow;
    }

    public void setWithdrow(Boolean withdrow) {
        this.withdrow = withdrow;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Transaction(Client client, Account fAccount, Account sAccount, ExchangeRate exchangeRate, Double money){
        this.client = client;
        this.fAccount = fAccount;
        this.sAccount = sAccount;
        this.exchangeRate = exchangeRate;
        this.money = money;
        this.time = new Date();
    }

    public Transaction(Client client, Account fAccount, Double money, Boolean withdrow){
        this.client = client;
        this.fAccount = fAccount;
        this.money = money;
        this.withdrow = withdrow;
        this.time = new Date();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", client=" + client +
                ", fAccount=" + fAccount +
                ", sAccount=" + sAccount +
                ", exchangeRate=" + exchangeRate +
                ", money=" + money +
                ", withdrow=" + withdrow +
                ", time=" + time +
                '}';
    }
}
