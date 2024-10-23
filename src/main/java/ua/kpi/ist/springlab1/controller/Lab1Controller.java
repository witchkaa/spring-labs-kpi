package ua.kpi.ist.springlab1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Lab1Controller {
    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "Hello, world!";
    }
    @GetMapping("/aboutus")
    @ResponseBody
    public String info() {
        return """
                <!DOCTYPE html>
                <html>
                <body>
               
                <h1>About me</h1>
                <p>My name is Victoria. I'm 19 y.o. and I study at KPI.</p> 
                <p>My hobbies:</p>
                <ul>
                <li>travelling</li>
                <li>beach volleyball</li>
                <li>cooking</li>
                <li>gym</li>
                </ul>
                </body>
                </html>""";
    }
}
