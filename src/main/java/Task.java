import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Task {
    private static WebDriver driver;

    public static void main(String[] args) {
        setupWeb();
        convert();
        closeWeb();
    }

    public static void convert() throws NullPointerException {

        JDialog.setDefaultLookAndFeelDecorated(true);

        Map<String, Double> kurs = new HashMap<>();
        kurs.put("$", valute());
        String[] selectionValues = {"$", "Руб"};
        String initialSelection = "Currency";
        String selection = (String) JOptionPane.showInputDialog(null, "Какая валюта вам нужна?",
                "Обмен валют", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        String userCurrency = (String) JOptionPane.showInputDialog(null, "Какую валюту предложите?",
                "Обмен валют", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);

        Double moneyDollar = kurs.get("$");
        double amount = Double.parseDouble(JOptionPane.showInputDialog("Сколько " + userCurrency + " вы собираетесь обменять?"));
        if(selection.equals(userCurrency)){
            JOptionPane.showMessageDialog(null, "При конвертации" + amount + userCurrency + " в " + selection + " Вы получите "  + amount + selection);
        }else {
            switch (selection) {
                case "$": {
                    JOptionPane.showMessageDialog(null, "При конвертации" + amount + userCurrency + " в " + selection + " Вы получите " + (amount / moneyDollar) + selection);
                    break;
                }
                case "Руб": {
                    JOptionPane.showMessageDialog(null, "При конвертации" + amount + userCurrency + " в " + selection + " Вы получите " + (moneyDollar * amount) + selection);
                    break;
                }
            }
        }
    }

    public static void closeWeb() {
        driver.close();
        driver.quit();
    }

        public static void setupWeb() {
            try {
                System.setProperty("webdriver.chrome.driver", "chromedriverDesktop.exe");
                driver = new ChromeDriver();
                driver.get("http://www.cbr.ru/scripts/XML_daily.asp");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public static Double valute() {
            WebElement dollar = driver.findElement(By.cssSelector("#collapsible11 > div.expanded > div.collapsible-content > div:nth-child(5) > span.text"));
            String dollarText = dollar.getText();
            Double getDollar = Double.parseDouble(dollarText.replace(",", "."));
            return getDollar;
        }
    }
