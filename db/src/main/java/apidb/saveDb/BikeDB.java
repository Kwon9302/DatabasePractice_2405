package apidb.saveDb;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BikeDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    private int rackTotCnt;
    private String stationName;
    private int parkingBikeTotCnt;
    private int shared;
//    private double stationLatitude;
//    private double stationLongitude;
// //    private String stationId;
// Entity에서 선언한 값들을 기준으로 save된다.
// => a:1, b:2, c:3의 json 데이터를 불러오고 save할때 entity에 
// private int a;
// private int b;
// 만 선언되어있다면 a,b의 값만 읽어온다.
// But, sql column은 생성되어 있어야 한다. (자동생성x)
}
