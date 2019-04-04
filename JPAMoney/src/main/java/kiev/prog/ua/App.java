package kiev.prog.ua;

import javax.persistence.*;
import java.util.*;

public class App {
    static EntityManagerFactory emf;
    static EntityManager em;
    static Client jack;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            // create connection
            emf = Persistence.createEntityManagerFactory("JPAm");
            em = emf.createEntityManager();
            jack = simylateBase();
            try {
                while (true) {
                    System.out.println("You login like \"Jack\"");
                    System.out.println("1: view all clients()");
                    System.out.println("2: view all Accounts()");
                    System.out.println("3: view all ExchangeRates()");
                    System.out.println("4: add money to Account");
                    System.out.println("5: withdrow money from account");
                    System.out.println("6: transfer money between accounts");

                    System.out.print("-> ");

                    String s = sc.next(); //Не nextLine из за странной дичи с filterByPrice();

                    switch (s) {
                        case "1":
                            viewClients();
                            break;
                        case "2":
                            viewAccounts();
                            break;
                        case "3":
                            viewExchangeRates();
                            break;
                        case "4":
                            addMoney(sc);
                            break;
                        case "5":
                            wythdrowMoney(sc);
                            break;
                        case "6":
                            transferMoney(sc);
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                em.close();
                emf.close();
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    private static Client simylateBase() {
        em.getTransaction().begin();
        Client bob = new Client("Bob");
        Client jack = new Client("Jack");
        Client marla = new Client("Marla");
        Client tailer = new Client("Tailer");

        try {
            em.persist(bob);
            em.persist(jack);
            em.persist(marla);
            em.persist(tailer);
            em.persist(new Account(bob, "EUR"));
            em.persist(new Account(bob, "EUR"));
            em.persist(new Account(jack, "UAH"));
            em.persist(new Account(jack, "USD"));
            em.persist(new Account(marla, "EUR"));
            em.persist(new Account(marla, "UAH"));
            em.persist(new Account(tailer, "USD"));
            em.persist(new Account(tailer, "EUR"));
            em.persist(new ExchangeRate("EUR","USD", 1.2));
            em.persist(new ExchangeRate("EUR","UAH", 28.2));
            em.persist(new ExchangeRate("USD","EUR", 0.8));
            em.persist(new ExchangeRate("USD","UAH", 26.4));
            em.persist(new ExchangeRate("UAH","USD", 0.037));
            em.persist(new ExchangeRate("UAH","EUR", 0.033));
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!");
            ex.printStackTrace();
        }
        return jack;
    }

    private static void viewClients() {
        Query query = em.createQuery("SELECT c FROM Client c", Client.class);
        List<Client> list = (List<Client>) query.getResultList();

        for (Client c : list) {
            System.out.println(c);
        }
    }

    private static void viewAccounts() {
        Query query = em.createQuery("SELECT c FROM Account c", Account.class);
        List<Account> list = (List<Account>) query.getResultList();

        for (Account c : list) {
            System.out.println(c);
        }
    }

    private static void viewExchangeRates() {
        Query query = em.createQuery("SELECT c FROM ExchangeRate c", ExchangeRate.class);
        List<ExchangeRate> list = (List<ExchangeRate>) query.getResultList();

        for (ExchangeRate c : list) {
            System.out.println(c);
        }
    }

    private static void addMoney(Scanner sc) {
        System.out.println("Input account id");
        long accId = sc.nextLong();
        System.out.println("Input money value");
        String sMoney = sc.next();
        Double money = Double.parseDouble(sMoney);

        try {
            Query query = em.createQuery("SELECT c FROM Account c WHERE c.id = :id", Account.class);
            query.setParameter("id", accId);
            Account acc = (Account) query.getSingleResult();
            acc.addMoney(money);
            Transaction t = new Transaction(jack, acc, money, false);
            jack.addTransaction(t);
        } catch (NullPointerException e) {
            System.err.println("Wrong input");
            return;
        }catch (NoResultException e){
            System.err.println("There no such ID");
            return;
        }
        System.out.println("Operation succes");
    }

    private static void wythdrowMoney(Scanner sc) {
        System.out.println("Input account id");
        long accId = sc.nextLong();
        System.out.println("Input money value");
        String sMoney = sc.next();
        Double money = Double.parseDouble(sMoney);

        try{
            Query query = em.createQuery("SELECT c FROM Account c WHERE c.id = :id", Account.class);
            query.setParameter("id", accId);
            Account acc = (Account) query.getSingleResult();
            acc.withdrowMoney(money);
            Transaction t = new Transaction(jack, acc, money, true);
            jack.addTransaction(t);
        }catch (NullPointerException e) {
            System.err.println("Wrong input");
            return;
        }catch (NoResultException e){
            System.err.println("There no such ID");
            return;
        }
        System.out.println("Operation succes");
    }

    public static void transferMoney(Scanner sc) throws NoResultException{
        System.out.println("Input first account id");
        String strAccOne = sc.next();
        long fAccount = Long.parseLong(strAccOne);

        Query firstQuery = em.createQuery("SELECT acc FROM Account acc WHERE acc.id = :id", Account.class);
        firstQuery.setParameter("id", fAccount);
        Account accOne = (Account) firstQuery.getSingleResult();

        System.out.println("Input second account id");
        String strAccTwo = sc.next();
        long sAccount = Long.parseLong(strAccTwo);

        Query seconfQuery = em.createQuery("SELECT acc FROM Account acc WHERE acc.id = :id", Account.class);
        seconfQuery.setParameter("id", sAccount);
        Account accTwo = (Account) seconfQuery.getSingleResult();

        System.out.println("Input money value");
        String sMoney = sc.next();
        Double money = Double.parseDouble(sMoney);

            accOne.withdrowMoney(money);

        ExchangeRate eRate = null;
            if (!accOne.getType().equals(accTwo.getType())) {
                Query rateQuery = em.createQuery("SELECT er FROM ExchangeRate er WHERE er.first = :from AND er.second = :to", ExchangeRate.class);
                rateQuery.setParameter("from", accOne.getType());
                rateQuery.setParameter("to", accTwo.getType());
                eRate = (ExchangeRate) rateQuery.getSingleResult();
                money = eRate.calculate(money);
            }

            accTwo.addMoney(money);
            Transaction t = new Transaction(jack, accOne, accTwo, eRate, money);
            jack.addTransaction(t);

    }
}


