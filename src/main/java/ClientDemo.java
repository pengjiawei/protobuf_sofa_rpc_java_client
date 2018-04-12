import java.io.*;
import java.net.*;

/**
 * Created by 91752 on 2018/4/12.
 */
public class ClientDemo {
    public static void main(String[] args) {
        //construct request
        Service.FooRequest request = Service.FooRequest.newBuilder().setCode(1).build();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            //like serialize_to in cpp or python
            request.writeTo(byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //http request
        String url_s = "http://192.168.1.230:8421/FooService.Foo";
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(url_s);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept","application/protobuf");
            connection.setRequestProperty("User-agent","Mozilla/5.0");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Cache-Control","no-cache");
            connection.setRequestProperty("Accept-Encoding","gzip, deflate");
            connection.setRequestProperty("Content-Type","charset=UTF-8");
            connection.setRequestProperty("Accept-Language","zh-cn");

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.connect();
            OutputStream outputStream = connection.getOutputStream();
            //write request to http
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.flush();
            outputStream.close();
            if(connection.getResponseCode() == 200){
                inputStream = connection.getInputStream();
            }else{
                System.out.println("post failed code = "+connection.getResponseCode());
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
            Service.FooResponse response = Service.FooResponse.parseFrom(inputStream);
            inputStream.close();
            connection.disconnect();
            System.out.println("response = "+response.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
