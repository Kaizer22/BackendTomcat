import TextGenerator.Generator;
import TextGenerator.ParsingManager;
import TextGenerator.StatManager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

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

            pw.println(generator.generateText(parsingManager.getStatistics(), len));
        }

    }
}
