package data;

import java.util.Date;
import java.util.Objects;

public class Child extends Base {
    private String chileName;
    private Integer age;

    public Child() { }

    public Child(String chileName, Integer age) {
        this.chileName = chileName;
        this.age = age;
    }

    public Child(Long id, String name, Date date, String chileName, Integer age) {
        super(id, name, date);
        this.chileName = chileName;
        this.age = age;
    }

    public String getChileName() {
        return chileName;
    }

    public void setChileName(String chileName) {
        this.chileName = chileName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Child child = (Child) o;
        return Objects.equals(chileName, child.chileName) &&
                Objects.equals(age, child.age);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), chileName, age);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Child{");
        sb.append("chileName='").append(chileName).append('\'');
        sb.append(", age=").append(age);
        sb.append(", id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }
}
