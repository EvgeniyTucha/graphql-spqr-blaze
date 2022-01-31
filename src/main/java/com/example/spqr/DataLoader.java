package com.example.spqr;

import com.example.spqr.model.Alias;
import com.example.spqr.model.Cat;
import com.example.spqr.model.Kitten;
import com.example.spqr.repository.AliasRepository;
import com.example.spqr.repository.CatRepository;
import com.example.spqr.repository.KittenRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final CatRepository catRepository;
    private final KittenRepository kittenRepository;
    private final AliasRepository aliasRepository;

    public DataLoader(CatRepository catRepository, KittenRepository kittenRepository, AliasRepository aliasRepository) {
        this.catRepository = catRepository;
        this.kittenRepository = kittenRepository;
        this.aliasRepository = aliasRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final Cat cat1 = Cat.builder().name("Alex").description("Bad cat").build();
        final Cat cat2 = Cat.builder().name("Bern").description("Good cat").build();
        catRepository.save(cat1);
        catRepository.save(cat2);
        Alias aliasCat1 = Alias.builder().alias("Alexandre").cat(cat1).build();
        Alias aliasCat12 = Alias.builder().alias("Alexandros").cat(cat1).build();
        Alias aliasCat2 = Alias.builder().alias("Bernie").cat(cat2).build();
        aliasRepository.save(aliasCat1);
        aliasRepository.save(aliasCat12);
        aliasRepository.save(aliasCat2);
        kittenRepository.save(Kitten.builder().name("Alex-1").cat(cat1).build());
        kittenRepository.save(Kitten.builder().name("Alex-2").cat(cat1).build());

        kittenRepository.save(Kitten.builder().name("Bern-1").cat(cat2).build());
        kittenRepository.save(Kitten.builder().name("Bern-2").cat(cat2).build());
    }
}
