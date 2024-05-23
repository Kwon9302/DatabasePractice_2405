package apidb.saveDb;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.lang.model.type.ArrayType;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class BikeService {

    private final BikeRepository bikeRepository;

    public void fetchAndSaveBikes() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
        urlBuilder.append("/" + URLEncoder.encode("6b4c4e444e6b383835334a4f6e4776", "UTF-8")); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8")); /*요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/" + URLEncoder.encode("bikeList", "UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
        urlBuilder.append("/" + URLEncoder.encode("100", "UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
        // 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.

        // 서비스별 추가 요청 인자이며 자세한 내용은 각 서비스별 '요청인자'부분에 자세히 나와 있습니다.
//        urlBuilder.append("/" + URLEncoder.encode("20220301","UTF-8")); /* 서비스별 추가 요청인자들*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
        BufferedReader rd;

        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();


        JSONObject json = new JSONObject(sb.toString());
        JSONObject rentBikeStatus = json.getJSONObject("rentBikeStatus");
        JSONArray rowArray = rentBikeStatus.getJSONArray("row");

        // 첫 번째 객체의 "rackTotCnt" 키 값 읽기
        for (int i = 0; i < rowArray.length(); i++) {
            JSONObject row = rowArray.getJSONObject(i);

            BikeDB bikeDB = new BikeDB();
//            bikeDB.setStationId(row.getString("stationId"));
            bikeDB.setStationName(row.getString("stationName"));
            bikeDB.setRackTotCnt(row.getInt("rackTotCnt"));
            bikeDB.setParkingBikeTotCnt(row.getInt("parkingBikeTotCnt"));
            bikeDB.setShared(row.getInt("shared"));
//            bikeDB.setStationLatitude(row.getDouble("stationLatitude"));
//            bikeDB.setStationLongitude(row.getDouble("stationLongitude"));

            bikeRepository.save(bikeDB);
        }
//        return bikeRepository.saveAll(sb);
//        System.out.println(urlBuilder.toString());
    }
}