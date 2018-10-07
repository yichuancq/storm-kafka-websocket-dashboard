package web.socket.demo.demain.pushInfo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 自定义标签实体
 */
@Document(collection = "socket_demo_data")
public class Data extends BaseEntity {

    @Id
    private String id;
    // 值
    @Field("value")
    private Integer value;
    // 标签名称

    @Field("area_id")
    private String areaId;

    @Field("name")
    private String name;

    public Data() {
    }
    public Data(String areaId, String name, Integer value) {
        this.value = value;
        this.areaId = areaId;
        this.name = name;
    }
    public Data(String name, Integer value) {
        this.value = value;
        this.name = name;
    }
    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "Data{" +
                "id='" + id + '\'' +
                ", value=" + value +
                ", areaId='" + areaId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
