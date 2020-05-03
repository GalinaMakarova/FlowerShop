package repositories;

import entities.Flower;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.DBConnection;
import utils.HibernateUtil;

import static org.junit.Assert.assertEquals;

class FlowerRepositoryTest {
    static DBConnection dbConnection;
    static FlowerRepository flowerRepository;

    @BeforeAll
    static void init() {
        dbConnection = new DBConnection();
        dbConnection.getConnection();
        flowerRepository = new FlowerRepository();
    }

    //new flower creation without any association validations
    @Test
    void addFlowerOnly() {
        //getting current count of flowers
        int count = flowerRepository.findAll().size();
        //saving a new one
        String flowerName = "Rose";
        Flower flower = new Flower();
        flower.setName(flowerName);
        flowerRepository.add(flower);
        //checking that flowers count has been changed to count+1
        assertEquals(count + 1, flowerRepository.findAll().size());
        Session session = HibernateUtil.getSessionFactory().openSession();
        //comparing all flowers from repository and from DataBase
        assertEquals(flowerRepository.findAll().size(), session.createNamedQuery("Flower.findAll", Flower.class).getResultList().size());
        session.close();
    }

    @Test
    void findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        //comparing all flowers from repository and from DataBase
        assertEquals(flowerRepository.findAll().size(), session.createNamedQuery("Flower.findAll", Flower.class).getResultList().size());
        session.close();
    }

    @Test
    void findById() {
        //creation new Flower
        String flowerName = "Rose";
        Flower flower = new Flower();
        flower.setName(flowerName);
        flowerRepository.add(flower);
        //comparing flower found from repository and from DataBase
        Session session = HibernateUtil.getSessionFactory().openSession();
        assertEquals(flowerRepository.findById(flower.getId()), session.createNamedQuery("Flower.findById", Flower.class).setParameter("id", flower.getId()).getSingleResult());
        session.close();
    }

    @Test
    void updateFlowerOnly() {
        //creation new Flower
        String flowerName = "Rose";
        Flower flower = new Flower();
        flower.setName(flowerName);
        flowerRepository.add(flower);
        //updating Flower.name and saving changes
        flowerName = "Gerbera";
        flower.setName(flowerName);
        flowerRepository.update(flower);
        Long id = flower.getId();
        //comparing Flower.name found from repository and from DataBase
        Session session = HibernateUtil.getSessionFactory().openSession();
        assertEquals(flowerRepository.findById(id).getName(), session.createNamedQuery("Flower.findById", Flower.class).setParameter("id", flower.getId()).getSingleResult().getName());
        session.close();
    }

    @Test
    void deleteFlowerOnly() {
        //getting current count of countries
        int count = flowerRepository.findAll().size();
        //creation new Flower
        String flowerName = "Rose";
        Flower flower = new Flower();
        flower.setName(flowerName);
        flowerRepository.add(flower);
        //changed country count to +1
        count = count + 1;
        //checking that count+1 is current count of flowers
        assertEquals(count, flowerRepository.findAll().size());
        //comparing flower found from repository and from DataBase
        Session session = HibernateUtil.getSessionFactory().openSession();
        assertEquals(flowerRepository.findAll().size(), session.createNamedQuery("Flower.findAll", Flower.class).getResultList().size());
        session.close();

        //deleting the Flower and changing flowers count to -1
        flowerRepository.delete(flower);
        count = count - 1;
        //checking that current count of flowers equals to "count" value
        assertEquals(count, flowerRepository.findAll().size());
        //comparing flower found from repository and from DataBase
        session = HibernateUtil.getSessionFactory().openSession();
        assertEquals(flowerRepository.findAll().size(), session.createNamedQuery("Flower.findAll", Flower.class).getResultList().size());
        session.close();
    }
}