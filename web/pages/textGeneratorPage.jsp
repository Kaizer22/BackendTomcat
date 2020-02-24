<%--
  Created by IntelliJ IDEA.
  User: denis
  Date: 24.02.2020
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1,maximum-scale=10">
    <title>Какой-то текст</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/textGeneratorStyle.css">
</head>
<body>
<div class="content-wrapper">
    <header class="header">
        <p style="font-size: 30px; margin: 0; padding: 10px 1em;">Текст сгенерирован методом цепей Маркова на основе произведений Л. Н. Толстого</p>
    </header>

    <div class="container clearfix">
        <main class="content">
            <!-- for-example -->
            <p style="text-align: center; font-size: 30px; margin: 0; padding: 1.5em 0;">
                <%
                    if (request.getAttribute("text") != null){
                        out.println(request.getAttribute("text"));
                    }
                %>
            </p>
        </main>
        <aside class="sidebar sidebar1">
            <p style="text-align: center;">

            </p>
        </aside>
    </div>

    <footer class="footer">
        <p style="font-size: 30px; margin: 0; padding: 10px 1em;">2020</p>
    </footer>
</div>
</body>
</html>
