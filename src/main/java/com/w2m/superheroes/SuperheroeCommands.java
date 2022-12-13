package com.w2m.superheroes;

import com.w2m.superheroes.model.entities.Superheroe;
import com.w2m.superheroes.services.contracts.SuperheroeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SuperheroeCommands implements CommandLineRunner {

    @Autowired
    private SuperheroeDAO service;

    @Override
    public void run(String... args) throws Exception {
        /*Superheroe batman = new Superheroe(null, "Batman");
        service.save(batman);
        Superheroe superman = new Superheroe(null, "Superman");
        service.save(superman);
        Superheroe spiderman = new Superheroe(null, "Spiderman");
        service.save(spiderman);
        Superheroe manolitoElFuerte = new Superheroe(null, "Manolito el fuerte");
        service.save(manolitoElFuerte);*/

        /*Iterable<Superheroe> superheroes = service.findAll();
        superheroes.forEach(System.out::println);*/

        /*Optional<Superheroe> superheroe = service.findById(4);
        superheroe.get().setName("SuperWoman");
        service.save(superheroe.get());
        System.out.println(superheroe.get().toString());*/

//        service.deleteById(6);

        Iterable<Superheroe> superheroes = service.findSuperheroesByNameContainsIgnoreCase("super");

        superheroes.forEach(System.out::println);
    }
}
