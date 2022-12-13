package com.w2m.superheroes.data;

import com.w2m.superheroes.model.entities.Superheroe;

public class DummyData {

    public static Superheroe batman() {
        return new Superheroe (null, "Batman");
    }

    public static Superheroe superman(boolean withId) {
        return new Superheroe(withId ? 1 : null, "Superman");
    }

    public static Superheroe spiderman() {
        return new Superheroe(null, "Spiderman");
    }

    public static Superheroe superwoman(boolean withId) {
        return new Superheroe (withId ? 2 : null, "Superwoman");
    }
}
