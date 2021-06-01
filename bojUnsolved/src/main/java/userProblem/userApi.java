package userProblem;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.Closeable;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class userApi {
    JsonParser parser = new JsonParser();
    public int getPage(String json){
        JsonElement element = parser.parse(json);

        JsonElement result = element.getAsJsonObject().get("result");
        int page = result.getAsJsonObject().get("total_page").getAsInt();
        return page;
    }
    public int getCnt(String json){
        JsonElement element = parser.parse(json);

        JsonElement result = element.getAsJsonObject().get("result");
        int cnt = result.getAsJsonObject().get("total_problems").getAsInt();
        return cnt;
    }
    public String getPagesJson(String url) throws IOException {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        request.setHeader("Content-Type", "application/json");

        CloseableHttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        if(response.getStatusLine().getStatusCode()==200){
            System.out.println("connect success");
            System.out.println(response.getStatusLine().getStatusCode());
            ResponseHandler<String> handler = new BasicResponseHandler();
            String body = handler.handleResponse(response);
            return body;
        }
        else{
            System.out.println("connect fail");
            System.out.println(response.getStatusLine().getStatusCode());
            return "fail";
        }
    }
    public List<Integer> getProblems(int page, String url) throws IOException {
        List<Integer> list=new ArrayList<>();
        for(int i=1;i<=page;i++){
            String json = getPagesJson(url + Integer.toString(i));
            JsonElement element = parser.parse(json);

            JsonElement result = element.getAsJsonObject().get("result");
            JsonArray problems = result.getAsJsonObject().getAsJsonArray("problems");
            for(int j=0;j<problems.size();j++){
                int problemId = problems.get(j).getAsJsonObject().get("id").getAsInt();
                list.add(problemId);
            }
        }
        return list;
    }
}
