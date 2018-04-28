import proto_generated.*;

import java.io.*;
import java.net.*;

/**
 * Created by 91752 on 2018/4/12.
 */
public class ClientDemo {
    public static void main(String[] args) {
        //construct request
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ControlServiceOuterClass.ControlCode code = ControlServiceOuterClass.ControlCode.newBuilder().setOptCode(1).build();


        try {
            //like serialize_to in cpp or python
//            request.writeTo(byteArrayOutputStream);

            code.writeTo(byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String url_s = "http://192.168.215.131:8080/query";
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));
        try {
            URL url = new URL(url_s);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/x-protobuf");

            //not necessary below
//            connection.setRequestProperty("User-agent","Mozilla/5.0");
//            connection.setRequestProperty("Connection", "Keep-Alive");
//            connection.setRequestProperty("Cache-Control","no-cache");
//            connection.setRequestProperty("Accept-Encoding","gzip, deflate");
//            connection.setRequestProperty("Content-Type","application/x-protobuf");
//            connection.setRequestProperty("Accept-Language","zh-cn");

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            System.out.println("url = " + connection.getURL());
            System.out.println("get post = " + connection.getRequestMethod());
            connection.connect();
//            OutputStream outputStream = connection.getOutputStream();
//            //write request to http
//            outputStream.write(byteArrayOutputStream.toByteArray());
//            outputStream.flush();
//            outputStream.close();
            System.out.println("response code = " + connection.getResponseCode());
            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
            } else {
                System.out.println("post failed code = " + connection.getResponseCode());
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //http response
        try {
//            MapServiceOuterClass.Map map = MapServiceOuterClass.Map.parseFrom(inputStream);
//            System.out.println("map cell size = "+map.getMapCellCount());

//            ControlServiceOuterClass.ErrorCode errorCode = ControlServiceOuterClass.ErrorCode.parseFrom(inputStream);
//            System.out.println("error code = "+errorCode.getErrorCode());

            Query.QueryInfo info = Query.QueryInfo.parseFrom(inputStream);
            System.out.println("info = " + info.getCleaningTime() + " " + info.getRemainingCapacity());

//            PoseOuterClass.Pose pose = PoseOuterClass.Pose.parseFrom(inputStream);
//            System.out.println("x = "+pose.getX()+" y = "+pose.getY());

            inputStream.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
