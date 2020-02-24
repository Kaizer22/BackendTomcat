import TextGenerator.Generator;
import TextGenerator.ParsingManager;
import TextGenerator.StatManager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet (name = "generated_text",  urlPatterns = "/generated_text")

public class MarkovTextGeneratorServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");

        PrintWriter pw = resp.getWriter();

        Integer len = 0;
        Boolean Error = false;

        String appPath = req.getServletContext().getRealPath("");
        String statPath = appPath + "WEB-INF/classes/textGenerator/";
        String cssPath = req.getContextPath();


        String param_len = req.getParameter("len");

        try {
            len = Integer.parseInt(param_len);
        }
        catch (NumberFormatException e) {
            Error = true;
        }

        if (Error) {
            pw.println("error");
        }
        else {
            String author= "Tolstoy";
            ParsingManager parsingManager = new ParsingManager(statPath, author);


            StatManager statManager = new StatManager(statPath, author);

            parsingManager.setStatistics(statManager.getStats());

            Generator generator = new Generator();
            String textResult = generator.generateText(parsingManager.getStatistics(), len);

            req.setAttribute("text", textResult);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("pages/textGeneratorPage.jsp");
            requestDispatcher.forward(req, resp);

            //showGeneratedTextPage(textResult, pw, cssPath);
        }

    }

    private void showGeneratedTextPage(String text, PrintWriter pw, String cssPath){



        //cssPath = cssPath.concat("textGeneratorStyle.css");

        System.out.println(cssPath);

       pw.println( "<!DOCTYPE html>");
       pw.println("<html lang=\"ru\">");
        pw.println("<head>");
        pw.println("<meta charset=\"UTF-8\">");
                pw.println("<meta name=\"viewport\" content=\"width=device-width,minimum-scale=1,maximum-scale=10\">");
                pw.println("<title>Your title</title>");
                pw.println("<link rel=\"StyleSheet\" href=\""+cssPath+"/css/textGeneratorStyle.css\" type = \"text\\css\">");
                pw.println("</head>");
                pw.println("<body>");
                pw.println("<div class=\"content-wrapper\">");
                pw.println("<header class=\"header\">");
                pw.println("<p style=\"font-size: 30px; margin: 0; padding: 10px 1em;\">Header</p>");
                pw.println("</header>");

                pw.println("<div class=\"container clearfix\">");
                pw.println("<main class=\"content\">");
                pw.println("<p style=\"text-align: center; font-size: 30px; margin: 0; padding: 1.5em 0;\">"+text+"</p>");
                pw.println("</main>");
                pw.println("<aside class=\"sidebar sidebar1\">");
                pw.println("<p style=\"text-align: center;\">sidebar 1</p>");
                pw.println("</aside>");
                pw.println("</div>");

                pw.println("<footer class=\"footer\">");
                pw.println("<p style=\"font-size: 30px; margin: 0; padding: 10px 1em;\">Footer</p>");
                pw.println("</footer>");
                pw.println("</div>");
                pw.println("</body>");
                pw.println("</html>");
    }
}
