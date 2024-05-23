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
//    private String stationId;

}
