import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import proto_generated.ControlServiceOuterClass;
import proto_generated.MapServiceOuterClass;

import java.io.IOException;

/**
 * Created by 91752 on 2018/4/16.
 */
public class OkHttpClientDemo {
    public static void main(String[] args) {
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
        String url = "http://192.168.1.230:8421/MapService.GetMap";
        url = "http://192.168.1.105:8080/mymap";

        ControlServiceOuterClass.ControlCode code = ControlServiceOuterClass.ControlCode.newBuilder().setOptCode(0).build();
//        RequestBody body;RequestBody.create()
        Request request = new Request.Builder().url(url)
                .addHeader("Accept","application/protobuf")

                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
//            ControlServiceOuterClass.ErrorCode errorCode = ControlServiceOuterClass.ErrorCode.parseFrom(response.body().bytes());
//            System.out.println("error code = "+errorCode.getErrorCode());

            MapServiceOuterClass.Map map = MapServiceOuterClass.Map.parseFrom(response.body().bytes());
            System.out.println("map size = "+map.getMapCellCount());
            for (int i = 0; i < map.getMapCellCount() ;++i){
                System.out.println("x = "+map.getMapCell(i).getX()+" y = "+map.getMapCell(i).getY()+" value = "+getValue(map.getMapCell(i).getValue()));
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    static String getValue(MapServiceOuterClass.MapCell.Value value){
        if (value == MapServiceOuterClass.MapCell.Value.FREE)
            return "FREE";
        else if(value == MapServiceOuterClass.MapCell.Value.OBSTACLE)
            return "OBSTACLE";
        else
            return "UNKNOWN";
    }
}
