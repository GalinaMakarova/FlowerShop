package repositories;

import entities.Flower;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class FlowerRepositoryTest {
    static FlowerRepository flowerRepository;

    @BeforeAll
    static void init() {
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
        //comparing all flowers from repository and from DataBase
        assertEquals(flowerRepository.findAll().size(), flowerRepository.findAllFromDB().size());
    }

    @Test
    void findAll() {
        //comparing all flowers from repository and from DataBase
        assertEquals(flowerRepository.findAll().size(), flowerRepository.findAllFromDB().size());
    }

    @Test
    void findById() {
        //creation new Flower
        String flowerName = "Rose";
        Flower flower = new Flower();
        flower.setName(flowerName);
        flowerRepository.add(flower);
        //comparing flower found from repository and from DataBase
        assertEquals(flowerRepository.findById(flower.getId()), flowerRepository.findByIdFromDB(flower.getId()));
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
        //comparing Flower.name found from repository and from DataBase
        assertEquals(flowerRepository.findById(flower.getId()), flowerRepository.findByIdFromDB(flower.getId()));
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
        assertEquals(flowerRepository.findAll().size(), flowerRepository.findAllFromDB().size());

        //deleting the Flower and changing flowers count to -1
        flowerRepository.delete(flower);
        count = count - 1;
        //checking that current count of flowers equals to "count" value
        assertEquals(count, flowerRepository.findAll().size());
        //comparing flower found from repository and from DataBase
        assertEquals(flowerRepository.findAll().size(), flowerRepository.findAllFromDB().size());
    }
}