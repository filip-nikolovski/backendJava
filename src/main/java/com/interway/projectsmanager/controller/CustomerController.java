package com.interway.projectsmanager.controller;

import com.interway.projectsmanager.dao.CustomerDAO;
import com.interway.projectsmanager.model.Customer;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NikolovskiF on 27.05.2015.
 */

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    //Get the spring context
    ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("Spring-Module.xml");
    CustomerDAO customerDAO = ctx.getBean("customerDAO", CustomerDAO.class);


    @RequestMapping("/")
    public String customer(){
        return "Hello hero!";
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Customer customerById(@PathVariable("id") int id){
        Customer customer = customerDAO.finByCustomerId(id);
        return customer;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Customer insert(@RequestBody Customer cust){



        try {
            customerDAO.insert(cust);
//          new  ResponseEntity<Customer>(cust, HttpStatus.OK);
            System.out.println("creatre ok");
        }catch (Exception e){

//           new ResponseEntity<Customer>(cust, HttpStatus.BAD_REQUEST);
            System.out.println("creatre not ok");
            e.printStackTrace();
        }

        return cust;
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Customer updateCustomer(@RequestBody Customer customer){

        try{
            customerDAO.update(customer);
            return customer;
        }catch (Exception e){
            System.out.println("User not updated!");
            e.printStackTrace();
        }

        return customer;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deleteCustoomer(@PathVariable("id") int id){
        customerDAO.delete(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Customer> getAllCustomers(){


        return customerDAO.getAll();
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){


       // String json = getSimpleJsonData();

        RestTemplate restTemplate = new RestTemplate();
     /*   private List<Customer> customers;
        Customer cust = restTemplate.getForObject("http://localhost:8080/api/customer/all", Customer.class);*/



        ResponseEntity<Customer[]> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/customer/all", Customer[].class);
        Customer[] test= responseEntity.getBody();
        MediaType contentType = responseEntity.getHeaders().getContentType();
        HttpStatus statusCode = responseEntity.getStatusCode();
        List<Object> objects = new ArrayList<Object>();


//        List<LinkedHashMap> emps = restTemplate.getForObject("http://localhost:8080/api/customer/all", List.class);

//        test[0].setSurname("testSurname");
        if (statusCode == HttpStatus.OK){
            for (int i=0; i<test.length; i++){
                try{
                    customerDAO.update(test[i]);
                    //return customer;
                }catch (Exception e){
                    System.out.println("User not updated!");
                    e.printStackTrace();
                }
            }
        }
        return contentType+", "+statusCode+", "+test[1];
    }

    //Close Spring Context
//    ctx.close();
}
