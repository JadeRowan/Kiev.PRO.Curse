package kiev.prog.ua;

import javax.persistence.*;

//Колонки:
//id,
//Имя Счета,
//Владелец и/или id_Владельца
//Тип
//Сума на счету
@Entity
@Table(name="Accounts")
public class Account{
    //Связано с транзакциями
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    //Связано с клиентами
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name="type", nullable = false)
    private String type;

    @Column(name="balance", nullable = false)
    private Double balanse;

    public Account() {}

    public Account(Client client, String type) {
        this.client = client;
        this.type = type;
        this.balanse = 0.0;
        client.addAccount(this);

    }

    public synchronized boolean checkBalance(Double Money){
        if(balanse >= Money)
            return true;
        return false;
    }

    public synchronized void addMoney(Double Money) throws NullPointerException{
        if(Money == null){
            throw new NullPointerException();
        }
        if (Money < 0.0){
            Money = 0.0;
        }
        this.balanse += Money;
    }

    public synchronized void withdrowMoney(Double Money) throws NullPointerException{
        if(Money == null){
            throw new NullPointerException();
        }
        if (Money < 0.0){
            Money = 0.0;
        }
        this.balanse -= Money;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getBalanse() {
        return balanse;
    }

    public void setBalanse(Double balanse) {
        this.balanse = balanse;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", client=" + client.getName() +
                ", type='" + type + '\'' +
                ", balanse=" + balanse +
                '}';
    }
}
