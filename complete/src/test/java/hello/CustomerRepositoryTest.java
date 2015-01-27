package hello;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@IntegrationTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class CustomerRepositoryTest
{
   @Autowired
   CustomerRepository repository;

   @Test
   public void testAll()
   {
              // save a couple of customers
        Customer jack =  new Customer("Jack", "Bauer");
        repository.save(jack);
        repository.save(new Customer("Chloe", "O'Brian"));

        Customer MsBower = new Customer("Kim", "Bauer");
        repository.save(MsBower);
        repository.save(new Customer("David", "Palmer"));
        repository.save(new Customer("Michelle", "Dessler"));


        assertEquals(5, repository.count());
        // fetch all customers
        Iterable<Customer> customers = repository.findAll();


        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");

       for (Customer customer : customers) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer by ID
        Customer customer = repository.findOne(1L);
        assertEquals(jack,customer);


        // fetch customers by last name
        List<Customer> bauers = repository.findByLastName("Bauer");
        assertTrue(bauers.contains(MsBower));
        assertTrue(bauers.contains(jack));

        System.out.println("Customer found with findByLastName('Bauer'):");
        System.out.println("--------------------------------------------");
        for (Customer bauer : bauers) {
            System.out.println(bauer);
        }


   }
}