package example;

public class HelloWorld {

    public String sayTitle(String from) {
        String result = "title is " + from;
        System.out.println(result);
        return result;
    }


    public String sayBody(String Other) {
        String result = "-------------body is-------------- " + Other;
        System.out.println(result);
        return result;
    }

    public String sayAll(String title,String body) {
        String result ="--------title:"+title+ "----------------/r/nbody:--------------------------- " + body;
        System.out.println(result);
        return result;
    }
}
