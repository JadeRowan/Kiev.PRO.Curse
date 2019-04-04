package kiev.prog.ua;

import javax.persistence.*;
import java.util.*;

public class App {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            // create connection
            emf = Persistence.createEntityManagerFactory("JPAd");
            em = emf.createEntityManager();
            try {
                while (true) {
                    System.out.println("1: add dish");
                    System.out.println("2: view dish set \"Filter by price\"");
                    System.out.println("3: view dish set \"Only with discount\"");
                    System.out.println("4: view dish set \"< 1kilo\"");
                    System.out.println("5: view all dishes");
                    System.out.print("-> ");

                    String s = sc.next(); //Не nextLine из за странной дичи с filterByPrice();
                    switch (s) {
                        case "1":
                            addDish(sc);
                            break;
                        case "2":
                            filterByPrice(sc);
                            break;
                        case "3":
                            viewDiscounts();
                            break;
                        case "4":
                            viewKiloSet();
                            break;
                        case "5":
                            viewDishes();
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
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }



    private static void addDish(Scanner sc) {
        System.out.print("Enter dish name: ");
        String name = sc.next();
        System.out.print("Enter dish price: ");
        String sPrice = sc.next();
        Double price = Double.parseDouble(sPrice);
        System.out.print("Enter dish weight: ");
        String sWeight = sc.next();
        Double weight = Double.parseDouble(sWeight);
        System.out.print("Enter dish discount(in percents): ");
        String sDiscount = sc.next();
        Integer discount = null;
        try {
            discount = Integer.parseInt(sDiscount);
        }catch (NumberFormatException e){
           ;
        }
        em.getTransaction().begin();
        try {
            Dish c = new Dish(name, price, weight, discount);
            em.persist(c);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void filterByPrice(Scanner sc) {
        System.out.println("Input min price:");
        Double min = sc.nextDouble();
        System.out.println("Input max price:");
        Double max = sc.nextDouble();
        Query query = em.createQuery("SELECT c FROM Dish c WHERE c.price >= :min AND c.price <= :max", Dish.class);
        query.setParameter("min", min);
        query.setParameter("max", max);
        List<Dish> list = (List<Dish>) query.getResultList();

        for (Dish c : list)
            System.out.println(c);
    }

    private static void viewDiscounts() {
        Query query = em.createQuery("SELECT c FROM Dish c WHERE c.disсount <> null", Dish.class);
        List<Dish> list = (List<Dish>) query.getResultList();

        for (Dish c : list)
            System.out.println(c);
    }

    private static void viewKiloSet() {
        Query query = em.createQuery("SELECT c FROM Dish c", Dish.class);
        List<Dish> inputList = (List<Dish>) query.getResultList();
        Collections.sort(inputList);
        List<Dish> resultList = new ArrayList<>();
        for (Dish c : inputList) {
            double weight = c.getWeight();
            if(weight < 1.0 && weight < (1.0 - dishWeightSum(resultList))){
                resultList.add(c);
            }
        }
        for (Dish c : resultList){ System.out.println(c);}

        System.out.println("The weight sum is: "+dishWeightSum(resultList));

    }

    private static double dishWeightSum(List<Dish> list){
        double sum = 0;
        for (Dish d: list) {
            sum +=d.getWeight();
        }
        return  sum;
    }


    private static void viewDishes() {
        Query query = em.createQuery("SELECT c FROM Dish c", Dish.class);
        List<Dish> list = (List<Dish>) query.getResultList();

        for (Dish c : list)
            System.out.println(c);
    }


}


