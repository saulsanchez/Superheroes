package com.w2m.superheroes.data;

import com.w2m.superheroes.models.entities.Superhero;

public class DummyData {

    public static Superhero batman() {
        return new Superhero(null, "Batman");
    }

    public static Superhero superman(boolean withId) {
        return new Superhero(withId ? 1 : null, "Superman");
    }

    public static Superhero spiderman() {
        return new Superhero(null, "Spiderman");
    }

    public static Superhero superwoman(boolean withId) {
        return new Superhero(withId ? 2 : null, "Superwoman");
    }
}
