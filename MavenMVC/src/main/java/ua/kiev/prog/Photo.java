package ua.kiev.prog;

import java.util.Arrays;
import java.util.Objects;

public class Photo {
    private Long id;
    private byte[] bytes;
    private String name;

    public Photo() {
    }

    public Photo(Long id, byte[] bytes, String name) {
        this.id = id;
        this.bytes = bytes;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Photo)) return false;
        Photo photo = (Photo) o;
        return Objects.equals(id, photo.id) &&
                Arrays.equals(bytes, photo.bytes) &&
                Objects.equals(name, photo.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", bytes=" + Arrays.toString(bytes) +
                ", name='" + name + '\'' +
                '}';
    }
}
