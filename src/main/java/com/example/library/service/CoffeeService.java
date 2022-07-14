package com.example.library.service;

import com.example.library.models.Coffee;
import com.example.library.models.Size;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CoffeeService {
    private List<Coffee> coffees = new ArrayList<>();

    AtomicInteger id = new AtomicInteger(0);

    public List<Coffee> findAllCoffees() {
        return coffees;
    }

    public Optional<Coffee> findOne(Integer id) {
        return coffees.stream().filter(coffee -> Objects.equals(coffee.id(), id)).findFirst();
    }

    public Coffee create(String name, Size size) {
        var coffee = new Coffee(id.incrementAndGet(), name, size);
        coffees.add(coffee);
        return coffee;
    }

    public Coffee update(Integer id, String name, Size size) {
        var updatedCoffee = new Coffee(id, name, size);
        Optional<Coffee> optional = coffees.stream().filter(c -> Objects.equals(c.id(), id)).findFirst();
        if (optional.isPresent()) {
            var coffee = optional.get();
            int index = coffees.indexOf(coffee);
            coffees.set(index, updatedCoffee);
        } else {
            throw new IllegalArgumentException("Invalid coffee");
        }
        return updatedCoffee;
    }

    public Coffee delete(Integer id) {
        var coffee = coffees.stream()
                .filter(c -> Objects.equals(c.id(), id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        coffees.remove(coffee);
        return coffee;
    }

    @PostConstruct
    private void init() {
        coffees.add(new Coffee(id.incrementAndGet(), "Americano", Size.GRANDE));
        coffees.add(new Coffee(id.incrementAndGet(), "Latte", Size.VENTI));
        coffees.add(new Coffee(id.incrementAndGet(), "Macchiato", Size.TALL));
    }
}
