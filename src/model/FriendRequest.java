package model;

import java.io.Serializable;
import java.util.Objects;

public class FriendRequest implements Serializable {

    private final int id;
    private final String name;

    public FriendRequest(int id, String name) {

        this.id = id;
        this.name = name;

    }

    public int getId() {

        return this.id;

    }

    public String getName() {

        return this.name;

    }

    @Override
    public int hashCode() {

        return Objects.hash(this.id);

    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {

            return true;

        }

        if (o == null || getClass() != o.getClass()) {

            return false;

        }

        FriendRequest that = (FriendRequest) o;

        return this.name.equals(that.name);

    }

    @Override
    public String toString() {

        return "Friend request from: " + this.name;

    }

}
