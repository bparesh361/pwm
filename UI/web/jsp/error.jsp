<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <head>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <title>Welcome to Promotion-Workflow</title>
    </head>

    <body>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
            <tr>
                <td>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr>
                            <td>
                                <h2>An unexpected error has Occurred..</h2>
                            </td>
                        </tr>
                        <tr><td style="height: 10px"></td></tr>
                        <tr>
                            <td>
                                <p>
                                    Please report this Error to your System Administrator.
                                    or appropriate technical support personnel.
                                    Thank you for your cooperation.
                                </p>

                            </td>
                        </tr>
                          <tr><td style="height: 10px"></td></tr>
                        <tr>
                            <td>
                                <h3>Error Message</h3>
                                <s:actionerror/>

                                <div id="error" >
                                    <%

                                                request.getAttribute("message");
                                                out.println(request.getAttribute("message"));
                                    %>
                                </div>
                            </td>
                        </tr>  <tr><td style="height: 10px"></td></tr>
                        <tr>
                            <td>

                                <p>
                                    <s:property value="%{exception.message}"/>
                                </p>
                             
                            </td>
                        </tr>
                          <tr><td style="height: 10px"></td></tr>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>
