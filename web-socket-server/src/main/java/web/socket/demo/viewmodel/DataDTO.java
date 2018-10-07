package web.socket.demo.viewmodel;

import java.io.Serializable;

public class DataDTO implements Serializable {

    private Integer value;
    // 标签名称
    private String name;

    public DataDTO(String name, Integer value) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DataDTO{" +
                "value=" + value +
                ", name='" + name + '\'' +
                '}';
    }
}
