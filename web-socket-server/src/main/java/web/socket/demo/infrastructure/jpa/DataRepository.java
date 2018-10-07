package web.socket.demo.infrastructure.jpa;

import org.springframework.data.mongodb.repository.MongoRepository;
import web.socket.demo.demain.pushInfo.Data;

public interface DataRepository extends MongoRepository<Data, String> {


}
