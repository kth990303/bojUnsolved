import userProblem.userApi;

import java.io.IOException;
import java.util.List;

public class HelloMain {
    public static void main(String[] args) throws IOException {
        String userName="hiha";
        String URL
                ="https://api.solved.ac/v2/search/problems.json?query=solved_by:"+userName+"&page=";

        userApi userApi=new userApi();
        String pagesJson = userApi.getPagesJson(URL);
        int page = userApi.getPage(pagesJson);
        int cnt = userApi.getCnt(pagesJson);
        List<Integer> problemsList = userApi.getProblems(page, URL);
        System.out.println(userName+"'s solve problems ': "+cnt+" questions");
        for (int i=0;i<problemsList.size();i++) {
            System.out.print(problemsList.get(i)+" ");
            if(i%15==14)
                System.out.println();
        }
    }
}
